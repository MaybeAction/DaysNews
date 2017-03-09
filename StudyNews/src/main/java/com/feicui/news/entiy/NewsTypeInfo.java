package com.feicui.news.entiy;

/**
 * Created by Administrator on 2017/2/10.
 */

public class NewsTypeInfo {

    private int subid;
    private String subgroup;

    public NewsTypeInfo(int subid, String subgroup) {
        this.subid = subid;
        this.subgroup = subgroup;
    }

    public int getSubid() {
        return subid;
    }

    public void setSubid(int subid) {
        this.subid = subid;
    }

    public String getSubgroup() {
        return subgroup;
    }

    public void setSubgroup(String subgroup) {
        this.subgroup = subgroup;
    }

    @Override
    public String toString() {
        return "NewsTypeInfo{" +
                "subid=" + subid +
                ", subgroup='" + subgroup + '\'' +
                '}';
    }
}
