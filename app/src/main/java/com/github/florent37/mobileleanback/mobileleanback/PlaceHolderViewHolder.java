package com.github.florent37.mobileleanback.mobileleanback;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by florentchampigny on 28/08/15.
 */
public class PlaceHolderViewHolder extends RecyclerView.ViewHolder {
    public PlaceHolderViewHolder(View itemView, int dimen) {
        super(itemView);
        if (dimen != -1) {
            itemView.getLayoutParams().width = dimen;
            itemView.requestLayout();
        }
    }

}