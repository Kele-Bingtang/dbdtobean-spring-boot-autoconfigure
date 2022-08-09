package cn.kbt.dbdtobean.log;

import cn.kbt.dbdtobean.utils.BeanUtils;

/**
 * @author Kele-Bing
 * @since  2021/9/25 16:46
 * @version 1.6
 * API 文档
 */
public class DbdToBeanApi {

    private final String oneLine = BeanUtils.getN(1);
    
    private final String oneTab = BeanUtils.getT(1);

    /**
     * 单个 Java Bean 实体类生成文档
     * @return API 文档
     */
    public String dbdToBeanEntityApi(){
        return  oneTab + "String driverName = " + BeanUtils.addColon("") + ";" + oneLine + 
                oneTab + "String url = " + BeanUtils.addColon("") + ";" + oneLine + 
                oneTab + "String username = " + BeanUtils.addColon("") + ";" + oneLine + 
                oneTab + "String password = " + BeanUtils.addColon("") + ";" + oneLine + 
                oneTab + "Connection connection = BeanUtils.getConnection(driverName, url, username, password);" + oneLine + 
                oneTab + "DbdToBean dbdToBean = new DbdToBean();" + oneLine + 
                oneTab + "dbdToBean.setConnection(connection);" + oneLine + 
                oneTab + "dbdToBean.setAuthorName(" + BeanUtils.addColon("作者名") + "); // 设置作者名" + oneLine + 
                oneTab + "dbdToBean.setLowerCamelCase(true); // 开启驼峰命名" + oneLine + 
                oneTab + "dbdToBean.setAllComments(true); // 打开注释" + oneLine + 
                oneTab + "dbdToBean.setHeadComment(true); // 打开类注释" + oneLine +
                oneTab + "dbdToBean.setCommentType(" + BeanUtils.addColon("/**") + "); // 注释类型：//  /*  /**" + oneLine +
                oneTab + "dbdToBean.setBeanFirstNameIsUp(true); // 文件名首字母大写" + oneLine + 
                oneTab + "dbdToBean.setEntityLocation(" + BeanUtils.addColon("cn.kbt.bean") +"); // 实体类的包名，不填写，则默认生成在桌面" + oneLine + 
                oneTab + "String beanContent = dbdToBean.createBeanFromTable(" + BeanUtils.addColon("表名") + "); // 生成实体类内容" + oneLine + 
                oneTab + "String createPath = dbdToBean.exportToFiles(beanContent); // 导出到实体类文件" + oneLine + 
                oneTab + "dbdToBean.closeConnection(); // 关闭数据库";
    }

    /**
     * 多个 Java Bean 实体类生成文档
     * @return API 文档
     */
    public String dbdToBeanEntityListApi(){
        return oneTab + "String driverName = " + BeanUtils.addColon("") + ";" + oneLine + 
                oneTab + "String url = " + BeanUtils.addColon("") + ";" + oneLine + 
                oneTab + "String username = " + BeanUtils.addColon("") + ";" + oneLine + 
                oneTab + "String password = " + BeanUtils.addColon("") + ";" + oneLine + 
                oneTab + "Connection connection = BeanUtils.getConnection(driverName, url, username, password);" + oneLine + 
                oneTab + "DbdToBean dbdToBean = new DbdToBean();" + oneLine + 
                oneTab + "dbdToBean.setConnection(connection);" + oneLine +
                oneTab + "dbdToBean.setAuthorName(" + BeanUtils.addColon("作者名") + "); // 设置作者名" + oneLine +
                oneTab + "dbdToBean.setLowerCamelCase(true); // 开启驼峰命名" + oneLine + 
                oneTab + "dbdToBean.setAllComments(true); // 打开注释" + oneLine + 
                oneTab + "dbdToBean.setHeadComment(true); // 打开类注释" + oneLine + 
                oneTab + "dbdToBean.setCommentType(" + BeanUtils.addColon("/**") + "); // 注释类型：//  /*  /**" + oneLine + 
                oneTab + "dbdToBean.setBeanFirstNameIsUp(true); // 文件名首字母大写" + oneLine +
                oneTab + "dbdToBean.setEntityLocation(" + BeanUtils.addColon("cn.kbt.bean") +"); // 实体类的包名，不填写，则默认生成在桌面" + oneLine +
                oneTab + "Map<String, String> beanContents = dbdToBean.createBeanFromDataBase(); // 读取 url 里数据库的所有表" + oneLine + 
                oneTab + "String createPath = dbdToBean.exportToFiles(beanContents); // 导出到实体类文件" + oneLine + 
                oneTab + "dbdToBean.closeConnection(); // 关闭数据库";
    }

