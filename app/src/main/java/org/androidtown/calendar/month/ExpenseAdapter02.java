package org.androidtown.calendar.month;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

/**
 * Adapter가 하는 역할은 사용자 데이터를 입력받아 View를 생성하는 것이며 Adapter에서 생성되는 View는 ListView 내 하나의 아이템 영역에 표시되는 것
 * User01List 에 뿌려줘야 함.
 *
 * Created by 서현 on 2017-07-27.
 */

public class ExpenseAdapter02 extends BaseAdapter {

    private Context context;
    private ExpenseBean expenseBean;


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

        LayoutInflater li = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = li.inflate(R.layout.lay_expense_first, null); // 여기다가 리스트 뷰를 뿌림

        final ExpenseBean.ExpenseSubBean BoyList = expenseBean.getExpenseBoyList().get(position);

        TextView txtPayment02 = (TextView)convertView.findViewById(R.id.txtPayment01);
        TextView txtDetail02 = (TextView)convertView.findViewById(R.id.txtDetail01);
        TextView txtEtc02 = (TextView)convertView.findViewById(R.id.txtEtc01);

        txtDetail02.setText(BoyList.getDetail());
        txtEtc02.setText(BoyList.getPlace());
        String Price = String.format("%,d",new Integer(BoyList.getMoney()));
        txtPayment02.setText(Price);

        return convertView;
    }
}









