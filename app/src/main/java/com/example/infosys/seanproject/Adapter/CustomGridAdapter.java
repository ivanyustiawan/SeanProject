package com.example.infosys.seanproject.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.infosys.seanproject.Class.Profile;
import com.example.infosys.seanproject.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CustomGridAdapter extends BaseAdapter {

    Context context;
    private List<Profile> list;

    public CustomGridAdapter(Context context, List<Profile> list) {
        this.context = context;
        this.list = list;
    }

    private class CustomHolder{
        private ImageView imageView;
        private TextView textView;

        public CustomHolder(View view) {
            imageView = view.findViewById(R.id.imageRow);
            textView = view.findViewById(R.id.textRow);
        }

        private void onBind(Profile profile){
            Picasso.with(context)
                    .load(profile.getThumbnailSrc())
                    .into(imageView);
            textView.setText(profile.getCaption());
        }
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = layoutInflater.inflate(R.layout.simple_image_with_text, null);

        CustomHolder holder = new CustomHolder(rowView);
        holder.onBind(list.get(position));

        return rowView;
    }
}
