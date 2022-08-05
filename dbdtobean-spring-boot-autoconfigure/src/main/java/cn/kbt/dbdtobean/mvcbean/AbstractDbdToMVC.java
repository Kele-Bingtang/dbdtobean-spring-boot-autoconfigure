package cn.kbt.dbdtobean.mvcbean;

import cn.kbt.dbdtobean.core.DbdToBeanContext;
import cn.kbt.dbdtobean.utils.BeanUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author Kele-Bing
 * @since 2021/9/21 23:30
 * @version 1.6
 * 抽象MVC类
 */
public abstract class AbstractDbdToMVC {
    /**
     * Maven目录
     **/
    public final static String MAVEN_HONE = "src\\main\\java\\";
    /**
     * 普通目录
     **/
    protected final static String SIMPLE_HONE = "src\\";
    /**
     * 实现类文件夹名
     **/
    public static String IMPL_NAME = "impl";
    /**
     * Mapper的xml开头配置
     **/
    public static String xmlPublicAndHttp = "PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\"\n" +
            "\t\t\"http://mybatis.org/dtd/mybatis-3-mapper.dtd\"";
    /**
     * 是否需要接口类
     **/
    protected boolean isInterface = false;
    /**
     * 注解
     **/
    protected String mvcAnnotation = "";

    /**
     * 创建接口类
     *
     * @param definition MVC 信息
     * @param createBeanName 文件名
     * @param mvcName mvc 类型
     * @return 内容
     * @throws IOException IO 异常
     */
    public String createInterfaces(DbdToMVCDefinition definition, String createBeanName, String mvcName) throws IOException {
        String location = parseLocation(definition, mvcName);
        File file = mvcFilePath(DbdToBeanContext.getDbdToMVCDefinition(), null, location);
        boolean mkdir = file.mkdirs();
        String createClassName  = parseMVCName(DbdToBeanContext.getDbdToMVCDefinition(), createBeanName, mvcName);
        file = new File(file + "\\" + createClassName + ".java");
        FileWriter fw = new FileWriter(file);
        fw.write(mvcInterContent(definition, createBeanName, mvcName));
        fw.flush();
        fw.close();
        return createClassName;
    }

    /**
     * 创建普通类或者接口的实现类
     * 
     * @param definition MVC 信息
     * @param createBeanName 文件名
     * @param mvcName mvc 类型
     * @param mvcInterfaceName MVC 接口类名
     * @return 内容
     * @throws IOException IO 异常
     */
    public String createBean(DbdToMVCDefinition definition, String createBeanName, String mvcName, String mvcInterfaceName) throws IOException {
        String location = parseLocation(definition, mvcName);
        File file = mvcFilePath(definition, mvcInterfaceName, location);
        boolean mkdir = file.mkdirs();
        // 给 createBeanName 加上 Controller 或 Service 或 Dao 或 Mapper
        String createClassName = parseMVCName(DbdToBeanContext.getDbdToMVCDefinition(), createBeanName, mvcName);
        file = new File(file + "\\" + createClassName + ".java");
        FileWriter fw = new FileWriter(file);
        fw.write(mvcBeanContent(definition, createBeanName, mvcName, mvcInterfaceName));
        fw.flush();
        fw.close();
        return createClassName;
    }

    /**
     * 解析路径，获取生成的路径
     *
     * @param definition MVC 信息
     * @param mvcInterfaceName MVC 接口名
     * @param location 包路径
     * @return 文件对象
     */
    public File mvcFilePath(DbdToMVCDefinition definition, String mvcInterfaceName, String location) {
        File file;
        if (BeanUtils.isNotEmpty(mvcInterfaceName)) {
            isInterface = true;
            file = new File(System.getProperty("user.dir") + "\\" + definition.getModulesName() + "\\" + definition.getMavenOrSimple() + BeanUtils.packageToPath(location) + "\\" + IMPL_NAME);
        } else {
            isInterface = false;
            file = new File(System.getProperty("user.dir") + "\\" + definition.getModulesName() + "\\" + definition.getMavenOrSimple() + BeanUtils.packageToPath(location));
        }
        return file;
    }

    /**
     * 生成接口类内容
     *
     * @param definition MVC 信息
     * @param createBeanName 文件名
     * @param mvcName mvc 类型
     * @return 内容
     */
    public String mvcInterContent(DbdToMVCDefinition definition, String createBeanName, String mvcName) {
        StringBuilder content = new StringBuilder();
        String location = parseLocation(definition, mvcName);
        content.append(DbdToBeanContext.getDbdToBeanDefinition().setThenGetPackageName(location)).append("\n");
        if (DbdToBeanContext.getDefaultComment().isSetHeadComment()) {
            content.append(DbdToBeanContext.getDbdToBeanDefinition().getHeadComment().generateHeadComments(DbdToBeanContext.getDbdToBeanProperties().getAuthorName()).toString());
        }
        if (definition.isMvcAnnotation() && mvcName.equals(DbdToMapper.MAPPER_INTERFACE_NAME)) {
            content.insert(content.indexOf(";") + 1, "\nimport org.apache.ibatis.annotations." + mvcAnnotation.substring(1) + ";");
            content.append(mvcAnnotation).append("\n");
        }
        String createClassName = parseMVCName(DbdToBeanContext.getDbdToMVCDefinition(), createBeanName, mvcName);
        content.append("public interface ").append(createClassName).append(" {\n\n");
        // 截取实体类名字
        new DbdToCurd().generateInterCURD(content, createBeanName);
        content.append("}");
        return content.toString();
    }

