package com.wyp.chalkitup.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wyp.chalkitup.R;
import com.wyp.chalkitup.models.MessageItem;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by yatinkaushal on 2/18/17.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    private final static int REGULAR_MESSAGE = 1;
    private final static int MY_MESSAGE = 2;
    private Context context;
    private List<MessageItem> messageItems;
    private String userId;

    public MessageAdapter(final Context context, final List<MessageItem> messageItems, final String userId){
        this.context = context;
        this.messageItems = messageItems;
        this.userId = userId;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        if (viewType == REGULAR_MESSAGE) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item, parent, false);
        } else {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_message_item, parent, false);
        }
        return new MessageAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Glide.with(context)
                .load(messageItems.get(position).photoUrl)
                .into(holder.imageView);
        holder.messageTxt.setText(messageItems.get(position).message);
    }

    @Override
    public int getItemViewType(int position) {
        if (messageItems.get(position).userId.equals(userId)) {
            return MY_MESSAGE;
        }
        return REGULAR_MESSAGE;
    }

    @Override
    public int getItemCount() {
        return messageItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView imageView;
        TextView messageTxt;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (CircleImageView) itemView.findViewById(R.id.imageView);
            messageTxt = (TextView) itemView.findViewById(R.id.textView2);
        }
    }
}
