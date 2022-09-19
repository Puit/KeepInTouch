package com.nunnos.keepintouch.presentation.component.recyclerviews.itemtext;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nunnos.keepintouch.R;
import com.nunnos.keepintouch.presentation.component.recyclerviews.itemtext.base.ExpansionLayout;
import com.nunnos.keepintouch.presentation.component.recyclerviews.itemtext.base.ExpansionLayoutCollection;

import java.util.List;

//RecyclerView Image and Text Adapter
public class RVITAdapter extends RecyclerView.Adapter<RVITAdapter.RVITAdapterViewHolder> {
    private List<ImageAndText> items;
    private Context context;
    private RVITAdapterViewHolder.CustomItemClick listener;
    private ExpansionLayout expansionLayout;
    private final ExpansionLayoutCollection expansionsCollection = new ExpansionLayoutCollection();


    public RVITAdapter(List<ImageAndText> items, RVITAdapterViewHolder.CustomItemClick listener, ExpansionLayout expansionLayout) {
        this.items = items;
        this.listener = listener;
        this.expansionLayout = expansionLayout;
        expansionsCollection.openOnlyOne(true);
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
    public RVITAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext(); //Context del recyclerview
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.component_image_and_text, parent, false);
        RVITAdapterViewHolder viewHolder = new RVITAdapterViewHolder(view);
        viewHolder.setListener(listener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RVITAdapterViewHolder holder, int position) {
        holder.bind(items.get(position));
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
    public static class RVITAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public interface CustomItemClick {
            void onItemClick(String gender, Drawable icon);
        }

        private ImageView imageView;
        private TextView textView;
        private CustomItemClick listener;

        public RVITAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
            itemView.setOnClickListener(this);
        }

        private void initView(View itemView) {
            imageView = itemView.findViewById(R.id.image_and_text_image);
            textView = itemView.findViewById(R.id.image_and_text_text);
        }

        public void setListener(CustomItemClick listener) {
            this.listener = listener;
        }

        public void bind(ImageAndText item) {
            if (item.getDrawable() != null) {
                imageView.setImageDrawable(item.getDrawable());
            } else {
                imageView.setImageBitmap(item.getBitmap());
            }
            textView.setText(item.getText());
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(textView.getText().toString(), imageView.getDrawable());
        }
    }
}
