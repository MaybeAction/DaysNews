package uianimationdemo.com.example.daysnews.entiy;

import java.util.List;

/**
 * Created by Steven on 2017-2-20.
 */

public class UserInfomation<User> {
    private String uid;
    private int integration;
    private List<User> loginlog;
    private int comnum;
    private String portrait;

    public int getComnum() {
        return comnum;
    }

    public void setComnum(int comnum) {
        this.comnum = comnum;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getIntegration() {
        return integration;
    }

    public void setIntegration(int integration) {
        this.integration = integration;
    }


    public List<User> getLoginlog() {
        return loginlog;
    }

    public void setLoginlog(List<User> loginlog) {
        this.loginlog = loginlog;
    }

    @Override
    public String toString() {
        return "UserInfomation{" +
                "uid='" + uid + '\'' +
                ", integration=" + integration +
                ", loginlog=" + loginlog +
                ", comnum=" + comnum +
                ", portrait='" + portrait + '\'' +
                '}';
    }

    public class User{
        private String time;
        private String address;
        private int device;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getDevice() {
            return device;
        }

        public void setDevice(int device) {
            this.device = device;
        }

        @Override
        public String toString() {
            return "User{" +
                    "time='" + time + '\'' +
                    ", address='" + address + '\'' +
                    ", device='" + device + '\'' +
                    '}';
        }
    }
}
