package org.androidtown.calendar.month;

import java.io.Serializable;

/**
 * Created by 서현 on 2017-07-27.
 */

//주석2
public class ExpenseBean implements Serializable {

    private String sex; // 지출액
    private String money; // 사용내역
    private String detail; // 카드/상점
    private String place; //사용처
    private String memo; //메모
    private String money_id;
    private String date; //날짜
    private String user_id; //아이디


    public ExpenseBean(String sex, String detail, String money, String place, String memo, String money_id, String date, String user_id) {
        this.sex = sex;
        this.money = money;
        this.detail = detail;
        this.place = place;
        this.memo = memo;
        this.money_id = money_id;
        this.date = date;
        this.user_id = user_id;
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

    public String getMoney_id() {
        return money_id;
    }

    public void setMoney_id(String money_id) {
        this.money_id = money_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
