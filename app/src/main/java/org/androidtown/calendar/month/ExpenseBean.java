package org.androidtown.calendar.month;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 서현 on 2017-07-27.
 */

//주석2
public class ExpenseBean{

    private String result;
    private List<ExpenseSubBean> ExpenseBoyList;
    private List<ExpenseSubBean> ExpenseGirlList;


    public class ExpenseSubBean {
        private String sex;
        private String money;
        private String detail;
        private String place;
        private String memo;
        private String money_Id;
        private String date;
        private String userId;

        //생성자
        public ExpenseSubBean(String sex, String detail, String money, String place, String memo, String money_Id, String date, String user_Id) {
            this.sex = sex;
            this.money = money;
            this.detail = detail;
            this.place = place;
            this.memo = memo;
            this.money_Id = money_Id;
            this.date = date;
            this.userId = userId;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public String getPlace() {
            return place;
        }

        public void setPlace(String place) {
            this.place = place;
        }

        public String getMemo() {
            return memo;
        }

        public void setMemo(String memo) {
            this.memo = memo;
        }

        public String getMoney_Id() {
            return money_Id;
        }

        public void setMoney_Id(String money_Id) {
            this.money_Id = money_Id;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    };//end ExpenseSubBean


    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<ExpenseSubBean> getExpenseBoyList() {
        return ExpenseBoyList;
    }

    public void setExpenseBoyList(List<ExpenseSubBean> expenseBoyList) {
        ExpenseBoyList = expenseBoyList;
    }

    public List<ExpenseSubBean> getExpenseGirlList() {
        return ExpenseGirlList;
    }

    public void setExpenseGirlList(List<ExpenseSubBean> expenseGirlList) {
        ExpenseGirlList = expenseGirlList;
    }
}
