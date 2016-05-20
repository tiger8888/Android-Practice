package com.chensuworks.asynctaskusages;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RestartableAsyncTaskActivity extends Activity implements View.OnClickListener {

    private TextView mTextView;
    private CounterTask singleTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restart_async_task);

        mTextView = (TextView) findViewById(R.id.counter);

        Button mButtonStopThread = (Button) findViewById(R.id.button_stop_thread);
        mButtonStopThread.setOnClickListener(this);

        Button mButtonStartThread = (Button) findViewById(R.id.button_start_thread);
        mButtonStartThread.setOnClickListener(this);
    }

    @Override
    protected void onPause() {
        singleTask.cancel(true);
        //Toast.makeText(this, "onPause()", Toast.LENGTH_SHORT).show();
        super.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();

        //singleTask = new CounterTask();
        //singleTask.execute();
    }

    @Override
    protected void onResume() {
        super.onResume();

        singleTask = new CounterTask();
        singleTask.execute();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.button_stop_thread:
                singleTask.cancel(true);
                break;
            case R.id.button_start_thread:
                // FINISHED/PENDING/RUNNING 
                if (singleTask.getStatus() == AsyncTask.Status.FINISHED) {
                    singleTask = new CounterTask();
                    singleTask.execute();
                }
                break;
            default:
                // do nothing
        }

    }

    private class CounterTask extends AsyncTask<Void, Integer, Integer> {

        public CounterTask() {}

        @Override
        protected Integer doInBackground(Void... input) {
            int i = 0;

            while (i < 100) {
                if (isCancelled()) {
                    return null;
                }

                SystemClock.sleep(50);
                i++;

                if (i % 5 == 0) {
                    publishProgress(i);
                }
            }

            return i;

        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            String text = progress[0] + "% Completed!";
            mTextView.setText(text);
        }

        @Override
        protected void onPostExecute(Integer result) {
            String text = "Count Completed! Counted to " + result.toString();
            mTextView.setText(text);
        }

        @Override
        protected void onCancelled() {
            String text = "Thread cancelled!";
            mTextView.setText(text);
        }

    }

}
