package cn.kbt.dbdtobean.log;

/**
 * @author Kele-Bing
 * @since  2021/9/25 16:46
 * @version 1.6
 * API 文档
 */
public class DbdToBeanAPI {

    /**
     * 单个 Java Bean 实体类生成文档
     * @return API 文档
     */
    public String DbdToBeanEntityApi(){
        return "\tString driverName = \"\";\n" +
                "\tString url = \"\";\n" +
                "\tString username = \"\";\n" +
                "\tString password = \"\";" +
                "\tConnection connection = DbdToBeanUtils.getMysqlConnection(driverName, url, username, password);\n" +
                "\tDbdToBean dbdToBean = new DbdToBean();\n" +
                "\tdbdToBean.setConnection(connection);\n" +
                "\tdbdToBean.setPackageName(\"包路径\"); // 设置实体类包路径\n" +
                "\tdbdToBean.setAuthorName(\"作者名\"); // 设置作者名\n" +
                "\tdbdToBean.setLowerCamelCase(true); // 开启驼峰命名\n" +
                "\tdbdToBean.setAllComments(true); // 打开注释\n" +
                "\tdbdToBean.setHeadComment(true); // 打开类注释\n" +
                "\tdbdToBean.setCommentType(\"/*\"); // 三种注释类型：//  /*  /**\n" +
                "\tdbdToBean.setBeanFirstNameIsUp(true); // 文件名首字母大写\n" +
                "dbdToBean.setEntityLocation(\"cn.kbt.bean\"); // 实体类的包名，不填写，则默认生成在桌面" +
                "\tString beanContent = dbdToBean.createBeanFromTable(\"表名\"); // 生成实体类内容\n" +
                "\tString createPath = dbdToBean.exportToFile(beanContent); // 导出到实体类文件\n" +
                "\tdbdToBean.closeConnection(); // 关闭数据库";
    }

    /**
     * 多个 Java Bean 实体类生成文档
     * @return API 文档
     */
    public String DbdToBeanEntityListApi(){
        return "\tString driverName = \"\";\n" +
                "\tString url = \"\";\n" +
                "\tString username = \"\";\n" +
                "\tString password = \"\";" +
                "\tConnection connection = DbdToBeanUtils.getMysqlConnection(driverName, url, username, password);\n" +
                "\tDbdToBean dbdToBean = new DbdToBean();\n" +
                "\tdbdToBean.setConnection(connection);\n" +
                "\tdbdToBean.setPackageName(\"包路径\"); // 设置实体类包路径\n" +
                "\tdbdToBean.setAuthorName(\"作者名\"); // 设置作者名\n" +
                "\tdbdToBean.setLowerCamelCase(true); // 开启驼峰命名\n" +
                "\tdbdToBean.setAllComments(true); // 打开注释\n" +
                "\tdbdToBean.setHeadComment(true); // 打开类注释\n" +
                "\tdbdToBean.setCommentType(\"/*\"); // 注释类型：//  /*  /**\n" +
                "\tdbdToBean.setBeanFirstNameIsUp(true); // 文件名首字母大写\n" +
                "dbdToBean.setEntityLocation(\"cn.kbt.bean\"); // 实体类的包名，不填写，则默认生成在桌面" +
                "\tHashMap<String, String> beanContents = dbdToBean.createBeanFromDataBase(); // 读取 url 里数据库的所有表\n" +
                "\tString createPath = dbdToBean.exportToFile(beanContents); // 导出到实体类文件\n" +
                "\tDbdToBean.closeConnection(); // 关闭数据库";
    }

