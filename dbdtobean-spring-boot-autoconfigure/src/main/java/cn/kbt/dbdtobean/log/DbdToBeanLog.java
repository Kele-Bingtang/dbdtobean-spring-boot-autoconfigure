package cn.kbt.dbdtobean.log;

import cn.kbt.dbdtobean.utils.BeanUtils;

/**
 * create by chenyicai And Kele
 * ---------------------------------
 * V1.0：
 * create time : 2021.06.28 20:18:52
 * addOrigin time : 2021.06.29 18:59:04
 * addComment time : 2021.06.30 14:14:28
 * addJarPackage time : 2021.06.30 16:27:27
 * mysql testEnd time : 2021.06.30 18:54:29
 * oracle testEnd time : 2021.07.01 16:48:01
 * ---------------------------------
 * V1.1：
 * startTime : 2021.07.05 11:01:30
 * endTime : 2021.07.05 13:13:52
 * ---------------------------------
 * V1.2：
 * startTime : 2021.08.09 13:31:27
 * addSpringBootStater
 * endTime : 2021.08.09 17:38:19
 * ---------------------------------
 * V1.3：
 * startTime : 2021.09.19 16:14:32
 * endTime : 2021.09.24 15:52:49
 * ---------------------------------
 * V1.4：
 * startTime : 2021.02.06 13:14:25
 * endTime : 2021.02.06 22:44:55
 * ---------------------------------
 * V1.5：
 * startTime : 2022.03.11 10:22:35
 * endTime : 2022.03.12 0:31:55
 * ---------------------------------
 * V1.6：
 * startTime : 2022.08.22
 * endTime : 2022.03.09
 */
public class DbdToBeanLog {
    
    String oneLine = BeanUtils.getN(1);
    String oneTab = BeanUtils.getT(1);
    String twoTab = BeanUtils.getT(2);

