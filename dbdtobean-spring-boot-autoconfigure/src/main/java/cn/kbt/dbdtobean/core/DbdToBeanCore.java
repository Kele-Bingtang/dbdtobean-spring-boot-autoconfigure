package cn.kbt.dbdtobean.core;

import cn.kbt.dbdtobean.config.DbdToBeanProperties;
import cn.kbt.dbdtobean.mvcbean.DbdToMvcDefinition;
import cn.kbt.dbdtobean.utils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.HashMap;

public abstract class DbdToBeanCore {
    private static final Logger logger = LoggerFactory.getLogger(DbdToBeanCore.class);
    /**
     * 数据库信息
     **/
    private ResultSetMetaData jdbcData = null;
    /**
     * 生成的文件名字
     **/
    private String createBeanName;

    private final String oneLineAndOneTab = BeanUtils.getNT(1, 1);
    private final String oneLineAndTwoTab = BeanUtils.getNT(1, 2);
    private final String oneLineAndFourTab = BeanUtils.getNT(1, 4);
    private final String oneTab = BeanUtils.getT(1);
    private final String twoTab = BeanUtils.getT(2);
    private final String oneLine = BeanUtils.getN(1);
    private final String twoLine = BeanUtils.getN(2);

    protected DbdToBeanCore() {
    }

    /**
     * 1、生成 JavaBean 的属性值
     * 2、是否构造函数
     * 3、是否生成 setter 和 getter 语句
     * 4、是否重写 toString 方法
     *
     * @param tableName     表名
     * @param isConstructor 是否生成构造器
     * @param isSetAndGet   是否生成 setter 和 getter 方法
     * @param isToString    是否生成 toString 方法
     * @return 内容
     * @throws SQLException SQL 异常
     */
    protected String createBeanFromTable(String tableName, boolean isConstructor, boolean isSetAndGet, boolean isToString) throws SQLException {
        DbdToBeanProperties dbdToBeanProperties = DbdToBeanContext.getDbdToBeanProperties();
        if (tableName == null) {
            logger.info("请输入要导出的表名或者数据库名");
            return null;
        }
        String sql = "select * from `" + tableName + "`";
        PreparedStatement stmt = getConnection().prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        // 通过结果集获取数据库信息
        jdbcData = rs.getMetaData();
        // 核心，存储 JavaBean 文件内容的缓冲区
        StringBuilder sb = new StringBuilder();
        // 判断使用什么数据库
        parseDataBaseTypeAndGetSql("");
        // 获取定义的信息
        DbdToBeanDefinition definition = DbdToBeanContext.getDbdToBeanDefinition();
        createBeanName = definition.getCreateBeanName();
        // 是否生成类注释，格式为 @author @since @version  
        if (DbdToBeanContext.getDefaultComment().isSetHeadComment() && BeanUtils.isEmpty(definition.getHeadComment().getHeadComments().toString())) {
            definition.getHeadComment().generateHeadComments(dbdToBeanProperties.getAuthorName());
        }
        sb.append(definition.setThenGetPackageName(definition.getPackageName()));
        // 添加第三方 jar 包
        addJarPackage(sb);
        // 添加自定义类注释或者默认注释
        sb.append(definition.getHeadComment().getHeadComments().toString());
        // 判断生成的 JavaBean 文件名是否为空，为空则以数据库表名为文件名
        if (BeanUtils.isEmpty(createBeanName)) {
            DbdToBeanContext.getDbdToBeanDefinitions().add(parseBeanName(definition, tableName));
        }
        // 创建属性值
        createField(sb, getColumnInfo(tableName));
        // 创建构造函数
        createConstructor(sb, isConstructor);
        // 创建 setter 和 getter 方法
        createSetAndGet(sb, isSetAndGet, getColumnInfo(tableName));
        // 创建重写 toString 方法
        createToString(sb, isToString);
        // 最后关闭资源
        BeanUtils.close(rs, stmt);
        return sb.toString();
    }