    /**
     * Java Bean、Mapper、Service、Controller 生成文档
     * @return API 文档
     */
    public String dbdToBeanMvcApi(){
        return oneTab + "String driverName = " + BeanUtils.addColon("") + ";" + oneLine + 
                oneTab + "String url = " + BeanUtils.addColon("") + ";" + oneLine + 
                oneTab + "String username = " + BeanUtils.addColon("") + ";" + oneLine + 
                oneTab + "String password = " + BeanUtils.addColon("") + ";" + oneLine + 
                oneTab + "Connection connection = BeanUtils.getConnection(driverName, url, username, password);" + oneLine + 
                oneTab + "DbdToBean dbdToBean = new DbdToBean();" + oneLine + 
                oneTab + "dbdToBean.setConnection(connection);" + oneLine +
                oneTab + "dbdToBean.setAuthorName(" + BeanUtils.addColon("作者名") + "); // 设置作者名" + oneLine +
                oneTab + "dbdToBean.setLowerCamelCase(true); // 开启驼峰命名" + oneLine + 
                oneTab + "dbdToBean.setAllComments(true); // 打开注释" + oneLine + 
                oneTab + "dbdToBean.setHeadComment(true); // 打开类注释" + oneLine +
                oneTab + "dbdToBean.setCommentType(" + BeanUtils.addColon("/**") + "); // 注释类型：//  /*  /**" + oneLine +
                oneTab + "dbdToBean.setBeanFirstNameIsUp(true); // 文件名首字母大写" + oneLine + 
                oneTab + "dbdToBean.setModulesName(" + BeanUtils.addColon("DbdToBean-spring-boot-autoconfigure") + "); // 模块名字，如果项目里有多个模块，可使用，反之不要使用" + oneLine + 
                oneTab + "dbdToBean.setControllerLocation(" + BeanUtils.addColon("cn.kbt.controller") + "); // Controller 的全类名" + oneLine + 
                oneTab + "dbdToBean.setServiceLocation(" + BeanUtils.addColon("cn.kbt.service") + "); // Service 的全类名" + oneLine + 
                oneTab + "dbdToBean.setDaoLocation(" + BeanUtils.addColon("cn.kbt.dao") + "); // Dao 的全类名，与 Mapper 二选一，因为默认不 set，不会生成该模块的类" + oneLine + 
                oneTab + "dbdToBean.setMapperLocation(" + BeanUtils.addColon("cn.kbt.mapper") + "); // Mapper 的全类名，与 Dao 二选一" + oneLine +
                oneTab + "dbdToBean.setEntityLocation(" + BeanUtils.addColon("cn.kbt.bean") +"); // 实体类的包名，不填写，则默认生成在桌面" + oneLine +
                oneTab + "dbdToBean.setGenerateCURD(true); // 生成增删改查的基础方法" + oneLine + 
                oneTab + "dbdToBean.setStartSwagger(true); // 生成 Swagger 的配置" + oneLine + 
                oneTab + "dbdToBean.setSwaggerLocation(" + BeanUtils.addColon("cn.kbt.config") + "); // 设置 Swagger 的配置文件路径" + oneLine + 
                oneTab + "dbdToBean.setSwaggerVersion(DbdToMvcDefinition.SwaggerVersion.SWAGGER_2); // 设置 Swagger 的版本" + oneLine + 
                oneTab + "Map<String, String> map = dbdToBean.createBeanFromDataBase(); // 读取 url 里数据库的所有表" + oneLine + 
                oneTab + "dbdToBean.exportToFiles(map); // 导出到文件" + oneLine + 
                oneTab + "dbdToBean.closeConnection(); // 关闭数据库";
    }

