package com.fyp.agrifarm.beans;

public class UserIntrestData {
    private int icon;
    private String icon_title;

    public UserIntrestData(int icon, String icon_title) {
        this.icon = icon;
        this.icon_title = icon_title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getIcon_title() {
        return icon_title;
    }

    public void setIcon_title(String icon_title) {
        this.icon_title = icon_title;
    }
}
