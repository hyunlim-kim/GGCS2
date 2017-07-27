package org.androidtown.calendar.month;

import java.util.List;

/**
 * Created by pc on 2017-07-27.
 */

public class UserBean extends CommonBean {

    private UserBeanSub userBean;
    private List<UserBeanSub> userList;

    public UserBeanSub getUserBean() {
        return userBean;
    }

    public void setUserBean(UserBeanSub userBean) {
        this.userBean = userBean;
    }

    public List<UserBeanSub> getUserList() {
        return userList;
    }

    public void setUserList(List<UserBeanSub> userList) {
        this.userList = userList;
    }

    class UserBeanSub extends CommonBean {
        private String userId;
        private String userPw;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserPw() {
            return userPw;
        }

        public void setUserPw(String userPw) {
            this.userPw = userPw;
        }
    }
}
