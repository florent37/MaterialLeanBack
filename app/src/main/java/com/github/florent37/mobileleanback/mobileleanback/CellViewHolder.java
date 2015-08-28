package com.github.florent37.mobileleanback.mobileleanback;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;

import com.github.florent37.mobileleanback.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by florentchampigny on 28/08/15.
 */
public class CellViewHolder extends RecyclerView.ViewHolder {

    protected CardView cardView;
    protected boolean enlarged = false;

    protected final MobileLeanBack.Adapter adapter;
    protected final MobileLeanBack.ViewHolder viewHolder;
    protected final MobileLeanBackSettings settings;
    protected final int row;

    public CellViewHolder(View itemView, int row, MobileLeanBack.Adapter adapter, MobileLeanBackSettings settings) {
        super(itemView);
        this.row = row;
        this.adapter = adapter;
        this.settings = settings;

        cardView = (CardView) itemView.findViewById(R.id.cardView);
        this.viewHolder = adapter.onCreateViewHolder(cardView,row);
        this.viewHolder.row = row;
        cardView.addView(viewHolder.itemView);
    }

    public void enlarge(int translationX, boolean withAnimation) {
        if (!enlarged && settings.animateCards) {
            int duration = withAnimation? 300 : 0;

            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.setDuration(duration);

            List<Animator> animatorList = new ArrayList<>();
            animatorList.add(ObjectAnimator.ofFloat(cardView,"scaleX",1.0f));
            animatorList.add(ObjectAnimator.ofFloat(cardView,"scaleY",1.0f));

            if(settings.overlapCards) {
                animatorList.add(ObjectAnimator.ofFloat(cardView, "translationX", translationX));
                animatorSet.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        cardView.setCardElevation(settings.elevationEnlarged);
                    }
                });
            }

            animatorSet.playTogether(animatorList);
            animatorSet.start();

            enlarged = true;
        }
    }

    public void reduce(int translationX, boolean withAnimation) {
        if (enlarged && settings.animateCards) {
            int duration = withAnimation? 300 : 0;

            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.setDuration(duration);

            List<Animator> animatorList = new ArrayList<>();
            animatorList.add(ObjectAnimator.ofFloat(cardView,"scaleX",0.8f));
            animatorList.add(ObjectAnimator.ofFloat(cardView,"scaleY",0.8f));

            if(settings.overlapCards) {
                animatorList.add(ObjectAnimator.ofFloat(cardView, "translationX", translationX));
                animatorSet.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        cardView.setCardElevation(settings.elevationReduced);
                    }
                });
            }

            animatorSet.playTogether(animatorList);
            animatorSet.start();

            enlarged = false;
        }
    }

    public void newPosition(int position) {

        int translationX;
        if (position == 1)
            translationX = 0;
        else if (position > 0)
            translationX = -100;
        else
            translationX = 100;


        if (position == 1)
            enlarge(translationX, true);
        else
            reduce(translationX, true);
    }

    public void onBind() {
        int cell = getAdapterPosition()-1;
        viewHolder.cell = cell;
        adapter.onBindViewHolder(viewHolder,cell);
    }
}
