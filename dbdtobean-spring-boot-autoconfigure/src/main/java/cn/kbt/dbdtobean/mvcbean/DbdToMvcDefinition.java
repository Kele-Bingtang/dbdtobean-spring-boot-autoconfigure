package cn.kbt.dbdtobean.mvcbean;

import cn.kbt.dbdtobean.core.DbdToBeanContext;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Kele-Bing
 * @version 1.6
 * @since 2021/9/22 19:15
 */
@ConfigurationProperties(prefix = "dbdtobean.mvc", ignoreInvalidFields = true)
public class DbdToMvcDefinition {
    /**
     * Controller 位置
     **/
    private String controllerLocation = null;
    /**
     * Service 位置
     **/
    private String serviceLocation = null;
    /**
     * Dao 位置
     **/
    private String daoLocation = null;
    /**
     * Mapper 位置
     **/
    private String mapperLocation = null;
    /**
     * Mapper 的 xml 位置
     **/
    private String mapperXmlLocation = "mapper";
    /**
     * 所有文件前缀
     **/
    private String prefix = "";
    /**
     * 所有文件后缀
     **/
    private String suffix = "";
    /**
     * Controller 文件前缀
     **/
    private String controllerPre = "";
    /**
     * Controller 文件后缀
     **/
    private String controllerSuf = "Controller";
    /**
     * Service 接口文件前缀
     **/
    private String serviceInterPre = "";
    /**
     * Service 接口文件后缀
     **/
    private String serviceInterSuf = "Service";
    /**
     * Service 实现文件前缀
     **/
    private String serviceImplPre = "";
    /**
     * Service 实现文件后缀
     **/
    private String serviceImplSuf = "ServiceImpl";
    /**
     * Dao 接口文件前缀
     **/
    private String daoInterPre = "";
    /**
     * Dao 接口文件后缀
     **/
    private String daoInterSuf = "Dao";
    /**
     * Dao 实现文件前缀
     **/
    private String daoImplPre = "";
    /**
     * Dao 实现文件后缀
     **/
    private String daoImplSuf = "DaoImpl";
    /**
     * Mapper 接口文件前缀
     **/
    private String mapperInterPre = "";
    /**
     * Mapper 接口文件后缀
     **/
    private String mapperInterSuf = "Mapper";
    /**
     * Mapper 的 xml 文件前缀
     **/
    private String mapperXmlPre = "";
    /**
     * Mapper 的 xml 文件后缀
     **/
    private String mapperXmlSuf = "Mapper";
    /**
     * 实体类路径
     **/
    private String entityLocation = null;
    /**
     * 所在模块名
     **/
    private String modulesName = null;
    /**
     * 是否生成 CURD
     **/
    private boolean generateCurd = false;
    /**
     * maven 目录或普通目录
     **/
    private boolean mavenOrSimple = true;
    /**
     * 是否生成 MVC 注解
     **/
    private boolean mvcAnnotation = true;
    /**
     * 是否生成 @RequestBody 注解
     */
    private boolean generateRequestBody = true;
    /**
     * 是否生成开启 Swagger 注解
     */
    private boolean openSwagger = false;
    /**
     * Mapper.xml 的一行字段数
     */
    private int columnNum = 6;
    /**
     * Swagger 配置文件路径
     */
    private String swaggerLocation = null;
    /**
     * Swagger 版本，2 或者 3，默认 2
     */
    private SwaggerVersion swaggerVersion = SwaggerVersion.SWAGGER_2;

    public String getControllerLocation() {
        return controllerLocation;
    }

    public void setControllerLocation(String controllerLocation) {
        this.controllerLocation = controllerLocation;
    }

    public String getServiceLocation() {
        return serviceLocation;
    }

    public void setServiceLocation(String serviceLocation) {
        this.serviceLocation = serviceLocation;
    }

    public String getDaoLocation() {
        return daoLocation;
    }

    public void setDaoLocation(String daoLocation) {
        this.daoLocation = daoLocation;
    }

    public String getMapperLocation() {
        return mapperLocation;
    }

    public void setMapperLocation(String mapperLocation) {
        this.mapperLocation = mapperLocation;
    }

    public String getMapperXmlLocation() {
        return mapperXmlLocation;
    }

