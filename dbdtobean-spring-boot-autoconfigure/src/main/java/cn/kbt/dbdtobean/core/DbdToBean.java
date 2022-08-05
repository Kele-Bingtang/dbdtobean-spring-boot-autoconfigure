package cn.kbt.dbdtobean.core;


import cn.kbt.dbdtobean.config.DbdToBeanProperties;
import cn.kbt.dbdtobean.log.DBDToBeanAPI;
import cn.kbt.dbdtobean.log.DBDToBeanLog;
import cn.kbt.dbdtobean.mvcbean.AbstractDbdToMVC;
import cn.kbt.dbdtobean.mvcbean.DbdToMVC;
import cn.kbt.dbdtobean.mvcbean.DbdToMVCDefinition;
import cn.kbt.dbdtobean.utils.BeanUtils;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.*;


public class DbdToBean extends DbdToBeanCore {

    private boolean isMultimediaContent = false;

    public DbdToBean() {
        this(null);
    }

    public DbdToBean(String createBeanName) {
        DbdToBeanContext.getDbdToBeanDefinition().setCreateBeanName(createBeanName);
    }

    public void setAuthorName(String authorName) {
        DbdToBeanContext.getDbdToBeanProperties().setAuthorName(authorName);
    }

    public void setAuthorName(boolean authorName) {
        if(!authorName){
            DbdToBeanContext.getDbdToBeanProperties().setAuthorName(null);
        }
    }

    public void setCreateBeanName(String createBeanName) {
        DbdToBeanContext.getDbdToBeanDefinition().setCreateBeanName(createBeanName);
    }
    @Override
    public String generateAttrFromTable(String tableName, boolean isConstructor, boolean isSetAndGet, boolean isToString) throws SQLException {
        return super.generateAttrFromTable(tableName, isConstructor, isSetAndGet, isToString);
    }

    public String generateAttrFromTable(String tableName) throws SQLException {
        return generateAttrFromTable(tableName, true, true, true);
    }

    public String generateAttrFromTable(String tableName, boolean isConstructor) throws SQLException {
        return generateAttrFromTable(tableName, isConstructor, true, true);
    }

    public String generateAttrFromTable(String tableName, boolean isConstructor, boolean isSetAndGet) throws SQLException {
        return generateAttrFromTable(tableName, isConstructor, isSetAndGet, true);
    }

    public void setPackageName(String packageName) {
        DbdToBeanContext.getDbdToBeanDefinition().setPackageName(packageName);
    }

    public void setPackageName(String packageName, boolean jarPackage) {
        DbdToBeanContext.getDbdToBeanDefinition().setPackageNameAndJarPackage(packageName,jarPackage);
    }

    public void setJarPackage(boolean jarPackage) {
        DbdToBeanContext.getDbdToBeanDefinition().setJarPackage(jarPackage);
    }

    public void setHeadComment(boolean setHeadComment) {
        DbdToBeanContext.getDefaultComment().setSetHeadComment(setHeadComment);
    }

    public void setHeadComment(String headComment) {
        DbdToBeanContext.getDbdToBeanDefinition().getHeadComment().setHeadComment(headComment);
    }

    public void setCommentType(String commentType) {
        DbdToBeanContext.getCustomComment().setCommentType(commentType);
    }

    public void setHeadComment(String headComment, String commentType) {
        DbdToBeanContext.getDbdToBeanDefinition().getHeadComment().setHeadComment(headComment,commentType);
    }

    public void setAllComments(boolean generateAllComment) {
        DbdToBeanContext.getDefaultComment().setAllComments(generateAllComment);
    }

    public void setComment(boolean isFieldComment, boolean isConstructorComment, boolean isSetAndGetComment, boolean isToStringComment) {
        DbdToBeanContext.getDefaultComment().setComment(isFieldComment, isConstructorComment, isSetAndGetComment, isToStringComment);
    }

    public void setFieldComment(boolean isFieldComment) {
        setComment(isFieldComment, DbdToBeanContext.getDefaultComment().isConstructorComment(), DbdToBeanContext.getDefaultComment().isSetAndGetComment(), DbdToBeanContext.getDefaultComment().isToStringComment());
    }

    public void setFiledComment(List<String> filedComment) {
        DbdToBeanContext.getCustomComment().setFiledComment(filedComment);
    }

    public void setConstructorComment(boolean isConstructorComment) {
        setComment(DbdToBeanContext.getDefaultComment().isFieldComment(), isConstructorComment, DbdToBeanContext.getDefaultComment().isSetAndGetComment(), DbdToBeanContext.getDefaultComment().isToStringComment());
    }

    public void setConstructorComment(String constructorComment) {
        DbdToBeanContext.getCustomComment().setParamConstructorComment(constructorComment);
    }

