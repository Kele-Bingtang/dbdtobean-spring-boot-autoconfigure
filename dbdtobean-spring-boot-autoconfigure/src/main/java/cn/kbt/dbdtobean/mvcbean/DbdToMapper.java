package cn.kbt.dbdtobean.mvcbean;

import cn.kbt.dbdtobean.core.DbdToBeanContext;
import cn.kbt.dbdtobean.utils.BeanUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Kele-Bing
 * @version 1.6
 * @since 2021/9/21 23:09
 */
public class DbdToMapper extends AbstractDbdToMVC {

    protected static final  String MAPPER_INTERFACE_NAME = "Mapper";
    protected static final  String MAVEN_MAPPER_XML_HONE = "src\\main\\resources\\";
    protected static final  String SIMPLE_MAPPER_XML_HONE = "src\\";
    protected static String interfacesName = null;
    private String entityName;
    private static final String RESULT_MAP_ID = "baseResultMap";
    private static final String SQL_ID = "baseColumnList";
    private ResultSetMetaData resultSetMetaData;
    private DatabaseMetaData databaseMetaData;

    private final String oneLineAndOneTab = BeanUtils.getNT(1, 1);
    private final String oneLineAndTwoTab = BeanUtils.getNT(1, 2);
    private final String oneLineAndThreeTab = BeanUtils.getNT(1, 3);
    private final String oneLineAndFourTab = BeanUtils.getNT(1, 4);
    private final String twoLineAndOneTab = BeanUtils.getNT(2, 1);
    private final String oneLine = BeanUtils.getN(1);
    private final String twoLine = BeanUtils.getN(2);

    /**
     * 生成 Mapper 层接口目录以及内容
     *
     * @param createBeanName 文件名
     * @throws IOException IO 异常
     */
    protected void mapperInterfaces(String createBeanName) throws IOException {
        createBeanName = BeanUtils.underlineToUpperCase(createBeanName);
        DbdToMvcDefinition definition = DbdToBeanContext.getDbdToMvcDefinition();
        interfacesName = super.createInterfaces(definition, createBeanName, MAPPER_INTERFACE_NAME);
    }

    /**
     * 生成 Mapper 层 xml 文件目录以及内容
     *
     * @param createBeanName 文件名
     * @throws IOException IO 异常
     */
    protected void mapperXml(String createBeanName, String tableName) throws IOException {
        createBeanName = BeanUtils.underlineToUpperCase(createBeanName);
        this.entityName = createBeanName;
        DbdToMvcDefinition definition = DbdToBeanContext.getDbdToMvcDefinition();
        File file = new File(System.getProperty("user.dir") + "/" + definition.getModulesName() + "/" + definition.getMapperPath() + BeanUtils.packageToPath(definition.getMapperXmlLocation()));
        file.mkdir();
        String createFileName = parseMvcName(definition, createBeanName, MAVEN_MAPPER_XML_HONE);
        file = new File(file + "/" + createFileName + ".xml");
        FileWriter fw = new FileWriter(file);
        fw.write(createXmlStart(tableName));
        fw.flush();
        fw.close();
    }

    /**
     * 生成 Mapper 层 xml 文件开头配置
     *
     * @return 内容
     */
    private String createXmlStart(String tableName) {
        return "<?xml version=" + BeanUtils.addColon("1.0") + " encoding="  + BeanUtils.addColon("UTF-8") + "?>" + oneLine + 
                "<!DOCTYPE mapper" + oneLineAndTwoTab + 
                xmlPublicAndHttp +
                ">" + oneLine +  
                "<mapper namespace = " + BeanUtils.addColon(DbdToBeanContext.getDbdToMvcDefinition().getMapperLocation() + "." + interfacesName) + ">" + 
                (DbdToBeanContext.getDbdToMvcDefinition().isGenerateCurd() ? twoLineAndOneTab + this.createXmlBeans(tableName) : "") +
                twoLine + "</mapper>";
    }

