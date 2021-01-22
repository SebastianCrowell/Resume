package com.example.egran;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListViewAdapter extends BaseAdapter {
    Context mContext;
    LayoutInflater inflater;
    private List<urlForPages> htmlPages = null;
    private ArrayList<urlForPages> arraylist;

    public ListViewAdapter(Context context, List<urlForPages> animalNamesList) {
        mContext = context;
        this.htmlPages = animalNamesList;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<urlForPages>();
        this.arraylist.addAll(htmlPages);
    }

    public class ViewHolder {
        TextView name;
    }

    @Override
    public int getCount() {
        return htmlPages.size();
    }


    @Override
    public urlForPages getItem(int position) {
        return htmlPages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.list_view_items, null);
            // Locate the TextViews in listview_item.xml
            holder.name = (TextView) view.findViewById(R.id.name);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.name.setText(htmlPages.get(position).getUrlForPages());
        return view;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        htmlPages.clear();
        if (charText.length() == 0) {
            htmlPages.addAll(arraylist);
        } else {
            for (urlForPages wp : arraylist) {
                if (wp.getUrlForPages().toLowerCase(Locale.getDefault()).contains(charText)) {
                    htmlPages.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}