package com.levi.jobscheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "TMS MainActivity";

    private Button startJob;
    private Button cancelJob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        startJob = findViewById(R.id.schedule);
        cancelJob = findViewById(R.id.cancel);


        startJob.setOnClickListener(startListener);
        cancelJob.setOnClickListener(cancelListener);
    }


    View.OnClickListener startListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            ComponentName componentName = new ComponentName(MainActivity.this, ExampleJobService.class);
            JobInfo jobInfo = new JobInfo.Builder(123, componentName)
                    .setRequiresCharging(true)
                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                    .setPersisted(true)
                    .setPeriodic(15 * 60 * 1000)
                    .build();

            JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
            int resultCode = scheduler.schedule(jobInfo);
            
            if (resultCode == JobScheduler.RESULT_SUCCESS) {
                Log.d(TAG, "onClick: JobSceduler");
            }
            else {
                Log.d(TAG, "onClick: Jobsceduler failed");
            }
        }
    };

    View.OnClickListener cancelListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
            scheduler.cancel(123);
            Log.d(TAG, "Canceled button onClick: Job canceled");

        }
    };




}
