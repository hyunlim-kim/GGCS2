package org.androidtown.calendar.month;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.text.format.Time;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

/**
 * 그리드뷰를 이용해 월별 캘린더를 만드는 방법에 대해 알 수 있습니다.
 *
 * @author Mike
 */
public class MainActivity extends AppCompatActivity {

    CalendarMonthView monthView;            // 월별 캘린더 뷰 객체
    CalendarMonthAdapter monthViewAdapter;  //월별 캘린더 어댑터
    TextView monthText;                     //월을 표시하는 텍스트뷰
    int curYear;                            //현재 연도
    int curMonth;                           // 현재 월


    /* 뒤로가기 두 번 누르면 종료하는 기능 */
    long pressTime;


    @Override
    public void onBackPressed() {

        if (System.currentTimeMillis() - pressTime < 2000) {
            finishAffinity();
            return;
        }
        Toast.makeText(this, "한 번 더 누르시면 앱이 종료됩니다", Toast.LENGTH_LONG).show();
        pressTime = System.currentTimeMillis();
    }

    final String tapColor1 = "#FFB9CD";
    final String tapColor2 = "#90D2D1";
    final String backColor = "#D6D6D6";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 월별 캘린더 뷰 객체 참조
        monthView = (CalendarMonthView) findViewById(R.id.monthView);
        monthViewAdapter = new CalendarMonthAdapter(this);
        monthView.setAdapter(monthViewAdapter);



        // 리스너 설정
        monthView.setOnDataSelectionListener(new OnDataSelectionListener() {
            public void onDataSelected(AdapterView parent, View v, int position, long id) {
                // 현재 선택한 일자 정보 표시
                MonthItem curItem = (MonthItem) monthViewAdapter.getItem(position);
                int day = curItem.getDay();
                Log.d("MainActivity", "Selected : " + day);


            }
        });

        monthText = (TextView) findViewById(R.id.monthText);
        setMonthText();

        // 이전 월로 넘어가는 이벤트 처리
        Button monthPrevious = (Button) findViewById(R.id.monthPrevious);
        monthPrevious.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                monthViewAdapter.setPreviousMonth();
                monthViewAdapter.notifyDataSetChanged();

