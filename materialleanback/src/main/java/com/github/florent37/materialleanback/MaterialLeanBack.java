package com.github.florent37.materialleanback;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.nineoldandroids.view.ViewHelper;

/**
 * Created by florentchampigny on 28/08/15.
 */
public class MaterialLeanBack extends FrameLayout {

    RecyclerView recyclerView;
    ImageView imageBackground;
    View imageBackgroundOverlay;

    MaterialLeanBack.Adapter adapter;
    MaterialLeanBack.Customizer customizer;
    MaterialLeanBackSettings settings = new MaterialLeanBackSettings();

    public MaterialLeanBack(Context context) {
        super(context);
    }

    public MaterialLeanBack(Context context, AttributeSet attrs) {
        super(context, attrs);
        settings.handleAttributes(context, attrs);
    }

    public MaterialLeanBack(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        settings.handleAttributes(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        if(isInEditMode())
            return;

        addView(LayoutInflater.from(getContext()).inflate(R.layout.mlb_layout, this, false));

        imageBackground = (ImageView) findViewById(R.id.mlb_imageBackground);
        imageBackgroundOverlay = findViewById(R.id.mlb_imageBackgroundOverlay);

        if (settings.backgroundId != null)
            imageBackground.setBackgroundDrawable(getContext().getResources().getDrawable(settings.backgroundId));

        if (settings.backgroundOverlay != null)
            ViewHelper.setAlpha(imageBackgroundOverlay,settings.backgroundOverlay);
        if (settings.backgroundOverlayColor != null)
            imageBackgroundOverlay.setBackgroundColor(settings.backgroundOverlayColor);

        recyclerView = (RecyclerView) findViewById(R.id.mlb_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new RecyclerView.Adapter() {

            @Override
            public int getItemViewType(int position) {
                if (position == 0)
                    return 0;
                else if (position == getItemCount() - 1)
                    return 1;
                else
                    return 2;
            }

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int type) {
                View view;
                switch (type) {
                    case 0:
                        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.mlb_placeholder, viewGroup, false);
                        return new PlaceHolderViewHolder(view, false, settings.paddingTop);
                    case 1:
                        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.mlb_placeholder, viewGroup, false);
                        return new PlaceHolderViewHolder(view, false, settings.paddingBottom);
                    default:
                        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.mlb_row, viewGroup, false);
                        return new LineViewHolder(view, adapter, settings, customizer);
                }
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int row) {
                if(viewHolder instanceof LineViewHolder)
                    ((LineViewHolder)viewHolder).onBind(row);
            }

            @Override
            public int getItemCount() {
                return adapter.getLineCount();
            }
        });
    }

    public interface Adapter<VH extends MaterialLeanBack.ViewHolder> {
        int getLineCount();

        int getCellsCount(int row);

        VH onCreateViewHolder(ViewGroup viewGroup, int row);

        void onBindViewHolder(VH viewHolder, int i);

        String getTitleForRow(int row);
    }

    public Adapter getAdapter() {
        return adapter;
    }

    public void setAdapter(Adapter adapter) {
        this.adapter = adapter;
    }

    public static class ViewHolder {

        public int row;
        public int cell;
        public View itemView;

        public ViewHolder(View itemView) {
            this.itemView = itemView;
        }

    }

    public interface Customizer {
        void customizeTitle(TextView textView);
    }

    public Customizer getCustomizer() {
        return customizer;
    }

    public void setCustomizer(Customizer customizer) {
        this.customizer = customizer;
    }

    public ImageView getImageBackground() {
        return imageBackground;
    }
}
