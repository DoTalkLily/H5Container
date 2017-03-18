package com.tt.ly.offlinepackage;

/**
 * Created by Seven on 17/3/11.
 */

import java.util.Date;

public class ResourceRecord {

    private long id; //资源包id

    private String application_package; //应用唯一标识

    private String name; //资源包名

    private String md5; //资源md5

    private String version; //版本号

    private String host; //cdn host

    private String path; //路径

    private String description; //资源包描述

    private Date create; // 创建时间

    private Date modified; //修改时间

    private Date expires; //过期时间

    private int status; //资源包状态 可用：1 异常：2

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getApplication_package() {
        return application_package;
    }

    public void setApplication_package(String application_package) {
        this.application_package = application_package;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreate() {
        return create;
    }

    public void setCreate(Date create) {
        this.create = create;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public Date getExpires() {
        return expires;
    }

    public void setExpires(Date expires) {
        this.expires = expires;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