    /**
     * 读取数据库，创建属性值
     *
     * @param sb          内容
     * @param columnsInfo 字段信息
     * @throws SQLException SQL 异常
     */
    private void createField(StringBuilder sb, ResultSet columnsInfo) throws SQLException {
        boolean startSwagger = DbdToBeanContext.getDbdToMvcDefinition().isOpenSwagger();
        if(startSwagger) {
            sb.append("@ApiModel(value = ").append(BeanUtils.addColon(createBeanName))
                    .append(", description = ").append(BeanUtils.addColon(createBeanName)).append(")").append(oneLine);
            String importContent = twoLine + "import io.swagger.annotations.ApiModel;" + oneLine + "import io.swagger.annotations.ApiModelProperty;";
            sb.insert(sb.indexOf(";") + 1, importContent);
        }
        sb.append("public class ").append(createBeanName).append(" {").append(oneLine);
        for (int i = 1; i <= jdbcData.getColumnCount(); i++) {
            String fieldName = BeanUtils.parseFieldName(jdbcData.getColumnName(i));
            // 添加自定义注释，长度不满足则生成规定的注释
            DbdToBeanContext.getCustomComment().customFiledComment(sb, columnsInfo, fieldName, i);
            sb.append(oneTab).append("private ").append(fieldType(jdbcData.getColumnClassName(i)))
                    .append(" ").append(fieldName).append(";").append(oneLine);
        }
        sb.append("}");
    }

    /**
     * 读取数据库，创建构造器
     *
     * @param sb            内容
     * @param isConstructor 是否生成构造器
     * @throws SQLException SQL 异常
     */
    private void createConstructor(StringBuilder sb, boolean isConstructor) throws SQLException {
        
        if (isConstructor) {
            // 去掉结尾 } 符号
            sb.setLength(sb.length() - 1);
            sb.append(oneLine);
            // 是否生成无参构造器注释，没有自定义注释则生成规定的注释
            DbdToBeanContext.getCustomComment().customConstructComment(sb, true);
            sb.append(oneTab).append("public ").append(createBeanName).append("() {")
                    .append(oneLineAndOneTab).append("}").append(twoLine);
            // 是否生成有参构造器注释，没有自定义注释则生成规定的注释
            DbdToBeanContext.getCustomComment().customConstructComment(sb, false);
            sb.append(oneTab).append("public ").append(createBeanName).append("(");
            for (int i = 1; i <= jdbcData.getColumnCount(); i++) {
                sb.append(fieldType(jdbcData.getColumnClassName(i))).append(" ")
                        .append(BeanUtils.parseFieldName(jdbcData.getColumnName(i))).append(", ");
            }
            // 把最后的 「, 」 和去掉
            sb.setLength(sb.length() - 2);
            sb.append(") {").append(oneLine);
            for (int i = 1; i <= jdbcData.getColumnCount(); i++) {
                sb.append(twoTab).append("this.").append(BeanUtils.parseFieldName(jdbcData.getColumnName(i))).append(" = ")
                        .append(BeanUtils.parseFieldName(jdbcData.getColumnName(i))).append(";").append(oneLine);
            }
            sb.append(oneTab).append("}").append(oneLine).append("}");
        }
    }

    /**
     * 读取数据库，创建 setter 和 getter 方法
     *
     * @param sb          内容
     * @param isSetAndGet 是否生成 setter 和 getter 方法
     * @param columnsInfo 字段信息
     * @throws SQLException SQL 异常
     */
    private void createSetAndGet(StringBuilder sb, boolean isSetAndGet, ResultSet columnsInfo) throws SQLException {
        if (isSetAndGet) {
            //去掉 } 和换行
            sb.setLength(sb.length() - 2);
            sb.append(twoLine);
            for (int i = 1; i <= jdbcData.getColumnCount(); i++) {
                String columnName = jdbcData.getColumnName(i);
                String columnClassName = jdbcData.getColumnClassName(i);
                //是否生成get注释，没有自定义注释则生成规定的注释
                DbdToBeanContext.getCustomComment().customSetGetComment(sb, columnsInfo, BeanUtils.parseFieldName(columnName), i, false);
                String fieldName = BeanUtils.parseFieldName(columnName);
                String setAndGetContent = BeanUtils.isTwoCharUpper(fieldName) ? fieldName : BeanUtils.firstCharToUpperCase(fieldName);
                sb.append(oneTab).append("public ").append(fieldType(columnClassName)).append(" get")
                        .append(setAndGetContent).append("() {")
                        .append(oneLineAndTwoTab).append("return ")
                        .append(fieldName).append(";").append(oneLineAndOneTab)
                        .append("}").append(twoLine);
                //是否生成set注释，没有自定义注释则生成规定的注释
                DbdToBeanContext.getCustomComment().customSetGetComment(sb, columnsInfo, columnName, i, true);
                sb.append(oneTab).append("public void set").append(setAndGetContent)
                        .append("(").append(fieldType(columnClassName)).append(" ")
                        .append(fieldName).append(") {")
                        .append(oneLineAndTwoTab).append("this.")
                        .append(fieldName).append(" = ").append(fieldName)
                        .append(";")
                        .append(oneLineAndOneTab).append("}").append(twoLine);
            }
            sb.setLength(sb.length() - 2);
            sb.append(oneLine).append("}");
        }
    }

