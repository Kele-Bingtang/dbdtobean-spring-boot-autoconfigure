package cn.kbt.dbdtobean.mvcbean;

import cn.kbt.dbdtobean.core.DbdToBeanContext;
import cn.kbt.dbdtobean.core.DbdToBeanDefinition;
import cn.kbt.dbdtobean.utils.BeanUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author Young Kbt
 * @since 2022/8/7 21:03
 * @version 1.6
 * Swagger 文档生成工具
 */
public class DbdToSwagger {

    /**
     * 生成 Swagger 文档
     * @throws IOException IO 异常
     */
    public void createSwagger() throws IOException {
        DbdToMvcDefinition dbdToMvcDefinition = DbdToBeanContext.getDbdToMvcDefinition();
        String controllerLocation = dbdToMvcDefinition.getControllerLocation();
        int index = controllerLocation.lastIndexOf(".");
        String basePath = controllerLocation.substring(0, index);
        String swaggerPath = dbdToMvcDefinition.getSwaggerLocation();
        // 如果 Swagger 路径为空，则使用默认路径
        if(BeanUtils.isEmpty(swaggerPath)){
            swaggerPath = basePath + ".config";
        }
        File file = new File(System.getProperty("user.dir") + "/" + dbdToMvcDefinition.getModulesName() + "/" + dbdToMvcDefinition.getMavenOrSimple() + BeanUtils.packageToPath(swaggerPath));
        file.mkdirs();
        file = new File(file + "/" + "SwaggerConfig.java");
        FileWriter fw = new FileWriter(file);
        fw.write(createSwaggerBean(swaggerPath));
        fw.flush();
        fw.close();
    }

    /**
     * 生成 Swagger 文档的内容
     * @param packageName 包名
     * @return Swagger 文档的内容
     */
    public String createSwaggerBean(String packageName) {
        DbdToMvcDefinition dbdToMvcDefinition = DbdToBeanContext.getDbdToMvcDefinition();
        DbdToBeanDefinition dbdToBeanDefinition = DbdToBeanContext.getDbdToBeanDefinition();
        String property = System.getProperty("user.dir");
        String location = property.substring(0, property.length() - 1);
        String projectName = property.substring(location.lastIndexOf("\\") + 1);
        String oneLine = BeanUtils.getN(1);
        String twoLine = BeanUtils.getN(2);
        String oneLineAndOneTab = BeanUtils.getNT(1, 1);
        String oneLineAndTwoTab = BeanUtils.getNT(1, 2);
        String oneLineAndFourTab = BeanUtils.getNT(1, 4);
        StringBuilder content = new StringBuilder();
        // 添加包路径
        content.append("package ").append(packageName).append(";").append(twoLine)
            .append("import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;").append(oneLine)
            .append("import org.springframework.context.annotation.Bean;").append(oneLine)
            .append("import org.springframework.context.annotation.Configuration;").append(oneLine)
            .append("import springfox.documentation.builders.ApiInfoBuilder;").append(oneLine)
            .append("import springfox.documentation.builders.PathSelectors;").append(oneLine)
            .append("import springfox.documentation.builders.RequestHandlerSelectors;").append(oneLine)
            .append("import springfox.documentation.service.ApiInfo;").append(oneLine)
            .append("import springfox.documentation.spi.DocumentationType;").append(oneLine)
            .append("import springfox.documentation.spring.web.plugins.Docket;").append(oneLine)
            .append("import springfox.documentation.swagger2.annotations.EnableSwagger2;").append(twoLine);
        // 添加自定义类注释或者默认注释，默认注释格式为 @author @since @version  
        content.append(dbdToBeanDefinition.getHeadComment().getHeadComments().toString());
        // 添加类
        content.append("@Configuration").append(oneLine)
                .append("@ConditionalOnProperty(value = ").append(BeanUtils.addColon("springfox.documentation.enabled"))
                .append(", havingValue = ").append(BeanUtils.addColon("true")).append(")").append(oneLine)
                .append(dbdToMvcDefinition.getSwaggerVersion() == DbdToMvcDefinition.SwaggerVersion.SWAGGER_2 ? "@EnableSwagger2" + oneLine : "")
                .append("public class SwaggerConfig {").append(oneLineAndOneTab);
        // 添加 createRestApi 方法注释
        content.append("/**").append(oneLineAndOneTab).append(" * 创建 API 应用").append(oneLineAndOneTab)
                .append(" * apiInfo()：增加 API 相关信息").append(oneLineAndOneTab)
                .append(" * 通过 select() 函数返回一个 ApiSelectorBuilder 实例，用来控制哪些接口暴露给 Swagger 来展现").append(oneLineAndOneTab)
                .append(" * apis()：扫描的包路径来定义指定要建立 API 的目录").append(oneLineAndOneTab)
                .append(" * ").append(oneLineAndOneTab).append(" * @return API 应用").append(oneLineAndOneTab).append(" */").append(oneLineAndOneTab);
        // 添加 createRestApi 方法
        content.append("@Bean").append(oneLineAndOneTab).append("public Docket createRestApi() {").append(oneLineAndTwoTab)
                .append("return new Docket(DocumentationType.")
                .append(dbdToMvcDefinition.getSwaggerVersion() == DbdToMvcDefinition.SwaggerVersion.SWAGGER_2 ? "SWAGGER_2)" : "OAS_30)").append(oneLineAndFourTab)
                .append(".apiInfo(apiInfo())").append(oneLineAndFourTab)
                .append(".select()").append(oneLineAndFourTab)
                .append(".apis(RequestHandlerSelectors.basePackage(").append(BeanUtils.addColon(dbdToMvcDefinition.getControllerLocation()))
                .append("))").append(oneLineAndFourTab)
                .append(".paths(PathSelectors.any())").append(oneLineAndFourTab)
                .append(".build();").append(oneLineAndOneTab).append("}").append(oneLineAndOneTab);
        // 添加 apiInfo 方法注释
        content.append("/**").append(oneLineAndOneTab).append(" * 创建该 API 的基本信息（这些基本信息会展现在文档页面中）").append(oneLineAndOneTab)
                .append(" * 访问地址：http://项目实际地址/swagger-ui.html").append(oneLineAndOneTab)
                .append(" * ").append(oneLineAndOneTab).append(" * @return API 的基本信息").append(oneLineAndOneTab).append(" */").append(oneLineAndOneTab);
        // 添加 apiInfo 方法
        content.append("private ApiInfo apiInfo() {").append(oneLineAndTwoTab)
                .append("return new ApiInfoBuilder()").append(oneLineAndFourTab)
                .append(".title(").append(BeanUtils.addColon(projectName + " 项目")).append(")").append(oneLineAndFourTab)
                .append(".description(").append(BeanUtils.addColon(projectName + " 项目接口文档说明")).append(")").append(oneLineAndFourTab)
                .append(".version(").append(BeanUtils.addColon("1.0")).append(")").append(oneLineAndFourTab)
                .append(".build();").append(oneLineAndOneTab).append("}").append(oneLine);
        // 添加 class 的结束标签
        content.append("}");
        return content.toString();
    }
    
}
