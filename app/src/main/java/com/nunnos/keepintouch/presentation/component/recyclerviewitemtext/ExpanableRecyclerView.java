package com.nunnos.keepintouch.presentation.component.recyclerviewitemtext;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.github.florent37.expansionpanel.ExpansionLayout;
import com.nunnos.keepintouch.R;
import com.nunnos.keepintouch.databinding.ComponentExpanableRecyclerviewBinding;
import com.nunnos.keepintouch.presentation.component.ExpanableTitle;

public class ExpanableRecyclerView extends LinearLayout {

    ComponentExpanableRecyclerviewBinding databinding;

    public ExpanableRecyclerView(Context context) {
        this(context, null);
    }

    public ExpanableRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
        setListeners();
    }

    public void setText(String text) {
        databinding.expanableRecyclerCustomEditText.setText(text);
    }

    public String getText() {
        return databinding.expanableRecyclerCustomEditText.getText();
    }

    public void setIcon(Drawable icon) {
        databinding.expanableRecyclerCustomEditText.setIcon(icon);
    }

    public void collapse(boolean setCollapset) {
        databinding.expanableRecyclerExpansionLayout.collapse(setCollapset);
    }

    public ExpansionLayout getExpansionLayout() {
        return databinding.expanableRecyclerExpansionLayout;
    }

    public RecyclerView getRecyclerView() {
        return databinding.expanableRecyclerRecyclerView;
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        databinding.expanableRecyclerRecyclerView.setAdapter(adapter);
    }

    public void setHasFixedSize(boolean hasFixedSize) {
        databinding.expanableRecyclerRecyclerView.setHasFixedSize(hasFixedSize);
    }

    private void init(AttributeSet attrs) {
        bindView();
        setListeners();
        parseAttributes(attrs);
    }

    private void setListeners() {
        OnClickListener listener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), String.valueOf(v.getClass().getSimpleName()), Toast.LENGTH_SHORT).show();
                databinding.expanableRecyclerHeader.setToggleOnClick(!databinding.expanableRecyclerHeader.isToggleOnClick());
            }
        };
        databinding.expanableRecyclerLinearLayout.setOnClickListener(listener);
        databinding.expanableRecyclerHeader.setOnClickListener(listener);
        databinding.expanableRecyclerCustomEditText.setListener(new ExpanableTitle.CustomListener() {
            @Override
            public void onItemClick() {
                listener.onClick(databinding.expanableRecyclerCustomEditText);
            }
        });
        databinding.expanableRecyclerHeaderIndicator.setOnClickListener(listener);
    }

    private void parseAttributes(AttributeSet attrs) {
        if (attrs == null) {
            return;
        }

        TypedArray attributes = getContext().obtainStyledAttributes(attrs, R.styleable.ExpanableRecyclerView, 0, 0);
        String hint = attributes.getString(R.styleable.ExpanableRecyclerView_expanable_recyclerview_hint_text);
        String text = attributes.getString(R.styleable.ExpanableRecyclerView_expanable_recyclerview_text_text);
        Drawable icon = attributes.getDrawable(R.styleable.ExpanableRecyclerView_expanable_recyclerview_icon);

        if (!TextUtils.isEmpty(hint)) {
            databinding.expanableRecyclerCustomEditText.setHint(hint);
        }
        if (!TextUtils.isEmpty(text)) {
            databinding.expanableRecyclerCustomEditText.setText(text);
        }
        if (icon != null) {
            databinding.expanableRecyclerCustomEditText.setIcon(icon);
        }
    }

    private void bindView() {
        databinding = ComponentExpanableRecyclerviewBinding.inflate(LayoutInflater.from(getContext()), this, true);
    }

}