    /**
     * Java Bean、Mapper、Service、Controller 生成文档
     * @return API 文档
     */
    public String DbdToBeanMvcApi(){
        return "\tConnection connection = DbdToBeanUtils.getMysqlConnection(driverName, url, username, password);\n" +
                "\tDbdToBean dbdToBean = new DbdToBean();\n" +
                "\tdbdToBean.setConnection(connection);\n" +
                "\tdbdToBean.setPackageName(\"包路径\"); // 设置实体类包路径\n" +
                "\tdbdToBean.setAuthorName(\"作者名\"); // 设置作者名\n" +
                "\tdbdToBean.setLowerCamelCase(true); // 开启驼峰命名\n" +
                "\tdbdToBean.setAllComments(true); // 打开注释\n" +
                "\tdbdToBean.setHeadComment(true); // 打开类注释\n" +
                "\tdbdToBean.setCommentType(\"/*\"); // 注释类型：//  /*  /**\n" +
                "\tdbdToBean.setBeanFirstNameIsUp(true); // 文件名首字母大写\n" +
                "\tdbdToBean.setModulesName(\"DbdToBean-spring-boot-autoconfigure\"); // 模块名字，如果项目里有多个模块，可使用，反之不要使用\n" +
                "\tdbdToBean.setControllerLocation(\"cn.kbt.controller\"); // Controller 的全类名\n" +
                "\tdbdToBean.setServiceLocation(\"cn.kbt.service\"); // Service 的全类名\n" +
                "\tdbdToBean.setDaoLocation(\"cn.kbt.dao\"); // Dao 的全类名，与 Mapper 二选一，因为默认不 set，不会生成该模块的类\n" +
                "\tdbdToBean.setMapperLocation(\"cn.kbt.mapper\"); // Mapper 的全类名，与 Dao 二选一\n" +
                "\tdbdToBean.setEntityLocation(\"cn.kbt.bean\"); // 实体类的包名，不填写，默认生成在桌面\n" +
                "\tdbdToBean.setGenerateCURD(true); // 生成增删改查的基础方法\n" +
                "\tdbdToBean.setStartSwagger(true); // 生成 Swagger 的配置\n" +
                "\tdbdToBean.setSwaggerLocation(true); // 设置 Swagger 的配置文件路径\n" +
                "\tdbdToBean.setSwaggerVersion(SwaggerVersion.SWAGGER_2); // 设置 Swagger 的版本\n" +
                "\tHashMap<String, String> map = dbdToBean.createBeanFromDataBase(); // 读取 url 里数据库的所有表\n" +
                "\tdbdToBean.exportToFile(map); // 导出到文件\n" +
                "\tdbdToBean.closeConnection(); // 关闭数据库";
    }

