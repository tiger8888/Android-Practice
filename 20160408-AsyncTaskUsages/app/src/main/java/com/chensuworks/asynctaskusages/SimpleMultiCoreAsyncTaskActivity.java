package com.chensuworks.asynctaskusages;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.widget.TextView;

public class SimpleMultiCoreAsyncTaskActivity extends Activity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.multicount);

        // Start counting off the main UI thread
        CountingTask tsk = new CountingTask();
        //tsk.execute(R.id.counter1);
        tsk.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, R.id.counter1); // TODO: understand executor

        CountingTask tsk2 = new CountingTask();
        tsk2.execute(R.id.counter2);

        CountingTask tsk3 = new CountingTask();
        tsk3.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, R.id.counter3);

        //startParallelTask(R.id.counter2);
        //startParallelTask(R.id.counter3);
        //startParallelTask(R.id.counter4);
        //startParallelTask(R.id.counter5);
        //startParallelTask(R.id.counter6);
        //startParallelTask(R.id.counter7);
        //startParallelTask(R.id.counter8);

    }

    private void startParallelTask(int id) {
        CountingTask tsk = new CountingTask();
        tsk.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, id);
        //tsk.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, id); // this will run all AsyncTasks in one thread.
        //tsk.execute(id);
    }

    private class CountingTask extends AsyncTask<Integer, Integer, Integer> {

        private int counterId;

        CountingTask() {
        }

        @Override
        protected Integer doInBackground(Integer... id) {

            counterId = id[0];
            int i = 0;

            while (i < 100) {
                SystemClock.sleep(100);
                i++;

                if (i % 5 == 0) {
                    // update UI with progress every 5%
                    publishProgress(i);
                }
            }

            return i;
        }

        protected void onProgressUpdate(Integer... progress) {
            TextView tv = (TextView) findViewById(counterId);
            String text = progress[0] + "% Complete!";
            tv.setText(text);
        }

        protected void onPostExecute(Integer result) {
            TextView tv = (TextView) findViewById(counterId);
            String text = "Count Complete! Counted to " + result.toString();
            tv.setText(text);
        }

    }

}