    /**
     * Java Bean、Mapper、Service、Controller 自定义生成文档
     * @return API 文档
     */
    public String dbdToBeanCustomerMvcApi(){
        return oneTab + "String driverName = " + BeanUtils.addColon("") + ";" + oneLine + 
                oneTab + "String url = " + BeanUtils.addColon("") + ";" + oneLine + 
                oneTab + "String username = " + BeanUtils.addColon("") + ";" + oneLine + 
                oneTab + "String password = " + BeanUtils.addColon("") + ";" + oneLine + 
                oneTab + "Connection connection = BeanUtils.getConnection(driverName, url, username, password);" + oneLine + 
                oneTab + "DbdToBean dbdToBean = new DbdToBean();" + oneLine + 
                oneTab + "dbdToBean.setConnection(connection);" + oneLine +
                oneTab + "dbdToBean.setAuthorName(" + BeanUtils.addColon("作者名") + "); // 设置作者名" + oneLine +
                oneTab + "dbdToBean.setLowerCamelCase(true); // 开启驼峰命名" + oneLine + 
                oneTab + "dbdToBean.setAllComments(true); // 打开注释" + oneLine + 
                oneTab + "dbdToBean.setHeadComment(true); // 打开类注释" + oneLine +
                oneTab + "dbdToBean.setCommentType(" + BeanUtils.addColon("/**") + "); // 注释类型：//  /*  /**" + oneLine +
                oneTab + "dbdToBean.setBeanFirstNameIsUp(true); // 文件名首字母大写" + oneLine +
                oneTab + "dbdToBean.setModulesName(" + BeanUtils.addColon("DbdToBean-spring-boot-autoconfigure") + "); // 模块名字，如果项目里有多个模块，可使用，反之不要使用" + oneLine +
                oneTab + "dbdToBean.setControllerLocation(" + BeanUtils.addColon("cn.kbt.controller") + "); // Controller 的全类名" + oneLine +
                oneTab + "dbdToBean.setServiceLocation(" + BeanUtils.addColon("cn.kbt.service") + "); // Service 的全类名" + oneLine +
                oneTab + "dbdToBean.setDaoLocation(" + BeanUtils.addColon("cn.kbt.dao") + "); // Dao 的全类名，与 Mapper 二选一，因为默认不 set，不会生成该模块的类" + oneLine +
                oneTab + "dbdToBean.setMapperLocation(" + BeanUtils.addColon("cn.kbt.mapper") + "); // Mapper 的全类名，与 Dao 二选一" + oneLine +
                oneTab + "dbdToBean.setEntityLocation(" + BeanUtils.addColon("cn.kbt.bean") +"); // 实体类的包名，不填写，则默认生成在桌面" + oneLine +
                oneTab + "dbdToBean.setGenerateCURD(true); // 生成增删改查的基础方法" + oneLine + 
                oneTab + "dbdToBean.setMvcAnnotation(true); // 生成 MVC 的注解" + oneLine + 
                oneTab + "dbdToBean.setGenerateRequestBody(true); // 在 Controller 层的方法参数添加 @RequestBody 注解" + oneLine + 
                oneTab + "dbdToBean.setColumnNum(6); // Mapper 的 XML 文件中，sql 标签每有多少字段换一次行" + oneLine + 
                oneTab + "dbdToBean.setStartSwagger(true); // 生成 Swagger 的配置" + oneLine +
                oneTab + "dbdToBean.setSwaggerLocation(" + BeanUtils.addColon("cn.kbt.config") + "); // 设置 Swagger 的配置文件路径" + oneLine +
                oneTab + "dbdToBean.setSwaggerVersion(DbdToMvcDefinition.SwaggerVersion.SWAGGER_2); // 设置 Swagger 的版本" + oneLine + 
                oneTab + "dbdToBean.setPrefix(" + BeanUtils.addColon("ke") + "); // MVC 所有类文件名添加前缀" + oneLine + 
                oneTab + "dbdToBean.setSuffix(" + BeanUtils.addColon("ng") + "); // MVC 所有类文件名添加后缀" + oneLine + 
                oneTab + "dbdToBean.setControllerPre(" + BeanUtils.addColon("le") + "); // Controller 层类文件名添加前缀" + oneLine + 
                oneTab + "dbdToBean.setControllerSuf(" + BeanUtils.addColon("bi") + "); // Controller 层类文件名添加后缀" + oneLine + 
                oneTab + "dbdToBean.setServiceInterPre(" + BeanUtils.addColon("le") + "); // Service 接口类文件名添加前缀" + oneLine + 
                oneTab + "dbdToBean.setServiceInterSuf(" + BeanUtils.addColon("bi") + "); // Service 接口类文件名添加后缀" + oneLine + 
                oneTab + "dbdToBean.setServiceImplPre(" + BeanUtils.addColon("ni") + "); // Service 实现类文件名添加前缀" + oneLine + 
                oneTab + "dbdToBean.setServiceImplSuf(" + BeanUtils.addColon("hao") + "); // Service 实现类文件名添加后缀" + oneLine + 
                oneTab + "dbdToBean.setDaoInterPre(" + BeanUtils.addColon("le") + "); // Dao 接口类文件名添加前缀" + oneLine + 
                oneTab + "dbdToBean.setDaoInterSuf(" + BeanUtils.addColon("bing") + "); // Dao 接口类文件名添加前后缀" + oneLine + 
                oneTab + "dbdToBean.setDaoImplPre(" + BeanUtils.addColon("bing") + "); // Dao 实现类文件名添加前前缀" + oneLine + 
                oneTab + "dbdToBean.setDaoImplSuf(" + BeanUtils.addColon("tang") + "); // Dao 实现类文件名添加前后缀" + oneLine + 
                oneTab + "dbdToBean.setMapperInterPre(" + BeanUtils.addColon("xue") + "); // Mapper 接口类文件名添加前前缀" + oneLine + 
                oneTab + "dbdToBean.setMapperInterSuf(" + BeanUtils.addColon("li") + "); // Mapper 接口类文件名添加前后缀" + oneLine + 
                oneTab + "dbdToBean.setMapperXmlPre(" + BeanUtils.addColon("yin") + "); // Mapper xml 文件名添加前前缀" + oneLine + 
                oneTab + "dbdToBean.setMapperXmlSuf(" + BeanUtils.addColon("ju") + "); // Mapper xml 文件名添加前后缀" + oneLine + 
                oneTab + "Map<String, String> map = dbdToBean.createBeanFromDataBase();" + oneLine + 
                oneTab + "dbdToBean.exportToFiles(map);" + oneLine + 
                oneTab + "dbdToBean.closeConnection();";
    }