    /**
     * 生成普通类内容或者接口的实现类内容
     *
     * @param definition MVC 信息
     * @param createBeanName 文件名
     * @param mvcName mvc 类型
     * @param mvcInterfaceName MVC 接口类型
     * @return 内容
     */
    public String mvcBeanContent(DbdToMVCDefinition definition, String createBeanName, String mvcName, String mvcInterfaceName) {
        StringBuilder content = new StringBuilder();
        String location = parseLocation(definition, mvcName);
        DbdToCurd dbdToCurd = new DbdToCurd();
        String createClassName = parseMVCName(DbdToBeanContext.getDbdToMVCDefinition(), createBeanName, mvcName);
        // 调用类，如 Controller 调用 Service，Service 调用 Mapper
        String importBeanName = null;
        if (isInterface) {
            // Service、Dao、Mapper
            content.append(DbdToBeanContext.getDbdToBeanDefinition().setThenGetPackageName(location + "." + IMPL_NAME))
                    .append("import ").append(location).append(".").append(mvcInterfaceName).append(";\n\n");
            if (DbdToBeanContext.getDefaultComment().isSetHeadComment()) {
                content.append(DbdToBeanContext.getDbdToBeanDefinition().getHeadComment().generateHeadComments(DbdToBeanContext.getDbdToBeanProperties().getAuthorName()).toString());
            }
            if (definition.isMvcAnnotation() && !mvcName.equals(DbdToDao.DAO_IMPL_NAME)) {
                content.insert(content.indexOf(";") + 1, "\nimport org.springframework.stereotype." + mvcAnnotation.substring(1) + ";");
                content.append(mvcAnnotation).append("\n");
            }
            content.append("public class ").append(createClassName).append(" implements ").append(mvcInterfaceName).append(" {\n\n");
            if (mvcName.equals(DbdToService.SERVICE_IMPL_NAME)) {
                if (BeanUtils.isNotEmpty(DbdToBeanContext.getDbdToMVCDefinition().getMapperLocation())) {
                    importBeanName = parseMVCName(DbdToBeanContext.getDbdToMVCDefinition(), createBeanName, DbdToMapper.MAPPER_INTERFACE_NAME);

                    content.insert(content.indexOf(";") + 1, "\nimport " + DbdToBeanContext.getDbdToMVCDefinition().getMapperLocation() + "." + importBeanName + ";\nimport org.springframework.beans.factory.annotation.Autowired;");
                    content.append("\t@Autowired\n\tprivate ").append(importBeanName).append(" ").append(BeanUtils.firstCharToLowerCase(importBeanName)).append(";\n\n");
                } else if (BeanUtils.isNotEmpty(DbdToBeanContext.getDbdToMVCDefinition().getDaoLocation())) {
                    importBeanName = parseMVCName(DbdToBeanContext.getDbdToMVCDefinition(), createBeanName, DbdToDao.DAO_IMPL_NAME);

                    content.insert(content.indexOf(";") + 1, "\nimport " + DbdToBeanContext.getDbdToMVCDefinition().getDaoLocation() + "." + IMPL_NAME + "." + importBeanName + ";\nimport org.springframework.beans.factory.annotation.Autowired;");
                    content.append("\t@Autowired\n\tprivate ").append(importBeanName).append(" ").append(BeanUtils.firstCharToLowerCase(importBeanName)).append(";\n\n");
                }
            }
            dbdToCurd.generateImplCURD(content, createBeanName, isInterface, importBeanName);
        } else {
            // Controller
            content.append(DbdToBeanContext.getDbdToBeanDefinition().setThenGetPackageName(location)).append("\n");
            if (DbdToBeanContext.getDefaultComment().isSetHeadComment()) {
                content.append(DbdToBeanContext.getDbdToBeanDefinition().getHeadComment().generateHeadComments(DbdToBeanContext.getDbdToBeanProperties().getAuthorName()).toString());
            }
            if (definition.isMvcAnnotation()) {
                // 添加 Controller 的注解
                this.addControllerAnnotation(content);
                content.append(mvcAnnotation).append("\n").append("@RequestMapping(\"").append("/").append(BeanUtils.firstCharToLowerCase(createBeanName)).append("\")").append("\n");
            }
            content.append("public class ").append(createClassName).append(" {\n\n");
            if (BeanUtils.isNotEmpty(DbdToBeanContext.getDbdToMVCDefinition().getServiceLocation())) {
                importBeanName = parseMVCName(DbdToBeanContext.getDbdToMVCDefinition(), createBeanName, DbdToService.SERVICE_IMPL_NAME);
                content.insert(content.indexOf(";") + 1, "\nimport " + DbdToBeanContext.getDbdToMVCDefinition().getServiceLocation() + "." + IMPL_NAME + "." + importBeanName + ";\nimport org.springframework.beans.factory.annotation.Autowired;\n");
                content.append("\t@Autowired\n\tprivate ").append(importBeanName).append(" ").append(BeanUtils.firstCharToLowerCase(importBeanName)).append(";\n\n");
            }
            dbdToCurd.generateControllerCURD(content, createBeanName, importBeanName);
        }
        content.append("}");
        return content.toString();
    }

