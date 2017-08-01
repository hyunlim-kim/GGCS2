package org.androidtown.calendar.month;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import android.widget.Toast;

import com.google.gson.Gson;

public class ExpenseSecond extends AppCompatActivity {


    private EditText mEdtMoney, mEdtDetail,mEdtPlace,mEdtMemo;
    private ProgressBar mprogressBar;
    private DatePicker mDatePicker;
    private String Sex;

    // 변수 선언
    private DatePicker mDatePicker;
    private RadioButton mWoman, mMan;
    private EditText mEdtMoney, mEdtDetail, mEdtPlace, mEdtMemo;
    private Button mBtnSave, mBtnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_second);

        mEdtMoney = (EditText)findViewById(R.id.edtMoney);
        mEdtDetail = (EditText)findViewById(R.id.edtDetail);
        mEdtPlace = (EditText)findViewById(R.id.edtPlace);
        mEdtMemo = (EditText)findViewById(R.id.edtMemo);
        mDatePicker = (DatePicker)findViewById(R.id.datePicker);
        RadioGroup SexGroup = (RadioGroup)findViewById(R.id.addRadio1);

        SexGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radiogroup, int i) {
                RadioButton genderButton = (RadioButton) findViewById(i);
                Sex = genderButton.getText().toString();
            }
        });


        mprogressBar = (ProgressBar)findViewById(R.id.progressBar);


        final String Date = String.format("%d.%d.%d", mDatePicker.getYear(),mDatePicker.getMonth() +1 , mDatePicker.getDayOfMonth());
        //취소버튼을 눌렀을 때
        final AlertDialog[] dialog = new AlertDialog[1];
        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener OKListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(ExpenseSecond.this);
                dialog[0] = builder.setMessage("정말 최소하시겠습니까? \n 내용은 자동저장되지 않습니다")
                        .setPositiveButton("확인",OKListener)
                        .setNegativeButton("취소",null)
                        .create();
                dialog[0].show();
            }//end onClick
        });//end OnClickListener

        //'저장하기' 버튼을 눌렀을 때


        findViewById(R.id.btnOK).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Money = mEdtMoney.getText().toString();
                String Detail = mEdtDetail.getText().toString();


                if (Money == null || Detail == null || Date == null || Sex == null) {
                    Toast.makeText(ExpenseSecond.this, "필수항목을 입력해주세요", Toast.LENGTH_SHORT).show();
                }//end if
                else {
                    new ExpenseSecond.expenseInsertTask().execute();
                }
            }
        });//end btnOK




    } //end onCreate


    private class expenseInsertTask extends AsyncTask<String, Void, String> {

        public static final String URL_INSERTEXPENSE = "http://172.16.8.188:8080/rest/insertExpense.do";

        private String Money,Detail,Place,Memo,Date;


        @Override
        protected void onPreExecute() {
            Money = mEdtMoney.getText().toString();
            Detail = mEdtDetail.getText().toString();
            Place = mEdtPlace.getText().toString();
            Memo = mEdtMemo.getText().toString();
            Date = String.format("%d.%d.%d",mDatePicker.getYear(),mDatePicker.getMonth() +1 , mDatePicker.getDayOfMonth());
            mprogressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new FormHttpMessageConverter());

                MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
                map.add("userId", LoginActivity.userId);
                map.add("Money", Money);
                map.add("Detail", Detail);
                map.add("Place", Place);
                map.add("Memo", Memo);
                map.add("Date",Date);
                map.add("Sex",Sex);


                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.ALL.APPLICATION_FORM_URLENCODED);

                HttpEntity<MultiValueMap<String,Object>> request = new HttpEntity<>(map, headers);

                return restTemplate.postForObject(URL_INSERTEXPENSE, request, String.class);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {


            mprogressBar.setVisibility(View.INVISIBLE);

            Gson gson = new Gson();

            try

            {
                UserBean bean = gson.fromJson(s, UserBean.class);
                if (bean != null) {
                    if (bean.getResult().equals("ok")) {
                        Toast.makeText(ExpenseSecond.this,"등록을 완료하였습니다.", Toast.LENGTH_SHORT).show();
                        finish();

                    } else {
                        Toast.makeText(ExpenseSecond.this, bean.getResultMsg(), Toast.LENGTH_SHORT).show();
                    }
                }

            } catch (Exception e) {
                Toast.makeText(ExpenseSecond.this, "파싱 실패", Toast.LENGTH_SHORT).show();
            }
        }
    }



} //ExpenseSecond class 닫힘