    /**
     * application.yml 配置文件的文档
     * @return API 文档
     */
    public String applicationApi(){
        return "dbdtobean:" + oneLine + 
                "  author-name: kele   # 作者名" + oneLine + 
                "  driver-name: com.mysql.cj.jdbc.Driver    # 驱动" + oneLine + 
                "  url: jdbc:mysql://localhost:3306/eightShoppingMail?useSSL-=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true" + oneLine + 
                "  username: root  # 数据库用户名" + oneLine + 
                "  password: 123456   # 数据库密码" + oneLine + 
                "  base:" + oneLine + 
                "    lower-camel-case: true # 开启数据库字段的 _ 去掉，并且后面首字母大写" + oneLine + 
                "  mvc:" + oneLine + 
                "    entity-location: cn.kbt.entity   # 实体类全类路径" + oneLine + 
                "    controller-location: cn.kbt.controller   # Controller 全类路径" + oneLine + 
                "    service-location: cn.kbt.service    # Service 全类路径" + oneLine + 
                "    mapper-location: cn.kbt.mapper      # Mapper 全类路径" + oneLine + 
                "    dao-location: cn.kbt.dao     # Dao 全类路径" + oneLine + 
                "    modules-name: cloud-provider-payment8001 # 模块名字，如果项目里有多个模块，可使用，反之不要使用" + oneLine + 
                "    generate-curd: true # 是否生成基础的 CURD 方法" + oneLine + 
                "    generate-request-body: true # CURD 方法的参数是否加上 @RequestBody" + oneLine + 
                "    open-swagger: true # 是否生成 Swagger 相关配置" + oneLine + 
                "    swagger-location: cn.kbt.config # Swagger 配置文件路径" + oneLine + 
                "  comment:  " + oneLine + 
                "    all-comments: true      # 是否开启全部注解" + oneLine +  
                "# 更多内容请访问 dbdToBeanCustomerMvcApi 方法：DbdToBeanApi.dbdToBeanCustomerMvcApi()";
    }
}