    /**
     * 解析位置，获取不同的MVC全类路径
     *
     * @param definition MVC 信息
     * @param mvcName MVC 类型
     * @return 内容
     */
    public String parseLocation(DbdToMVCDefinition definition, String mvcName) {
        String location = DbdToBeanContext.getDbdToBeanDefinition().getPackageName();
        switch (mvcName) {
            case DbdToController.CONTROLLER_NAME:
                location = definition.getControllerLocation();
                if (!mvcAnnotation.equals("@RestController")) {
                    mvcAnnotation = "@RestController";
                }
                break;
            case DbdToService.SERVICE_IMPL_NAME:
            case DbdToService.SERVICE_INTERFACE_NAME:
                location = definition.getServiceLocation();
                if (!mvcAnnotation.equals("@Service")) {
                    mvcAnnotation = "@Service";
                }
                break;
            case DbdToDao.DAO_IMPL_NAME:
            case DbdToDao.DAO_INTERFACE_NAME:
                location = definition.getDaoLocation();
                if (!mvcAnnotation.equals("@Mapper")) {
                    mvcAnnotation = "@Mapper";
                }
                break;
            case DbdToMapper.MAPPER_INTERFACE_NAME:
                location = definition.getMapperLocation();
                if (!mvcAnnotation.equals("@Mapper")) {
                    mvcAnnotation = "@Mapper";
                }
                break;
        }
        return location;
    }

    /**
     * 解析MVC的文件名字，封装文件名字的前缀和后缀
     *
     * @param definition MVC 信息
     * @param createBeanName 文件名
     * @param mvcName mvc 类型
     * @return 内容
     */
    public String parseMVCName(DbdToMVCDefinition definition, String createBeanName, String mvcName) {
        switch (mvcName) {
            case DbdToController.CONTROLLER_NAME:
                return definition.getPrefix() + definition.getControllerPre() + createBeanName + definition.getControllerSuf() + definition.getSuffix();
            case DbdToService.SERVICE_INTERFACE_NAME:
                return definition.getPrefix() + definition.getServiceInterPre() + createBeanName + definition.getServiceInterSuf() + definition.getSuffix();
            case DbdToService.SERVICE_IMPL_NAME:
                return definition.getPrefix() + definition.getServiceImplPre() + createBeanName + definition.getServiceImplSuf() + definition.getSuffix();
            case DbdToDao.DAO_INTERFACE_NAME:
                return definition.getPrefix() + definition.getDaoInterPre() + createBeanName + definition.getDaoInterSuf() + definition.getSuffix();
            case DbdToDao.DAO_IMPL_NAME:
                return definition.getPrefix() + definition.getDaoImplPre() + createBeanName + definition.getDaoImplSuf() + definition.getSuffix();
            case DbdToMapper.MAPPER_INTERFACE_NAME:
                return definition.getPrefix() + definition.getMapperInterPre() + createBeanName + definition.getMapperInterSuf() + definition.getSuffix();
            case DbdToMapper.MAVEN_MAPPER_XML_HONE:
                return definition.getPrefix() + definition.getMapperXmlPre() + createBeanName + definition.getMapperXmlSuf() + definition.getSuffix();
        }
        return createBeanName;
    }

    /**
     * 添加 Controller 的通用注解
     * @param content 内容
     */
    public void addControllerAnnotation(StringBuilder content) {
        content.insert(content.indexOf(";") + 1, "\nimport org.springframework.web.bind.annotation." + mvcAnnotation.substring(1) + ";");
        content.insert(content.indexOf(";") + 1, "\nimport org.springframework.web.bind.annotation.GetMapping;");
        content.insert(content.indexOf(";") + 1, "\nimport org.springframework.web.bind.annotation.PostMapping;");
        content.insert(content.indexOf(";") + 1, "\nimport org.springframework.web.bind.annotation.RequestBody;");
        content.insert(content.indexOf(";") + 1, "\nimport org.springframework.web.bind.annotation.RequestMapping;");
    }

}
