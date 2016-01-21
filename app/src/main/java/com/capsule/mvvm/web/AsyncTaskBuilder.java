package com.capsule.mvvm.web;

import android.os.AsyncTask;

/**
 * Created by 宇宙神帝 on 2015/9/1.
 */
public class AsyncTaskBuilder {

    private BackgroundTask backgroundTask;

    private ResultHandler resultHandler;

    public static AsyncTaskBuilder createBuilder() {
        return new AsyncTaskBuilder();
    }

    public AsyncTaskBuilder setBackgroundTask(BackgroundTask backgroundTask) {
        this.backgroundTask = backgroundTask;
        return this;
    }

    public AsyncTaskBuilder setResultHandler(ResultHandler resultHandler) {
        this.resultHandler = resultHandler;
        return this;
    }

    public void execute() {
        new AsyncTask<Object, Object, Result>() {

            @Override
            protected Result doInBackground(Object[] params) {
                try {
                    if(backgroundTask != null) {
                        return backgroundTask.onBackground();
                    }
                } catch (Exception e) {

                }
                return null;
            }

            @Override
            protected void onPostExecute(Result result) {
                if(resultHandler != null) {
                    resultHandler.onResult(result);
                }
            }
        }.execute();
    }

    public static interface BackgroundTask {
        Result onBackground() throws Exception;
    }

    public static interface ResultHandler {
        void onResult(Result result);
    }
}
