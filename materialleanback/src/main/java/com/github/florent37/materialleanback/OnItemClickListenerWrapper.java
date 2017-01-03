package com.github.florent37.materialleanback;

import android.support.annotation.Nullable;

public class OnItemClickListenerWrapper {
    private MaterialLeanBack.OnItemClickListener onItemClickListener;

    @Nullable
    public MaterialLeanBack.OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(MaterialLeanBack.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
