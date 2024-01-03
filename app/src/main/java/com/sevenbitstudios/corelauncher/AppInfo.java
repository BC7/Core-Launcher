package com.sevenbitstudios.corelauncher;

import android.graphics.drawable.Drawable;

public class AppInfo {
    private String name;
    private String packageName;
    private Drawable icon;

    public AppInfo(String name, String packageName, Drawable img) {
        this.name = name;
        this.packageName = packageName;
        this.icon = img;
    }

    public String getName() { return name; }
    public String getPackageName() { return packageName; }

    public Drawable getIcon() { return icon;}

    public void setName(String name){
        this.name = name;
    }
    public void setPackageName(String packageName){
        this.packageName = packageName;
    }
    public void setIcon(Drawable icon){
        this.icon = icon;
    }

}