    /**
     * 读取数据库，创建重写 toString 方法
     *
     * @param sb         内容
     * @param isToString 是否生成 toString 方法
     * @throws SQLException SQL 异常
     */
    private void createToString(StringBuilder sb, boolean isToString) throws SQLException {
        if (isToString) {
            sb.setLength(sb.length() - 2);
            sb.append(twoLine);
            DbdToBeanContext.getCustomComment().customToString(sb);
            sb.append(oneTab).append("@Override").append(oneLineAndOneTab)
                    .append("public String toString(){")
                    .append(oneLineAndTwoTab).append("return ")
                    .append(BeanUtils.addColon(createBeanName + " {")).append(" + ")
                    .append(oneLineAndFourTab);
            for (int i = 1; i <= jdbcData.getColumnCount(); i++) {
                String columns = jdbcData.getColumnName(i);
                String fieldName = BeanUtils.parseFieldName(columns);
                if("String".equals(fieldType(jdbcData.getColumnClassName(i)))) {
                    sb.append(BeanUtils.addColon(", " + fieldName + "="))
                            .append(" + ").append(fieldName)
                            .append(" + ").append(oneLineAndFourTab);
                }else {
                    sb.append(BeanUtils.addColon(", " + fieldName + "="))
                            .append(" + ").append(fieldName)
                            .append(" + '\\'' + ").append(oneLineAndFourTab);
                }
                
            }
            sb.append(BeanUtils.addColon("}")).append(";")
                    .append(oneLineAndOneTab).append("}")
                            .append(oneLine).append("}");
        }
    }

    /**
     * 读取数据库，在指定路径生成 JavaBean 文件
     * 文件名 由 表名构成
     *
     * @param dateBaseName  数据库名
     * @param isConstructor 是否生成构造器
     * @param isSetAndGet   是否生成 setter 和 getter 方法
     * @param isToString    是否生成 toString 方法
     * @return 内容
     * @throws SQLException SQL 异常
     * @throws IOException  IO 异常
     */
    protected HashMap<String, String> createBeanFromDataBase(String dateBaseName, boolean isConstructor, boolean isSetAndGet, boolean isToString) throws SQLException, IOException {
        String dateBaseType = DbdToBeanContext.getDbdToBeanDefinition().getDateBaseType();
        // 获取不同数据库的不同的查询多表的sql语句
        PreparedStatement stmt = getConnection().prepareStatement(parseDataBaseTypeAndGetSql(dateBaseName));
        ResultSet rs = stmt.executeQuery();
        // 存储整个数据库的生成的bean文件内容
        HashMap<String, String> fileContentMap = new HashMap<>();
        while (rs.next()) {
            String tableName;
            //文件名首字母是否大写
            DbdToBeanContext.getDbdToBeanDefinitions().add(parseBeanName(DbdToBeanContext.getDbdToBeanDefinition(), rs.getString(1)));
            if ("MySQL".equals(dateBaseType)) {
                tableName = rs.getString(1);
            } else if ("Oracle".equals(dateBaseType)) {
                tableName = rs.getString(1).toLowerCase();
            } else {
                logger.info("既不是 Oracle 也不是 MySQL，该工具仅适配这两个数据库，其他数据库默认以 MySQL 数据库形式导出");
                return new HashMap<>();
            }
            String content = createBeanFromTable(tableName, isConstructor, isSetAndGet, isToString);
            fileContentMap.put(createBeanName, content);
        }
        BeanUtils.close(rs, stmt);
        return fileContentMap;
    }

