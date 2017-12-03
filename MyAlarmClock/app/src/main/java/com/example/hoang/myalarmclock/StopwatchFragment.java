package com.example.hoang.myalarmclock;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by hoang on 12/3/2017.
 */
public class StopwatchFragment extends MyFragment {

    public StopwatchFragment(){
        setName("Bấm Giờ");
    }
    Button btnStop,btnStart, btnPause;
    TextView txtTimer;
    long lStartTime, lPauseTime, lSystemTime = 0L;
    Handler handler = new Handler();
    boolean isRun;

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            lSystemTime = SystemClock.uptimeMillis() - lStartTime;
            long lUpdateTime = lPauseTime + lSystemTime;
            long secs = (long)(lUpdateTime/1000);
            long mins= secs/60;
            secs = secs %60;
            long milliseconds = (long)(lUpdateTime%1000);
            txtTimer.setText(""+mins+":" + String.format("%02d",secs) + ":" + String.format("%03d",milliseconds));
            handler.postDelayed(this,0);
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timer,null);
        btnStart = (Button)view.findViewById(R.id.btnStart);
        btnStop = (Button)view.findViewById(R.id.btnStop);
        btnPause = (Button)view.findViewById(R.id.btnPause);
        txtTimer = (TextView)view.findViewById(R.id.txtTimer);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        clickStart();
        clickStop();
        clickPause();
    }

    public void clickStart()
    {
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isRun)
                    return;
                isRun = true;
                lStartTime = SystemClock.uptimeMillis();
                handler.postDelayed(runnable, 0);
            }
        });
    }


    public void clickStop()
    {
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isRun)
                    return;
                isRun = false;
                lPauseTime = 0;
                handler.removeCallbacks(runnable);
            }
        });
    }

    public void clickPause()
    {
        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isRun)
                    return;
                isRun = false;
                lPauseTime += lSystemTime;
                handler.removeCallbacks(runnable);
            }
        });
    }
}
