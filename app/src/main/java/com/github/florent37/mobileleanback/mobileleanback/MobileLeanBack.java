package com.github.florent37.mobileleanback.mobileleanback;

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

import com.github.florent37.mobileleanback.R;

/**
 * Created by florentchampigny on 28/08/15.
 */
public class MobileLeanBack extends FrameLayout {

    RecyclerView recyclerView;
    ImageView imageBackground;
    View imageBackgroundOverlay;

    MobileLeanBack.Adapter adapter;
    MobileLeanBack.Customizer customizer;
    MobileLeanBackSettings settings = new MobileLeanBackSettings();

    public MobileLeanBack(Context context) {
        super(context);
    }

    public MobileLeanBack(Context context, AttributeSet attrs) {
        super(context, attrs);
        settings.handleAttributes(context, attrs);
    }

    public MobileLeanBack(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        settings.handleAttributes(context,attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        addView(LayoutInflater.from(getContext()).inflate(R.layout.mlb_layout, this, false));

        imageBackground = (ImageView) findViewById(R.id.mlb_imageBackground);
        imageBackgroundOverlay = findViewById(R.id.mlb_imageBackgroundOverlay);

        if(settings.backgroundId != null)
            imageBackground.setBackgroundDrawable(getContext().getResources().getDrawable(settings.backgroundId));

        if(settings.backgroundOverlay != null)
            imageBackgroundOverlay.setAlpha(settings.backgroundOverlay);
        if(settings.backgroundOverlayColor != null)
            imageBackgroundOverlay.setBackgroundColor(settings.backgroundOverlayColor);

        recyclerView = (RecyclerView) findViewById(R.id.mlb_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new RecyclerView.Adapter<LineViewHolder>() {

            @Override
            public LineViewHolder onCreateViewHolder(ViewGroup viewGroup, int type) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.mlb_row, viewGroup, false);
                return new LineViewHolder(view, adapter, settings, customizer);
            }

            @Override
            public void onBindViewHolder(LineViewHolder viewHolder, int row) {
                viewHolder.onBind(row);
            }

            @Override
            public int getItemCount() {
                return adapter.getLineCount();
            }
        });
    }

    public interface Adapter<VH extends MobileLeanBack.ViewHolder>{
        int getLineCount();
        int getCellsCount(int row);
        VH  onCreateViewHolder(ViewGroup viewGroup, int row);
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

        public ViewHolder(View itemView){
            this.itemView = itemView;
        }

    }

    public interface Customizer{
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
