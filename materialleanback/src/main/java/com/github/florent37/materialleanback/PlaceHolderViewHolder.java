package com.github.florent37.materialleanback;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by florentchampigny on 28/08/15.
 */
public class PlaceHolderViewHolder extends RecyclerView.ViewHolder {

    public PlaceHolderViewHolder(View itemView, boolean horizontal, int dimen) {
        super(itemView);

        if(horizontal) {
            if (dimen != -1) {
                //itemView.getLayoutParams().width = dimen;
                itemView.requestLayout();
            }
        }else{
            if (dimen != -1) {
                itemView.getLayoutParams().height = dimen;
                itemView.requestLayout();
            }
        }
    }

}