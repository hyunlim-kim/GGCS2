package org.androidtown.calendar.month;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ExpenseFirstActivity extends AppCompatActivity {

    private TextView txtDate, txtTotal01, txtTotal02;
    private Button btnAdd01, btnAdd02, btnModify01, btnModify02, btnDelete01, btnDelete02;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView)findViewById(R.id.listView);


        txtDate = (TextView)findViewById(R.id.txtDate); // 달력에서 받은 날짜 텍스트 뷰에 뿌림
        txtTotal01 = (TextView)findViewById(R.id.txtTotal01);              // 총합
        txtTotal02 = (TextView)findViewById(R.id.txtTotal02);

        btnAdd01 = (Button)findViewById(R.id.btnAdd01);                // 목록 추가 - Expense02 로 이동
        btnAdd02 = (Button)findViewById(R.id.btnAdd02);
        btnModify01 = (Button)findViewById(R.id.btnModify01);             // 내역 수정하기 - Expense 02 로 이동 ( 내역 전달 )
//        btnModify02 = (Button)findViewById(R.id.btnModify02);
        btnDelete01 = (Button)findViewById(R.id.btnDelete01);             // 내역 삭제 , 테이블에서 삭제
//        btnDelete02 = (Button)findViewById(R.id.btnDelete02);

//         public ExpenseBean (String payment,String detail, String etc)
        ExpenseBean exp1 = new ExpenseBean("성별", "사용내역", "금액", "사용처", "메모", "money_id", "날짜", "사용자 아이디");
        ExpenseBean exp2 = new ExpenseBean("성별", "사용내역", "금액", "사용처", "메모", "money_id", "날짜", "사용자 아이디");


        List<ExpenseBean> list = new ArrayList<ExpenseBean>();  // 리스트에 위에 입력한거 넣음 임시.. 테스트
        list.add(exp1);
        list.add(exp2);


        ExpenseAdapter adapter = new ExpenseAdapter(this, list);


        listView.setAdapter(adapter);

        txtTotal01.setText("합계 넣기");
        txtDate.setText("날짜 넣기");



    }
}
