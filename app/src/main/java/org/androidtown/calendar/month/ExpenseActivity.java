package org.androidtown.calendar.month;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ExpenseActivity extends AppCompatActivity {

    TextView dateView;
    int year;   //년
    int month;  //월
    int date;   //일




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);
    }
}
