package com.whf.network;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Map;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>{

    private List<App> dataList;

    public RecyclerViewAdapter(List<App> list) {
        dataList = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        App data = dataList.get(position);
        holder.textId.setText(data.getId());
        holder.textName.setText(data.getName());
        holder.textVersion.setText(data.getVersion());

    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView textId,textName,textVersion;

        private MyViewHolder(View itemView) {
            super(itemView);
            textId = (TextView) itemView.findViewById(R.id.text_id);
            textName = (TextView) itemView.findViewById(R.id.text_name);
            textVersion = (TextView) itemView.findViewById(R.id.text_version);
        }
    }
}
