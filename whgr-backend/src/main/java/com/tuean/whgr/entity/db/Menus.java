package com.tuean.whgr.entity.db;
public class Menus {
    private Long id;
    private String menuName;
    private Integer menuType;
    private String menuUrl;
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getMenuName() {
        return this.menuName;
    }
    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }
    public Integer getMenuType() {
        return this.menuType;
    }
    public void setMenuType(Integer menuType) {
        this.menuType = menuType;
    }
    public String getMenuUrl() {
        return this.menuUrl;
    }
    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }
}