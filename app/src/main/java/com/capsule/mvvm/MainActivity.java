package com.capsule.mvvm;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;

import com.alibaba.fastjson.JSON;
import com.capsule.mvvm.adapter.WorksAdapter;
import com.capsule.mvvm.bean.WorksBean;
import com.capsule.mvvm.databinding.MainActivityBinding;
import com.capsule.mvvm.web.AsyncTaskBuilder;
import com.capsule.mvvm.web.Result;
import com.capsule.mvvm.widget.LoadMoreView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 宇宙神帝 on 16/1/18.
 */
public class MainActivity extends Activity {

    private int page;

    private OkHttpClient okHttpClient = new OkHttpClient();

    private List<WorksBean> worksBeans;

    private WorksAdapter worksAdapter;

    private MainActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.main_activity);
        worksAdapter = new WorksAdapter(this);
        binding.listView.setAdapter(worksAdapter);
        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh(true);
            }
        });

        binding.listView.setLoadMoreListener(new LoadMoreView.LoadMoreListener() {
            @Override
            public void loadMore() {
                refresh(false);
            }
        });

        refresh(true);
    }

    public void refresh(boolean isHeader) {
        binding.swipeRefreshLayout.setRefreshing(true);
        if(isHeader) {
            page = 1;
            worksBeans = new ArrayList<WorksBean>();
        }

        AsyncTaskBuilder.createBuilder().setBackgroundTask(new AsyncTaskBuilder.BackgroundTask() {
            @Override
            public Result onBackground() throws Exception {
                RequestBody requestBody = new FormBody.Builder()
                        .add("page", String.valueOf(page))
                        .build();

                Request request = new Request.Builder()
                        .url("http://182.92.107.35/api/getWorksList")
                        .post(requestBody)
                        .build();

                Response response = okHttpClient.newCall(request).execute();
                String body = response.body().string();
                Map<String, Object> map = JSON.parseObject(body, Map.class);
                String errno = map.get("errno").toString();

                Result result = new Result();
                Map<String, Object> data = (Map<String, Object>) map.get("data");
                if(errno.equals("0")) {
                    List<Map<String, Object>> worksList = (List) data.get("worksList");
                    for (Map<String, Object> works : worksList) {
                        WorksBean worksBean = new WorksBean();
                        worksBean.setName(works.get("name").toString());
                        worksBean.setImageUrl(works.get("mainPic_url").toString());
                        worksBeans.add(worksBean);
                    }
                    result.setFlag(true);
                }
                return result;
            }
        }).setResultHandler(new AsyncTaskBuilder.ResultHandler() {
            @Override
            public void onResult(Result result) {
                binding.swipeRefreshLayout.setRefreshing(false);
                binding.listView.loadEnd();
                if (result.isFlag()) {
                    page++;
                    worksAdapter.setWorksBeans(worksBeans);
                    worksAdapter.notifyDataSetChanged();
                }
            }
        }).execute();
    }
}
