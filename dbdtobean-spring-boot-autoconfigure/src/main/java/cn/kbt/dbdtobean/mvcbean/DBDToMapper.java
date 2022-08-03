package cn.kbt.dbdtobean.mvcbean;

import cn.kbt.dbdtobean.core.DBDToBeanContext;
import cn.kbt.dbdtobean.core.DBDToBeanDefinition;
import cn.kbt.dbdtobean.utils.DBDToBeanUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kele-Bing
 * @version 1.0
 * @since 2021/9/21 23:09
 */
public class DBDToMapper extends AbstractDBDToMVC {

    protected final static String MAPPER_INTERFACE_NAME = "Mapper";
    protected final static String MAVEN_MAPPER_XML_HONE = "src\\main\\resources\\";
    protected final static String SIMPLE_MAPPER_XML_HONE = "src\\";
    protected static String interfacesName = null;
    private String entityName;
    private ResultSetMetaData resultSetMetaData;
    
    private DatabaseMetaData databaseMetaData;

    /**
     * 生成Mapper层接口目录以及内容
     *
     * @param createBeanName 文件名
     * @return 内容
     * @throws IOException IO 异常
     */
    public String mapperInterfaces(String createBeanName) throws IOException {
        createBeanName = DBDToBeanUtils._CharToUpperCase(createBeanName);
        DBDToMVCDefinition definition = DBDToBeanContext.getDbdToMVCDefinition();
        interfacesName = super.createInterfaces(definition, createBeanName, MAPPER_INTERFACE_NAME);
        return interfacesName;
    }

    /**
     * 生成Mapper层xml文件目录以及内容
     *
     * @param createBeanName 文件名
     * @throws IOException IO 异常
     */
    public void mapperXML(String createBeanName, String tableName) throws IOException {
        createBeanName = DBDToBeanUtils._CharToUpperCase(createBeanName);
        this.entityName = createBeanName;
        DBDToMVCDefinition definition = DBDToBeanContext.getDbdToMVCDefinition();
        File file = new File(System.getProperty("user.dir") + "\\" + definition.getModulesName() + "\\" + definition.getMapperPath() + DBDToBeanUtils.packageToPath(definition.getMapperXmlLocation()));
        boolean mkdir = file.mkdir();
        String createFileName = parseMVCName(definition, createBeanName, MAVEN_MAPPER_XML_HONE);
        file = new File(file + "\\" + createFileName + ".xml");
        FileWriter fw = new FileWriter(file);
        fw.write(createXmlStart(tableName));
        fw.flush();
        fw.close();
    }

    /**
     * 生成Mapper层xml文件开头配置
     *
     * @return 内容
     */
    public String createXmlStart(String tableName) {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<!DOCTYPE mapper\n\t\t" +
                xmlPublicAndHttp +
                ">\n" +
                "<mapper namespace = \"" +
                DBDToBeanContext.getDbdToMVCDefinition().getMapperLocation() + "." + interfacesName +
                "\">" + (DBDToBeanContext.getDbdToMVCDefinition().isGeneratecurd() ? "\n\n\t" + createXmlBeansCURD(tableName) : "") +
                "\n\n</mapper>";
    }

    /**
     * 生成Mapper层xml文件CURD目录
     *
     * @return 内容
     */
    public String createXmlBeansCURD(String tableName) {
        try {
            this.getMetaData(tableName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return "<select id=\"query" + entityName + "ById\" " +
                " parameterType=\"string\"" +
                " resultType=\"" +
                DBDToBeanContext.getDbdToMVCDefinition().getEntityLocation() + "." + entityName + "\">" +
                "\n\t\t" + createQueryOne(tableName) + "\n" +
                "\t</select>\n\n" + "\t" +

                "<select id=\"query" + entityName + "List\"" +
                " resultType=\"" +
                DBDToBeanContext.getDbdToMVCDefinition().getEntityLocation() + "." + entityName + "\">" +
                "\n\t\t\n" + "\t</select>\n\n" + "\t" +

                "<insert id=\"insert" + entityName + "\"" +
                " parameterType=\"" +
                DBDToBeanContext.getDbdToMVCDefinition().getEntityLocation() + "." + entityName + "\">" +
                "\n\t\t\n" + "\t</insert>\n\n" + "\t" +

                "<update id=\"update" + entityName + "\"" +
                " parameterType=\"" +
                DBDToBeanContext.getDbdToMVCDefinition().getEntityLocation() + "." + entityName + "\">" +
                "\n\t\t\n" + "\t</update>\n\n" + "\t" +

                "<delete id=\"delete" + entityName + "ById\"" +
                " parameterType=\"string\">" +
                "\n\t\t\n" + "\t</delete>";
    }
    
    public String createQueryOne(String tableName) {
        StringBuilder sb = new StringBuilder("select ");
        try {
            for (int i = 0; i < resultSetMetaData.getColumnCount(); i++) {
                sb.append(resultSetMetaData.getColumnName(i)).append(", ");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        sb.deleteCharAt(sb.length() - 2);
        sb.append("\n\t\tfrom ").append(tableName).append(" where ");
        for (String primaryKey : getPrimaryKeys(tableName)) {
            sb.append(primaryKey).append(" = #{").append(parseFieldName(primaryKey)).append("}, ");
        }
        sb.deleteCharAt(sb.length() - 2);
        return sb.toString();
    }
    
    public void createQueryList() {
        
    }
    public void createInsert() {

    }
    public void createUpdate() {

    }
    public void createDelete() {

    }
    
    public void getMetaData(String tableName) throws SQLException {
        Connection connection = DBDToBeanContext.getDbdToBeanProperties().getConn();
        databaseMetaData = connection.getMetaData();
        PreparedStatement stmt = connection.prepareStatement("select * from " + tableName);
        ResultSet rs = stmt.executeQuery();
        resultSetMetaData = rs.getMetaData();
        DBDToBeanUtils.close(rs, stmt, connection);
    }

    public List<String> getPrimaryKeys(String tableName){
        try(ResultSet rs = databaseMetaData.getPrimaryKeys(null, null, tableName)){
            if (null == rs) {
                System.out.printf("表 " + tableName + " 没有主键");
            }
            List<String> pks = new ArrayList<String>();
            while (rs.next()) {
                String pkStr = rs.getString("COLUMN_NAME");
                pks.add(pkStr);
            }
            return pks;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private String parseFieldName(String content) {
        DBDToBeanDefinition definition = DBDToBeanContext.getDbdToBeanDefinition();
        if (definition.isFieldNameAllLower() && definition.is_ToUpper()) {
            return DBDToBeanUtils._CharToUpperCase(content.toLowerCase());
        } else if ((definition.isFieldNameAllLower())) {
            return content.toLowerCase();
        } else if (definition.is_ToUpper()) {
            return DBDToBeanUtils._CharToUpperCase(content);
        } else {
            return content;
        }
    }
}
