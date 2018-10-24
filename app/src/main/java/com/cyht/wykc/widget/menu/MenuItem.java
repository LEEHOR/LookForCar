package com.cyht.wykc.widget.menu;

/**
 * Author：Bro0cL on 2016/12/26.
 */

public class MenuItem {
    private int icon;
    private String text;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;
    public MenuItem() {}

    public MenuItem(String text) {
        this.text = text;
    }

    public MenuItem(String id,String text) {
        this.text = text;
        this.id=id;
    }

    public MenuItem(int iconId, String text) {
        this.icon = iconId;
        this.text = text;
    }

    public int getIcon() {
        return icon;

}
    public void setIcon(int iconId) {
        this.icon = iconId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