    /**
     * 版本日志
     *
     * @return 版本日志
     */
    public String logInfo() {
        return "V1.0(2021.06.28 20:18:52 - 2021.07.01 16:48:01)" + oneLine + 
                "实现了数据库的表自动生成JavaBean文件的基本功能" + oneLine +
                oneTab + "大功能：" + oneLine +
                twoTab + "1.单个表生成JavaBean" + oneLine +
                twoTab + "2.一个数据库的所有表生成JavaBean" + oneLine +
                oneTab + "小功能：" + oneLine +
                twoTab + "1.添加了选择 生成构造器和 setter 和 getter 方法和重写 toString 方法" + oneLine +
                twoTab + "2.添加了选择 获取数据库的注释功能，如果没有，则生成规定的注释，也可以自定义注释" + oneLine +
                twoTab + "3.添加了选择 设置生成文件所在的包路径，即开头的 package xxxx;" + oneLine +
                twoTab + "4.添加了选择 导入 Java 自带的 jar 包，即非 java.lang 的 jar 包" + oneLine +
                twoTab + "5.添加了选择 类注释功能，如果没有，则生成规定的注释，也可以自定义注释" + oneLine +
                twoTab + "6.添加了选择 生成单个 JavaBean 文件的路径和文件夹名字，两个参数，路径不能有不存在的文件夹，默认路径在电脑桌面" + oneLine +
                twoTab + "7.添加了选择 生成一个数据库的 JavaBean 文件的路径和文件夹名字，默认路径在电脑桌面的一个文件夹里，该文件夹以该数据库名字 + 随机数字生成" + oneLine +
                twoTab + "8.添加了选择 Oracle 数据库生成的 JavaBean 文件中，属性值可以大小写，默认全大写，因为 Oracle 的字段全是大写" + oneLine +
                twoTab + "9.目前仅测试了 MySQL 和 Oracle" + oneLine +
                twoTab + oneLine +
                "V1.1(2021.07.05 11:01:30 - 2021.07.05 13:13:52)" + oneLine +
                oneTab + "1.添加了选择 去掉下划线并且把下划线后的首字母改为大写，即驼峰命名法" + oneLine +
                oneTab + "2.解决 Oracle 数据库生成单个表的 JavaBean 文件，无法设置属性字符串为小写" + oneLine +
                oneTab + "3.分离了各个功能，优化了结构，减少代码重复，提高一些性能和代码的维护性" + oneLine +
                oneTab + "4.解决 多个表生成 JavaBean 时，类注释只出现在第一个 JavaBean 里" + oneLine +
                oneTab + "5.解决 多个表生成 JavaBea n时，设置去掉下划线，文件名没有去掉下划线" + oneLine +
                oneTab + oneLine +
                "V1.2(2021.08.09 13:31:27 - 2021.08.09 17:38:19)" + oneLine +
                oneTab + "1.支持 Maven，提供与 Spring Boot 的 starter 启动类，无需手动导入 jar 包，只需导入依赖即可：dbdtobean-spring-boot-start" + oneLine +
                oneTab + "2.优化整体结构，降低耦合度，提供对外接口" + oneLine +
                oneTab + "3.去掉了数据库所有表生成时，自动生成文件功能，现在文件路径的接口都移到 exportToFile 中，需要手动调用该接口" + oneLine +
                oneTab + "4.生成 JavaBean 文件时，路径可以有不存在的文件夹，不存在则会在路径后创建文件夹" + oneLine +
                oneTab + "5.解决了 当字段为 x_xxx 时，生成 setter 和 getter，与 Java 定义的 setter 和 getter 不规范，" + oneLine +
                twoTab + "如：private String bUser " + oneLine +
                twoTab + "原版：生成 setter 和 getter 为       public void setBUser(String bUser){this.bUser = bUser;}  public String getBUser(){return bUser;} " + oneLine +
                twoTab + "新版：方法名符合 Java 规范    public void setbUser(String bUser){this.bUser = bUser;}  public String getbUser(){return bUser;} " + oneLine +
                oneTab + "6.支持 Spring Boot 的自动装配，并且提供 properties 类，只需在 application 配置文件里以 dbdtobean 开头的数据库参数，即可自动生成数据库 Connection 对象" + oneLine +
                oneTab + "7.把类注解的 @CreateTime 替换为 @Create" + oneLine +
                oneTab + "8.支持类注释 @Author 的自定义，默认读取电脑的用户名" + oneLine +
                oneTab + oneLine +
                oneTab + "BUG：1.mysql 的 blob 类型无法转换成 Java 的 byte[] 类型" + oneLine +
                oneTab + "后期：beanNameUpOrLow 作用是把首字母改为大小写，该名称应该改为 FirstBeanNameUpOrLow" + oneLine +
                twoTab + "拆分文件名和属性值，因为下划线的统一使用 set_ToUpper，应该一分为二，文件名下划线可改为 setBeanName_ToUpper，属性值为 setField_ToUpper" + oneLine +
                twoTab + "setFieldNameIsLower 不合理，默认把字段名改为小写，应该把默认改为不改变字段名称，提高布尔值才确定改不改" + oneLine +
                oneTab + oneLine +
                "V1.3(2021.09.19 16:14:32 - 2021.09.24 15:52:49) " + oneLine +
                oneTab + "(2021.09.19)" + oneLine +
                oneTab + "1.添加 CustomComment 和 DefaultComment 类，将注释相关内容从核心类移出到这两个类，减少耦合度，提高代码优雅度" + oneLine +
                oneTab + "2.添加上下文 DBDToBeanContext 类，使用单例模式存储需要在核心类使用的其他类，全局管理者" + oneLine +
                oneTab + "3.将核心类的工具方法，移出到 DBDToBeanUtils 工具类里，对外提供 API，如首字母小写，获取随机数等" + oneLine +
                oneTab + "4.将 beanNameUpOrLow改为beanFirstNameUp" + oneLine +
                oneTab + oneLine +
                oneTab + "(2021.09.20) " + oneLine +
                oneTab + "1.添加 DBDToBeanDefinition 类，将配置信息相关内容从核心类移出到这个类，减少耦合度，提高代码优雅度" + oneLine +
                oneTab + "2.优化核心类代码，减少不必要的判断等，使得代码更加简洁优雅" + oneLine +
                oneTab + "3.添加 HeadComment 头部注释类，自定义 Author 等信息" + oneLine +
                oneTab + "4.优化控制台输出提示信息" + oneLine +
                oneTab + "5.导出文件API添加返回值：导出文件所在的路径" + oneLine +
                oneTab + oneLine +
                oneTab + "(2021.09.21)中秋节" + oneLine +
                oneTab + "1.添加两个接口，统一规范" + oneLine +
                oneTab + "2.简单实现了 指定 MVC 路径，即可生成以数据库表名为前缀的 MVC 的 Java 文件，MVC 指的是Controller、Service、Dao、Mapper" + oneLine +
                oneTab + "3.不指定 MVC 路径，则不生产对应的 Java 文件" + oneLine +
                oneTab + oneLine +
                oneTab + "(2021.09.21)" + oneLine +
                oneTab + "1.添加 AbstractDBDToMVC 类，基于不同的 MVC 层生成不同的文件，所有 MVC 类继承此类" + oneLine +
                oneTab + "2.添加 DBDToMVCDefinition 类，存有 MVC 的前缀和后缀" + oneLine +
                oneTab + "3.添加自定义前缀和后缀，包括统一的MVC前缀和后缀，以及不同的 MVC（Controller、Service、Dao、Mapper）的前缀和后缀" + oneLine +
                oneTab + "4.提供默认的前缀和后缀，如实现类后缀为 Impl" + oneLine +
                oneTab + oneLine +
                oneTab + "纠结：目前生成不同的MVC文件需要不同的MVC的静态常量进行判断，但是 DBDToMVCDefinition 类有了与静态常量对应的变量，是否替换，去掉不必要的静态常量呢？" + oneLine +
                oneTab + oneLine +
                oneTab + "(2021.09.23)" + oneLine +
                oneTab + "1.提供自定义实现类的相比较父类接口的路径，默认为 Impl" + oneLine +
                oneTab + "2.将 DBDToBeanProperties 类移入 DBDToBeanContext 类，实现单例以及统一管理" + oneLine +
                oneTab + "3.生成的 MVC 文件提供 CRUD（增删改查) 共 5 个方法生成，默认不开启生成" + oneLine +
                oneTab + "4.MVC 文件支持生成类注释，与实体类注释开启同步" + oneLine +
                oneTab + oneLine +
                oneTab + "后期（未实现）：提供自定义接口，实现自定义CRUD方法，每个 MVC 文件统一生成这些方法" + oneLine +
                oneTab + oneLine +
                oneTab + "(2021.09.24)" + oneLine +
                oneTab + "1.支持 Maven 目录以及普通目录" + oneLine +
                oneTab + "2.普通目录下，Mapper 的 XML 文件与接口文件处于同一个目录" + oneLine +
                oneTab + "3.提供 API 使用说明，位于 LogInfo 类的方法里" + oneLine +
                oneTab + oneLine +
                oneTab + "(2021.09.25)" + oneLine +
                oneTab + "1.提供选择 生成 MVC 文件，类上方加上对他的 MVC 注解" + oneLine +
                oneTab + "2.优化 Spring Boot 的starter，提供更多的配置信息，即直接在配置文件设置好参数，即可导入，如数据库驱动，url等" + oneLine +
                oneTab + "3.添加 Spring Boot 配置文件输入 dbdbtobean 的参数，会有提示的功能" + oneLine +
                oneTab + "4.优化 dbdtobean 多个类加入 Spring 容器的顺序" + oneLine +
                oneTab + oneLine +
                "V1.4(2022.02.06 13:14:25 - 2022.02.06 22:44:55)" + oneLine +
                oneLine +
                oneTab + "1.优化生成 MVC 文件时，import 的间距过长" + oneLine +
                oneTab + "2.生成 Controller 类时，自动调用 Service 对应的方法，以及 Service 调用 Dao 或 Mapper 对应的方法（两者都存在时，Mapper 优先级大于 Dao）" + oneLine +
                oneTab + "3.Dao 层不再提供 @Mapper 注解，这作为原生 JDBC 使用" + oneLine +
                oneTab + oneLine +
                "V1.5(2022.03.11 10:22:35 - 2022.03.12 0:31:55) " + oneLine +
                oneLine +
                oneTab + "1.解决生成 XML 文件时，方法不匹配 Mapper 接口" + oneLine +
                oneTab + "2.解决添加前缀和后缀后，导致类内容混乱的问题" + oneLine +
                oneTab + oneLine + 
                "V1.6(2022.08.02 - 2021.08.13) " + oneLine +
                oneTab + "(2022.08.02)" + oneLine +
                oneTab + "1.在类和方法添加 @RequstMapping，参数 value，默认为方法名" + oneLine +
                oneTab + "2.queryIssueById 和 deleteIssueById 的参数 Id 改为类对象" + oneLine +
                oneTab + "3.类的 @controller 注解改为 @RestController 注解" + oneLine +
                oneTab + "4.方法的参数添加 @requestbody" + oneLine +
                oneTab + oneLine +
                oneTab + "(2022.08.03) " + oneLine +
                oneTab + "1.修复无法将 DBDtoBeanConfiguration 注入到 Spring" + oneLine +
                oneTab + "2.删除了 ContextConfiguration 类，将内容移至 DbdToBeanConfiguration 类里" + oneLine +
                oneTab + "3.Controller 加上通用注解，将参数 id 改成类对象" + oneLine +
                oneTab + "4.Mybaties 的 XML 添加 SQL" + oneLine +
                oneTab + "5.目前这个版本无法获取表的主键，报空指针异常" + oneLine +
                oneTab + oneLine +
                oneTab + "(2022.08.05)" + oneLine +
                oneTab + "1.Mybaties 的 XML 添加 SQL" + oneLine +
                oneTab + "2.Mybaties 的 XML 添加常用标签" + oneLine +
                oneTab + "3.修改类名和一些方法名" + oneLine +
                oneTab + oneLine +
                oneTab + "(2022.08.06)" + oneLine +
                oneTab + "1.重构一些方法" + oneLine +
                oneTab + "2.将 \\n 和 \\t 进行封装，并全局使用" + oneLine +
                oneTab + "3.添加 MVC CURD 接口的注释" + oneLine +
                oneTab + "4.修改所有的类名和部分方法名，符合小驼峰命名法" + oneLine +
                oneTab + oneLine +
                oneTab + "(2022.08.07)" + oneLine +
                oneTab + "1.重构一些方法" + oneLine +
                oneTab + "2.添加 Swagger 的配置" + oneLine +
                oneTab + "3.API 的完善" + oneLine +
                oneTab + "4.使用 Logger 进行日志输出" + oneLine +
                oneTab + "5.优化类注释的样式" + oneLine +
                oneTab + oneLine +
                oneTab + "(2022.08.09)" + oneLine +
                oneTab + "1.完善 API 的输出" + oneLine +
                oneTab + "2.添加 README.MD 文档" + oneLine +
                oneTab + "3.修复生成文件时的缓慢速度（解决反复递归问题）" + oneLine +
                oneTab + oneLine +
                oneTab + "(2022.08.13)" + oneLine +
                oneTab + "1.格式化项目的代码，添加 DbdToBean 的方法说明" + oneLine +
                oneTab + "2.发布 1.6 版本";
    }
}