                setMonthText();
            }
        });

        // 다음 월로 넘어가는 이벤트 처리
        Button monthNext = (Button) findViewById(R.id.monthNext);
        monthNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                monthViewAdapter.setNextMonth();
                monthViewAdapter.notifyDataSetChanged();

                setMonthText();
            }
        });

    }

    // * 월 표시 텍스트 설정

    private void setMonthText() {
        curYear = monthViewAdapter.getCurYear();
        curMonth = monthViewAdapter.getCurMonth();

        monthText.setText(curYear + "년 " + (curMonth + 1) + "월");
    }

    /**
     * 일자 정보를 담기 위한 클래스 정의
     *
     * @author Mike
     *
     */
    public static class MonthItem {

        private int dayValue;

        public MonthItem() {

        }

        public MonthItem(int day) {
            dayValue = day;
        }

        public int getDay() {
            return dayValue;
        }

        public void setDay(int day) {
            this.dayValue = day;
        }



    }

    /**
     * 일자에 표시하는 텍스트뷰 정의
     *
     * @author Mike
     */
    public static class MonthItemView extends AppCompatTextView {

        private MonthItem item;

        public MonthItemView(Context context) {
            super(context);

            init();
        }

        public MonthItemView(Context context, AttributeSet attrs) {
            super(context, attrs);

            init();
        }

        private void init() {
            setBackgroundColor(Color.WHITE);
        }


        public MonthItem getItem() {
            return item;
        }

        public void setItem(MonthItem item) {
            this.item = item;

            int day = item.getDay();
            if (day != 0) {
                setText(String.valueOf(day));
            } else {
                setText("");
            }

        }


    }

    /**
     * 어댑터 객체 정의
     *
     * @author Mike
     *
     */
    public static class CalendarMonthAdapter extends BaseAdapter {

        public static final String TAG = "CalendarMonthAdapter";

        Context mContext;

        public static int oddColor = Color.rgb(225, 225, 225);
        public static int headColor = Color.rgb(12, 32, 158);
        public static int pink = Color.rgb(255, 216, 216);

        private int selectedPosition = -1;

        private MonthItem[] items;

        private int countColumn = 7;

        int mStartDay;
        int startDay;
        int curYear;
        int curMonth;

        int firstDay;
        int lastDay;

        Calendar mCalendar;
        boolean recreateItems = false;

        public CalendarMonthAdapter(Context context) {
            super();

            mContext = context;

            init();
        }

        public CalendarMonthAdapter(Context context, AttributeSet attrs) {
            super();

            mContext = context;

            init();
        }

        private void init() {
            items = new MonthItem[7 * 6];

            mCalendar = Calendar.getInstance();
            recalculate();
            resetDayNumbers();

        }

        public void recalculate() {

            // set to the first day of the month
            mCalendar.set(Calendar.DAY_OF_MONTH, 1);

            // get week day
            int dayOfWeek = mCalendar.get(Calendar.DAY_OF_WEEK);
            firstDay = getFirstDay(dayOfWeek);
            Log.d(TAG, "firstDay : " + firstDay);

            mStartDay = mCalendar.getFirstDayOfWeek();
            curYear = mCalendar.get(Calendar.YEAR);
            curMonth = mCalendar.get(Calendar.MONTH);
            lastDay = getMonthLastDay(curYear, curMonth);

            Log.d(TAG, "curYear : " + curYear + ", curMonth : " + curMonth + ", lastDay : " + lastDay);

            int diff = mStartDay - Calendar.SUNDAY - 1;
            startDay = getFirstDayOfWeek();
            Log.d(TAG, "mStartDay : " + mStartDay + ", startDay : " + startDay);

        }

        public void setPreviousMonth() {
            mCalendar.add(Calendar.MONTH, -1);
            recalculate();

            resetDayNumbers();
            selectedPosition = -1;
        }

        public void setNextMonth() {
            mCalendar.add(Calendar.MONTH, 1);
            recalculate();

            resetDayNumbers();
            selectedPosition = -1;
        }

        public void resetDayNumbers() {
            for (int i = 0; i < 42; i++) {
                // calculate day number
                int dayNumber = (i+1) - firstDay;
                if (dayNumber < 1 || dayNumber > lastDay) {
                    dayNumber = 0;
                }

                // save as a data item
                items[i] = new MonthItem(dayNumber);
            }
        }

        private int getFirstDay(int dayOfWeek) {
            int result = 0;
            if (dayOfWeek == Calendar.SUNDAY) {
                result = 0;
            } else if (dayOfWeek == Calendar.MONDAY) {
                result = 1;
            } else if (dayOfWeek == Calendar.TUESDAY) {
                result = 2;
            } else if (dayOfWeek == Calendar.WEDNESDAY) {
                result = 3;
            } else if (dayOfWeek == Calendar.THURSDAY) {
                result = 4;
            } else if (dayOfWeek == Calendar.FRIDAY) {
                result = 5;
            } else if (dayOfWeek == Calendar.SATURDAY) {
                result = 6;
            }

            return result;
        }


        public int getCurYear() {
            return curYear;
        }

        public int getCurMonth() {
            return curMonth;
        }


        public int getNumColumns() {
            return 7;
        }

        public int getCount() {
            return 7 * 6;
        }

        public Object getItem(int position) {
            return items[position];
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            Log.d(TAG, "getView(" + position + ") called.");

            MonthItemView itemView;
            if (convertView == null) {
                itemView = new MonthItemView(mContext);
            } else {
                itemView = (MonthItemView) convertView;
            }

            // create a params
            int screenHeight = ((Activity)mContext).getWindowManager().getDefaultDisplay().getHeight();
            int columnHeight = screenHeight / getNumColumns();

            GridView.LayoutParams params = new GridView.LayoutParams(
                    GridView.LayoutParams.MATCH_PARENT,
                    columnHeight);

            // calculate row and column
            int rowIndex = position / countColumn;
            int columnIndex = position % countColumn;

            Log.d(TAG, "Index : " + rowIndex + ", " + columnIndex);

            // set item data and properties
            itemView.setItem(items[position]);
            itemView.setLayoutParams(params);
            itemView.setPadding(2, 2, 2, 2);

            // set properties
            itemView.setGravity(Gravity.LEFT);

            if (columnIndex == 0) {
                itemView.setTextColor(Color.RED);
            }
            else if(columnIndex == 6){
                itemView.setTextColor(Color.BLUE);
            }
            else {
                itemView.setTextColor(Color.BLACK);
            }

            // set background color
            if (position == getSelectedPosition()) {
                itemView.setBackgroundColor(pink);
            } else {
                itemView.setBackgroundColor(Color.WHITE);
            }




            return itemView;
        }


        /**
         * Get first day of week as android.text.format.Time constant.
         * @return the first day of week in android.text.format.Time
         */
        public static int getFirstDayOfWeek() {
            int startDay = Calendar.getInstance().getFirstDayOfWeek();
            if (startDay == Calendar.SATURDAY) {
                return Time.SATURDAY;
            } else if (startDay == Calendar.MONDAY) {
                return Time.MONDAY;
            } else {
                return Time.SUNDAY;
            }
        }


        /**
         * get day count for each month
         *
         * @param year
         * @param month
         * @return
         */
        private int getMonthLastDay(int year, int month){
            switch (month) {
                case 0:
                case 2:
                case 4:
                case 6:
                case 7:
                case 9:
                case 11:
                    return (31);

                case 3:
                case 5:
                case 8:
                case 10:
                    return (30);

                default:
                    if(((year%4==0)&&(year%100!=0)) || (year%400==0) ) {
                        return (29);   // 2월 윤년계산
                    } else {
                        return (28);
                    }
            }
        }








        /**
         * set selected row
         *
         * @param selectedPosition
         */
        public void setSelectedPosition(int selectedPosition) {
            this.selectedPosition = selectedPosition;
        }

        /**
         * get selected row
         *
         * @return
         */
        public int getSelectedPosition() {
            return selectedPosition;
        }


    }

    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }//end onCreateOptionsMenu

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add) {
            Intent intent = new Intent(getApplicationContext(), ExpenseSecond.class);
            startActivity(intent);
            return super.onOptionsItemSelected(item);
        }//end if
        return true;
    }//end onOptionsItemSelected

}//end class