    public void setConstructorComment(String nullConstructorComment, String constructorComment) {
        DbdToBeanContext.getCustomComment().setConstructorComment(nullConstructorComment,constructorComment);
    }

    public void setSetAndGetComment(boolean isSetAndGetComment) {
        setComment(DbdToBeanContext.getDefaultComment().isFieldComment(), DbdToBeanContext.getDefaultComment().isConstructorComment(), isSetAndGetComment, DbdToBeanContext.getDefaultComment().isToStringComment());
    }

    public void setSetAndGetComment(List<String> SetComment, List<String> GetComment) {
        DbdToBeanContext.getCustomComment().setSetAndGetComment(SetComment,GetComment);
    }

    public void setSetComment(List<String> SetComment) {
        DbdToBeanContext.getCustomComment().setSetComment(SetComment);
    }

    public void setGetComment(List<String> GetComment) {
        DbdToBeanContext.getCustomComment().setGetComment(GetComment);
    }

    public void setToStringComment(boolean isToStringComment) {
        setComment(DbdToBeanContext.getDefaultComment().isFieldComment(), DbdToBeanContext.getDefaultComment().isConstructorComment(), DbdToBeanContext.getDefaultComment().isSetAndGetComment(), isToStringComment);
    }

    public void setToStringComment(String toStringComment) {
        DbdToBeanContext.getCustomComment().setToStringComment(toStringComment);
    }

    public void setBeanFirstNameIsUp(boolean beanFirstNameUp) {
        DbdToBeanContext.getDbdToBeanDefinition().setBeanFirstNameUp(beanFirstNameUp);
    }

    public void setFieldNameAllLower(boolean fieldNameAllLower) {
        DbdToBeanContext.getDbdToBeanDefinition().setFieldNameAllLower(fieldNameAllLower);
    }

    public void generateAttrFromDataBase(boolean fieldNameAllLower) throws SQLException, IOException {
        DbdToBeanContext.getDbdToBeanDefinition().setFieldNameAllLower(fieldNameAllLower);
        generateAttrFromDataBase(null, true, true, true);
    }

    public HashMap<String, String> generateAttrFromDataBase() throws SQLException, IOException {
        return generateAttrFromDataBase(getConnection().getCatalog(), true, true, true);
    }

    public HashMap<String, String> generateAttrFromDataBase(String dateBaseName) throws SQLException, IOException {
        return generateAttrFromDataBase(dateBaseName, true, true, true);
    }

    public HashMap<String, String> generateAttrFromDataBase(String dateBaseName, boolean isConstructor) throws SQLException, IOException {
        return generateAttrFromDataBase(dateBaseName, isConstructor, true, true);
    }

    public HashMap<String, String> generateAttrFromDataBase(String dateBaseName, boolean isConstructor, boolean isSetAndGet) throws SQLException, IOException {
        return generateAttrFromDataBase(dateBaseName, isConstructor, isSetAndGet, true);
    }
    @Override
    public HashMap<String, String> generateAttrFromDataBase(String dateBaseName, boolean isConstructor, boolean isSetAndGet, boolean isToString) throws SQLException, IOException {
        return super.generateAttrFromDataBase(dateBaseName, isConstructor, isSetAndGet, isToString);
    }

    public String exportToFile(String fileContent) throws IOException {
        String createPath = exportToFile(fileContent, null, null);
        System.out.println("创建【" + DbdToBeanContext.getDbdToBeanDefinition().getCreateBeanName() + "】文件成功，位于：" + new File(createPath).getAbsolutePath());
        return createPath;
    }

    public String exportToFile(String fileContent, String path) throws IOException {
        String createPath = exportToFile(fileContent, path, null);
        System.out.println("创建【" + DbdToBeanContext.getDbdToBeanDefinition().getCreateBeanName() + "】文件成功，位于：" + new File(createPath).getAbsolutePath());
        return createPath;
    }
    @Override
    public String exportToFile(String fileContent, String path, String dirName) throws IOException {
        if(!isMultimediaContent){
            if(BeanUtils.isEmpty(path)){
                if(BeanUtils.isEmpty(dirName) && BeanUtils.isEmpty(DbdToBeanContext.getDbdToMVCDefinition().getEntityLocation())){
                    System.out.println("正在【电脑桌面】路径下为您创建【JavaBean文件】" );
                }else if(BeanUtils.isEmpty(DbdToBeanContext.getDbdToMVCDefinition().getEntityLocation())){
                    System.out.println("正在【电脑桌面】路径下的【" + dirName + "】文件夹里为您创建【JavaBean文件】" );
                }
            }else if(BeanUtils.isEmpty(dirName)){
                System.out.println("正在【" + path + "】路径下为您创建【JavaBean文件】" );
            }else {
                System.out.println("正在【" + path + "】路径下的【" + dirName + "】文件夹里为您创建【JavaBean文件】" );
            }
        }
        String createPath = super.exportToFile(fileContent, path, dirName);
        DbdToMVC dbdToMVC = new DbdToMVC();
        dbdToMVC.dbdToMVC();
        return createPath;
    }

