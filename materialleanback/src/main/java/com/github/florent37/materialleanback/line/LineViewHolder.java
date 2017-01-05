package com.github.florent37.materialleanback.line;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.florent37.materialleanback.MaterialLeanBack;
import com.github.florent37.materialleanback.MaterialLeanBackSettings;
import com.github.florent37.materialleanback.OnItemClickListenerWrapper;
import com.github.florent37.materialleanback.R;
import com.github.florent37.materialleanback.cell.CellAdapter;
import com.github.florent37.materialleanback.cell.CellViewHolder;

/**
 * Created by florentchampigny on 28/08/15.
 */
public class LineViewHolder extends RecyclerView.ViewHolder {

    protected final MaterialLeanBackSettings settings;
    protected final RecyclerView recyclerView;
    protected final MaterialLeanBack.Adapter adapter;
    protected final MaterialLeanBack.Customizer customizer;

    protected ViewGroup layout;
    protected TextView title;

    protected OnItemClickListenerWrapper onItemClickListenerWrapper;

    protected int row;
    protected boolean wrapped = false;

    public LineViewHolder(View itemView, @NonNull MaterialLeanBack.Adapter adapter, @NonNull MaterialLeanBackSettings settings, final MaterialLeanBack.Customizer customizer, final OnItemClickListenerWrapper onItemClickListenerWrapper) {
        super(itemView);
        this.adapter = adapter;
        this.settings = settings;
        this.customizer = customizer;
        this.onItemClickListenerWrapper = onItemClickListenerWrapper;

        layout = (ViewGroup) itemView.findViewById(R.id.row_layout);
        title = (TextView) itemView.findViewById(R.id.row_title);

        recyclerView = (RecyclerView) itemView.findViewById(R.id.row_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setHasFixedSize(true);
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public void onBind(final int row) {
        this.row = row;

        {
            final String titleString = adapter.getTitleForRow(this.row);
            if (!adapter.hasRowTitle(row) || (titleString == null || titleString.trim().isEmpty()))
                title.setVisibility(View.GONE);
            else
                title.setText(titleString);

            title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListenerWrapper != null) {
                        final MaterialLeanBack.OnItemClickListener onItemClickListener = onItemClickListenerWrapper.getOnItemClickListener();
                        if (onItemClickListener != null) {
                            onItemClickListener.onTitleClicked(row, titleString);
                        }
                    }
                }
            });

            if (settings.titleColor != null)
                title.setTextColor(settings.titleColor);
            if (settings.titleSize != -1)
                title.setTextSize(settings.titleSize);

            if (this.customizer != null)
                customizer.customizeTitle(title);
        }
        {
            if (settings.lineSpacing != null) {
                layout.setPadding(
                        layout.getPaddingLeft(),
                        layout.getPaddingTop(),
                        layout.getPaddingRight(),
                        settings.lineSpacing
                );
            }
        }

        recyclerView.setAdapter(new CellAdapter(row, adapter, settings, onItemClickListenerWrapper, new CellAdapter.HeightCalculatedCallback() {
            @Override
            public void onHeightCalculated(int height) {
                if (!wrapped) {
                    recyclerView.getLayoutParams().height = height;
                    recyclerView.requestLayout();
                    wrapped = true;
                }
            }
        }));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    adapter.getEnlargedItemPosition(adapter.itemPosition);
                }
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                for (int i = 0; i < recyclerView.getChildCount(); ++i) {
                    RecyclerView.ViewHolder viewHolder = recyclerView.getChildViewHolder(recyclerView.getChildAt(i));
                    if (viewHolder instanceof CellViewHolder) {
                        CellViewHolder cellViewHolder = ((CellViewHolder) viewHolder);
                        cellViewHolder.newPosition(i);
                    }
                }
            }
        });
    }
}