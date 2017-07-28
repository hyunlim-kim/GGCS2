package org.androidtown.calendar.month;

import android.content.Intent;
import android.os.AsyncTask;
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


// 주석
public class JoinActivity extends AppCompatActivity {

    private EditText mEdtId, mEdtPw;
    private Button mBtnJoin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        mEdtId = (EditText)findViewById(R.id.edtId);
        mEdtPw = (EditText)findViewById(R.id.edtPw);
        mBtnJoin = (Button)findViewById(R.id.btnJoin);

        findViewById(R.id.btnJoin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new JoinTask().execute();
            }
        });
    }//end onCreate()

    private class JoinTask extends AsyncTask<String, Void, String> {

        public static final String URL_LOGIN_PROC = "http://117.17.93.203:8888/rest/JoinProc.do";

        private String userId, userPw;

        @Override
        protected void onPreExecute() {
            userId = mEdtId.getText().toString();
            userPw = mEdtPw.getText().toString();
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new FormHttpMessageConverter());

                MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
                map.add("Id", userId);
                map.add("Password", userPw);

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.ALL.APPLICATION_FORM_URLENCODED);

                HttpEntity<MultiValueMap<String,Object>> request = new HttpEntity<>(map, headers);

                return restTemplate.postForObject(URL_LOGIN_PROC, request, String.class);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            Gson gson = new Gson();

            try

            {
                UserBean bean = gson.fromJson(s, UserBean.class);
                if (bean != null) {
                    if (bean.getResult().equals("ok")) {
                        Intent i = new Intent(JoinActivity.this, LoginActivity.class);
                        startActivity(i);

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