    /**
     * Java Bean、Mapper、Service、Controller 自定义生成文档
     * @return API 文档
     */
    public String DbdToBeanCustomerMvcApi(){
        return "\tConnection connection = DbdToBeanUtils.getMysqlConnection(driverName, url, username, password);\n" +
                "\tDbdToBean dbdToBean = new DbdToBean();\n" +
                "\tdbdToBean.setConnection(connection);\n" +
                "\tdbdToBean.setPackageName(\"包路径\"); // 设置实体类包路径\n" +
                "\tdbdToBean.setAuthorName(\"作者名\");\n" +
                "\tdbdToBean.setLowerCamelCase(true); // 开启驼峰命名\n" +
                "\tdbdToBean.setAllComments(true); // 打开注释\n" +
                "\tdbdToBean.setHeadComment(true); // 打开类注释\n" +
                "\tdbdToBean.setCommentType(\"/*\"); // 注释类型：//  /*  /**\n" +
                "\tdbdToBean.setBeanFirstNameIsUp(true); // 文件名首字母大写\n" +
                "\tdbdToBean.setModulesName(\"所在模块名\");  // 模块名字，如果项目里有多个模块，可使用，反之不要使用\n" +
                "\tdbdToBean.setControllerLocation(\"cn.kbt.controller\"); // Controller 的全类名\n" +
                "\tdbdToBean.setServiceLocation(\"cn.kbt.service\"); // Service 的全类名\n" +
                "\tdbdToBean.setDaoLocation(\"cn.kbt.dao\"); // Dao 的全类名，与 Mapper 二选一，因为默认不 set，不会生成该模块的类\n" +
                "\tdbdToBean.setMapperLocation(\"cn.kbt.mapper\"); // Mapper 的全类名，与 Dao 二选一\n" +
                "\tdbdToBean.setEntityLocation(\"cn.kbt.bean\"); // 实体类的包名，不填写，默认生成在桌面\n" +
                "\tdbdToBean.setGenerateCURD(true); // 生成增删改查的基础方法\n" +
                "\tdbdToBean.setMvcAnnotation(true); // 生成 MVC 的注解\n" +
                "\tdbdToBean.setGenerateRequestBody(true); // 在 Controller 层的方法参数添加 @RequestBody 注解\n" +
                "\tdbdToBean.setColumnNum(6); // Mapper 的 XML 文件中，sql 标签每有多少字段换一次行\n" +
                "\tdbdToBean.setStartSwagger(true); // 生成 Swagger 的配置\n" +
                "\tdbdToBean.setSwaggerLocation(true); // 设置 Swagger 的配置文件路径\n" +
                "\tdbdToBean.setSwaggerVersion(SwaggerVersion.SWAGGER_2); // 设置 Swagger 的版本\n" +
                "\tdbdToBean.setPrefix(\"ke\"); // 所有类文件名添加前缀\n" +
                "\tdbdToBean.setSuffix(\"ng\"); // 所有类文件名添加后缀\n" +
                "\tdbdToBean.setControllerPre(\"le\"); // Controller 类文件名添加前缀\n" +
                "\tdbdToBean.setControllerSuf(\"bi\"); // Controller 类文件名添加后缀\n" +
                "\tdbdToBean.setServiceInterPre(\"le\"); // Service 接口类文件名添加前缀\n" +
                "\tdbdToBean.setServiceInterSuf(\"bi\"); // Service 接口类文件名添加后缀\n" +
                "\tdbdToBean.setServiceImplPre(\"ni\"); // Service 实现类文件名添加前缀\n" +
                "\tdbdToBean.getServiceImplSuf(\"hao\"); // Service 实现类文件名添加后缀\n" +
                "\tdbdToBean.setDaoInterPre(\"le\"); // Dao 接口类文件名添加前缀\n" +
                "\tdbdToBean.setDaoInterSuf(\"bing\"); // Dao 接口类文件名添加前后缀\n" +
                "\tdbdToBean.setDaoImplPre(\"bing\"); // Dao 实现类文件名添加前前缀\n" +
                "\tdbdToBean.setDaoImplSuf(\"tang\"); // Dao 实现类文件名添加前后缀\n" +
                "\tdbdToBean.setMapperInterPre(\"xue\"); // Mapper 接口类文件名添加前前缀\n" +
                "\tdbdToBean.setMapperInterSuf(\"li\"); // Mapper 接口类文件名添加前后缀\n" +
                "\tdbdToBean.setMapperXmlPre(\"yin\"); // Mapper xml 文件名添加前前缀\n" +
                "\tdbdToBean.setMapperXmlSuf(\"ju\"); // Mapper xml 文件名添加前后缀\n" +
                "\tHashMap<String, String> map = dbdToBean.createBeanFromDataBase();\n" +
                "\tdbdToBean.exportToFile(map);\n" +
                "\tdbdToBean.closeConnection();";
    }

    /**
     * application.properties 配置文件的文档
     * @return API 文档
     */
    public String applicationApi(){
        return "DbdToBean:\n" +
                "  author-name: kele   # 作者名\n" +
                "  driver-name: com.mysql.cj.jdbc.Driver    # 驱动\n" +
                "  url: jdbc:mysql://localhost:3306/eightShoppingMail?useSSL-=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true\n" +
                "  username: root  # 数据库用户名\n" +
                "  password: 123456   # 数据库密码\n" +
                "  base:\n" +
                "    lower-camel-case: true # 开启数据库字段的 _ 去掉，并且后面首字母大写\n" +
                "  mvc:\n" +
                "    entity-location: cn.kbt.entity   # 实体类全类路径\n" +
                "    controller-location: cn.kbt.controller   # Controller 全类路径\n" +
                "    service-location: cn.kbt.service    # Service 全类路径\n" +
                "    mapper-location: cn.kbt.mapper      # Mapper 全类路径\n" +
                "    dao-location: cn.kbt.dao     # Dao 全类路径\n" +
                "    modules-name: cloud-provider-payment8001 # 模块名字，如果项目里有多个模块，可使用，反之不要使用\n" +
                "    generate-curd: true # 是否生成基础的 CURD 方法\n" +
                "    generate-request-body: true # CURD 方法的参数是否加上 @RequestBody\n" +
                "    open-swagger: true # 是否生成 Swagger 相关配置\n" +
                "    swagger-location: cn.kbt.config # Swagger 配置文件路径\n" +
                "  comment:  \n" +
                "    all-comments: true      # 是否开启全部注解\n" + 
                "# 更多内容请访问 DbdToBeanCustomerMVCAPI 方法：DbdToBeanAPI.DbdToBeanCustomerMVCAPI()";
    }
}
