package com.example.android.sitiservices.Login;

import com.example.android.sitiservices.R;

enum CustomObject {

    RED(R.string.viewPagerfirst, R.layout.viewpagerfirst),
    BLUE(R.string.viewPagersecondAddress, R.layout.viewpagersecond),
    GREEN(R.string.ViewpagerthirdAddress, R.layout.viewpagerthird);

    private int mTitleResId;
    private int mLayoutResId;

    CustomObject(int titleResId, int layoutResId) {
        mTitleResId = titleResId;
        mLayoutResId = layoutResId;
    }

    public int getTitleResId() {
        return mTitleResId;
    }

    public int getLayoutResId() {
        return mLayoutResId;
    }

}