    /**
     * 将数据导出为 java 文件
     *
     * @param fileContent 内容
     * @param path        路径
     * @param dirName     文件夹名
     * @return 内容
     * @throws IOException IO 异常
     */
    protected String exportToFile(String fileContent, String path, String dirName) throws IOException {
        File location = beanLocation();
        String createPath;
        // 生成路径为空，则默认生成路径为桌面
        if (BeanUtils.isEmpty(path) || " ".equals(path)) {
            path = location.getPath();
        } else { // 生成路径不为空，如果路径最后有 \，则去掉，生成文件时再加 \，统一要求
            if (path.lastIndexOf("/") == path.length() - 1) {
                path = path.substring(0, path.lastIndexOf("/"));
            }
        }
        // 文件夹名不为空，则文件放到文件夹里
        if (BeanUtils.isNotEmpty(dirName) && !" ".equals(dirName)) {
            createPath = path + "/" + dirName + "/";
            boolean mkdirs = new File(createPath).mkdirs();
            if (mkdirs) {
                logger.info("创建文件夹成功");
            }
            createPath = createPath + createBeanName + ".java";
        } else {  // 文件夹名为空，则文件放到生成路径下
            dirName = "";
            boolean mkdirs = new File(path).mkdirs();
            if (mkdirs) {
                logger.info("创建文件夹成功");
            }
            createPath = path + "/" + createBeanName + ".java";
        }
        // 如果要生成的缓存存区不为空，则生成文件，否则提示报错
        if (BeanUtils.isNotEmpty(fileContent)) {
            File file = new File(createPath);
            FileWriter fw = new FileWriter(file);
            fw.write(fileContent);
            fw.flush();
            fw.close();
        } else if (fileContent == null) {
            logger.info("输出的内容为空");
        } else {
            logger.info("正在 {} 路径下为您创建随机文件夹名：{}，并生成「JavaBean 文件」", path, dirName);
        }
        return path + "/" + dirName;
    }

    /**
     * 将数据导出为 Java 文件
     *
     * @param createBeanName 文件名
     * @param fileContent    内容
     * @param path           路径
     * @param dirName        文件夹名
     * @return 内容
     * @throws IOException IO 异常
     */
    protected String exportToFile(String createBeanName, String fileContent, String path, String dirName) throws IOException {
        this.createBeanName = createBeanName;
        return exportToFile(fileContent, path, dirName);
    }

    /**
     * 获取生成路径
     *
     * @return 文件路径
     */
    protected File beanLocation() {
        DbdToMvcDefinition dbdToMvcDefinition = DbdToBeanContext.getDbdToMvcDefinition();
        // 默认生成路径：桌面
        if (BeanUtils.isNotEmpty(dbdToMvcDefinition.getEntityLocation())) {
            return new File(System.getProperty("user.dir") + "/" + dbdToMvcDefinition.getModulesName() + "/" + dbdToMvcDefinition.getMavenOrSimple() + BeanUtils.packageToPath(dbdToMvcDefinition.getEntityLocation()));
        } else {
            return FileSystemView.getFileSystemView().getHomeDirectory();
        }
    }

    /**
     * 将数据库的对应 Java 的类型截取出来（去掉包名）
     *
     * @param dataType 数据库类型
     * @return 数据库类型
     */
    private String fieldType(String dataType) {
        int index = dataType.lastIndexOf(".");
        return dataType.substring(index + 1);
    }

