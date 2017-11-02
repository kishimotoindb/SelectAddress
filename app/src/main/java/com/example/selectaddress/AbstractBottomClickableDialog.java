package com.example.selectaddress;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import butterknife.ButterKnife;

/**
 * Created by BigFaceBear on 2017.10.31
 */

public abstract class AbstractBottomClickableDialog extends Dialog implements View.OnClickListener{

    public AbstractBottomClickableDialog(@NonNull Context context) {
        super(context, R.style.BottomDialogTheme);
        setContentView(getLayoutResId());
        ButterKnife.bind(this);
        setBottom(context);
        initView();
    }

    protected abstract int getLayoutResId();

    protected abstract void initView();

    private void setBottom(Context context) {
        //配置为底部展示的dialog
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, context.getResources().getDisplayMetrics());
        lp.gravity = Gravity.BOTTOM;
        onWindowAttributesChanged(lp);
    }
}
