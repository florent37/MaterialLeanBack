package com.github.florent37.materialleanback.sample;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.florent37.materialleanback.MaterialLeanBack;


/**
 * Created by florentchampigny on 28/08/15.
 */
public class TestViewHolder extends MaterialLeanBack.ViewHolder {

    protected TextView textView;
    protected ImageView imageView;

    public TestViewHolder(View itemView) {
        super(itemView);
        textView = (TextView) itemView.findViewById(R.id.textView);
        imageView = (ImageView) itemView.findViewById(R.id.imageView);
    }
}
