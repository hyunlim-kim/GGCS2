package org.androidtown.calendar.month;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.text.format.Time;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
    String today;
    static int MONTH;
    static int YEAR;

    static List<ExpenseBean.ExpenseSubBean> GirlMonthlList;
    static List<ExpenseBean.ExpenseSubBean> BoyMonthList;


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
    }//end onBackPressed



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 월별 캘린더 뷰 객체 참조
        monthView = (CalendarMonthView) findViewById(R.id.monthView);
        monthViewAdapter = new CalendarMonthAdapter(this);
        monthView.setAdapter(monthViewAdapter);


        /*해당날짜 클릭시 상세 페이지로 넘어가기 위한 리스너 정의*/
        monthView.setOnDataSelectionListener(new OnDataSelectionListener() {
            public void onDataSelected(AdapterView parent, View v, int position, long id) {
                // 현재 선택한 일자 정보 표시
                MonthItem curItem = (MonthItem) monthViewAdapter.getItem(position);
                int day = curItem.getDay();
                today = curYear+"." + (curMonth+1) +"." + day;

                Intent dataIntent = new Intent(MainActivity.this, ExpenseFirstActivity.class);
                dataIntent.putExtra("today",today);
                startActivity(dataIntent);

            }
        });//end monthView.setOnDataSelectionListener


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

        super.onResume();
        new ExpenseMonthTask().execute("http://172.16.8.188:8080/rest/selectExpenseMonthList.do?userId="
                + LoginActivity.PuserId + "&date=" + curYear +"."+ (curMonth+1));

        MONTH = curMonth;
        YEAR = curYear;

    }//end onCreate

    @Override
    protected void onResume() {
        super.onResume();
        new ExpenseMonthTask().execute("http://172.16.8.188:8080/rest/selectExpenseMonthList.do?userId="
                + LoginActivity.PuserId + "&date=" + curYear +"."+ (curMonth+1));

        MONTH = curMonth;
        YEAR = curYear;

    }

    class ExpenseMonthTask extends AsyncTask<String,String,ExpenseBean> {

        private ProgressDialog prd;

        @Override
        protected void onPreExecute(){
            prd = new ProgressDialog(MainActivity.this);
            prd.setMessage("달력을 로딩중입니다...");
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
                GirlMonthlList = Bean.getExpenseGirlList();
                BoyMonthList = Bean.getExpenseBoyList();

                monthViewAdapter.notifyDataSetChanged();//getView를 다시 호출하는 명령어

            }//end if



        }//end onPostExecute


    };//end ExpenseMonthTask
//----------------------------------------------------------------------------------------------------

    private void setMonthText() {// * 월 표시 텍스트 설정
        curYear = monthViewAdapter.getCurYear();
        curMonth = monthViewAdapter.getCurMonth();

        monthText.setText(curYear + "년 " + (curMonth + 1) + "월");
    }//end setMonthText

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

        //그리드 뷰 하나의 색깔을 정의
        private void init() {
            setBackgroundColor(Color.WHITE);
        }


        public MonthItem getItem() {
            return item;
        }

        //*************그리드뷰 하나의 텍스트를 정의하는************************************************************
        public void setItem(MonthItem item) {
            this.item = item;

            int day = item.getDay();
            if(day != 0){
                setText(String.valueOf(day));
            }
            else{
                setText("");
            }

        }//end setItem

        public void addText(String addText) {

            setText( getText() + "\n" + addText );

        }//end setItem


    }//end MonthItemView

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
        public static int mblue = Color.rgb(138, 200, 241);
        public static int mpink = Color.rgb(255, 216, 216);

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

        //달을 다시 계산하는 함수
        public void recalculate() {

            // set to the first day of the month
            mCalendar.set(Calendar.DAY_OF_MONTH, 1);

            // get week day
            int dayOfWeek = mCalendar.get(Calendar.DAY_OF_WEEK);
            firstDay = getFirstDay(dayOfWeek);

            mStartDay = mCalendar.getFirstDayOfWeek();
            curYear = mCalendar.get(Calendar.YEAR);
            curMonth = mCalendar.get(Calendar.MONTH);
            lastDay = getMonthLastDay(curYear, curMonth);

            int diff = mStartDay - Calendar.SUNDAY - 1;
            startDay = getFirstDayOfWeek();

        }

        //이전달을 계산하는 함수
        public void setPreviousMonth() {
            mCalendar.add(Calendar.MONTH, -1);
            recalculate();

            resetDayNumbers();
            selectedPosition = -1;
        }

        //다음달을 계산하는 함수
        public void setNextMonth() {
            mCalendar.add(Calendar.MONTH, 1);
            recalculate();

            resetDayNumbers();
            selectedPosition = -1;
        }

        //날짜를 ?
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

        //요일을 표시하는 함수
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


        //현재 년도를 가져오는
        public int getCurYear() {
            return curYear;
        }

        //현재 월을 가져오는
        public int getCurMonth() {
            return curMonth;
        }


        //칼럼의 갯수 지정
        public int getNumColumns() {
            return 7;
        }

        //그리드 뷰의 갯수 지정
        public int getCount() {
            return 7 * 6;
        }

        //
        public Object getItem(int position) {
            return items[position];
        }

        //
        public long getItemId(int position) {
            return position;
        }

        //
        public View getView(int position, View convertView, ViewGroup parent) {

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
                itemView.setBackgroundColor(mpink);
            } else {
                itemView.setBackgroundColor(Color.WHITE);
            }

            ////////////////////////////////////////////////////////////////
            if(GirlMonthlList != null) {
                for (int i = 0; i < GirlMonthlList.size(); i++) {
                    ExpenseBean.ExpenseSubBean bean = GirlMonthlList.get(i);
                    if (Integer.parseInt(bean.getDay()) == items[position].getDay()) {
                        itemView.addText("G:" + bean.getSumMoney());
                    }
                }
            }
            if(BoyMonthList != null) {
                for (int i = 0; i < BoyMonthList.size(); i++) {
                    ExpenseBean.ExpenseSubBean bean = BoyMonthList.get(i);
                    if (Integer.parseInt(bean.getDay()) == items[position].getDay()) {
                        itemView.addText("B:" + bean.getSumMoney());
                    }
                }
            }
            ////////////////////////////////////////////////////////////////

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