    /**
     * 获取表名
     * 获取数据库的表的全部列信息
     *
     * @param tableName 表名
     * @return 结果集
     * @throws SQLException SQL 异常
     */
    private ResultSet getColumnInfo(String tableName) throws SQLException {
        DatabaseMetaData metaData = getConnection().getMetaData();
        // 参数：1.数据库名称，可通过数据库对象获取 2.数据库的登录名，可通过数据库对象获取 3.表名称，null 代表获取所有表 4.类型类型（tabke，view 等）
        ResultSet tables = metaData.getTables(getConnection().getCatalog(), getConnection().getMetaData().getUserName(), null, new String[]{"TABLE"});
        ResultSet columns = null;
        if (tables.next()) {
            // 同理，不过此时的表是指定的表
            columns = metaData.getColumns(getConnection().getCatalog(), getConnection().getMetaData().getUserName(), tableName, null);
        }
        return columns;
    }

    /**
     * 添加导入的 jar 包语句
     *
     * @param sb 内容
     * @throws SQLException SQL 异常
     */
    private void addJarPackage(StringBuilder sb) throws SQLException {
        if (DbdToBeanContext.getDbdToBeanDefinition().isJarPackage()) {
            for (int i = 1; i <= jdbcData.getColumnCount(); i++) {
                if (!jdbcData.getColumnClassName(i).startsWith("java.lang") && sb.indexOf(jdbcData.getColumnClassName(i)) == -1) {
                    sb.append(oneLine).append("import ").append(jdbcData.getColumnClassName(i)).append(";");
                }
            }
            sb.append(oneLine);
        }
        sb.append(oneLine);
    }

    /**
     * 解析生成的文件名字，判断是否首字母大写
     *
     * @param definition bean 信息
     * @param tableName  表名
     * @return 内容
     */
    private DbdToBeanDefinition parseBeanName(DbdToBeanDefinition definition, String tableName) {
        // 文件名首字母大写或者小写，如果 Oracle 表，先转小写，再将首字母大写
        if (definition.isBeanFirstNameUp()) {
            definition.setCreateBeanName(BeanUtils.firstCharToUpperCase(tableName.toLowerCase()));
        } else {
            definition.setCreateBeanName(tableName.toLowerCase());
        }
        definition.setCreateBeanName(BeanUtils.underlineToUpperCase(definition.getCreateBeanName()));
        createBeanName = definition.getCreateBeanName();
        DbdToBeanDefinition dbdToBeanDefinition = new DbdToBeanDefinition();
        dbdToBeanDefinition.setCreateBeanName(createBeanName);
        dbdToBeanDefinition.setTableName(tableName);
        return dbdToBeanDefinition;
    }

    /**
     * 解析数据库，获取数据库类型以及查询表的 SQL 语句
     *
     * @param dateBaseName 数据库名
     * @return SQL 语句
     * @throws SQLException SQL 异常
     */
    protected String parseDataBaseTypeAndGetSql(String dateBaseName) throws SQLException {
        DbdToBeanProperties dbdToBeanProperties = DbdToBeanContext.getDbdToBeanProperties();
        String sql = "select table_name from information_schema.tables where table_schema = '" + dateBaseName + "'";
        // 不用数据库的查询全部表的 SQL 语句
        if ("MySQL".equals(dbdToBeanProperties.getConn().getMetaData().getDatabaseProductName())) {
            sql = "select table_name from information_schema.tables where table_schema = '" + dateBaseName + "'";
            // 获取数据库类型
            DbdToBeanContext.getDbdToBeanDefinition().setDateBaseType("MySQL");
        } else if ("Oracle".equals(dbdToBeanProperties.getConn().getMetaData().getDatabaseProductName())) {
            sql = "select table_name from user_tables";
            // 获取数据库类型
            DbdToBeanContext.getDbdToBeanDefinition().setDateBaseType("Oracle");
        } else {   // 手动添加其他数据库查询全部表的语句
            DbdToBeanContext.getDbdToBeanDefinition().setDateBaseType("MySQL");
            logger.info("既不是 Oracle 也不是 MySQL，该工具仅适配这两个数据库，其他数据库默认以 MySQL 数据库形式导出");
        }
        return sql;
    }

    /**
     * 获取数据库源对象
     *
     * @return 数据库对象
     */
    protected Connection getConnection() {
        return DbdToBeanContext.getDbdToBeanProperties().getConn();
    }

}