    public void setMapperXmlLocation(String mapperXmlLocation) {
        this.mapperXmlLocation = mapperXmlLocation;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getControllerPre() {
        return controllerPre;
    }

    public void setControllerPre(String controllerPre) {
        this.controllerPre = controllerPre;
    }

    public String getControllerSuf() {
        return controllerSuf;
    }

    public void setControllerSuf(String controllerSuf) {
        this.controllerSuf = controllerSuf;
    }

    public String getServiceInterPre() {
        return serviceInterPre;
    }

    public void setServiceInterPre(String serviceInterPre) {
        this.serviceInterPre = serviceInterPre;
    }

    public String getServiceInterSuf() {
        return serviceInterSuf;
    }

    public void setServiceInterSuf(String serviceInterSuf) {
        this.serviceInterSuf = serviceInterSuf;
    }

    public String getServiceImplPre() {
        return serviceImplPre;
    }

    public void setServiceImplPre(String serviceImplPre) {
        this.serviceImplPre = serviceImplPre;
    }

    public String getServiceImplSuf() {
        return serviceImplSuf;
    }

    public void setServiceImplSuf(String serviceImplSuf) {
        this.serviceImplSuf = serviceImplSuf;
    }

    public String getDaoInterPre() {
        return daoInterPre;
    }

    public void setDaoInterPre(String daoInterPre) {
        this.daoInterPre = daoInterPre;
    }

    public String getDaoInterSuf() {
        return daoInterSuf;
    }

    public void setDaoInterSuf(String daoInterSuf) {
        this.daoInterSuf = daoInterSuf;
    }

    public String getDaoImplPre() {
        return daoImplPre;
    }

    public void setDaoImplPre(String daoImplPre) {
        this.daoImplPre = daoImplPre;
    }

    public String getDaoImplSuf() {
        return daoImplSuf;
    }

    public void setDaoImplSuf(String daoImplSuf) {
        this.daoImplSuf = daoImplSuf;
    }

    public String getMapperInterPre() {
        return mapperInterPre;
    }

    public void setMapperInterPre(String mapperInterPre) {
        this.mapperInterPre = mapperInterPre;
    }

    public String getMapperInterSuf() {
        return mapperInterSuf;
    }

    public void setMapperInterSuf(String mapperInterSuf) {
        this.mapperInterSuf = mapperInterSuf;
    }

    public String getMapperXmlPre() {
        return mapperXmlPre;
    }

    public void setMapperXmlPre(String mapperXmlPre) {
        this.mapperXmlPre = mapperXmlPre;
    }

    public String getMapperXmlSuf() {
        return mapperXmlSuf;
    }

    public void setMapperXmlSuf(String mapperXmlSuf) {
        this.mapperXmlSuf = mapperXmlSuf;
    }

    public String getEntityLocation() {
        if (entityLocation == null) {
            return "";
        }
        return entityLocation;
    }

    public void setEntityLocation(String entityLocation) {
        this.entityLocation = entityLocation;
    }

    public String getModulesName() {
        if (modulesName == null) {
            return "";
        }
        return modulesName;
    }

    public void setModulesName(String modulesName) {
        this.modulesName = modulesName;
    }

    public boolean isGenerateCurd() {
        return generateCurd;
    }

    public void setGenerateCurd(boolean generateCurd) {
        this.generateCurd = generateCurd;
    }

    public boolean isMavenOrSimple() {
        return mavenOrSimple;
    }

    public void setMavenOrSimple(boolean mavenOrSimple) {
        this.mavenOrSimple = mavenOrSimple;
    }

    public boolean isMvcAnnotation() {
        return mvcAnnotation;
    }

    public void setMvcAnnotation(boolean mvcAnnotation) {
        this.mvcAnnotation = mvcAnnotation;
    }

    public boolean isGenerateRequestBody() {
        return generateRequestBody;
    }

    public void setGenerateRequestBody(boolean generateRequestBody) {
        this.generateRequestBody = generateRequestBody;
    }

    public int getColumnNum() {
        return columnNum;
    }

    public void setColumnNum(int columnNum) {
        this.columnNum = columnNum;
    }

    public boolean isOpenSwagger() {
        return openSwagger;
    }

    public void setOpenSwagger(boolean openSwagger) {
        this.openSwagger = openSwagger;
    }

    public String getSwaggerLocation() {
        return swaggerLocation;
    }

    public SwaggerVersion getSwaggerVersion() {
        return swaggerVersion;
    }

    public void setSwaggerVersion(SwaggerVersion swaggerVersion) {
        this.swaggerVersion = swaggerVersion;
    }

    public void setSwaggerLocation(String swaggerLocation) {
        this.swaggerLocation = swaggerLocation;
    }

    /**
     * 获取 Maven 或者普通目录
     *
     * @return Maven 结构还是普通 Java 结构
     */
    public String getMavenOrSimple() {
        if (mavenOrSimple) {
            return AbstractDbdToMvc.MAVEN_HONE;
        } else {
            return AbstractDbdToMvc.SIMPLE_HONE;
        }
    }

    /**
     * 获取 Mapper 的 xml 路径
     *
     * @return Mapper 的 XML 路径
     */
    protected String getMapperPath() {
        if (mavenOrSimple) {
            return DbdToMapper.MAVEN_MAPPER_XML_HONE;
        } else {
            DbdToMvcDefinition dbdToMvcDefinition = DbdToBeanContext.getDbdToMvcDefinition();
            dbdToMvcDefinition.setMapperXmlLocation(dbdToMvcDefinition.getMapperLocation());
            return DbdToMapper.SIMPLE_MAPPER_XML_HONE;
        }
    }

    /**
     * SWAGGER_2 为 Swagger 2.x 版本
     * OAS_30 为 Swagger 3.x 版本
     */
    public enum SwaggerVersion {
        SWAGGER_2, OAS_30
    }
}
