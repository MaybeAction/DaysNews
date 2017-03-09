package uianimationdemo.com.example.daysnews.entiy;

import java.util.List;

/**
 * Created by Steven on 2017/2/16.
 */
public class NewsCommentInfo<Comment> {
    /*
    {
	“status”:0,
	“message”:”OK”,
	“data”:[
		{
			“cid”:评论编号,
			“uid”:评论者名字,
			“portrait”:用户头像链接,
			“stamp”:评论时间,
			“content":评论内容
		},……
	]
     */
    private String message;
    private List<Comment> data;
    private int status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<Comment> getData() {
        return data;
    }

    public void setData(List<Comment> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "NewsCommentInfo{" +
                "message='" + message + '\'' +
                ", data='" + data + '\'' +
                ", status=" + status +
                '}';
    }


    public class Comment {
        private int cid;
        private String uid;
        private String stamp;
        private String content;
        private String portrait;

        public int getCid() {
            return cid;
        }

        public void setCid(int cid) {
            this.cid = cid;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getStamp() {
            return stamp;
        }

        public void setStamp(String stamp) {
            this.stamp = stamp;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getPortrait() {
            return portrait;
        }

        public void setPortrait(String portrait) {
            this.portrait = portrait;
        }

        @Override
        public String toString() {
            return "Comment{" +
                    "cid='" + cid + '\'' +
                    ", uid='" + uid + '\'' +
                    ", stamp='" + stamp + '\'' +
                    ", content='" + content + '\'' +
                    ", portrait='" + portrait + '\'' +
                    '}';
        }
    }
}

