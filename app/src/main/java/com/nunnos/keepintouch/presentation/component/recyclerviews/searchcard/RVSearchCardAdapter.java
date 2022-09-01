package com.nunnos.keepintouch.presentation.component.recyclerviews.searchcard;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nunnos.keepintouch.R;
import com.nunnos.keepintouch.domain.model.Contact;
import com.nunnos.keepintouch.presentation.component.recyclerviews.itemtext.RVITAdapter;
import com.nunnos.keepintouch.presentation.feature.main.activity.MainActivity;
import com.nunnos.keepintouch.utils.FileManager;
import com.nunnos.keepintouch.utils.ImageHelper;

import java.util.List;

public class RVSearchCardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Contact> items;
    private Context context;
    private CustomItemClick listener;

    public RVSearchCardAdapter(List<Contact> items, CustomItemClick listener) {
        this.items = items;
        this.listener = listener;
    }
    public void addItems(List<Contact> items) {
        items.add(new Contact());
        this.items.addAll(items);
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
        View view = layoutInflater.inflate(R.layout.component_searched_recyclerview_contact, parent, false);
        RVSearchAdapterViewHolder viewHolder = new RVSearchAdapterViewHolder(view);
        viewHolder.setListener(listener);
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

    public interface CustomItemClick {
        void onItemClick();
    }
    /**
     * CLASS VIEWHOLDER
     **/
    public class RVSearchAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Contact contact;
        private TextView textView;
        private ImageView userImage;
        private CustomItemClick listener;

        public RVSearchAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
            itemView.setOnClickListener(this);
        }

        private void initView(View itemView) {
            textView = itemView.findViewById(R.id.s_r_contact_name);
            userImage = itemView.findViewById(R.id.s_r_contact_image);
        }

        public void setListener(CustomItemClick listener) {
            this.listener = listener;
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
            listener.onItemClick();
            /*if (context instanceof MainActivity) {
                ((MainActivity) context).getShareViewModel().setContactSelectedID(contact.getId());
                ((MainActivity) context).getShareViewModel().navigateToContactInfo();
                ((MainActivity) context).getShareViewModel().clearSearch();

                ((MainActivity) context).onBackPressed();
            }*/
        }
    }
}
