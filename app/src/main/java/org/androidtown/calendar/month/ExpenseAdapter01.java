package org.androidtown.calendar.month;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by qnqnqn1239 on 2017. 8. 1..
 */

public class ExpenseAdapter01 extends BaseAdapter {

        private Context context;
        private ExpenseBean expenseBean;


        public ExpenseAdapter01(Context context, ExpenseBean expenseBean){
            this.context = context;
            this.expenseBean = expenseBean;
        }

        @Override
        public int getCount() {
            return expenseBean.getExpenseGirlList().size();
        }

        @Override
        public Object getItem(int position) {
            return expenseBean.getExpenseGirlList().get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater li = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = li.inflate(R.layout.lay_expense_first, null); // 여기다가 리스트 뷰를 뿌림

            final ExpenseBean.ExpenseSubBean GirlList = expenseBean.getExpenseGirlList().get(position);

            TextView txtPayment02 = (TextView)convertView.findViewById(R.id.txtPayment01);
            TextView txtDetail02 = (TextView)convertView.findViewById(R.id.txtDetail01);
            TextView txtEtc02 = (TextView)convertView.findViewById(R.id.txtEtc01);

            txtDetail02.setText(GirlList.getDetail());
            txtEtc02.setText(GirlList.getPlace());
            String Price = String.format("%,d",new Integer(GirlList.getMoney()));
            txtPayment02.setText(Price);

            return convertView;
        }



}
