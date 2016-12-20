package com.fflorio.simplevalueconverter.ui;

import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fflorio.simplevalueconverter.R;
import com.fflorio.simplevalueconverter.network.models.responses.CurrencyList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * This Class was created for SimpleValueConverter on 20/12/16
 * Designed and developed by Francesco Florio
 * All Right Reserved.
 */
public class CurrencySpinnerAdapter extends BaseAdapter{
    private CurrencyList dataset = new CurrencyList();

    @Override public int getCount() { return dataset.size(); }
    @Override public Object getItem(int position) { return dataset.get(position); }
    @Override public long getItemId(int position) { return position; }
    @Override public View getView(int position, View convertView, ViewGroup parent) { return getCustomView(position, convertView, parent, false); }
    @Override public View getDropDownView(int position, View convertView, ViewGroup parent) { return getCustomView(position, convertView, parent, true); }

    private View getCustomView(int position, View view, ViewGroup parent, boolean isDropDown) {
        ViewHolder holder;
        if (view != null) { holder = (ViewHolder) view.getTag(); }
        else {
            @LayoutRes int layoutId = isDropDown ? R.layout.adapter__textview_for_dropdown : R.layout.adapter__textview_for_spinner;
            view = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }

        holder.label.setText(dataset.get(position).toString());
        return view;
    }
    public void updateList(CurrencyList newDataset){
        this.dataset = newDataset;
        notifyDataSetChanged();
    }

    static class ViewHolder {
        @BindView(R.id.label) TextView label;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
