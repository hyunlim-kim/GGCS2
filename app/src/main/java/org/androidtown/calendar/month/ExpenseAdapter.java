package org.androidtown.calendar.month;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Adapter가 하는 역할은 사용자 데이터를 입력받아 View를 생성하는 것이며 Adapter에서 생성되는 View는 ListView 내 하나의 아이템 영역에 표시되는 것
 * User01List 에 뿌려줘야 함.
 *
 * Created by 서현 on 2017-07-27.
 */

public class ExpenseAdapter extends BaseAdapter {

    private Context context;
    private List<ExpenseBean> list;

    private TextView txtPayment01, txtDetail01, txtEtc01;
    private TextView txtPayment02, txtDetail02, txtEtc02;

    public ExpenseAdapter(Context context, List<ExpenseBean>list){
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater li = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        convertView = li.inflate(R.layout.lay_expense_first, null); // 여기다가 리스트 뷰를 뿌림

        final ExpenseBean exp = list.get(position); // 리스트에 넣을 ..

        txtPayment01 = (TextView)convertView.findViewById(R.id.txtPayment01);            // 지출액
//        txtPayment02 = (TextView)convertView.findViewById(R.id.txtPayment02);
        txtDetail01 = (TextView)convertView.findViewById(R.id.txtDetail01);             // 지출내역
//        txtDetail02 = (TextView)convertView.findViewById(R.id.txtDetail02);

        txtEtc01 = (TextView)convertView.findViewById(R.id.txtEtc01);              // 카드 or 현금 , 사용처
//        txtEtc02 = (TextView)convertView.findViewById(R.id.txtEtc02);

        txtPayment01.setText(exp.getPayment()); // 지출액에 빈에서 읽어온 지출액을 표시 .,.일단은 하나에 다 표시 ..
        txtDetail01.setText(exp.getDetail());
        txtEtc01.setText(exp.getEtc());



        return convertView;
    }
}
