package cn.kbt.dbdtobean.core;

import cn.kbt.dbdtobean.comment.HeadComment;
import cn.kbt.dbdtobean.utils.BeanUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Kele-Bing
 * @version 1.6
 * 定义信息类
 * @since 2021/9/20 11:38
 */
@ConfigurationProperties(prefix = "dbdtobean.base", ignoreInvalidFields = true)
public class DbdToBeanDefinition {
    /**
     * 开头类路径
     **/
    private static final String PACKAGE = "package ";
    /**
     * 生成的文件名
     **/
    private String createBeanName;
    /**
     * 文件名首字母是否大写
     **/
    private boolean beanFirstNameUp = true;
    /**
     * 是否导入文件需要的 jar 包
     **/
    private boolean jarPackage = true;
    /**
     * 数据库的字段和生成的 JavaBean 内容属性值是否保持一样或者小写
     **/
    private boolean fieldNameAllLower = false;
    /**
     * 去掉下划线后的首字母大写
     **/
    private boolean lowerCamelCase = false;
    /**
     * 数据库类型
     **/
    private String dateBaseType = "MySQL";
    /**
     * 包路径名
     **/
    private String packageName;
    /**
     * 类注释，无法设置
     **/
    private HeadComment headComment;
    /**
     * 表名，无法设置
     */
    private String tableName;


    public String getCreateBeanName() {
        return createBeanName;
    }

    public void setCreateBeanName(String createBeanName) {
        this.createBeanName = createBeanName;
    }

    public boolean isBeanFirstNameUp() {
        return beanFirstNameUp;
    }

    public void setBeanFirstNameUp(boolean beanFirstNameUp) {
        this.beanFirstNameUp = beanFirstNameUp;
    }

    public boolean isJarPackage() {
        return jarPackage;
    }

    public void setJarPackage(boolean jarPackage) {
        this.jarPackage = jarPackage;
    }

    public boolean isFieldNameAllLower() {
        return fieldNameAllLower;
    }

    public void setFieldNameAllLower(boolean fieldNameAllLower) {
        this.fieldNameAllLower = fieldNameAllLower;
    }

    public boolean isLowerCamelCase() {
        return lowerCamelCase;
    }

    public void setLowerCamelCase(boolean lowerCamelCase) {
        this.lowerCamelCase = lowerCamelCase;
    }

    public String getDateBaseType() {
        return dateBaseType;
    }

    public void setDateBaseType(String dateBaseType) {
        this.dateBaseType = dateBaseType;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getPackageName() {
        if (BeanUtils.isNotEmpty(DbdToBeanContext.getDbdToMvcDefinition().getEntityLocation())) {
            return DbdToBeanContext.getDbdToMvcDefinition().getEntityLocation();
        }
        if (packageName == null) {
            return "";
        }
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String setThenGetPackageName(String packageName) {
        return PACKAGE + packageName + ";" + BeanUtils.getN(1);
    }

    public HeadComment getHeadComment() {
        if (headComment == null) {
            headComment = new HeadComment();
        }
        return headComment;
    }

    public void setHeadComment(HeadComment headComment) {
        this.headComment = headComment;
    }

    public void setPackageNameAndJarPackage(String packageName, boolean jarPackage) {
        this.packageName = PACKAGE + packageName + ";" + BeanUtils.getN(1);
        this.jarPackage = jarPackage;
    }

}
