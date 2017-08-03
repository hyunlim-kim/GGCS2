package org.androidtown.calendar.month;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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

import java.util.List;


/*Boy의 listView adapter*/
public class ExpenseAdapter02 extends BaseAdapter {

    private Context context;
    private ExpenseBean expenseBean;
    private int mId;


    public ExpenseAdapter02(Context context, ExpenseBean expenseBean){
        this.context = context;
        this.expenseBean = expenseBean;
    }

    @Override
    public int getCount() {
        return expenseBean.getExpenseBoyList().size();
    }

    @Override
    public Object getItem(int position) {
        return expenseBean.getExpenseBoyList().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final int pos = position;

        LayoutInflater li = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = li.inflate(R.layout.lay_expense_first, null); // 여기다가 리스트 뷰를 뿌림

        final ExpenseBean.ExpenseSubBean BoyList = expenseBean.getExpenseBoyList().get(position);

        TextView txtPayment02 = (TextView)convertView.findViewById(R.id.txtPayment01);
        TextView txtDetail02 = (TextView)convertView.findViewById(R.id.txtDetail01);
        TextView txtEtc02 = (TextView)convertView.findViewById(R.id.txtEtc01);

        //내역 삭제하기
        final AlertDialog[] dialog = new AlertDialog[1];
        convertView.findViewById(R.id.btnDelete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener OKListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mId = pos;
                        new ExpenseAdapter02.deleteExpenseTask().execute();
                        Intent i = new Intent(context, MainActivity.class);
                        context.startActivity(i);
                    }//end onclick
                };//end OKListener
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                dialog[0] = builder.setMessage("정말 삭제하시겠습니까? \n내용은 되돌릴 수 없습니다.")
                        .setPositiveButton("확인",OKListener)
                        .setNegativeButton("취소",null)
                        .create();
                dialog[0].show();
            }//end onClick
        });//삭제하기

        txtDetail02.setText(BoyList.getDetail());
        txtEtc02.setText(BoyList.getPlace());
        int money = new Integer(BoyList.getMoney());
        String Price = String.format("%,d", money);
        txtPayment02.setText(Price);

        return convertView;
    }//end getView

    private class deleteExpenseTask extends AsyncTask<String, Void, String> {

        private String money_Id;
        // URL_LOGIN_PROC 설정(자기 IP에 맞게 설정할 것!!!!!!!!!!)
        public final String URL_LOGIN_PROC = commonActivity.BASE_URL +"/rest/deleteExpense.do?";
        // DataBase에 저장되어 있는 table의 항목 이름(자바에서 변수랑 똑같아야 함)


        @Override
        protected void onPreExecute() {
            money_Id = expenseBean.getExpenseBoyList().get(mId).getMoney_Id();
        } // onPreExecute()


        @Override
        protected String doInBackground(String... params) {

            try {
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new FormHttpMessageConverter());

                MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
                map.add("Money_Id",money_Id);


                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

                HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(map, headers);

                return restTemplate.postForObject(URL_LOGIN_PROC, request, String.class);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        } // doInBackground()

        @Override
        protected void onPostExecute(String s) {
            Gson gson = new Gson();
            try {
                UserBean bean = gson.fromJson(s, UserBean.class);
                if (bean != null)   {
                    if (bean.getResult().equals("ok"))  {
                        Toast.makeText(context, "내역이 삭제되었습니다", Toast.LENGTH_SHORT).show();
                    } else  {
                        Toast.makeText(context, bean.getResultMsg(), Toast.LENGTH_SHORT).show();
                    }
                } // try
            }   catch (Exception e) {
                Toast.makeText(context, "파싱실패", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            } // catch

            ExpenseFirstActivity.listView02.clearChoices();
            notifyDataSetChanged();


        } // onPostExecute()

    } // class LoginProcTask


    //총액계산
    public void setTotDispTextView(TextView txtView) {
        List<ExpenseBean.ExpenseSubBean> boyList = expenseBean.getExpenseBoyList();
        int totMoney = 0;
        for(int i=0; i<boyList.size(); i++) {
            totMoney += Integer.parseInt( boyList.get(i).getMoney() );
        }


        String TotalMoney = String.format("%,d",totMoney);

        txtView.setText( TotalMoney + " 원" );
    }

}









