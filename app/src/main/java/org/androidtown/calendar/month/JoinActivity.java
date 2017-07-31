package org.androidtown.calendar.month;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


// 주석
public class JoinActivity extends AppCompatActivity {

    private EditText mEdtId, mEdtPw,mEdtPwCheck;
    private ProgressBar JoinProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        mEdtId = (EditText)findViewById(R.id.edtId);
        mEdtPw = (EditText)findViewById(R.id.edtPw);
        mEdtPwCheck = (EditText)findViewById(R.id.edtPwCheck);

        JoinProgressBar = (ProgressBar)findViewById(R.id.JoinprogressBar);



        findViewById(R.id.btnJoin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Pw1 = mEdtPw.getText().toString();
                String Pw2 = mEdtPwCheck.getText().toString();
                String ID = mEdtId.getText().toString();

                if(Pw1.equals(Pw2) && ID.length() > 3 ){
                        new JoinTask().execute();
                }
                else if(ID.length() <= 3){
                    Toast.makeText(JoinActivity.this,"아이디는 3자 이상이여야 합니다",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(JoinActivity.this,"비밀번호가 일치하지 않습니다 \n 다시 확인해주세요", Toast.LENGTH_SHORT).show();
                }
            }//end onClick

        });
    }//end onCreate()

    private class JoinTask extends AsyncTask<String, Void, String> {

        public static final String URL_JOIN_PROC = "http://117.17.93.203:8888/rest/insertUser.do";

        private String userId, userPw;

        @Override
        protected void onPreExecute() {
            userId = mEdtId.getText().toString();
            userPw = mEdtPw.getText().toString();

            JoinProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new FormHttpMessageConverter());

                MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
                map.add("userId", userId);
                map.add("UserPw", userPw);

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.ALL.APPLICATION_FORM_URLENCODED);

                HttpEntity<MultiValueMap<String,Object>> request = new HttpEntity<>(map, headers);

                return restTemplate.postForObject(URL_JOIN_PROC, request, String.class);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {


            JoinProgressBar.setVisibility(View.INVISIBLE);

            Gson gson = new Gson();

            try

            {
                UserBean bean = gson.fromJson(s, UserBean.class);
                if (bean != null) {
                    if (bean.getResult().equals("ok")) {
                        Toast.makeText(JoinActivity.this,"회원가입에 성공하셨습니다", Toast.LENGTH_SHORT).show();
                        finish();

                    } else {
                        Toast.makeText(JoinActivity.this, bean.getResultMsg(), Toast.LENGTH_SHORT).show();
                    }
                }

            } catch (Exception e) {
                Toast.makeText(JoinActivity.this, "파싱 실패", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
