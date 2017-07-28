package org.androidtown.calendar.month;

import java.io.Serializable;

/**
 * Created by 서현 on 2017-07-27.
 */

public class ExpenseBean implements Serializable {

    private String payment; // 지출액
    private String detail; // 사용내역
    private String etc; // 카드/상점


    public ExpenseBean(String payment, String detail, String etc) {
        this.payment = payment;
        this.detail = detail;
        this.etc = etc;

    }


    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getEtc() {
        return etc;
    }

    public void setEtc(String etc) {
        this.etc = etc;
    }



}
