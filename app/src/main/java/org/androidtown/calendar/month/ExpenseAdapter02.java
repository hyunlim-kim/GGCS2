package org.androidtown.calendar.month;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;


/*Boy의 listView adapter*/
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
        int money = new Integer(BoyList.getMoney());
        String Price = String.format("%,d", money);
        txtPayment02.setText(Price);

        return convertView;
    }


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









