package com.vmall.search.analyzer.cfg;

/**
 * Created by ss on 2016/10/29.
 */
public class JdbcConfig {

    private String driver;
    private String password;
    private String username;
    private String url;

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
