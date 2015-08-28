package com.github.florent37.mobileleanback.mobileleanback;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.github.florent37.mobileleanback.R;

/**
 * Created by florentchampigny on 28/08/15.
 */
public class LineViewHolder extends RecyclerView.ViewHolder {

    protected final MobileLeanBackSettings settings;
    protected final RecyclerView recyclerView;
    protected final MobileLeanBack.Adapter adapter;

    protected TextView title;
    protected boolean wrapped = false;

    protected int row;

    public LineViewHolder(View itemView, final MobileLeanBack.Adapter adapter, MobileLeanBackSettings settings) {
        super(itemView);
        this.adapter = adapter;
        this.settings = settings;

        title = (TextView) itemView.findViewById(R.id.title);

        recyclerView = (RecyclerView) itemView.findViewById(R.id.lineRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setHasFixedSize(true);
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public void onBind(int row) {
        this.row = row;

        {
            String titleString = adapter.getTitleForRow(this.row);
            if (titleString == null || titleString.trim().isEmpty())
                title.setVisibility(View.GONE);
            else
                title.setText(titleString);

            if (settings.titleColor != -1)
                title.setTextColor(settings.titleColor);
            if (settings.titleSize != -1)
                title.setTextSize(settings.titleSize);
        }

        recyclerView.setAdapter(new RecyclerView.Adapter() {

            @Override
            public int getItemViewType(int position) {
                switch (position) {
                    case 0:
                        return 0;
                    default:
                        return 1;
                }
            }

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int type) {
                final View view;
                switch (type) {
                    case 0:
                        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.mlb_placeholder, viewGroup, false);
                        return new RecyclerView.ViewHolder(view) {
                        };
                    default:
                        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.mlb_cell, viewGroup, false);

                        if(!wrapped) {
                            //simulate wrap_content
                            view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                                @Override
                                public boolean onPreDraw() {
                                    recyclerView.getLayoutParams().height = view.getHeight();
                                    recyclerView.requestLayout();
                                    view.getViewTreeObserver().removeOnPreDrawListener(this);
                                    wrapped = true;
                                    return false;
                                }
                            });
                        }

                        return new CellViewHolder(view, LineViewHolder.this.row, adapter, settings);
                }
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
                if (viewHolder instanceof CellViewHolder) {
                    CellViewHolder cellViewHolder = (CellViewHolder) viewHolder;
                    if (position == 1)
                        cellViewHolder.enlarged = false;
                    else
                        cellViewHolder.enlarged = true;
                    cellViewHolder.newPosition(position);

                    cellViewHolder.onBind();
                }
            }

            @Override
            public int getItemCount() {
                return LineViewHolder.this.adapter.getCellsCount(LineViewHolder.this.row);
            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
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