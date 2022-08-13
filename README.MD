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

## 更新日志

项目刚开始由 Chenyicai 创建，后面由 Kele_Bingtang 维护并更新。

### V1.0

时间：2021.06.28 20:18:52 - 2021.07.01 16:48:01

实现了数据库的表自动生成 JavaBean 文件的基本功能。

大功能：

1. 单个表生成JavaBean
   2. 一个数据库的所有表生成JavaBean

小功能：

1. 添加了选择 生成构造器和 setter 和 getter 方法和重写 toString 方法
2. 添加了选择 获取数据库的注释功能，如果没有，则生成规定的注释，也可以自定义注释
3. 添加了选择 设置生成文件所在的包路径，即开头的 `package xxxx;`
4. 添加了选择 导入 Java 自带的 jar 包，即非 java.lang 的 jar 包
5. 添加了选择 类注释功能，如果没有，则生成规定的注释，也可以自定义注释
6. 添加了选择 生成单个 JavaBean 文件的路径和文件夹名字，两个参数，路径不能有不存在的文件夹，默认路径在电脑桌面
7. 添加了选择 生成一个数据库的 JavaBean 文件的路径和文件夹名字，默认路径在电脑桌面的一个文件夹里，该文件夹以该数据库名字 + 随机数字生成
8. 添加了选择 Oracle 数据库生成的 JavaBean 文件中，属性值可以大小写，默认全大写，因为 Oracle 的字段全是大写
9. 目前仅测试了 MySQL 和 Oracle

### V1.1

时间：2021.07.05 11:01:30 - 2021.07.05 13:13:52

1. 添加了选择 去掉下划线并且把下划线后的首字母改为大写，即驼峰命名法
2. 解决 Oracle 数据库生成单个表的 JavaBean 文件，无法设置属性字符串为小写
3. 分离了各个功能，优化了结构，减少代码重复，提高一些性能和代码的维护性
4. 解决 多个表生成 JavaBean 时，类注释只出现在第一个 JavaBean 里
5. 解决 多个表生成 JavaBean 时，设置去掉下划线，文件名没有去掉下划线

### V1.2

时间：2021.08.09 13:31:27 - 2021.08.09 17:38:19

1. 支持 Maven，提供与 Spring Boot 的 starter 启动类，无需手动导入 jar 包，只需导入依赖即可：dbdtobean-spring-boot-start
2. 优化整体结构，降低耦合度，提供对外接口
3. 去掉了数据库所有表生成时，自动生成文件功能，现在文件路径的接口都移到 exportToFile 中，需要手动调用该接口
4. 生成 JavaBean 文件时，路径可以有不存在的文件夹，不存在则会在路径后创建文件夹

5. 解决了 当字段为 x_xxx 时，生成 setter 和 getter，与 Java 定义的 setter 和 getter 不规范，如：

   ```java
   private String bUser 
   ```

   原版：生成 setter 和 getter 为

   ```java
   public void setBUser(String bUser){this.bUser = bUser;}  public String getBUser(){return bUser;}
   ```

   新版：方法名符合 Java 规范：

   ```java
   public void setbUser(String bUser){this.bUser = bUser;}  public String getbUser(){return bUser;}
   ```

6. 支持 Spring Boot 的自动装配，并且提供 properties 类，只需在 application 配置文件里以 dbdtobean 开头的数据库参数，即可自动生成数据库 Connection 对象
7. 把类注解的 @CreateTime 替换为 @Create
8. 支持类注释 @Author 的自定义，默认读取电脑的用户名

> BUG（待解决）

MySQL 的 blob 类型无法转换成 Java 的 byte[] 类型

> 下版本可以解决的问题

1. 后期：beanNameUpOrLow 作用是把首字母改为大小写，该名称应该改为 FirstBeanNameUpOrLow
2. 拆分文件名和属性值，因为下划线的统一使用 set_ToUpper，应该一分为二，文件名下划线可改为 setBeanName_ToUpper，属性值为 setField_ToUpper
3. setFieldNameIsLower 不合理，默认把字段名改为小写，应该把默认改为不改变字段名称，提高布尔值才确定改不改

### V1.3

时间：2021.09.19 16:14:32 - 2021.09.24 15:52:49 

> 2021.09.19

1. 添加 CustomComment 和 DefaultComment 类，将注释相关内容从核心类移出到这两个类，减少耦合度，提高代码优雅度
2. 添加上下文 DBDToBeanContext 类，使用单例模式存储需要在核心类使用的其他类，全局管理者
3. 将核心类的工具方法，移出到 DBDToBeanUtils 工具类里，对外提供 API，如首字母小写，获取随机数等
4. 将beanNameUpOrLow 改为 beanFirstNameUp

> 2021.09.20

