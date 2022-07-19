package com.nunnos.keepintouch.presentation.component.recyclerviewitemtext;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.florent37.expansionpanel.ExpansionLayout;
import com.github.florent37.expansionpanel.viewgroup.ExpansionLayoutCollection;
import com.nunnos.keepintouch.R;

import org.w3c.dom.CDATASection;

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
        /*viewHolder.setIsRecyclable(false);*/
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
//        ExpansionLayout expansionLayout;


        public RVITAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
            itemView.setOnClickListener(this);
        }

        private void initView(View itemView) {
            imageView = itemView.findViewById(R.id.image_and_text_image);
            textView = itemView.findViewById(R.id.image_and_text_text);
//            expansionLayout = itemView.findViewById(R.id.image_and_text_expansionLayout);//TODO: BUSCAR EL DEL FRAGMENT
        }

        public void setListener(CustomItemClick listener) {
            this.listener = listener;
        }

        public void bind(ImageAndText item) {
            imageView.setImageDrawable(item.getDrawable());
            textView.setText(item.getText());
//            expansionLayout.collapse(false);
        }

//        public ExpansionLayout getExpansionLayout() {
//            return expansionLayout;
//        }


        @Override
        public void onClick(View v) {
            listener.onItemClick(textView.getText().toString(), imageView.getDrawable());
        }
    }
}
