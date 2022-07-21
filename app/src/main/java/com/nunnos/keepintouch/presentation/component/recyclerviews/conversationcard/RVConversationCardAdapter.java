package com.nunnos.keepintouch.presentation.component.recyclerviews.conversationcard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nunnos.keepintouch.R;
import com.nunnos.keepintouch.domain.model.Conversation;
import com.nunnos.keepintouch.presentation.feature.main.activity.MainActivity;

import java.util.List;

public class RVConversationCardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Conversation> items;
    private Context context;

    public RVConversationCardAdapter(List<Conversation> items) {
        this.items = items;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    /**
     * BASE FUNCTIONS
     **/

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext(); //Context del recyclerview
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.component_conversations_recyclerview_conversation, parent, false);
        RVConversationCardAdapterViewHolder viewHolder = new RVConversationCardAdapterViewHolder(view);
        viewHolder.setIsRecyclable(false);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((RVConversationCardAdapterViewHolder) holder).bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }


    /**
     * CLASS VIEWHOLDER
     **/
    public class RVConversationCardAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Conversation conversation;
        private ImageView starImage;
        private TextView fullNameTv;

        public RVConversationCardAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
            setView();
            itemView.setOnClickListener(this);
        }

        private void initView(View itemView) {
            starImage = itemView.findViewById(R.id.recyclerView_conversation_star);
            fullNameTv = itemView.findViewById(R.id.recyclerView_conversation_name);
        }

        private void setView() {

        }

        @SuppressLint("UseCompatLoadingForDrawables")
        public void bind(Conversation conversation) {
            this.conversation = conversation;
            fullNameTv.setText(conversation.getChat());
            starImage.setImageDrawable(context.getDrawable(conversation.isImportant() ? R.drawable.ic_star_full : R.drawable.ic_star));
        }

        @Override
        public void onClick(View v) {
            if (context instanceof MainActivity) {/*
                ((MainActivity) context).getShareViewModel().setContactSelectedID(conversation.getId());
                ((MainActivity) context).getShareViewModel().navigateToContactInfo();*/
            }
        }
    }
}
