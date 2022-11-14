package com.nunnos.keepintouch.presentation.component.recyclerviews.contactsselector;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.nunnos.keepintouch.R;
import com.nunnos.keepintouch.databinding.ComponentExpanableMultipleSelectorRecyclerviewBinding;
import com.nunnos.keepintouch.domain.model.Contact;
import com.nunnos.keepintouch.presentation.component.ExpanableTitle;
import com.nunnos.keepintouch.presentation.component.recyclerviews.itemtext.base.ExpansionLayout;

import java.util.ArrayList;
import java.util.List;

public class ExpanableMultipleSelectorRecyclerView extends LinearLayout {

    ComponentExpanableMultipleSelectorRecyclerviewBinding databinding;
    private List<Contact> selectedContacts = new ArrayList<>();
    private RVContactAdapter selectedContactsAdapter;

    public ExpanableMultipleSelectorRecyclerView(Context context) {
        this(context, null);
    }

    public ExpanableMultipleSelectorRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
        setListeners();
    }

    public void addSelectedContacts(String ids, List<Contact> contacts) {
        for (Contact c : contacts) {
            if (ids.contains("," + c.getId() + ",")) {
                addSelectedContact(c);
            }
        }
    }

    public void addSelectedContact(Contact contact) {
        if(isInSelectedContacts(contact)) return;

        selectedContacts.add(contact);
        RVContactAdapter.RVContactdapterViewHolder.CustomItemClick listener = new RVContactAdapter.RVContactdapterViewHolder.CustomItemClick() {
            @Override
            public void onItemClick(Contact contact) {
                //Do nothing
            }

            @Override
            public void onRightImageClick(Contact contact) {
                selectedContacts.remove(contact);
                selectedContactsAdapter.notifyDataSetChanged();
            }
        };
        selectedContactsAdapter = new RVContactAdapter(selectedContacts,
                listener,
                databinding.expanableMultipleRecyclerExpansionLayoutForSelection,
                true);
        databinding.expanableMultipleRecyclerRecyclerViewSelected.setAdapter(selectedContactsAdapter);
        databinding.expanableMultipleRecyclerRecyclerViewSelected.setHasFixedSize(false);
    }

    private boolean isInSelectedContacts(Contact contact) {
        boolean isSelected = false;
        for (Contact selected: selectedContacts) {
            if (Contact.areSameContact(selected,contact)){
                isSelected = true;
            }
        }
        return isSelected;
    }

    public void setText(String text) {
        databinding.expanableMultipleRecyclerCustomEditText.setText(text);
    }

    public String getText() {
        return databinding.expanableMultipleRecyclerCustomEditText.getText();
    }

    public void setIcon(Drawable icon) {
        databinding.expanableMultipleRecyclerCustomEditText.setIcon(icon);
    }

    public void collapse(boolean setCollapset) {
        databinding.expanableMultipleRecyclerExpansionLayoutForSelection.collapse(setCollapset);
        databinding.expanableMultipleRecyclerRecyclerViewSelected.setVisibility(setCollapset ? VISIBLE : GONE);
    }

    public ExpansionLayout getExpansionLayoutForSelection() {
        return databinding.expanableMultipleRecyclerExpansionLayoutForSelection;
    }

    public RecyclerView getRecyclerView() {
        return databinding.expanableMultipleRecyclerRecyclerViewSelector;
    }

    public List<Contact> getSelectedContacts() {
        return selectedContacts;
    }

    public void clearContacts() {
        selectedContacts.clear();
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        databinding.expanableMultipleRecyclerRecyclerViewSelector.setAdapter(adapter);
    }

    public void setHasFixedSize(boolean hasFixedSize) {
        databinding.expanableMultipleRecyclerRecyclerViewSelector.setHasFixedSize(hasFixedSize);
    }

    private void init(AttributeSet attrs) {
        bindView();
        setListeners();
        parseAttributes(attrs);
    }

    private void setListeners() {
        OnClickListener listener = v -> databinding.expanableMultipleRecyclerHeader.setToggleOnClick(!databinding.expanableMultipleRecyclerHeader.isToggleOnClick());
        databinding.expanableMultipleRecyclerLinearLayout.setOnClickListener(listener);
        databinding.expanableMultipleRecyclerHeader.setOnClickListener(listener);
        databinding.expanableMultipleRecyclerCustomEditText.setListener(new ExpanableTitle.CustomListener() {
            @Override
            public void onItemClick() {
                listener.onClick(databinding.expanableMultipleRecyclerCustomEditText);
            }
        });
        databinding.expanableMultipleRecyclerHeaderIndicator.setOnClickListener(listener);
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
            databinding.expanableMultipleRecyclerCustomEditText.setHint(hint);
        }
        if (!TextUtils.isEmpty(text)) {
            databinding.expanableMultipleRecyclerCustomEditText.setText(text);
        }
        if (icon != null) {
            databinding.expanableMultipleRecyclerCustomEditText.setIcon(icon);
        }
    }

    private void bindView() {
        databinding = ComponentExpanableMultipleSelectorRecyclerviewBinding.inflate(LayoutInflater.from(getContext()), this, true);
    }
}
