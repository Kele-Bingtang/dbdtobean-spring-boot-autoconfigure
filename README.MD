## 如何使用该 DbdToBean？

常用的 API 位于 DbdToBeanApi 类，你可以这样获取该类：

```java
DbdToBeanApi dbdToBeanApi = new DbdToBeanApi();
```

又或者这样获取该类：

```java
DbdToBean dbdToBean = new DbdToBean();

DbdToBeanApi dbdToBeanApi = dbdToBean.getDbdToBeanApi();
```

如果使用 Spring Boot 的注入，也可以如下：

```java
@Autowired
DbdToBean dbdToBean;

DbdToBeanApi dbdToBeanApi = dbdToBean.getDbdToBeanApi();
```

DbdToBeanApi 类里有五个方法：

- `dbdToBeanEntityApi()`：单个 Java Bean 实体类生成文档
- `dbdToBeanEntityListApi()`：多个 Java Bean 实体类生成文档
- `dbdToBeanMvcApi()`：Java Bean、Mapper、Service、Controller 生成文档
- `dbdToBeanCustomerMvcApi()`：Java Bean、Mapper、Service、Controller 自定义生成文档
- `applicationApi()`：application.yml 配置文件的文档

如果你想使用默认的配置，则使用 `dbdToBeanMvcApi()`，或者 `applicationApi()`，如果想自定义更多内容，使用 `dbdToBeanCustomerMvcApi()`。

前四个方法返回的内容都是 Java 代码，您需要将这些代码放到 Spring Boot 的 test 测试目录下运行即可。

如果你不想在代码里使用多个 set 方法，可以使用第五个方法 `applicationApi()` 的代码，放入 application.yml 即可。

## 代码示例

下面代码都放到 Spring Boot 的 test 测试目录下：

```java
package cn.kbt;

import cn.kbt.dbdtobean.config.DbdToBeanDataSource;
import cn.kbt.dbdtobean.config.DbdToBeanProperties;
import cn.kbt.dbdtobean.core.DbdToBean;
import cn.kbt.dbdtobean.log.DbdToBeanApi;
import cn.kbt.dbdtobean.utils.BeanUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

@SpringBootTest
public class DbdtobeanTestApplicationTests {
    @Autowired
    DbdToBean dbdToBean;
    
    @Test
    public void api() {
        DbdToBeanApi dbdToBeanApi = new DbdToBeanApi();
        System.out.println(dbdToBeanApi.dbdToBeanCustomerMvcApi());
    }
    
    @Test
    public void testMaven() throws IOException, SQLException {
        String driverName = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/eightsm?useSSL-=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true";
        String username = "root";
        String password = "YoungKbt1234";
        Connection connection = BeanUtils.getConnection(driverName, url, username, password);
        DbdToBean dbdToBean = new DbdToBean();
        dbdToBean.setConnection(connection);
        dbdToBean.setAuthorName("Kele"); // 设置作者名
        dbdToBean.setLowerCamelCase(true); // 开启驼峰命名
        dbdToBean.setAllComments(true); // 打开注释
        dbdToBean.setHeadComment(true); // 打开类注释
        dbdToBean.setCommentType("//"); // 注释类型：//  /*  /**
        dbdToBean.setBeanFirstNameIsUp(true); // 文件名首字母大写
        dbdToBean.setControllerLocation("cn.kbt.controller"); // Controller 的全类名
        dbdToBean.setServiceLocation("cn.kbt.service"); // Service 的全类名
        dbdToBean.setMapperLocation("cn.kbt.mapper"); // Mapper 的全类名，与 Dao 二选一
        dbdToBean.setEntityLocation("cn.kbt.bean"); // 实体类的包名，不填写，默认生成在桌面
        dbdToBean.setGenerateCURD(true); // 生成增删改查的基础方法
        // Map<String, String> map = dbdToBean.createBeanFromDataBase(); // 读取 url 里数据库的所有表
        String map = dbdToBean.createBeanFromTable("product_info");
        dbdToBean.exportToFiles(map); // 导出到文件
        dbdToBean.closeConnection(); // 关闭数据库
    }
    @Test
    public void dbdtobeanTest() throws SQLException, IOException {
        Map<String, String> s = dbdToBean.createBeanFromDataBase();
        dbdToBean.exportToFiles(s);
        dbdToBean.closeConnection();
    }
}
```

`testMaven()` 方法都是很多 setXXX 方法，而 `dbdtobeanTest()` 无需写这些 setXXX 方法，因为都放到了 application.yml 里

```yml
dbdtobean:
  author-name: kele
  mvc:
    entity-location: cn.kbt.entity
    controller-location: cn.kbt.controller
    service-location: cn.kbt.service
    mapper-location: cn.kbt.mapper
    dao-location: cn.kbt.dao
    swagger-location: cn.kbt.swagger
    generate-request-body: true
    generate-curd: true
    open-swagger: true
  comment:
    all-comments: true
  driver-name: com.mysql.cj.jdbc.Driver
  url: jdbc:mysql://localhost:3306/eightsm?useSSL-=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true
  username: root
  password: YoungKbt1234
  base:
    lower-camel-case: true

spring:
  mvc:
    path match:
      matching-strategy: ant-path-matcher
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/eightsm?useSSL-=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true
    username: root
    password: YoungKbt1234
mybatis:
  mapper-locations: classpath:mapper/*Mapper.xml
```

**值得注意的是**：application.yml 里既有 DbdToBean 的数据库配置，也有 Spring Boot 的数据库配置。如果两者都写，那么以 Spring Boot 的数据库配置为主，所以您完全可以不写 DbdToBean 的数据库配置。

这也是考虑到兼容 Spring Boot。

> 如果你想在 yml 加更多内容，可以访问 `dbdToBeanApi.dbdToBeanCustomerMvcApi()` 里的 setXXX 配置，按照规范匹配的填写即可。