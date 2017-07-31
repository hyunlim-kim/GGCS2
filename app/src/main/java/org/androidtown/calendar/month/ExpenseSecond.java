package org.androidtown.calendar.month;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

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

            if (v.getId() == R.id.btnSave)  {

                String mStringEdtMoney = mEdtMoney.getText().toString();
                String mStringEdtDetail = mEdtDetail.getText().toString();
                String mStringEdtPlace = mEdtPlace.getText().toString();
                String mStringEdtMemo = mEdtMemo.getText().toString();

                if(mStringEdtMoney.length() > 1 && mStringEdtDetail.length() >1)    {
                    // new expenseDetailToDB.execute();

                } // 금맥과 사용내역에 텍스트를 입력한 경우
                else {
                    Toast.makeText(ExpenseSecond.this,"금액(또는 사용내역)을 작성하지 않았습니다. \n 다시 확인해주세요", Toast.LENGTH_SHORT).show();
                } // 금액과 사용내역에 텍스트를 입력하지 않은 경우
            } // if (v.getId() == R.id. btnSave)

            if (v.getId() == R.id.btnCancel)    {
                Toast.makeText(ExpenseSecond.this, "입력을 취소합니다", Toast.LENGTH_SHORT).show();
            } // if(v.getId() == R.id.btnCancel)
        } // void onClick(View v)
    }; // expenseBtnClick에 대한 onClickListener 닫힘

    // 확인을 누르면 상세내역이 DataBase로 넘어가는 과정
    private class expenseDetailToDB extends AsyncTask<String, Void, String> {

        public static final String URL_JOIN_PROC = "http://117.17.93.204:8086/rest/insertUser.do";

        private String sex, Money, Detail, Place, Memo, Date;

        @Override
        protected void onPreExecute() {
            Money = mEdtMoney.getText().toString();
            Detail = mEdtDetail.getText().toString();
        } // onPreExecute()

        @Override
        protected String doInBackground(String... params) {
            try {

            }   catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        } // doInBackground

        @Override
        protected void onPostExecute(String s) {

            Gson gson = new Gson();

            try {
                ExpenseBean bean = gson.fromJson(s, ExpenseBean.class);
                if ( bean != null)  {

                } else  {

                }

            }   catch (Exception e) {
                Toast.makeText(ExpenseSecond.this, "저장 실패입니다.", Toast.LENGTH_SHORT).show();
            }
        } // onPostExecute
    }

} //ExpenseSecond class 닫힘
