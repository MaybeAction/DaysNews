package uianimationdemo.com.example.daysnews.entiy;

/**
 * Created by Steven on 2017/2/10.
 */

public class NewsListInfo {
    private String subgroup;
    private int subid;
    private boolean isClick;

    public boolean isClick() {
        return isClick;
    }

    public void setClick(boolean click) {
        isClick = click;
    }

    public NewsListInfo(String sungroup, int subid) {
        this.subgroup = sungroup;
        this.subid = subid;
    }

    public String getSungroup() {
        return subgroup;
    }

    public void setSungroup(String sungroup) {
        this.subgroup = sungroup;
    }

    public int getSubid() {
        return subid;
    }

    public void setSubid(int subid) {
        this.subid = subid;
    }

    @Override
    public String toString() {
        return "NewsListInfo{" +
                "subgroup='" + subgroup + '\'' +
                ", subid=" + subid +
                '}';
    }
}
