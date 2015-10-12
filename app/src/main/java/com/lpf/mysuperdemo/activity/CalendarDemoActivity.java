package com.lpf.mysuperdemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.TextView;

import com.lpf.mysuperdemo.R;

import java.text.SimpleDateFormat;

public class CalendarDemoActivity extends Activity {

    private CalendarView mCalendarView;
    private TextView mTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_demo);

        initViews();

        initEvents();
    }


    private void initViews() {
        mCalendarView = (CalendarView)this.findViewById(R.id.calendarView);
        mTime = (TextView)this.findViewById(R.id.id_time);

        Long nowTime = mCalendarView.getDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String nowTimeStr = sdf.format(nowTime);
        mTime.setText(nowTimeStr);
    }

    private void initEvents() {
        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {

                String nowTimeStr = year + "/" + (month + 1) + "/" + dayOfMonth;
                mTime.setText(nowTimeStr);
            }
        });
    }

}