    public String exportToFile(HashMap<String,String> fileContentMap) throws IOException, SQLException {
        return this.exportToFile(fileContentMap,null,null);
    }

    public String exportToFile(HashMap<String,String> fileContentMap, String path) throws IOException, SQLException {
        return this.exportToFile(fileContentMap,path,null);
    }

    public String exportToFile(HashMap<String,String> fileContentMap, String path, String dirName) throws IOException, SQLException {
        isMultimediaContent = true;
        String createPath = "";
        System.out.println("系统检测到提供的数据库共有【" + fileContentMap.size() + "】个表，准备生成文件");
        if ((BeanUtils.isEmpty(dirName) || dirName.equals(" ")) && BeanUtils.isEmpty(path)) {
            // 默认生成一个文件夹，以数据库名字+随机数字为文件夹名
            if(BeanUtils.isEmpty(DbdToBeanContext.getDbdToMVCDefinition().getEntityLocation())){
                dirName = super.getConnection().getMetaData().getDatabaseProductName() + "_" + BeanUtils.randomNum();
            }
            // 生成文件夹
            createPath = super.exportToFile("", path, dirName);
            System.out.println("在【" + createPath + "】处创建成功！" );
        }
        int i = 1;
        path = path != null ? path : super.beanLocation().getPath();
        System.out.println("正在【" + path + "】路径下" + (dirName == null?"" : "的【" + dirName + "】文件夹里") + "为您创建【JavaBean文件】:" );
        for (Map.Entry<String, String> entry : fileContentMap.entrySet()) {
            System.out.println("正在创建第【" + i++ + "】个文件：" + entry.getKey());
            createPath = super.exportToFiles(entry.getKey(), entry.getValue(), path, dirName);
        }
        DbdToMVC dbdToMVC = new DbdToMVC();
        dbdToMVC.dbdToMVC();
        System.out.println("所有文件创建完毕，位于" + new File(createPath).getAbsolutePath());
        return createPath;
    }

    public void set_ToUpper(boolean _ToUpper) {
        DbdToBeanContext.getDbdToBeanDefinition().setLowerCamelCase(_ToUpper);
    }

    public String firstCharToUpperCase(String fieldName) {
        return BeanUtils.firstCharToUpperCase(fieldName);
    }

    public String firstCharToLowerCase(String fieldName) {
        return BeanUtils.firstCharToLowerCase(fieldName);
    }

    public String _CharToUpperCase(String name) {
        return BeanUtils.underlineToUpperCase(name);
    }

    public void setDbdToBeanProperties(String driverName, String url, String username, String password) {
        DbdToBeanContext.getDbdToBeanProperties().setDriverName(driverName);
        DbdToBeanContext.getDbdToBeanProperties().setUrl(url);
        DbdToBeanContext.getDbdToBeanProperties().setUsername(username);
        DbdToBeanContext.getDbdToBeanProperties().setPassword(password);
    }

    public void setModulesName(String modulesName) {
        DbdToBeanContext.getDbdToMVCDefinition().setModulesName(modulesName);
    }

    public void setEntityLocation(String entityLocation) {
        DbdToBeanContext.getDbdToMVCDefinition().setEntityLocation(entityLocation);
    }

    public void setControllerLocation(String controllerLocation) {
        DbdToBeanContext.getDbdToMVCDefinition().setControllerLocation(controllerLocation);
    }

    public void setServiceLocation(String serviceLocation) {
        DbdToBeanContext.getDbdToMVCDefinition().setServiceLocation(serviceLocation);
    }

    public void setDaoLocation(String daoLocation) {
        DbdToBeanContext.getDbdToMVCDefinition().setDaoLocation(daoLocation);
    }

    public void setMapperLocation(String mapperLocation) {
        DbdToBeanContext.getDbdToMVCDefinition().setMapperLocation(mapperLocation);
    }

    public String getMapperXMLLocation() {
        return DbdToBeanContext.getDbdToMVCDefinition().getMapperXmlLocation();
    }

    public void setMapperXMLLocation(String mapperXMLLocation) {
        DbdToBeanContext.getDbdToMVCDefinition().setMapperXmlLocation(mapperXMLLocation);
    }

    public void setConnection(Connection conn) {
        DbdToBeanContext.getDbdToBeanProperties().setConn(conn);
    }

    public void closeConnection() throws SQLException {
        if (null != DbdToBeanContext.getDbdToBeanProperties().getConn()) {
            DbdToBeanContext.getDbdToBeanProperties().getConn().close();
        }
    }

