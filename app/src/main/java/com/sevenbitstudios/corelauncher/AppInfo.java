package com.sevenbitstudios.corelauncher;

import android.graphics.drawable.Drawable;

public class AppInfo {
    private String name;
    private String packageName;
    private Drawable icon;

    private Boolean isInDrawer;

    public AppInfo(String name, String packageName, Drawable img, Boolean isInDrawer) {
        this.name = name;
        this.packageName = packageName;
        this.icon = img;
        this.isInDrawer = isInDrawer;
    }

    public String getName() { return name; }
    public String getPackageName() { return packageName; }

    public Drawable getIcon() { return icon;}
    public Boolean getIsInDrawer(){ return isInDrawer;}
    public void setName(String name){
        this.name = name;
    }
    public void setPackageName(String packageName){
        this.packageName = packageName;
    }
    public void setIcon(Drawable icon){
        this.icon = icon;
    }

    public void setIsInDrawer(Boolean status) {
        this.isInDrawer = status;
    }
}
