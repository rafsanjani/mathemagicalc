package com.example.azizrafsanjani.numericals.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.azizrafsanjani.numericals.R;
import com.example.azizrafsanjani.numericals.model.Topic;

import java.util.List;

/**
 * Created by forev on 3/16/2018.
 */

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.TopicViewHolder> {
    private Context mCtx;
    private List<Topic> topicList;

    public TopicAdapter(Context mCtx, List<Topic> topicList) {
        this.mCtx = mCtx;
        this.topicList = topicList;
    }

    @Override
    public TopicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View itemView = inflater.inflate(R.layout.list_item_improved,parent, false);

        return new TopicViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TopicViewHolder holder, int position) {
        Topic topic = topicList.get(position);

        holder.title.setText(topic.getTitle());
        holder.description.setText(topic.getDescription());
    }

    @Override
    public int getItemCount() {
        return topicList.size();
    }


    class TopicViewHolder extends RecyclerView.ViewHolder{

        private TextView title, description;

        TopicViewHolder(View itemView) {
            super(itemView);
          //  title = itemView.findViewById(R.id.topic);
           // description = itemView.findViewById(R.id.description);
        }


    }
}
