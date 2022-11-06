package com.nunnos.keepintouch.presentation.component.recyclerviews.contactsselector;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nunnos.keepintouch.R;
import com.nunnos.keepintouch.domain.model.Contact;
import com.nunnos.keepintouch.presentation.component.recyclerviews.itemtext.base.ExpansionLayout;
import com.nunnos.keepintouch.presentation.component.recyclerviews.itemtext.base.ExpansionLayoutCollection;
import com.nunnos.keepintouch.utils.FileManager;

import java.util.List;

public class RVContactAdapter extends RecyclerView.Adapter<RVContactAdapter.RVContactdapterViewHolder> {
    private List<Contact> items;
    private Context context;
    private RVContactdapterViewHolder.CustomItemClick listener;
    private ExpansionLayout expansionLayout;
    private final ExpansionLayoutCollection expansionsCollection = new ExpansionLayoutCollection();
    private boolean showRemoveIcon = false;


    public RVContactAdapter(List<Contact> items, RVContactdapterViewHolder.CustomItemClick listener, ExpansionLayout expansionLayout, boolean showRemoveIcon) {
        this.items = items;
        this.listener = listener;
        this.expansionLayout = expansionLayout;
        expansionsCollection.openOnlyOne(true);
        this.showRemoveIcon = showRemoveIcon;
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
    public RVContactdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext(); //Context del recyclerview
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.component_selected_contact, parent, false);
        RVContactdapterViewHolder viewHolder = new RVContactdapterViewHolder(view);
        viewHolder.setListener(listener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RVContactdapterViewHolder holder, int position) {
        holder.bind(items.get(position), context, showRemoveIcon);
        //just add the ExpansionLayout (with findViewById) to the expansionsCollection
        expansionsCollection.add(expansionLayout);
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    /**
     * CLASS VIEWHOLDER
     **/
    public static class RVContactdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public interface CustomItemClick {
            void onItemClick(Contact contact);

            void onRightImageClick(Contact contact);
        }

        private ImageView imageLeft;
        private ImageView imageRight;
        private TextView textView;
        private CustomItemClick listener;
        private Contact contact;

        public RVContactdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
            initListeners();
            itemView.setOnClickListener(this);
        }

        private void initListeners() {
            imageRight.setOnClickListener(v -> listener.onRightImageClick(contact));
        }

        private void initView(View itemView) {
            imageLeft = itemView.findViewById(R.id.selected_contact_image_left);
            imageRight = itemView.findViewById(R.id.selected_contact_image_right);
            textView = itemView.findViewById(R.id.selected_contact_text);
        }

        public void setListener(RVContactdapterViewHolder.CustomItemClick listener) {
            this.listener = listener;
        }

        public void bind(Contact contact, Context context, boolean showRemoveIcon) {
            imageLeft.setImageBitmap(FileManager.getBitmapPhoto(contact.getPhoto()));
            if (showRemoveIcon) {
                imageRight.setImageDrawable(context.getDrawable(R.drawable.ic_baseline_delete_24));
            }
            textView.setText(contact.getFullName());
            this.contact = contact;
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(contact);
        }
    }
}