package com.capsule.mvvm.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.capsule.mvvm.bean.WorksBean;
import com.capsule.mvvm.databinding.AdapterItemBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 宇宙神帝 on 16/1/20.
 */
public class WorksAdapter extends BaseAdapter {

    private List<WorksBean> worksBeans;

    private Context context;

    public WorksAdapter(Context context) {
        this.context = context;
        worksBeans = new ArrayList<WorksBean>();
    }

    @Override
    public int getCount() {
        return worksBeans.size();
    }

    @Override
    public Object getItem(int i) {
        return worksBeans.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null) {
            AdapterItemBinding adapterItemBinding = AdapterItemBinding.inflate(LayoutInflater.from(context), viewGroup, false);
            view = adapterItemBinding.getRoot();
            view.setTag(adapterItemBinding);
        }

        AdapterItemBinding adapterItemBinding = (AdapterItemBinding) view.getTag();
        adapterItemBinding.setWorksBean(worksBeans.get(i));
        adapterItemBinding.draweeView.setImageURI(Uri.parse(worksBeans.get(i).getImageUrl()));
        return view;
    }

    public void setWorksBeans(List<WorksBean> worksBeans) {
        this.worksBeans = worksBeans;
    }
}
