package com.qilin.AnimationLearing.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qilin.AnimationLearing.R;

/**
 * Created by Administrator on 2017/5/21.
 */

public class AnimtionListAdapter extends RecyclerView.Adapter<AnimtionListAdapter.MyViewHolder> {
    private String[] list;

    public AnimtionListAdapter(String[] list) {
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.setTextView(list[position]);
        holder.setOnClick(list[position]);
    }

    @Override
    public int getItemCount() {
        return list.length;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private Context context;

        public MyViewHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false));
            context = parent.getContext();
            textView = (TextView) itemView.findViewById(R.id.txtv_value);
        }

        public void setTextView(String string) {
            textView.setText(string);
        }

        public void setOnClick(final  String action) {
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setAction(String.format("com.qilin.AnimationLearing.%s", action));
                    context.startActivity(intent);
                }
            });

        }
    }
}
