package com.github.florent37.mobileleanback.mobileleanback;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.github.florent37.mobileleanback.R;

/**
 * Created by florentchampigny on 28/08/15.
 */
public class MobileLeanBackSettings {

    public Integer titleColor;
    public int titleSize;
    public boolean animateCards;
    public boolean overlapCards;
    public int elevationReduced;
    public int elevationEnlarged;

    public Integer backgroundId;
    public Float backgroundOverlay;
    public Integer backgroundOverlayColor;

    protected void handleAttributes(Context context, AttributeSet attrs) {
        try {
            TypedArray styledAttrs = context.obtainStyledAttributes(attrs, R.styleable.MobileLeanBack);

            {
                if(styledAttrs.hasValue(R.styleable.MobileLeanBack_mlb_titleColor))
                    titleColor = styledAttrs.getColor(R.styleable.MobileLeanBack_mlb_titleColor, -1);
                titleSize = styledAttrs.getDimensionPixelSize(R.styleable.MobileLeanBack_mlb_titleSize, -1);
                animateCards = styledAttrs.getBoolean(R.styleable.MobileLeanBack_mlb_animateCards, true);
                overlapCards = styledAttrs.getBoolean(R.styleable.MobileLeanBack_mlb_overlapCards, true);
                elevationEnlarged = styledAttrs.getInteger(R.styleable.MobileLeanBack_mlb_cardElevationEnlarged, 8);
                elevationReduced = styledAttrs.getInteger(R.styleable.MobileLeanBack_mlb_cardElevationReduced, 5);

                if(styledAttrs.hasValue(R.styleable.MobileLeanBack_mlb_background))
                    backgroundId = styledAttrs.getResourceId(R.styleable.MobileLeanBack_mlb_background, -1);

                if(styledAttrs.hasValue(R.styleable.MobileLeanBack_mlb_backgroundOverlay))
                    backgroundOverlay = styledAttrs.getFloat(R.styleable.MobileLeanBack_mlb_backgroundOverlay, -1);

                if(styledAttrs.hasValue(R.styleable.MobileLeanBack_mlb_backgroundOverlayColor))
                    backgroundOverlayColor = styledAttrs.getColor(R.styleable.MobileLeanBack_mlb_backgroundOverlayColor, -1);
            }

            styledAttrs.recycle();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
