package com.nunnos.keepintouch.presentation.component.recyclerviewcontactcard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
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
import com.nunnos.keepintouch.presentation.feature.main.activity.MainActivity;

import java.util.List;

//RecyclerView Contact Card Adapter
public class RVCCAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Contact> items;
    private Context context;

    public RVCCAdapter(List<Contact> items) {
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
        View view = layoutInflater.inflate(R.layout.component_main_recyclerview_contact, parent, false);
        RVCDAdapterViewHolder viewHolder = new RVCDAdapterViewHolder(view);
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


    /**
     * CLASS VIEWHOLDER
     **/
    public class RVCDAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private CardView cardView;
        private ConstraintLayout layout;
        private ImageView userImage;
        private ImageView starImage;
        private TextView fullNameTv;
        private TextView professionTv;
        private TextView ageValueTv;
        private TextView lastTimeValueTv;

        public RVCDAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
            setView();
            itemView.setOnClickListener(this);
        }

        private void initView(View itemView) {
            cardView = itemView.findViewById(R.id.recyclerView_contact_card);
            layout = itemView.findViewById(R.id.recyclerView_contact_constraintLayout);
            userImage = itemView.findViewById(R.id.recyclerView_contact_image);
            starImage = itemView.findViewById(R.id.recyclerView_contact_star);
            fullNameTv = itemView.findViewById(R.id.recyclerView_contact_name);
            professionTv = itemView.findViewById(R.id.recyclerView_contact_profession);
            ageValueTv = itemView.findViewById(R.id.recyclerView_contact_age_value);
            lastTimeValueTv = itemView.findViewById(R.id.recyclerView_contact_last_time_value);
        }

        private void setView() {

        }

        @SuppressLint("UseCompatLoadingForDrawables")
        public void bind(Contact contact) {
            cardView.setCardBackgroundColor(contact.getBgColor());
            ageValueTv.setText(String.valueOf(contact.getAge()));
            fullNameTv.setText(contact.getFullName());
            professionTv.setText(contact.getProfession());
            lastTimeValueTv.setText(contact.getDaysSinceLastChat());
            starImage.setImageDrawable(context.getDrawable(contact.isFavorite() ? R.drawable.ic_star_full : R.drawable.ic_star));
        }

        @Override
        public void onClick(View v) {
            if (context instanceof MainActivity) {
                ((MainActivity) context).getShareViewModel().navigateToContactInfo();
            }
        }
    }
}
