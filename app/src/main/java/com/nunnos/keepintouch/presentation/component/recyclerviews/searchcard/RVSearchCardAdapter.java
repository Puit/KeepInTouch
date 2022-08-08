package com.nunnos.keepintouch.presentation.component.recyclerviews.searchcard;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nunnos.keepintouch.R;
import com.nunnos.keepintouch.domain.model.Contact;
import com.nunnos.keepintouch.presentation.feature.main.activity.MainActivity;
import com.nunnos.keepintouch.utils.FileManager;
import com.nunnos.keepintouch.utils.ImageHelper;

import java.util.List;

public class RVSearchCardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Contact> items;
    private String titleAdapter;
    private Context context;
    private boolean isTitle = true;

    public RVSearchCardAdapter(List<Contact> items, String title) {
        this.items = items;
        //si no fem aixó la primera vegada possa el titol i passa al index següent
        // saltant-se un contacte
        items.add(0, new Contact());
        this.titleAdapter = title;
    }
    public void addItems(List<Contact> items, String title) {
        items.add(new Contact());
        isTitle = true;
        this.items.addAll(items);
        this.titleAdapter = title;
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
        if (items.size() == 0) return null;
        context = parent.getContext(); //Context del recyclerview
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        if (isTitle) {
            isTitle = false;
            View view = layoutInflater.inflate(R.layout.component_searched_recyclerview_title, parent, false);
            RVSearchTitleAdapterViewHolder viewHolder = new RVSearchTitleAdapterViewHolder(view);
            viewHolder.setIsRecyclable(false);
            return viewHolder;
        }
        View view = layoutInflater.inflate(R.layout.component_searched_recyclerview_contact, parent, false);
        RVSearchAdapterViewHolder viewHolder = new RVSearchAdapterViewHolder(view);
        viewHolder.setIsRecyclable(false);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof RVSearchAdapterViewHolder) {
            ((RVSearchAdapterViewHolder) holder).bind(items.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    /**
     * CLASS VIEWHOLDER
     **/
    public class RVSearchAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Contact contact;
        private TextView textView;
        private ImageView userImage;

        public RVSearchAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
            setView();
            itemView.setOnClickListener(this);
        }

        private void initView(View itemView) {
            textView = itemView.findViewById(R.id.s_r_contact_name);
            userImage = itemView.findViewById(R.id.s_r_contact_image);

        }

        private void setView() {
        }

        public void bind(Contact contact) {
            this.contact = contact;
            setUserImage(contact);
            textView.setText(contact.getFullName());
        }

        private void setUserImage(Contact contact) {
            if (context instanceof MainActivity) {
                Bitmap bitmap = FileManager.getBitmapPhoto(contact.getPhoto());
                if (bitmap == null) {
                    userImage.setImageDrawable(context.getDrawable(R.drawable.ic_unknown));
                } else {
                    userImage.setImageBitmap(bitmap);
//                    userImage.setImageBitmap(getResizedBitmap(userImage, bitmap));
                    ImageHelper.resizeImage(userImage, bitmap);
                    userImage.setRotation(contact.getAngle());
                }
            }
        }

        @Override
        public void onClick(View v) {
            if (context instanceof MainActivity) {
                ((MainActivity) context).getShareViewModel().setContactSelectedID(contact.getId());
                ((MainActivity) context).getShareViewModel().navigateToContactInfo();
            }
        }
    }

    public class RVSearchTitleAdapterViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTv;

        public RVSearchTitleAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
            setView();
        }

        private void initView(View itemView) {
            titleTv = itemView.findViewById(R.id.s_r_title);
        }

        private void setView() {
            titleTv.setText(titleAdapter);
        }
    }
}
