package cn.kbt.dbdtobean.core;


import cn.kbt.dbdtobean.config.DbdToBeanProperties;
import cn.kbt.dbdtobean.log.DbdToBeanAPI;
import cn.kbt.dbdtobean.log.DbdToBeanLog;
import cn.kbt.dbdtobean.mvcbean.AbstractDbdToMVC;
import cn.kbt.dbdtobean.mvcbean.DbdToMvc;
import cn.kbt.dbdtobean.mvcbean.DbdToMvcDefinition;
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


public class DbdToBean extends DbdToBeanCore {

    private static final Logger logger = LoggerFactory.getLogger(DbdToBean.class);

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
    public String createBeanFromTable(String tableName, boolean isConstructor, boolean isSetAndGet, boolean isToString) throws SQLException {
        return super.createBeanFromTable(tableName, isConstructor, isSetAndGet, isToString);
    }

    public String createBeanFromTable(String tableName) throws SQLException {
        return createBeanFromTable(tableName, true, true, true);
    }

    public String createBeanFromTable(String tableName, boolean isConstructor) throws SQLException {
        return createBeanFromTable(tableName, isConstructor, true, true);
    }

    public String createBeanFromTable(String tableName, boolean isConstructor, boolean isSetAndGet) throws SQLException {
        return createBeanFromTable(tableName, isConstructor, isSetAndGet, true);
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

    public void setSetAndGetComment(List<String> setterComment, List<String> getterComment) {
        DbdToBeanContext.getCustomComment().setSetAndGetComment(setterComment,getterComment);
    }

    public void setSetComment(List<String> setterComment) {
        DbdToBeanContext.getCustomComment().setSetterComment(setterComment);
    }

    public void setGetComment(List<String> getterComment) {
        DbdToBeanContext.getCustomComment().setGetterComment(getterComment);
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

    public void createBeanFromDataBase(boolean fieldNameAllLower) throws SQLException, IOException {
        DbdToBeanContext.getDbdToBeanDefinition().setFieldNameAllLower(fieldNameAllLower);
        createBeanFromDataBase(null, true, true, true);
    }

    public Map<String, String> createBeanFromDataBase() throws SQLException, IOException {
        return createBeanFromDataBase(getConnection().getCatalog(), true, true, true);
    }

    public Map<String, String> createBeanFromDataBase(String dateBaseName) throws SQLException, IOException {
        return createBeanFromDataBase(dateBaseName, true, true, true);
    }

    public Map<String, String> createBeanFromDataBase(String dateBaseName, boolean isConstructor) throws SQLException, IOException {
        return createBeanFromDataBase(dateBaseName, isConstructor, true, true);
    }

    public Map<String, String> createBeanFromDataBase(String dateBaseName, boolean isConstructor, boolean isSetAndGet) throws SQLException, IOException {
        return createBeanFromDataBase(dateBaseName, isConstructor, isSetAndGet, true);
    }
    
    public HashMap<String, String> createBeanFromDataBase(String dateBaseName, boolean isConstructor, boolean isSetAndGet, boolean isToString) throws SQLException, IOException {
        return super.createBeanFromDataBase(dateBaseName, isConstructor, isSetAndGet, isToString);
    }

    public String exportToFile(String fileContent) throws IOException {
        String createPath = exportToFile(fileContent, null, null);
        logger.info("创建 {} 实体类成功，位于：{}", DbdToBeanContext.getDbdToBeanDefinition().getCreateBeanName(), new File(createPath).getAbsolutePath());
        return createPath;
    }

    public String exportToFile(String fileContent, String path) throws IOException {
        String createPath = exportToFile(fileContent, path, null);
        logger.info("创建 {} 实体类成功，位于：{}", DbdToBeanContext.getDbdToBeanDefinition().getCreateBeanName(), new File(createPath).getAbsolutePath());
        return createPath;
    }
    
    public String exportToFile(String fileContent, String path, String dirName) throws IOException {
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
        DbdToMvc dbdToMVC = new DbdToMvc();
        dbdToMVC.dbdToMvc();
        return createPath;
    }

    public String exportToFile(Map<String,String> fileContentMap) throws IOException, SQLException {
        return this.exportToFile(fileContentMap,null,null);
    }

    public String exportToFile(Map<String,String> fileContentMap, String path) throws IOException, SQLException {
        return this.exportToFile(fileContentMap,path,null);
    }

    public String exportToFile(Map<String,String> fileContentMap, String path, String dirName) throws IOException, SQLException {
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
            logger.info("正在创建第【{}】个文件：{}", i++, entry.getKey());
            createPath = super.exportToFiles(entry.getKey(), entry.getValue(), path, dirName);
        }
        DbdToMvc dbdToMVC = new DbdToMvc();
        dbdToMVC.dbdToMvc();
        logger.info("所有文件创建完毕，位于：{}", new File(createPath).getAbsolutePath());
        return createPath;
    }

    public void setLowerCamelCase(boolean lowerCamelCase) {
        DbdToBeanContext.getDbdToBeanDefinition().setLowerCamelCase(lowerCamelCase);
    }

    public String firstCharToUpperCase(String fieldName) {
        return BeanUtils.firstCharToUpperCase(fieldName);
    }

    public String firstCharToLowerCase(String fieldName) {
        return BeanUtils.firstCharToLowerCase(fieldName);
    }

    public String underlineToUpperCase(String name) {
        return BeanUtils.underlineToUpperCase(name);
    }

    public void setDbdToBeanProperties(String driverName, String url, String username, String password) {
        DbdToBeanContext.getDbdToBeanProperties().setDriverName(driverName);
        DbdToBeanContext.getDbdToBeanProperties().setUrl(url);
        DbdToBeanContext.getDbdToBeanProperties().setUsername(username);
        DbdToBeanContext.getDbdToBeanProperties().setPassword(password);
    }

    public void setModulesName(String modulesName) {
        DbdToBeanContext.getDbdToMvcDefinition().setModulesName(modulesName);
    }

    public void setEntityLocation(String entityLocation) {
        DbdToBeanContext.getDbdToMvcDefinition().setEntityLocation(entityLocation);
    }

    public void setControllerLocation(String controllerLocation) {
        DbdToBeanContext.getDbdToMvcDefinition().setControllerLocation(controllerLocation);
    }

    public void setServiceLocation(String serviceLocation) {
        DbdToBeanContext.getDbdToMvcDefinition().setServiceLocation(serviceLocation);
    }

    public void setDaoLocation(String daoLocation) {
        DbdToBeanContext.getDbdToMvcDefinition().setDaoLocation(daoLocation);
    }

    public void setMapperLocation(String mapperLocation) {
        DbdToBeanContext.getDbdToMvcDefinition().setMapperLocation(mapperLocation);
    }

    public String getMapperXMLLocation() {
        return DbdToBeanContext.getDbdToMvcDefinition().getMapperXmlLocation();
    }

    public void setMapperXMLLocation(String mapperXMLLocation) {
        DbdToBeanContext.getDbdToMvcDefinition().setMapperXmlLocation(mapperXMLLocation);
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
        super.parseDataBaseTypeAndGetSql("");
        return DbdToBeanContext.getDbdToBeanDefinition().getDateBaseType();
    }

    // ----------------------------------- DBDToMVC -----------------------------------

    public void setPrefix(String prefix) {
        DbdToBeanContext.getDbdToMvcDefinition().setPrefix(prefix);
    }

    public void setSuffix(String suffix) {
        DbdToBeanContext.getDbdToMvcDefinition().setSuffix(suffix);
    }

    public void setControllerPre(String controllerPre) {
        DbdToBeanContext.getDbdToMvcDefinition().setControllerPre(controllerPre);
    }

    public void setControllerSuf(String controllerSuf) {
        DbdToBeanContext.getDbdToMvcDefinition().setControllerSuf(controllerSuf);
    }

    public void setServiceInterPre(String servicePre) {
        DbdToBeanContext.getDbdToMvcDefinition().setServiceInterPre(servicePre);
    }

    public void setServiceInterSuf(String serviceSuf) {
        DbdToBeanContext.getDbdToMvcDefinition().setServiceInterSuf(serviceSuf);
    }

    public void setServiceImplPre(String servicePre) {
        DbdToBeanContext.getDbdToMvcDefinition().setServiceImplPre(servicePre);
    }

    public void setServiceImplSuf(String serviceSuf) {
        DbdToBeanContext.getDbdToMvcDefinition().setServiceImplSuf(serviceSuf);
    }

    public void setDaoInterPre(String daoPre) {
        DbdToBeanContext.getDbdToMvcDefinition().setDaoInterPre(daoPre);
    }

    public void setDaoInterSuf(String daoSuf) {
        DbdToBeanContext.getDbdToMvcDefinition().setDaoInterSuf(daoSuf);
    }

    public void setDaoImplPre(String daoPre) {
        DbdToBeanContext.getDbdToMvcDefinition().setDaoImplPre(daoPre);
    }

    public void setDaoImplSuf(String daoSuf) {
        DbdToBeanContext.getDbdToMvcDefinition().setDaoImplSuf(daoSuf);
    }


    public void setMapperInterPre(String mapperPre) {
        DbdToBeanContext.getDbdToMvcDefinition().setMapperInterPre(mapperPre);
    }

    public void setMapperInterSuf(String mapperSuf) {
        DbdToBeanContext.getDbdToMvcDefinition().setMapperInterSuf(mapperSuf);
    }

    public void setMapperXmlPre(String mapperPre) {
        DbdToBeanContext.getDbdToMvcDefinition().setMapperXmlPre(mapperPre);
    }

    public void setMapperXmlSuf(String mapperSuf) {
        DbdToBeanContext.getDbdToMvcDefinition().setMapperXmlSuf(mapperSuf);
    }

    public DbdToBeanDefinition getDbdToBeanDefinition() {
        return DbdToBeanContext.getDbdToBeanDefinition();
    }

    public List<DbdToBeanDefinition> getDbdToBeanDefinitions() {
        return DbdToBeanContext.getDbdToBeanDefinitions();
    }

    public DbdToMvcDefinition getDbdToMVCDefinition() {
        return DbdToBeanContext.getDbdToMvcDefinition();
    }

    public void setGenerateCURD(boolean generateCURD) {
        DbdToBeanContext.getDbdToMvcDefinition().setGenerateCurd(generateCURD);
    }

    public void setMVImplName(String implLocation){
        AbstractDbdToMVC.IMPL_NAME = implLocation;
    }

    public void setXmlPublicAndHttp(String xmlPublicAndHttp){
        AbstractDbdToMVC.xmlPublicAndHttp = xmlPublicAndHttp;
    }

    public void setMavenOrSimple(boolean mavenOrSimple) {
        DbdToBeanContext.getDbdToMvcDefinition().setMavenOrSimple(mavenOrSimple);
    }

    public void setGenerateRequestBody(boolean generateRequestBody) {
        DbdToBeanContext.getDbdToMvcDefinition().setGenerateRequestBody(generateRequestBody);
    }

    public void setColumnNum(int columnNum) {
        DbdToBeanContext.getDbdToMvcDefinition().setColumnNum(columnNum);
    }
    
    public void setStartSwagger(boolean startSwagger) {
        DbdToBeanContext.getDbdToMvcDefinition().setStartSwagger(startSwagger);
    }

    // ----------------------------------- log -----------------------------------

    public DbdToBeanLog getLogInfo(){
        return new DbdToBeanLog();
    }

    public DbdToBeanAPI getDBDToBeanAPI(){
        return new DbdToBeanAPI();
    }

    // ----------------------------------- Utils -----------------------------------

    public void explain(ResultSet rs) throws SQLException {
        BeanUtils.explain(rs);
    }

}
