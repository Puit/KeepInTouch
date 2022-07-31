package com.nunnos.keepintouch.presentation.component.recyclerviews.conversationcard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.nunnos.keepintouch.R;
import com.nunnos.keepintouch.domain.model.Conversation;
import com.nunnos.keepintouch.presentation.feature.contactinfo.activity.ContactInfoActivity;
import com.nunnos.keepintouch.utils.FileManager;

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
        private CardView cardView;
        private CardView cardImageContainer;
        private ImageView bigImage;
        private ImageView moreImage;
        private TextView chatTv;
        private TextView timeTv;
        private TextView placeTv;

        public RVConversationCardAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
            setView();
            setListeners();
            itemView.setOnClickListener(this);
        }

        private void initView(View itemView) {
            cardView = itemView.findViewById(R.id.recyclerView_conversation_card);
            cardImageContainer = itemView.findViewById(R.id.recyclerView_conversation_image_card);
            bigImage = itemView.findViewById(R.id.recyclerView_conversation_image);
            chatTv = itemView.findViewById(R.id.recyclerView_conversation_chat);
            timeTv = itemView.findViewById(R.id.recyclerView_conversation_time);
            placeTv = itemView.findViewById(R.id.recyclerView_conversation_place);
            moreImage = itemView.findViewById(R.id.recyclerView_conversation_more);
        }

        private void setView() {

        }

        private void setListeners() {
            moreImage.setOnClickListener(v -> {
                if (context instanceof ContactInfoActivity) {
                    ((ContactInfoActivity)context).getShareViewModel().setNewConversation(conversation);
                    ((ContactInfoActivity)context).getShareViewModel().showNewConversationFragment();
                }
            });
        }

        @SuppressLint("UseCompatLoadingForDrawables")
        public void bind(Conversation conversation) {
            this.conversation = conversation;
            chatTv.setText(conversation.getChat());
            setTime(conversation);
            placeTv.setText(conversation.getPlace());
            setBigImage(conversation);
            cardView.setCardBackgroundColor(context.getColor(conversation.isImportant() ? R.color.background_red : R.color.white));
        }

        private void setTime(Conversation conversation) {
            String time = "";
            if (conversation.getTime() != null) {
                time = conversation.getTime().trim();
            }
            if (conversation.getDate() != null) {
                if (!time.isEmpty()) {
                    time = time + "\n";
                }
                time = time + conversation.getDate().replaceAll(" ", "").trim();
            }
            timeTv.setText(time);
        }

        private void setBigImage(Conversation conversation) {
            Bitmap bitmap = FileManager.getBitmapPhoto(conversation.getPhoto());
            if (bitmap == null) {
                cardImageContainer.setVisibility(View.GONE);
            } else {
                bigImage.setImageBitmap(bitmap);
                bigImage.setRotation(conversation.getAngle());
            }
        }

        @Override
        public void onClick(View v) {
            if (context instanceof ContactInfoActivity) {
                Toast.makeText(context, "Click on Layout", Toast.LENGTH_SHORT).show();
                /*
            TODO: navigate to edit
                ((MainActivity) context).getShareViewModel().setContactSelectedID(conversation.getId());
                ((MainActivity) context).getShareViewModel().navigateToContactInfo();*/
            }
        }
    }
}
