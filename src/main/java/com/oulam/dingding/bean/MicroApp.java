package com.oulam.dingding.bean;

/**
 * 微应用实体类
 */
public class MicroApp {
    private String appIcon;
    private int agentId;
    private String appDesc;
    private boolean isSelf;
    private String name;
    private String homepageLink;
    private String pcHomepageLink;
    private int appStatus;
    private String ompLink;

    public MicroApp() {
    }

    public String getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(String appIcon) {
        this.appIcon = appIcon;
    }

    public int getAgentId() {
        return agentId;
    }

    public void setAgentId(int agentId) {
        this.agentId = agentId;
    }

    public String getAppDesc() {
        return appDesc;
    }

    public void setAppDesc(String appDesc) {
        this.appDesc = appDesc;
    }

    public boolean isSelf() {
        return isSelf;
    }

    public void setSelf(boolean self) {
        isSelf = self;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHomepageLink() {
        return homepageLink;
    }

    public void setHomepageLink(String homepageLink) {
        this.homepageLink = homepageLink;
    }

    public String getPcHomepageLink() {
        return pcHomepageLink;
    }

    public void setPcHomepageLink(String pcHomepageLink) {
        this.pcHomepageLink = pcHomepageLink;
    }

    public int getAppStatus() {
        return appStatus;
    }

    public void setAppStatus(int appStatus) {
        this.appStatus = appStatus;
    }

    public String getOmpLink() {
        return ompLink;
    }

    public void setOmpLink(String ompLink) {
        this.ompLink = ompLink;
    }

    @Override
    public String toString() {
        return "MicroApp{" +
                "appIcon='" + appIcon + '\'' +
                ", agentId=" + agentId +
                ", appDesc='" + appDesc + '\'' +
                ", isSelf=" + isSelf +
                ", name='" + name + '\'' +
                ", homepageLink='" + homepageLink + '\'' +
                ", pcHomepageLink='" + pcHomepageLink + '\'' +
                ", appStatus=" + appStatus +
                ", ompLink='" + ompLink + '\'' +
                '}';
    }
}