1. 添加 DBDToBeanDefinition 类，将配置信息相关内容从核心类移出到这个类，减少耦合度，提高代码优雅度
2. 优化核心类代码，减少不必要的判断等，使得代码更加简洁优雅
3. 添加 HeadComment 头部注释类，自定义 Author 等信息
4. 优化控制台输出提示信息
5. 导出文件 API 添加返回值：导出文件所在的路径

> 2021.09.21：中秋节

1. 添加两个接口，统一规范
2. 简单实现了 指定 MVC 路径，即可生成以数据库表名为前缀的 MVC 的 Java 文件，MVC 指的是 Controller、Service、Dao、Mapper
3. 不指定 MVC 路径，则不生产对应的 Java 文件

> 2021.09.21

1. 添加 AbstractDBDToMVC 类，基于不同的 MVC 层生成不同的文件，所有 MVC 类继承此类
2. 添加 DBDToMVCDefinition 类，存有 MVC 的前缀和后缀
3. 添加自定义前缀和后缀，包括统一的 MVC 前缀和后缀，以及不同的 MVC（Controller、Service、Dao、Mapper）的前缀和后缀
4. 提供默认的前缀和后缀，如实现类后缀为 Impl

纠结：目前生成不同的 MVC 文件需要不同的 MVC 的静态常量进行判断，但是 DBDToMVCDefinition 类有了与静态常量对应的变量，是否替换，去掉不必要的静态常量呢？

> 2021.09.23

1. 提供自定义实现类的相比较父类接口的路径，默认为 Impl
2. 将 DBDToBeanProperties 类移入 DBDToBeanContext 类，实现单例以及统一管理
3. 生成的 MVC 文件提供 CRUD（增删改查) 共 5 个方法生成，默认不开启生成
4. MVC 文件支持生成类注释，与实体类注释开启同步

后期（未实现）：提供自定义接口，实现自定义 CRUD 方法，每个 MVC 文件统一生成这些方法

> 2021.09.24

1. 支持 Maven 目录以及普通目录
2. 普通目录下，Mapper 的 XML 文件与接口文件处于同一个目录
3. 提供 API 使用说明，位于 LogInfo 类的方法里

> 2021.09.25

1. 提供选择 生成 MVC 文件，类上方加上对他的 MVC 注解
2. 优化 Spring Boot 的 starter，提供更多的配置信息，即直接在配置文件设置好参数，即可导入，如数据库驱动，url 等
3. 添加 Spring Boot 配置文件输入 dbdbtobean 的参数，会有提示的功能
4. 优化 dbdtobean 多个类加入 Spring 容器的顺序

### V1.4

时间：2022.02.06 13:14:25 - 2020.02.06 22:44:55

1. 优化生成 MVC 文件时，import 的间距过长
2. 生成 Controller 类时，自动调用 Service 对应的方法，以及 Service 调用 Dao 或 Mapper 对应的方法（两者都存在时，Mapper 优先级大于 Dao）
3. Dao 层不再提供 @Mapper 注解，这作为原生 JDBC 使用

### V1.5

时间：2022.03.11 10:22:35 - 2022.03.12 0:31:55

1. 解决生成 XML 文件时，方法不匹配 Mapper 接口
2. 解决添加前缀和后缀后，导致类内容混乱的问题

### V1.6

时间：2022.08.02 - 2022.08.13

> 2022.08.02

1. 在类和方法添加 @RequstMapping，参数 value，默认为方法名
2. queryIssueById 和 deleteIssueById 的参数 Id 改为类对象
3. 类的 @controller 注解改为 @RestController 注解
4. 方法的参数添加 @requestbody

> 2022.08.03

1. 修复无法将 DBDtoBeanConfiguration 注入到 Spring
1. 删除了 ContextConfiguration 类，将内容移至 DbdToBeanConfiguration 类里
2. Controller 加上通用注解，将参数 id 改成类对象
3. Mybaties 的 XML 添加 SQL
4. 目前这个版本无法获取表的主键，报空指针异常

> 2022.08.05

1. Mybaties 的 XML 添加 SQL
2. Mybaties 的 XML 添加常用标签
3. 修改类名和一些方法名

> 2022.08.06

1. 重构一些方法
2. 将 \n 和 \t 进行封装，并全局使用
3. 添加 MVC CURD 接口的注释
4. 修改所有的类名和部分方法名，符合小驼峰命名法

> 2022.08.07

1. 重构一些方法
2. 添加 Swagger 的配置
3. API 的完善
4. 使用 Logger 进行日志输出
5. 优化类注释的样式

> 2022.08.09

1. 完善 API 的输出
2. 添加 README.MD 文档
3. 修复生成文件时的缓慢速度（解决反复递归问题）

> 2022.08.13

1. 格式化项目的代码，添加 DbdToBean 的方法说明
2. 发布 1.6 版本