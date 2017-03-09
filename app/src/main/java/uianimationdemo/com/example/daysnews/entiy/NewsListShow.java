package uianimationdemo.com.example.daysnews.entiy;

import java.io.Serializable;
import java.util.List;

/**
 * 新闻列表实体类
 * Gson解析规则，遇到大括号，写类，遇到中括号写集合
 */

public class NewsListShow<News> {
   /*
{
	“status”:0,
	“message”:”OK”,
	“data”:[
		{
	“nid:”新闻id,
	“icon”:新闻图标,
	“title”:新闻标题,
    “summary”:摘要,
	“link:”新闻的网页链接
	"stamp:" 新闻时间戳
        }
 */
    private int status;
    private String message;
    private List<News> data;


    public static class News implements Serializable{
        private int nid;
        private String icon;
        private String title;
        private String link;
        private String summary;
        private String stamp;

        public News() {

        }


        @Override
        public String toString() {
            return "news{" +
                    "nid=" + nid +
                    ", icon='" + icon + '\'' +
                    ", title='" + title + '\'' +
                    ", link='" + link + '\'' +
                    ", summary='" + summary + '\'' +
                    ", stamp='" + stamp + '\'' +
                    '}';
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getStamp() {
            return stamp;
        }

        public void setStamp(String stamp) {
            this.stamp = stamp;
        }

        public int getNid() {
            return nid;
        }

        public void setNid(int nid) {
            this.nid = nid;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }
    }

    @Override
    public String toString() {
        return "NewsListShow{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<News> getData() {
        return data;
    }

    public void setData(List<News> data) {
        this.data = data;
    }
}
