package com.lacker.micros.data.domain.datasource.properties;

public class DefaultPorps {

    private String urlTemplate;
    private String username;
    private String password;

    public DefaultPorps() {
        this.urlTemplate = "'jdbc:mysql://${database}?characterEncoding=utf8&serverTimezone=GMT%2B8&useSSL=false'";
    }

    public String getUrlTemplate() {
        return urlTemplate;
    }

    public void setUrlTemplate(String urlTemplate) {
        this.urlTemplate = urlTemplate;
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
