package org.androidtown.calendar.month;

import android.content.Intent;
import android.os.AsyncTask;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class LoginActivity extends AppCompatActivity {

    // 변수 선언
    private EditText mEdtUserId, mEdtUserPw;
    private Button mBtnLogin, mBtnJoin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // 변수와 xml에서 component 찾아 연결
        mEdtUserId = (EditText)findViewById(R.id.edtUserId);
        mEdtUserPw = (EditText)findViewById(R.id.edtUserPw);

        // Button들에 대해 onClickListener 걸어주기
        mBtnLogin.setOnClickListener(btnClick);
        mBtnJoin.setOnClickListener(btnClick);

    } // onCreate

    // OnClickListener Event 설정
    private View.OnClickListener btnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.btnLogin)   {

            } else if ( v.getId() == R.id.btnJoin)  {
                Intent intent = new Intent(LoginActivity.this, JoinActivity.class);
                LoginActivity.this.startActivity(intent);
            }
        } // onClick
    }; // onClickListener

    private class LoginProcTask extends AsyncTask<String, Void, String> {

        // URL_LOGIN_PROC 설정(자기 IP에 맞게 설정할 것!!!!!!!!!!)
        public static final String URL_LOGIN_PROC = "http://117.17.93.204:8086/rest/loginProc.do";
        // DataBase에 저장되어 있는 table의 항목 이름(자바에서 변수랑 똑같아야 함)
        private String userId, userPw;

        @Override
        protected void onPreExecute() {
            // 사용자가 아이디와 비밀번호 입력한 내용들을 string으로 불러옴
            userId = mEdtUserId.getText().toString();
            userPw = mEdtUserPw.getText().toString();
        } // onPreExecute()

        @Override
        protected String doInBackground(String... params) {

            try {
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new FormHttpMessageConverter());

                MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
                map.add("userId",userId);
                map.add("userPw",userPw);

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

                HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(map, headers);

                return restTemplate.postForObject(URL_LOGIN_PROC, request, String.class);

            }   catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        } // doInBackground()

        @Override
        protected void onPostExecute(String s) {
            // 

        } // onPostExecute()

    } // class LoginProcTask
} // public class LoginActivity
