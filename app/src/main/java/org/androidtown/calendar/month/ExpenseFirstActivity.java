package org.androidtown.calendar.month;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.androidtown.calendar.month.LoginActivity.PuserId;


/*사용내역 상세조회 */

public class ExpenseFirstActivity extends AppCompatActivity {

    private TextView txtDate, txtTotal01, txtTotal02;
    private ListView listView01, listView02;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_first);

        listView01 = (ListView) findViewById(R.id.listView01); //Girl의 ListView
        listView02 = (ListView) findViewById(R.id.listView02); //Boy의 ListView

        txtDate = (TextView) findViewById(R.id.txtDate);
        txtTotal01 = (TextView) findViewById(R.id.txtTotal01);  //Girl의 total
        txtTotal02 = (TextView) findViewById(R.id.txtTotal02);  //Boy의 total

        String today = getIntent().getStringExtra("today"); //선택된 날짜
        txtDate.setText(today);

        //Girl 내역 추가하기
        findViewById(R.id.btnAdd01).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ExpenseFirstActivity.this, ExpenseSecond.class);
                startActivity(i);
            }
        });

        //Boy 내역 추가하기
        findViewById(R.id.btnAdd02).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ExpenseFirstActivity.this, ExpenseSecond.class);
                startActivity(i);
            }
        });

        //상세내역을 받아오는 URL
        new ExpenseTask().execute("http://172.16.8.188:8080/rest/selectExpenseList.do?userId=" + LoginActivity.PuserId + "&date=" + today);


    }//end onCreate


    //상세내역 조회하는 Task
    class ExpenseTask extends AsyncTask<String,String,ExpenseBean> {

        private ProgressDialog prd;

        @Override
        protected void onPreExecute(){
            prd = new ProgressDialog(ExpenseFirstActivity.this);
            prd.setMessage("사용내역을 가져오는 중입니다");
            prd.setCancelable(false);
            prd.show();
        }//end onPreExecute

        @Override
        protected ExpenseBean doInBackground(String... params) {

            StringBuilder output = new StringBuilder(); //StringBuilder는 String 값을 버퍼에 저장하여 한꺼번에 가져와주는 !!

            try {
                URL url = new URL(params[0]);

                BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
                String line = null;

                //정상적으로 들어온 데이터를 읽어낸다
                while (true){
                    line = reader.readLine();
                    if(line == null) break;
                    output.append(line+"\n");
                }//end while
                reader.close();

                String ExpenseData = output.toString();

                //파싱을 시작한다
                Gson gson = new GsonBuilder().create();
                ExpenseBean Bean = gson.fromJson(ExpenseData, ExpenseBean.class);

                return Bean;

            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        } //end doInBackground

        @Override
        protected void onPostExecute(ExpenseBean Bean) {
            prd.dismiss();
            if(Bean !=null){ //들어온 정보가 비어있거나 길이가 0 이 아닌 즉,제대로 들어오는 경우

                //Girl의 리스트뷰 어뎁터 지정
                ExpenseAdapter01 adapter01 = new ExpenseAdapter01(ExpenseFirstActivity.this,Bean);
                adapter01.setTotDispTextView( txtTotal01 );
                listView01.setAdapter(adapter01);

                //Boy의 리스트뷰 어뎁터 지정
                ExpenseAdapter02 adapter02 = new ExpenseAdapter02(ExpenseFirstActivity.this,Bean);
                adapter02.setTotDispTextView( txtTotal02 );
                listView02.setAdapter(adapter02);

            }//end if
        }//end onPostExecute


    };//end ExpenseTask

}//end class
