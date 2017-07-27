package org.androidtown.calendar.month;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/* 어플리케이션을 실행했을 때 뜨는 로고 화면 */

public class SplashActivity extends Activity {

    private static int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        }, SPLASH_TIME_OUT);
    }
}