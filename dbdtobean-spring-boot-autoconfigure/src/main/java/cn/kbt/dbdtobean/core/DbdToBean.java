package cn.kbt.dbdtobean.core;


import cn.kbt.dbdtobean.config.DbdToBeanProperties;
import cn.kbt.dbdtobean.inter.IDbdToBean;
import cn.kbt.dbdtobean.log.DbdToBeanApi;
import cn.kbt.dbdtobean.log.DbdToBeanLog;
import cn.kbt.dbdtobean.mvcbean.AbstractDbdToMvc;
import cn.kbt.dbdtobean.mvcbean.DbdToMvc;
import cn.kbt.dbdtobean.mvcbean.DbdToMvcDefinition;
import cn.kbt.dbdtobean.mvcbean.DbdToSwagger;
import cn.kbt.dbdtobean.utils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DbdToBean extends DbdToBeanCore implements IDbdToBean {

    private static final Logger logger = LoggerFactory.getLogger(DbdToBean.class);

    private boolean isMultimediaContent = false;

    public DbdToBean() {
        this(null);
    }

    /**
     * new DbdToBean 的时候，指定创建的实体类名，仅针对单表生成
     * @param createBeanName 实体类名
     */
    public DbdToBean(String createBeanName) {
        DbdToBeanContext.getDbdToBeanDefinition().setCreateBeanName(createBeanName);
    }

    /**
     * 设置类注释上的作者名
     * @param authorName 作者名
     */
    public void setAuthorName(String authorName) {
        DbdToBeanContext.getDbdToBeanProperties().setAuthorName(authorName);
    }

    /**
     * 是否生成作者名
     * @param authorName 是否生成作者名
     */
    public void setAuthorName(boolean authorName) {
        if(!authorName){
            DbdToBeanContext.getDbdToBeanProperties().setAuthorName(null);
        }
    }

    /**
     * 指定创建的实体类名，仅针对单表生成
     * @param createBeanName 实体类名
     */
    public void setCreateBeanName(String createBeanName) {
        DbdToBeanContext.getDbdToBeanDefinition().setCreateBeanName(createBeanName);
    }

    /**
     * 根据表名生成实体类内容
     * 是否生成构造方法、getter、setter、toString 方法、是否生成 toString 方法
     * @param tableName 表名
     * @param isConstructor true：生成构造方法；false：不生成构造方法
     * @param isSetAndGet true：生成 getter 和 setter 方法；false：不生成 getter 和 setter 方法
     * @param isToString true：生成 toString 方法；false：不生成 toString 方法
     * @return 实体类内容
     * @throws SQLException SQL异常
     */
    @Override
    public String createBeanFromTable(String tableName, boolean isConstructor, boolean isSetAndGet, boolean isToString) throws SQLException {
        return super.createBeanFromTable(tableName, isConstructor, isSetAndGet, isToString);
    }

    /**
     * 根据表名生成实体类内容
     * @param tableName 表名
     * @return 实体类内容
     * @throws SQLException SQL异常
     */
    public String createBeanFromTable(String tableName) throws SQLException {
        return createBeanFromTable(tableName, true, true, true);
    }

    /**
     * 根据表名生成实体类内容
     * 是否生成构造方法
     * @param tableName 表名
     * @param isConstructor true：生成构造方法；false：不生成构造方法
     * @return 实体类内容
     * @throws SQLException SQL异常
     */
    public String createBeanFromTable(String tableName, boolean isConstructor) throws SQLException {
        return createBeanFromTable(tableName, isConstructor, true, true);
    }

    /**
     * 根据表名生成实体类内容
     * 是否生成构造方法，是否生成 getter 和 setter 方法
     * @param tableName 表名
     * @param isConstructor true：生成构造方法；false：不生成构造方法
     * @param isSetAndGet true：生成 getter 和 setter 方法；false：不生成 getter 和 setter 方法
     * @return 实体类内容
     * @throws SQLException SQL异常
     */
    public String createBeanFromTable(String tableName, boolean isConstructor, boolean isSetAndGet) throws SQLException {
        return createBeanFromTable(tableName, isConstructor, isSetAndGet, true);
    }

    /**
     * 是否生成 import 导入包
     * @param jarPackage true：生成，false：不生成
     */
    public void setJarPackage(boolean jarPackage) {
        DbdToBeanContext.getDbdToBeanDefinition().setJarPackage(jarPackage);
    }

    /**
     * 是否生成类注释
     * @param setHeadComment true：生成，false：不生成
     */
    public void setHeadComment(boolean setHeadComment) {
        DbdToBeanContext.getDefaultComment().setSetHeadComment(setHeadComment);
    }

    /**
     * 自定义一个类注释内容
     * @param headComment 类注释内容
     */
    public void setHeadComment(String headComment) {
        DbdToBeanContext.getDbdToBeanDefinition().getHeadComment().setHeadComment(headComment);
    }

    /**
     * 设置注释的类型，注释类型：//  /*  /**。默认为：/**
     * @param commentType 注释类型
     */
    public void setCommentType(String commentType) {
        DbdToBeanContext.getCustomComment().setCommentType(commentType);
    }

    /**
     * 自定义一个类注释内容，并设置注释的类型，注释类型：//  /*  /**。默认为：/**
     * @param headComment 类注释内容
     * @param commentType 注释类型
     */
    public void setHeadComment(String headComment, String commentType) {
        DbdToBeanContext.getDbdToBeanDefinition().getHeadComment().setHeadComment(headComment,commentType);
    }

    /**
     * 是否生成实体类的注释。默认从数据库的表中获取注释，如果没有，则使用默认注释
     * @param generateAllComment true：生成，false：不生成
     */
    public void setAllComments(boolean generateAllComment) {
        DbdToBeanContext.getDefaultComment().setAllComments(generateAllComment);
    }

    /**
     * 是否生成实体类的注释。默认从数据库的表中获取注释，如果没有，则使用默认注释
     * 是否生成属性的注释、是否生成构造器的注释、是否生成 getter 和 setter 方法的注释、是否生成 toString 方法的注释
     * @param isFieldComment true：生成，false：不生成
     * @param isConstructorComment true：生成，false：不生成
     * @param isSetAndGetComment true：生成，false：不生成
     * @param isToStringComment true：生成，false：不生成
     */
    public void setComment(boolean isFieldComment, boolean isConstructorComment, boolean isSetAndGetComment, boolean isToStringComment) {
        DbdToBeanContext.getDefaultComment().setComment(isFieldComment, isConstructorComment, isSetAndGetComment, isToStringComment);
    }

    /**
     * 是否生成属性的注释
     * @param isFieldComment true：生成，false：不生成
     */
    public void setFieldComment(boolean isFieldComment) {
        setComment(isFieldComment, DbdToBeanContext.getDefaultComment().isConstructorComment(), DbdToBeanContext.getDefaultComment().isSetAndGetComment(), DbdToBeanContext.getDefaultComment().isToStringComment());
    }

    /**
     * 自定义属性的注释，List 集合按顺序从上而下给属性设置注释，如果长度不够，则剩下的属性使用默认注释
     * @param filedComment 自定义属性的注释
     */
    public void setFiledComment(List<String> filedComment) {
        DbdToBeanContext.getCustomComment().setFiledComment(filedComment);
    }

    /**
     * 是否生成构造器的注释
     * @param isConstructorComment true：生成，false：不生成
     */
    public void setConstructorComment(boolean isConstructorComment) {
        setComment(DbdToBeanContext.getDefaultComment().isFieldComment(), isConstructorComment, DbdToBeanContext.getDefaultComment().isSetAndGetComment(), DbdToBeanContext.getDefaultComment().isToStringComment());
    }

    /**
     * 自定义有参构造器的注释
     * @param constructorComment true：生成，false：不生成
     */
    public void setConstructorComment(String constructorComment) {
        DbdToBeanContext.getCustomComment().setParamConstructorComment(constructorComment);
    }

    /**
     * 自定义无参构造器的注释、自定义有参构造器的注释
     * @param nullConstructorComment 无参构造器的注释
     * @param constructorComment 有参构造器的注释
     */
    public void setConstructorComment(String nullConstructorComment, String constructorComment) {
        DbdToBeanContext.getCustomComment().setConstructorComment(nullConstructorComment,constructorComment);
    }

    /**
     * 是否生成 getter 和 setter 方法的注释
     * @param isSetAndGetComment true：生成，false：不生成
     */
    public void setSetAndGetComment(boolean isSetAndGetComment) {
        setComment(DbdToBeanContext.getDefaultComment().isFieldComment(), DbdToBeanContext.getDefaultComment().isConstructorComment(), isSetAndGetComment, DbdToBeanContext.getDefaultComment().isToStringComment());
    }

    /**
     * 自定义 setter 方法的注释
     * @param setterComment setter 方法的注释
     */
    public void setSetComment(List<String> setterComment) {
        DbdToBeanContext.getCustomComment().setSetterComment(setterComment);
    }

    /**
     * 自定义 getter 方法的注释
     * @param getterComment getter 方法的注释
     */
    public void setGetComment(List<String> getterComment) {
        DbdToBeanContext.getCustomComment().setGetterComment(getterComment);
    }

    /**
     * 自定义 getter 和 setter 方法的注释，List 集合按顺序从上而下给方法设置注释，如果长度不够，则剩下的方法使用默认注释
     * @param setterComment 自定义 getter 方法的注释
     * @param getterComment 自定义 setter 方法的注释
     */ 
    public void setSetAndGetComment(List<String> setterComment, List<String> getterComment) {
        DbdToBeanContext.getCustomComment().setSetAndGetComment(setterComment,getterComment);
    }

    /**
     * 是否生成 toString 方法的注释
     * @param isToStringComment true：生成，false：不生成
     */
    public void setToStringComment(boolean isToStringComment) {
        setComment(DbdToBeanContext.getDefaultComment().isFieldComment(), DbdToBeanContext.getDefaultComment().isConstructorComment(), DbdToBeanContext.getDefaultComment().isSetAndGetComment(), isToStringComment);
    }

    /**
     * 自定义 toString 方法的注释
     * @param toStringComment toString 方法的注释
     */
    public void setToStringComment(String toStringComment) {
        DbdToBeanContext.getCustomComment().setToStringComment(toStringComment);
    }

    /**
     * 文件名首字母是否大写
     * @param beanFirstNameUp true：大写，false：小写
     */
    public void setBeanFirstNameIsUp(boolean beanFirstNameUp) {
        DbdToBeanContext.getDbdToBeanDefinition().setBeanFirstNameUp(beanFirstNameUp);
    }

    /**
     * 数据库的字段和生成的 JavaBean 内容属性值是否保持一样或者小写
     * @param fieldNameAllLower true：保持一样，false：小写
     */
    public void setFieldNameAllLower(boolean fieldNameAllLower) {
        DbdToBeanContext.getDbdToBeanDefinition().setFieldNameAllLower(fieldNameAllLower);
    }

    /**
     * 读取数据库的所有表，存入 Map 里，key 为表名，value 为 JavaBean 内容
     * @param fieldNameAllLower 数据库的字段和生成的 JavaBean 内容属性值是否保持一样或者小写，true：保持一样，false：小写
     * @throws SQLException SQL 异常
     * @throws IOException IO 异常
     */
    public void createBeanFromDataBase(boolean fieldNameAllLower) throws SQLException, IOException {
        DbdToBeanContext.getDbdToBeanDefinition().setFieldNameAllLower(fieldNameAllLower);
        createBeanFromDataBase(null, true, true, true);
    }

    /**
     * 读取数据库的所有表，存入 Map 里，key 为表名，value 为 JavaBean 内容
     * @return 所有表的 JavaBean 内容
     * @throws SQLException SQL 异常
     * @throws IOException IO 异常
     */
    public Map<String, String> createBeanFromDataBase() throws SQLException, IOException {
        return createBeanFromDataBase(getConnection().getCatalog(), true, true, true);
    }

    /**
     * 读取指定的数据库的所有表，存入 Map 里，key 为表名，value 为 JavaBean 内容
     * @param dateBaseName 数据库名
     * @return 指定的数据库的所有表的 JavaBean 内容
     * @throws SQLException SQL 异常
     * @throws IOException IO 异常
     */
    public Map<String, String> createBeanFromDataBase(String dateBaseName) throws SQLException, IOException {
        return createBeanFromDataBase(dateBaseName, true, true, true);
    }

    /**
     * 读取指定的数据库的所有表，存入 Map 里，key 为表名，value 为 JavaBean 内容
     * 可以指定是否生成注释，是否生成构造方法
     * @param dateBaseName 数据库名
     * @param isConstructor 是否生成构造方法
     * @return 指定的数据库的所有表的 JavaBean 内容
     * @throws SQLException SQL 异常
     * @throws IOException IO 异常
     */
    public Map<String, String> createBeanFromDataBase(String dateBaseName, boolean isConstructor) throws SQLException, IOException {
        return createBeanFromDataBase(dateBaseName, isConstructor, true, true);
    }

    /**
     * 读取指定的数据库的所有表，存入 Map 里，key 为表名，value 为 JavaBean 内容
     * 可以指定是否生成构造方法、是否生成 getter 和 setter 方法
     * @param dateBaseName 数据库名
     * @param isConstructor 是否生成构造方法
     * @param isSetAndGet 是否生成 setter 和 getter 方法
     * @return 指定的数据库的所有表的 JavaBean 内容
     * @throws SQLException SQL 异常
     * @throws IOException IO 异常
     */
    public Map<String, String> createBeanFromDataBase(String dateBaseName, boolean isConstructor, boolean isSetAndGet) throws SQLException, IOException {
        return createBeanFromDataBase(dateBaseName, isConstructor, isSetAndGet, true);
    }

    /**
     * 读取指定的数据库的所有表，存入 Map 里，key 为表名，value 为 JavaBean 内容
     * 可指定是否生成构造方法、setter 和 getter 方法、toString 方法
     * @param dateBaseName 数据库名
     * @param isConstructor 是否生成构造方法
     * @param isSetAndGet 是否生成 setter 和 getter 方法
     * @param isToString 是否生成 toString 方法
     * @return 指定的数据库的所有表的 JavaBean 内容
     * @throws SQLException SQL 异常
     * @throws IOException IO 异常
     */
    @Override
    public HashMap<String, String> createBeanFromDataBase(String dateBaseName, boolean isConstructor, boolean isSetAndGet, boolean isToString) throws SQLException, IOException {
        return super.createBeanFromDataBase(dateBaseName, isConstructor, isSetAndGet, isToString);
    }

    /**
     * 将指定的内容导出为 Java 文件
     * @param fileContent 文件内容
     * @return 生成的文件总路径
     * @throws IOException IO 异常
     */
    public String exportToFiles(String fileContent) throws IOException {
        return exportToFiles(fileContent, null, null);
    }

    /**
     * 将指定的内容到处为 java 文件，并指定路径
     * @param fileContent 文件内容
     * @param path 文件路径
     * @return 生成的文件总路径
     * @throws IOException IO 异常
     */
    public String exportToFiles(String fileContent, String path) throws IOException {
        return exportToFiles(fileContent, path, null);
    }

    /**
     * 将指定的内容导出为 Java 文件，并指定路径和文件名
     * @param fileContent 文件内容
     * @param path 文件路径
     * @param dirName 文件夹名
     * @return 生成的文件总路径
     * @throws IOException IO 异常
     */
    @Override
    public String exportToFiles(String fileContent, String path, String dirName) throws IOException {
        if(!isMultimediaContent){
            if(BeanUtils.isEmpty(path)){
                if(BeanUtils.isEmpty(dirName) && BeanUtils.isEmpty(DbdToBeanContext.getDbdToMvcDefinition().getEntityLocation())){
                    logger.info("正在「电脑桌面」路径下为您创建「JavaBean 文件」");
                }else if(BeanUtils.isEmpty(DbdToBeanContext.getDbdToMvcDefinition().getEntityLocation())){
                    logger.info("正在「电脑桌面」路径下的 {} 文件夹里为您创建「JavaBean 文件」", dirName);
                }
            }else if(BeanUtils.isEmpty(dirName)){
                logger.info("正在 {} 路径下为您创建「JavaBean 文件」", path);
            }else {
                logger.info("正在 {} 路径下的 {} 文件夹里为您创建「JavaBean 文件」", path, dirName);
            }
        }
        String createPath = super.exportToFile(fileContent, path, dirName);
        if(BeanUtils.isNotEmpty(DbdToBeanContext.getDbdToMvcDefinition().getControllerLocation())) {
            logger.info("开始生成上面 Bean 的 MVC 文件");
            DbdToMvc dbdToMvc = new DbdToMvc();
            dbdToMvc.dbdToMvc();
            logger.info("生成 MVC 文件成功");
        }
        if(DbdToBeanContext.getDbdToMvcDefinition().isOpenSwagger()) {
            logger.info("开始生成 Swagger 文件");
            DbdToSwagger dbdToSwagger = new DbdToSwagger();
            dbdToSwagger.createSwagger();
            logger.info("成功生成 Swagger 文件");
        }
        logger.info("所有文件创建完毕，位于：{}", new File(createPath.substring(0, createPath.lastIndexOf("\\"))).getAbsolutePath());
        return createPath;
    }

    /**
     * 将指定的 Map 内容依次到处为 Java 文件，key 为表名，value 为 JavaBean 内容
     * @param fileContentMap 文件内容
     * @return 生成的文件总路径
     * @throws IOException IO 异常
     * @throws SQLException SQL 异常
     */
    public String exportToFiles(Map<String,String> fileContentMap) throws IOException, SQLException {
        return this.exportToFiles(fileContentMap,null,null);
    }

    /**
     * 将指定的 Map 内容依次到处为 Java 文件，key 为表名，value 为 JavaBean 内容，并指定路径
     * @param fileContentMap 文件内容
     * @param path 文件路径
     * @return 生成的文件总路径
     * @throws IOException IO 异常
     * @throws SQLException SQL 异常
     */
    public String exportToFiles(Map<String,String> fileContentMap, String path) throws IOException, SQLException {
        return this.exportToFiles(fileContentMap,path,null);
    }

    /**
     * 将指定的 Map 内容依次到处为 Java 文件，key 为表名，value 为 JavaBean 内容，并指定路径和文件名
     * @param fileContentMap 文件内容
     * @param path 文件路径
     * @param dirName 文件夹名
     * @return 生成的文件总路径
     * @throws IOException IO 异常
     * @throws SQLException SQL 异常
     */
    @Override
    public String exportToFiles(Map<String,String> fileContentMap, String path, String dirName) throws IOException, SQLException {
        isMultimediaContent = true;
        String createPath = "";
        logger.info("系统检测到提供的数据库共有 {} 个表，准备生成文件", fileContentMap.size());
        if ((BeanUtils.isEmpty(dirName) || " ".equals(dirName)) && BeanUtils.isEmpty(path)) {
            // 默认生成一个文件夹，以数据库名字 + 随机数字为文件夹名
            if(BeanUtils.isEmpty(DbdToBeanContext.getDbdToMvcDefinition().getEntityLocation())){
                dirName = super.getConnection().getMetaData().getDatabaseProductName() + "_" + BeanUtils.randomNum();
            }
            // 生成文件夹
            createPath = super.exportToFile("", path, dirName);
            logger.info("在 {} 处创建成功！", createPath);
        }
        int i = 1;
        path = path != null ? path : super.beanLocation().getPath();
        if(dirName == null) {
            logger.info("正在 {} 路径下为您创建「JavaBean 文件」", path);
        }else {
            logger.info("正在 {} 路径下的 {} 文件夹里为您创建「JavaBean 文件」", path, dirName);
        }
        for (Map.Entry<String, String> entry : fileContentMap.entrySet()) {
            logger.info("正在创建第 {} 个 Bean 文件：{}", i++, entry.getKey());
            createPath = super.exportToFile(entry.getKey(), entry.getValue(), path, dirName);
        }
        if(BeanUtils.isNotEmpty(DbdToBeanContext.getDbdToMvcDefinition().getControllerLocation())) {
            logger.info("开始生成上面 Bean 的 MVC 文件");
            DbdToMvc dbdToMvc = new DbdToMvc();
            dbdToMvc.dbdToMvc();
            logger.info("生成 MVC 文件成功");
        }
        if(DbdToBeanContext.getDbdToMvcDefinition().isOpenSwagger()) {
            logger.info("开始生成 Swagger 文件");
            DbdToSwagger dbdToSwagger = new DbdToSwagger();
            dbdToSwagger.createSwagger();
            logger.info("生成 Swagger 文件成功");
        }
        logger.info("所有文件创建完毕，位于：{}", new File(createPath.substring(0, createPath.lastIndexOf("\\"))).getAbsolutePath());
        if(DbdToBeanContext.getDbdToMvcDefinition().isOpenSwagger()) { 
            logger.info("你已经生成 Swagger 相关配置，请自行在 pom.xml 里引入 Swagger 的依赖，这里提供一个依赖版本：\n" +
                    "<dependency>\n" +
                    "    <groupId>io.springfox</groupId>\n" +
                    "    <artifactId>springfox-swagger2</artifactId>\n" +
                    "    <version>2.9.2</version>\n" +
                    "</dependency>\n" +
                    "<dependency>\n" +
                    "    <groupId>io.springfox</groupId>\n" +
                    "    <artifactId>springfox-swagger-ui</artifactId>\n" +
                    "    <version>2.9.2</version>\n" +
                    "</dependency>");
        }
        return createPath;
    }

    /**
     * 开启驼峰命名
     * @param lowerCamelCase true 开启，false 关闭
     */
    public void setLowerCamelCase(boolean lowerCamelCase) {
        DbdToBeanContext.getDbdToBeanDefinition().setLowerCamelCase(lowerCamelCase);
    }

    /**
     * 将字符串的首字母转为大写
     * @param fieldName 字符串
     * @return 转换后的字符串
     */
    public String firstCharToUpperCase(String fieldName) {
        return BeanUtils.firstCharToUpperCase(fieldName);
    }

    /**
     * 将字符串的首字母转为小写
     * @param fieldName 字符串
     * @return 转换后的字符串
     */
    public String firstCharToLowerCase(String fieldName) {
        return BeanUtils.firstCharToLowerCase(fieldName);
    }

    /**
     * 去掉字符串的下划线，并且下划线后的首字母大写
     * @param name 字符串
     * @return 转换后的字符串
     */
    public String underlineToUpperCase(String name) {
        return BeanUtils.underlineToUpperCase(name);
    }

    /**
     * 设置数据库的驱动类名、数据库连接 url、用户名、密码
     * @param driverName 驱动类名
     * @param url 数据库连接 url
     * @param username 数据库用户名
     * @param password 数据库密码
     */
    public void setDbdToBeanProperties(String driverName, String url, String username, String password) {
        DbdToBeanContext.getDbdToBeanProperties().setDriverName(driverName);
        DbdToBeanContext.getDbdToBeanProperties().setUrl(url);
        DbdToBeanContext.getDbdToBeanProperties().setUsername(username);
        DbdToBeanContext.getDbdToBeanProperties().setPassword(password);
    }

    /**
     * 设置模块名字，如果项目里有多个模块，可使用
     * @param modulesName 模块名字
     */
    public void setModulesName(String modulesName) {
        DbdToBeanContext.getDbdToMvcDefinition().setModulesName(modulesName);
    }

    /**
     * 设置 JavaBean 实体类所在的全包路径，基于项目的根目录下
     * @param entityLocation 实体类所在的全包路径
     */
    public void setEntityLocation(String entityLocation) {
        DbdToBeanContext.getDbdToMvcDefinition().setEntityLocation(entityLocation);
    }

    /**
     * 设置 MVC Controller 层所在的全包路径，基于项目的根目录下
     * @param controllerLocation Controller 层所在的全包路径
     */
    public void setControllerLocation(String controllerLocation) {
        DbdToBeanContext.getDbdToMvcDefinition().setControllerLocation(controllerLocation);
    }

    /**
     * 设置 MVC Service 层所在的全包路径，基于项目的根目录下
     * @param serviceLocation Service 层所在的全包路径
     */
    public void setServiceLocation(String serviceLocation) {
        DbdToBeanContext.getDbdToMvcDefinition().setServiceLocation(serviceLocation);
    }

    /**
     * 设置 Dao 层所在的全包路径，基于项目的根目录下
     * @param daoLocation Dao 层所在的全包路径
     */
    public void setDaoLocation(String daoLocation) {
        DbdToBeanContext.getDbdToMvcDefinition().setDaoLocation(daoLocation);
    }

    /**
     * 设置 MVC Mapper 层所在的全包路径，基于项目的根目录下
     * @param mapperLocation Mapper 层所在的全包路径
     */
    public void setMapperLocation(String mapperLocation) {
        DbdToBeanContext.getDbdToMvcDefinition().setMapperLocation(mapperLocation);
    }

    /**
     * 获取 Mapper 的 XML 层所在的全包路径
     * @return Mapper XML 层所在的全包路径
     */
    public String getMapperXMLLocation() {
        return DbdToBeanContext.getDbdToMvcDefinition().getMapperXmlLocation();
    }

    /**
     * 设置 MVC Mapper 的 XML 层所在的全包路径，基于项目的 resources 目录下
     * @param mapperXMLLocation Mapper 的 XML 层所在的全包路径
     */
    public void setMapperXMLLocation(String mapperXMLLocation) {
        DbdToBeanContext.getDbdToMvcDefinition().setMapperXmlLocation(mapperXMLLocation);
    }

    /**
     * 设置数据库连接对象
     * @param conn 数据库连接对象
     */
    public void setConnection(Connection conn) {
        DbdToBeanContext.getDbdToBeanProperties().setConn(conn);
    }

    /**
     * 关闭数据库连接
     * @throws SQLException SQL 异常
     */
    public void closeConnection() throws SQLException {
        if (null != DbdToBeanContext.getDbdToBeanProperties().getConn()) {
            DbdToBeanContext.getDbdToBeanProperties().getConn().close();
        }
    }

    /**
     * 关闭指定的数据库连接
     * @param connection 数据库连接对象
     */
    public void closeConnection(Connection connection) {
        try {
            if (null != connection) {
                connection.close();
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    /**
     * 设置数据库的连接类
     * @param dbdToBeanProperties 数据库的连接类
     */
    public void setDbdToBeanProperties(DbdToBeanProperties dbdToBeanProperties){
        DbdToBeanContext.setDbdToBeanProperties(dbdToBeanProperties);
    }

    /**
     * 获取数据库的类型
     * @return 数据库的类型
     * @throws SQLException SQL 异常
     */
    public String getDateBaseType() throws SQLException {
        super.parseDataBaseTypeAndGetSql("");
        return DbdToBeanContext.getDbdToBeanDefinition().getDateBaseType();
    }

    // ----------------------------------- DBDToMVC -----------------------------------

    /**
     * 所有 MVC 类文件名添加前缀
     * @param prefix 前缀
     */
    public void setPrefix(String prefix) {
        DbdToBeanContext.getDbdToMvcDefinition().setPrefix(prefix);
    }

    /**
     * 所有 MVC 层的类文件名添加后缀
     * @param suffix 后缀
     */
    public void setSuffix(String suffix) {
        DbdToBeanContext.getDbdToMvcDefinition().setSuffix(suffix);
    }

    /**
     * Controller 层的类文件名添加前缀
     * @param controllerPre 前缀
     */
    public void setControllerPre(String controllerPre) {
        DbdToBeanContext.getDbdToMvcDefinition().setControllerPre(controllerPre);
    }

    /**
     * Controller 层的类文件名添加后缀
     * @param controllerSuf 后缀
     */
    public void setControllerSuf(String controllerSuf) {
        DbdToBeanContext.getDbdToMvcDefinition().setControllerSuf(controllerSuf);
    }

    /**
     * Service 接口层的类文件名添加前缀
     * @param servicePre 前缀
     */
    public void setServiceInterPre(String servicePre) {
        DbdToBeanContext.getDbdToMvcDefinition().setServiceInterPre(servicePre);
    }

    /**
     * Service 接口层的类文件名添加后缀
     * @param serviceSuf 后缀
     */
    public void setServiceInterSuf(String serviceSuf) {
        DbdToBeanContext.getDbdToMvcDefinition().setServiceInterSuf(serviceSuf);
    }

    /**
     * Service 实现层的类文件名添加前缀
     * @param servicePre 前缀
     */
    public void setServiceImplPre(String servicePre) {
        DbdToBeanContext.getDbdToMvcDefinition().setServiceImplPre(servicePre);
    }

    /**
     * Service 实现层的类文件名添加后缀
     * @param serviceSuf 后缀
     */
    public void setServiceImplSuf(String serviceSuf) {
        DbdToBeanContext.getDbdToMvcDefinition().setServiceImplSuf(serviceSuf);
    }

    /**
     * Dao 接口层的类文件名添加前缀
     * @param daoPre 前缀
     */
    public void setDaoInterPre(String daoPre) {
        DbdToBeanContext.getDbdToMvcDefinition().setDaoInterPre(daoPre);
    }

    /**
     * Dao 接口层的类文件名添加后缀
     * @param daoSuf 后缀
     */
    public void setDaoInterSuf(String daoSuf) {
        DbdToBeanContext.getDbdToMvcDefinition().setDaoInterSuf(daoSuf);
    }

    /**
     * Dao 实现层的类文件名添加前缀
     * @param daoPre 前缀
     */
    public void setDaoImplPre(String daoPre) {
        DbdToBeanContext.getDbdToMvcDefinition().setDaoImplPre(daoPre);
    }

    /**
     * Dao 实现层的类文件名添加后缀
     * @param daoSuf 后缀
     */
    public void setDaoImplSuf(String daoSuf) {
        DbdToBeanContext.getDbdToMvcDefinition().setDaoImplSuf(daoSuf);
    }

    /**
     * Mapper 接口层的类文件名添加前缀
     * @param mapperPre 前缀
     */
    public void setMapperInterPre(String mapperPre) {
        DbdToBeanContext.getDbdToMvcDefinition().setMapperInterPre(mapperPre);
    }

    /**
     * Mapper 接口层的类文件名添加后缀
     * @param mapperSuf 后缀
     */
    public void setMapperInterSuf(String mapperSuf) {
        DbdToBeanContext.getDbdToMvcDefinition().setMapperInterSuf(mapperSuf);
    }

    /**
     * Mapper 的 XML 层的 xml 文件名添加前缀
     * @param mapperPre 前缀
     */
    public void setMapperXmlPre(String mapperPre) {
        DbdToBeanContext.getDbdToMvcDefinition().setMapperXmlPre(mapperPre);
    }

    /**
     * Mapper 的 XML 层的 xml 文件名添加后缀
     * @param mapperSuf 后缀
     */
    public void setMapperXmlSuf(String mapperSuf) {
        DbdToBeanContext.getDbdToMvcDefinition().setMapperXmlSuf(mapperSuf);
    }

    /**
     * 获取 DbdToBean 的基础配置类
     * @return DbdToBean 的基础配置类
     */
    public DbdToBeanDefinition getDbdToBeanDefinition() {
        return DbdToBeanContext.getDbdToBeanDefinition();
    }

    /**
     * 获取 DbdToBean 的基础配置类集合
     * @return DbdToBean 的基础配置类集合
     */
    public List<DbdToBeanDefinition> getDbdToBeanDefinitions() {
        return DbdToBeanContext.getDbdToBeanDefinitions();
    }

    /**
     * 获取 DbdToBean 的 MVC 基础配置类
     * @return MVC 的基础类
     */
    public DbdToMvcDefinition getDbdToMvcDefinition() {
        return DbdToBeanContext.getDbdToMvcDefinition();
    }

    /**
     * 是否生成 MVC 的 CURD 基础方法
     * @param generateCURD true 生成，false 不生成
     */
    public void setGenerateCURD(boolean generateCURD) {
        DbdToBeanContext.getDbdToMvcDefinition().setGenerateCurd(generateCURD);
    }

    /**
     * 设置 MVC 的实现类的包路径
     * @param implLocation 实现类的包路径
     */
    public void setMvcImplName(String implLocation){
        AbstractDbdToMvc.IMPL_NAME = implLocation;
    }

    /**
     * 设置 Mapper 的 XML 文件 PUBLIC 和 Http 地址
     * @param xmlPublicAndHttp PUBLIC 和 Http 地址
     */
    public void setXmlPublicAndHttp(String xmlPublicAndHttp){
        AbstractDbdToMvc.xmlPublicAndHttp = xmlPublicAndHttp;
    }

    /**
     * 指定当前项目是 Maven 项目还是普通 Java 项目
     * @param mavenOrSimple true Maven 项目，false 普通 Java 项目
     */
    public void setMavenOrSimple(boolean mavenOrSimple) {
        DbdToBeanContext.getDbdToMvcDefinition().setMavenOrSimple(mavenOrSimple);
    }

    /**
     * 是否生成 MVC 的注解，如 @Controller、@Service、@Mapper、@Repository 等
     * @param mvcAnnotation true 生成，false 不生成
     */
    public void setMvcAnnotation(boolean mvcAnnotation) {
        DbdToBeanContext.getDbdToMvcDefinition().setMvcAnnotation(mvcAnnotation);
    }

    /**
     * 是否在 Controller 的方法参数里生成 RequestBody 注解
     * @param generateRequestBody true 生成，false 不生成
     */
    public void setGenerateRequestBody(boolean generateRequestBody) {
        DbdToBeanContext.getDbdToMvcDefinition().setGenerateRequestBody(generateRequestBody);
    }

    /**
     * 设置 Mapper 的 XML 里，sql 标签的字段换行数量，即一行允许出现的字段，超出则换行
     * @param columnNum sql 标签的字段换行数量
     */
    public void setColumnNum(int columnNum) {
        DbdToBeanContext.getDbdToMvcDefinition().setColumnNum(columnNum);
    }

    /**
     * 是否开启 Swagger 的功能
     * @param startSwagger true 开启，false 不开启
     */
    public void setStartSwagger(boolean startSwagger) {
        DbdToBeanContext.getDbdToMvcDefinition().setOpenSwagger(startSwagger);
    }

    /**
     * 设置 Swagger 配置类的包路径，默认获取 Controller 的包路径（去掉最后一个包名），然后加上 config
     * 如 Controller 的包路径为 cn.kbt.controller，则 Swagger 的配置文件包路径为 cn.kbt.config
     * 如 Controller 的包路径为 cn.kbt.aa.bb，则 Swagger 的配置文件包路径为 cn.kbt.aa.config
     * @param swaggerLocation Swagger 配置类的包路径
     */
    public void setSwaggerLocation(String swaggerLocation) {
        DbdToBeanContext.getDbdToMvcDefinition().setSwaggerLocation(swaggerLocation);
    }

    /**
     * 设置 Swagger 的版本，默认为 2 版本
     * DbdToMvcDefinition.SwaggerVersion.SWAGGER_2 为 2 版本
     * DbdToMvcDefinition.SwaggerVersion.OAS_30 为 3 版本
     * @param swaggerVersion Swagger 的版本
     */
    public void setSwaggerVersion(DbdToMvcDefinition.SwaggerVersion swaggerVersion) {
        DbdToBeanContext.getDbdToMvcDefinition().setSwaggerVersion(swaggerVersion);
    }

    // ----------------------------------- log -----------------------------------

    /**
     * 打印 DbdToBean 的更新日志记录
     * @return DbdToBean 的更新日志记录
     */
    public DbdToBeanLog getLogInfo(){
        return new DbdToBeanLog();
    }

    /**
     * 获取 DbdToBean 的常用 API
     * @return DbdToBean 的常用 API
     */
    public DbdToBeanApi getDbdToBeanApi(){
        return new DbdToBeanApi();
    }

    // ----------------------------------- Utils -----------------------------------

    /**
     * 通过结果集，获取数据库的各种数据
     * @param rs 结果集
     * @throws SQLException SQL 异常
     */
    public void explain(ResultSet rs) throws SQLException {
        BeanUtils.explain(rs);
    }

}