    /**
     * 初始化获取数据库数据信息，创建 Mapper 的 Bean
     * @param tableName 表名
     * @return 内容
     */
    private String createXmlBeans(String tableName) {
        // 初始化获取数据库数据信息
        try {
            this.getMetaData(tableName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return this.createResultMap(tableName) + this.createSqlLabel() + this.createXmlBeansCurd(tableName);
    }

    /**
     * 生成 Mapper 层 xml 文件 CURD 目录
     *
     * @return 内容
     */
    private String createXmlBeansCurd(String tableName) {
        return "<select id = " + BeanUtils.addColon("query" + entityName + "ById") +
                " parameterType = " + BeanUtils.addColon(DbdToBeanContext.getDbdToMvcDefinition().getEntityLocation() + "." + entityName) + 
                " resultMap = " + BeanUtils.addColon(RESULT_MAP_ID) + ">" +
                oneLineAndTwoTab + this.createQueryOne(tableName) +
                oneLineAndOneTab + "</select>" + twoLineAndOneTab + 

                "<select id = " + BeanUtils.addColon("query" + entityName + "List") +
                " resultMap = " + BeanUtils.addColon(RESULT_MAP_ID) + ">" +
                oneLineAndTwoTab + this.createQueryList(tableName) +
                oneLineAndOneTab + "</select>" + twoLineAndOneTab +

                "<insert id = " + BeanUtils.addColon("insert" + entityName) +
                " parameterType = " + BeanUtils.addColon(DbdToBeanContext.getDbdToMvcDefinition().getEntityLocation() + "." + entityName) + ">" +
                oneLineAndTwoTab + this.createInsert(tableName) +
                oneLineAndOneTab + "</insert>" + twoLineAndOneTab +

                "<update id = " + BeanUtils.addColon("update" + entityName) +
                " parameterType = " + BeanUtils.addColon(DbdToBeanContext.getDbdToMvcDefinition().getEntityLocation() + "." + entityName) + ">" +
                oneLineAndTwoTab + this.createUpdate(tableName) +
                oneLineAndOneTab + "</update>" + twoLineAndOneTab +

                "<delete id = " + BeanUtils.addColon("delete" + entityName + "ById") +
                " parameterType = " + BeanUtils.addColon(DbdToBeanContext.getDbdToMvcDefinition().getEntityLocation() + "." + entityName) + ">" +
                oneLineAndTwoTab + this.createDelete(tableName) +
                oneLineAndOneTab + "</delete>";
    }

    /**
     * 创建 ResultMap 标签及其内容
     * @param tableName 表名
     * @return 内容
     */
    private String createResultMap(String tableName) {
        StringBuilder sb = new StringBuilder("<resultMap id = ");
        sb.append(BeanUtils.addColon(RESULT_MAP_ID)).append(" type = ")
                .append(BeanUtils.addColon(DbdToBeanContext.getDbdToMvcDefinition().getEntityLocation() + "." + entityName))
                .append(">").append(oneLineAndTwoTab);
        List<String> primaryKeys = getPrimaryKeys(tableName);
        // 主键存在，则是 id 标签
        if(!primaryKeys.isEmpty()) {
            for (String primaryKey : primaryKeys) {
                sb.append("<id column = ").append(BeanUtils.addColon(primaryKey))
                        .append(" property = ").append(BeanUtils.addColon(BeanUtils.parseFieldName(primaryKey)))
                        .append(" />").append(oneLineAndTwoTab);
            }
        }
        try {
            if(BeanUtils.isNotEmpty(databaseMetaData)) {
                for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
                    if(columnIsPrimaryKey(tableName, resultSetMetaData.getColumnName(i))) {
                        continue;
                    }
                    sb.append("<result column = ").append(BeanUtils.addColon(resultSetMetaData.getColumnName(i))).append("")
                            .append(" property = ").append(BeanUtils.addColon(BeanUtils.parseFieldName(resultSetMetaData.getColumnName(i))))
                            .append(" />").append(oneLineAndTwoTab);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        sb.delete(sb.length() - 1, sb.length());
        sb.append("</resultMap>").append(twoLineAndOneTab);
        return sb.toString();
    }
    
    /**
     * 创建 sql 标签及其内容
     * @return 内容
     */
    private String createSqlLabel() {
        StringBuilder sb = new StringBuilder("<sql id = ");
        sb.append(BeanUtils.addColon(SQL_ID)).append(">").append(oneLineAndTwoTab);
        try {
            if(BeanUtils.isNotEmpty(databaseMetaData)) {
                for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
                    sb.append(resultSetMetaData.getColumnName(i)).append(", ");
                    if(i == DbdToBeanContext.getDbdToMvcDefinition().getColumnNum()) {
                        sb.append(oneLineAndTwoTab);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        sb.delete(sb.length() - 2, sb.length());
        sb.append(oneLineAndOneTab).append("</sql>");
        sb.append(twoLineAndOneTab);
        return sb.toString();
    }
    
    /**
     * 创建 select 标签及其内容：通过 ID 查询数据
     * @param tableName 表名
     * @return 内容
     */
    private String createQueryOne(String tableName) {
        if (BeanUtils.isNotEmpty(databaseMetaData)) {
            StringBuilder sb = new StringBuilder("select");
            sb.append(oneLineAndThreeTab).append("<include refid = ").append(BeanUtils.addColon(SQL_ID))
                    .append(" />").append(oneLineAndTwoTab)
                    .append("from ").append(tableName);
            this.createWhereClause(sb, tableName);
            return sb.toString();
        }
        return null;
    }
    
    /**
     * 创建 select 标签及其内容：查询全部数据
     * @param tableName 表名
     * @return 内容
     */
    private String createQueryList(String tableName) {
        return BeanUtils.isNotEmpty(databaseMetaData) ?
                "select" + oneLineAndThreeTab + "<include refid = " + BeanUtils.addColon(SQL_ID)
                        + " />" + oneLineAndTwoTab + "from " + tableName : null;
        
    }

    /**
     * 创建 insert 标签及其内容
     * @param tableName 表名
     * @return 内容
     */
    private String createInsert(String tableName) {
        if (BeanUtils.isNotEmpty(databaseMetaData)) {
            StringBuilder sb = new StringBuilder("insert into " + tableName);
            sb.append(oneLineAndTwoTab).append("<trim prefix = ").append(BeanUtils.addColon("("))
                    .append(" suffix = ").append(BeanUtils.addColon(")"))
                    .append(" suffixOverrides = ").append(BeanUtils.addColon(","))
                    .append(">").append(oneLineAndThreeTab);
            try {
                for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
                    if(columnIsPrimaryKey(tableName, resultSetMetaData.getColumnName(i))) {
                        continue;
                    }
                    sb.append("<if test = ")
                            .append(BeanUtils.addColon(BeanUtils.parseFieldName(resultSetMetaData.getColumnName(i)) + "!= null"))
                            .append(">").append(oneLineAndFourTab)
                            .append(resultSetMetaData.getColumnName(i))
                            .append(",").append(oneLineAndThreeTab)
                            .append("</if>").append(oneLineAndThreeTab);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            sb.delete(sb.length() - 1, sb.length());
            sb.append("</trim>").append(oneLineAndTwoTab).append("<trim prefix = ")
                    .append(BeanUtils.addColon("values (")).append(" suffix = ")
                    .append(BeanUtils.addColon(")")).append(" suffixOverrides = ")
                    .append(BeanUtils.addColon(",")).append(">").append(oneLineAndThreeTab);
            try {
                for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
                    if(columnIsPrimaryKey(tableName, resultSetMetaData.getColumnName(i))) {
                        continue;
                    }
                    sb.append("<if test = ").append(BeanUtils.addColon(BeanUtils.parseFieldName(resultSetMetaData.getColumnName(i) + " != null")))
                            .append(">").append(oneLineAndFourTab)
                            .append("#{")
                            .append(BeanUtils.parseFieldName(resultSetMetaData.getColumnName(i)))
                            .append("},").append(oneLineAndThreeTab).append("</if>").append(oneLineAndThreeTab);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            sb.delete(sb.length() - 1,sb.length());
            sb.append("</trim>");
            return sb.toString();
        }
        return null;
    }
    
    /**
     * 创建 update 标签及其内容
     * @param tableName 表名
     * @return 内容
     */
    private String createUpdate(String tableName) {
        if (BeanUtils.isNotEmpty(databaseMetaData)) {
            StringBuilder sb = new StringBuilder("update " + tableName);
            sb.append(oneLineAndTwoTab).append("<set>").append(oneLineAndThreeTab);
            try {
                for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
                    if(columnIsPrimaryKey(tableName, resultSetMetaData.getColumnName(i))) {
                        continue;
                    }
                    sb.append("<if test = ").append(BeanUtils.addColon(BeanUtils.parseFieldName(resultSetMetaData.getColumnName(i) + " != null")))
                            .append(">").append(oneLineAndFourTab)
                            .append(resultSetMetaData.getColumnName(i)).append(" = #{")
                            .append(BeanUtils.parseFieldName(resultSetMetaData.getColumnName(i)))
                            .append("},").append(oneLineAndThreeTab).append("</if>").append(oneLineAndThreeTab);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            sb.delete(sb.length() - 1,sb.length());
            sb.append("</set>").append(oneLineAndTwoTab);
            this.createWhereClause(sb, tableName);
            return sb.toString();
        }
        return null;
    }

    /**
     * 创建 delete 标签及其内容
     * @param tableName 表名
     * @return 内容
     */
    private String createDelete(String tableName) {
        StringBuilder sb = new StringBuilder( "delete from " + tableName);
        this.createWhereClause(sb, tableName);
        return sb.toString();
    }
    /**
     * 创建 where 标签及其内容
     * @param sb 内容
     * @param tableName 表名
     */
    private void createWhereClause(StringBuilder sb, String tableName) {
        List<String> primaryKeys = getPrimaryKeys(tableName);
        sb.append(" where ");
        try {
            // 主键不存在，则取第一个字段作为条件
            if (primaryKeys.isEmpty()) {
                sb.append(resultSetMetaData.getColumnName(1)).append(" = #{").append(BeanUtils.parseFieldName(resultSetMetaData.getColumnName(1))).append("}, ");
            }else {
                // 主键存在
                for (String primaryKey : primaryKeys) {
                    sb.append(primaryKey).append(" = #{").append(BeanUtils.parseFieldName(primaryKey)).append("} and ");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        sb.delete(sb.length() - 5, sb.length());
    }

    /**
     * 获取数据源信息
     * @param tableName 表名
     */
    private void getMetaData(String tableName) throws SQLException {
        Connection connection = DbdToBeanContext.getDbdToBeanProperties().getConn();
        databaseMetaData = connection.getMetaData();
        String sql = "select * from `" + tableName + "`";
        PreparedStatement stmt = connection.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        if(rs.next()) {
            resultSetMetaData = rs.getMetaData();
        }
        BeanUtils.close(rs, stmt);
    }

    /**
     * 获取表的主键
     * @param tableName 表名
     * @return 主键集合
     */
    private List<String> getPrimaryKeys(String tableName){
        if(null != databaseMetaData) {
            try(ResultSet rs = databaseMetaData.getPrimaryKeys(null, null, tableName)){
                if (null == rs) {
                    return Collections.emptyList();
                }
                List<String> pks = new ArrayList<>();
                while (rs.next()) {
                    String pkStr = rs.getString("COLUMN_NAME");
                    pks.add(pkStr);
                }
                return pks;
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return Collections.emptyList();
    }
    
    /**
     * 判断当前字段是否是主键
     * @param tableName 表名
     * @param columnName 字段
     * @return true：该字段是主键，false：该字段不是主键
     */
    private boolean columnIsPrimaryKey(String tableName, String columnName) {
        List<String> primaryKeys = getPrimaryKeys(tableName);
        if(!primaryKeys.isEmpty()) {
            for (String primaryKey : primaryKeys) {
                if(primaryKey.equals(columnName)) {
                    return true;
                }
            }
        }
        return false;
    }
}
