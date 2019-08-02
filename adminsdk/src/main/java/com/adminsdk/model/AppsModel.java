package com.adminsdk.model;

public class AppsModel {
    private String app_package;
    private String app_name;
    private String app_icon;

    public AppsModel(String app_package, String app_name, String app_icon) {
        this.app_package = app_package;
        this.app_name = app_name;
        this.app_icon = app_icon;
    }

    public String getApp_package() {
        return app_package;
    }

    public void setApp_package(String app_package) {
        this.app_package = app_package;
    }

    public String getApp_name() {
        return app_name;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }

    public String getApp_icon() {
        return app_icon;
    }

    public void setApp_icon(String app_icon) {
        this.app_icon = app_icon;
    }


}
