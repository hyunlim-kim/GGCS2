package org.androidtown.calendar.month;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class ExpenseSecond extends AppCompatActivity {

    // 변수 선언
    private DatePicker mDatePicker;
    private RadioButton mWoman, mMan;
    private EditText mEdtMoney, mEdtDetail, mEdtPlace, mEdtMemo;
    private Button mBtnSave, mBtnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_second);

        // 변수와 xml에서 component 찾아 연결
        mDatePicker = (DatePicker)findViewById(R.id.datePicker);

        mWoman = (RadioButton)findViewById(R.id.woman);
        mMan = (RadioButton)findViewById(R.id.man);

        mEdtMoney  = (EditText)findViewById(R.id.edtMoney);
        mEdtDetail = (EditText)findViewById(R.id.edtDetail);
        mEdtPlace = (EditText)findViewById(R.id.edtPlace);
        mEdtMemo = (EditText)findViewById(R.id.edtMemo);

        mBtnSave = (Button)findViewById(R.id.btnSave);
        mBtnCancel = (Button)findViewById(R.id.btnCancel);

    } //onCreate 닫힘

    // 여성, 남성에 대한 radio 버튼 event 설정
    private View.OnClickListener selectSexBtnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    }; // selectSexBtnClick에 대한 onClickListener 닫힘

    // save, cancel에 대한 버튼 event 설정
    private View.OnClickListener expenseBtnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    }; // expenseBtnClick에 대한 onClickListener 닫힘


} //ExpenseSecond class 닫힘
