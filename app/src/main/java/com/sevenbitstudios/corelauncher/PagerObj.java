package com.sevenbitstudios.corelauncher;

import java.util.ArrayList;

public class PagerObj {
    private ArrayList<AppInfo> appList;

    public PagerObj (ArrayList<AppInfo> appList) {
        this.appList = appList;
    }

    public ArrayList<AppInfo> getAppList() {
        return appList;
    }
}
