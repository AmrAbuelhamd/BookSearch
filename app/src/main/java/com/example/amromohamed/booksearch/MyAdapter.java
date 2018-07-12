package com.example.amromohamed.booksearch;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

class MyAdapter extends ArrayAdapter<SearchResultData> {

    private ArrayList<SearchResultData> dataSet;
    private Context mContext;


    public MyAdapter(@NonNull Context context,ArrayList<SearchResultData> data) {
        super(context, R.layout.row_item,data);

        this.dataSet = data;
        this.mContext=context;
    }

    // View lookup cache
    private static class ViewHolder {
        TextView title;

        ImageView thumbnail;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        SearchResultData dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item, parent, false);
            viewHolder.title =  convertView.findViewById(R.id.textView);
            viewHolder.thumbnail = convertView.findViewById(R.id.imageView);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        viewHolder.title.setText(dataModel.getTitle());
        Picasso.get().load(dataModel.getThumbnail()).into(viewHolder.thumbnail);

        // Return the completed view to render on screen
        return convertView;
    }

}
