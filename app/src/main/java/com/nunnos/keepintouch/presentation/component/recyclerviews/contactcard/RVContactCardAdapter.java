package com.nunnos.keepintouch.presentation.component.recyclerviews.contactcard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.nunnos.keepintouch.R;
import com.nunnos.keepintouch.domain.model.Contact;
import com.nunnos.keepintouch.presentation.component.recyclerviews.searchcard.RVSearchCardAdapter;
import com.nunnos.keepintouch.presentation.feature.main.activity.MainActivity;
import com.nunnos.keepintouch.utils.FileManager;
import com.nunnos.keepintouch.utils.ImageHelper;

import java.util.List;

//RecyclerView Contact Card Adapter
public class RVContactCardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Contact> items;
    private Context context;
    private CustomItemClick listener;


    public RVContactCardAdapter(List<Contact> items, CustomItemClick listener) {
        this.items = items;
        this.listener = listener;
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
        View view = layoutInflater.inflate(R.layout.component_main_recyclerview_contact, parent, false);
        RVCDAdapterViewHolder viewHolder = new RVCDAdapterViewHolder(view);
        viewHolder.setListener(listener);
        viewHolder.setIsRecyclable(false);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((RVCDAdapterViewHolder) holder).bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    public interface CustomItemClick {
        void onItemClick(int contactId);
    }
    /**
     * CLASS VIEWHOLDER
     **/
    public class RVCDAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Contact contact;
        private CardView cardView;
        private ConstraintLayout layout;
        private ImageView userImage;
        private ImageView favImage;
        private TextView fullNameTv;
        private TextView professionTv;
        private TextView ageValueTv;
        private TextView lastTimeValueTv;
        private CustomItemClick listener;

        public RVCDAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
            itemView.setOnClickListener(this);
        }

        private void initView(View itemView) {
            cardView = itemView.findViewById(R.id.recyclerView_contact_card);
            layout = itemView.findViewById(R.id.recyclerView_contact_constraintLayout);
            userImage = itemView.findViewById(R.id.recyclerView_contact_image);
            favImage = itemView.findViewById(R.id.recyclerView_contact_fav);
            fullNameTv = itemView.findViewById(R.id.recyclerView_contact_name);
            professionTv = itemView.findViewById(R.id.recyclerView_contact_profession);
            ageValueTv = itemView.findViewById(R.id.recyclerView_contact_age_value);
            lastTimeValueTv = itemView.findViewById(R.id.recyclerView_contact_last_time_value);
        }

        public void setListener(CustomItemClick listener) {
            this.listener = listener;
        }

        @SuppressLint("UseCompatLoadingForDrawables")
        public void bind(Contact contact) {
            this.contact = contact;
            setUserImage(contact);
            cardView.setBackgroundResource(contact.getBgColor());
            if(contact.getAge() == -1){
                ageValueTv.setVisibility(View.GONE);
            }else {
                ageValueTv.setText(String.valueOf(contact.getAge()));

            }
            fullNameTv.setText(contact.getFullName());
            professionTv.setText(contact.getProfession());
            lastTimeValueTv.setText(contact.getDaysSinceLastChat());
            favImage.setVisibility(contact.isFavorite() ? View.VISIBLE : View.GONE);
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
            listener.onItemClick(contact.getId());
        }
    }
}
