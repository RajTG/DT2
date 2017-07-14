package com.example.android.deltatask2;

import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.io.File;
import java.util.ArrayList;


public class imgAdapter extends ArrayAdapter<theImage> {


    public imgAdapter(MainActivity mainActivity, ArrayList<theImage> thatImage) {
        super(mainActivity, 0, thatImage);

    }
    private static class ViewHolder {
        ImageView imgIcon;
    }
    @Override
    public View getView(int position,  View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.rowz, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.imgIcon = (ImageView) convertView.findViewById(R.id.imgView);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        theImage Img = getItem(position);
        viewHolder.imgIcon.setImageURI(Uri.fromFile(new File(Img.getPath())));
        int size = 96;
        viewHolder.imgIcon.setImageBitmap(ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(Img.getPath()), size, size));
        return convertView;
    }
}