    public void closeConnection(Connection connection) {
        try {
            if (null != connection) {
                connection.close();
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    public void setDbdToBeanProperties(DbdToBeanProperties dbdToBeanPorperties){
        DbdToBeanContext.setDbdToBeanProperties(dbdToBeanPorperties);
    }

    public String getDateBaseType() throws SQLException {
        super.parseDateBaseTypeAndGetSQL("");
        return DbdToBeanContext.getDbdToBeanDefinition().getDateBaseType();
    }

    // ----------------------------------- DBDToMVC -----------------------------------

    public void setPrefix(String prefix) {
        DbdToBeanContext.getDbdToMVCDefinition().setPrefix(prefix);
    }

    public void setSuffix(String suffix) {
        DbdToBeanContext.getDbdToMVCDefinition().setSuffix(suffix);
    }

    public void setControllerPre(String controllerPre) {
        DbdToBeanContext.getDbdToMVCDefinition().setControllerPre(controllerPre);
    }

    public void setControllerSuf(String controllerSuf) {
        DbdToBeanContext.getDbdToMVCDefinition().setControllerSuf(controllerSuf);
    }

    public void setServiceInterPre(String servicePre) {
        DbdToBeanContext.getDbdToMVCDefinition().setServiceInterPre(servicePre);
    }

    public void setServiceInterSuf(String serviceSuf) {
        DbdToBeanContext.getDbdToMVCDefinition().setServiceInterSuf(serviceSuf);
    }

    public void setServiceImplPre(String servicePre) {
        DbdToBeanContext.getDbdToMVCDefinition().setServiceImplPre(servicePre);
    }

    public void setServiceImplSuf(String serviceSuf) {
        DbdToBeanContext.getDbdToMVCDefinition().setServiceImplSuf(serviceSuf);
    }

    public void setDaoInterPre(String daoPre) {
        DbdToBeanContext.getDbdToMVCDefinition().setDaoInterPre(daoPre);
    }

    public void setDaoInterSuf(String daoSuf) {
        DbdToBeanContext.getDbdToMVCDefinition().setDaoInterSuf(daoSuf);
    }

    public void setDaoImplPre(String daoPre) {
        DbdToBeanContext.getDbdToMVCDefinition().setDaoImplPre(daoPre);
    }

    public void setDaoImplSuf(String daoSuf) {
        DbdToBeanContext.getDbdToMVCDefinition().setDaoImplSuf(daoSuf);
    }


    public void setMapperInterPre(String mapperPre) {
        DbdToBeanContext.getDbdToMVCDefinition().setMapperInterPre(mapperPre);
    }

    public void setMapperInterSuf(String mapperSuf) {
        DbdToBeanContext.getDbdToMVCDefinition().setMapperInterSuf(mapperSuf);
    }

    public void setMapperXmlPre(String mapperPre) {
        DbdToBeanContext.getDbdToMVCDefinition().setMapperXmlPre(mapperPre);
    }

    public void setMapperXmlSuf(String mapperSuf) {
        DbdToBeanContext.getDbdToMVCDefinition().setMapperXmlSuf(mapperSuf);
    }

    public DbdToBeanDefinition getDbdToBeanDefinition() {
        return DbdToBeanContext.getDbdToBeanDefinition();
    }

    public List<DbdToBeanDefinition> getDbdToBeanDefinitions() {
        return DbdToBeanContext.getDbdToBeanDefinitions();
    }

    public DbdToMVCDefinition getDbdToMVCDefinition() {
        return DbdToBeanContext.getDbdToMVCDefinition();
    }

    public void setGenerateCURD(boolean generateCURD) {
        DbdToBeanContext.getDbdToMVCDefinition().setGeneratecurd(generateCURD);
    }

    public void setMVImplName(String implLocation){
        AbstractDbdToMVC.IMPL_NAME = implLocation;
    }

    public void setXmlPublicAndHttp(String xmlPublicAndHttp){
        AbstractDbdToMVC.xmlPublicAndHttp = xmlPublicAndHttp;
    }

    public void setMavenOrSimple(boolean mavenOrSimple) {
        DbdToBeanContext.getDbdToMVCDefinition().setMavenOrSimple(mavenOrSimple);
    }

    public void setGenerateRequestBody(boolean generateRequestBody) {
        DbdToBeanContext.getDbdToMVCDefinition().setGenerateRequestBody(generateRequestBody);
    }

    public void setColumnNum(int columnNum) {
        DbdToBeanContext.getDbdToMVCDefinition().setColumnNum(columnNum);
    }

    // ----------------------------------- log -----------------------------------

    public DBDToBeanLog getLogInfo(){
        return new DBDToBeanLog();
    }

    public DBDToBeanAPI getDBDToBeanAPI(){
        return new DBDToBeanAPI();
    }

    // ----------------------------------- Utils -----------------------------------

    public void explain(ResultSet rs) throws SQLException {
        BeanUtils.explain(rs);
    }

}
