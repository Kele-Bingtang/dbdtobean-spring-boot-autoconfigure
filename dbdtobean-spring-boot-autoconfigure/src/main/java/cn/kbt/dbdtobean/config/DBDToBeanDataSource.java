package cn.kbt.dbdtobean.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Kele-Bing
 * @since  2022-28-06 21:28:26
 * 读取 Spring Boot 配置文件的数据库信息
 */
@ConfigurationProperties(prefix = "spring.datasource", ignoreInvalidFields = true)
public class DBDToBeanDataSource {
    /**
     * 数据库驱动
     **/
    private String driverClassName;
    /**
     * 数据库url
     **/
    private String url;
    /**
     * 数据库用户名
     **/
    private String username;
    /**
     * 数据库密码
     **/
    private String password;

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
