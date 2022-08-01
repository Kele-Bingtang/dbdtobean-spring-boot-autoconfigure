package cn.kbt.dbdtobean.config;

import cn.kbt.dbdtobean.utils.DBDToBeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.sql.Connection;

@ConfigurationProperties(prefix = "dbdtobean", ignoreInvalidFields = true)
@Component("dBDToBeanProperties")
@ConditionalOnClass(DBDToBeanDataSource.class)
public class DBDToBeanProperties {
    /**
     * 适配Springboot的数据库配置
     **/
    @Autowired
    DBDToBeanDataSource dataSource;
    /**
     * 数据库源对象
     **/
    private Connection conn;
    /**
     * 数据库驱动
     **/
    private String driverName;
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
    /**
     * 数据库类型
     **/
    private String dateBaseType = "MySQL";
    /**
     * 作者
     **/
    private String authorName = System.getenv().get("USERNAME");

    public DBDToBeanProperties() {
    }

    public Connection getConn() {
        if (conn == null) {
            if (dateBaseType.equals("MySQL")) {
                if (DBDToBeanUtils.isNotEmpty(driverName) && DBDToBeanUtils.isNotEmpty(url) && DBDToBeanUtils.isNotEmpty(username) && DBDToBeanUtils.isNotEmpty(password)) {
                    conn = DBDToBeanUtils.getMysqlConnection(driverName, url, username, password);
                } else if (DBDToBeanUtils.isNotEmpty(dataSource.getDriverClassName()) && DBDToBeanUtils.isNotEmpty(dataSource.getUrl()) && DBDToBeanUtils.isNotEmpty(dataSource.getUsername()) && DBDToBeanUtils.isNotEmpty(dataSource.getPassword())) {
                    conn = DBDToBeanUtils.getMysqlConnection(dataSource.getDriverClassName(), dataSource.getUrl(), dataSource.getUsername(), dataSource.getPassword());
                }
            } else if (dateBaseType.equals("Oracle")) {
                conn = DBDToBeanUtils.getOracleConnection(driverName, url, username, password);
            }
        }
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
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

    public String getDateBaseType() {
        return dateBaseType;
    }

    public void setDateBaseType(String dateBaseType) {
        this.dateBaseType = dateBaseType;
    }

    public String getAuthorName() {
        if (DBDToBeanUtils.isEmpty(authorName)) {
            return "";
        }
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
}
