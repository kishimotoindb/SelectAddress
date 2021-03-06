package com.example.selectaddress;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by BigFaceBear on 2017.11.02
 */

public class XDialogLevelAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public XDialogLevelAdapter(@Nullable List<String> data) {
        super(R.layout.item_x_dialog_level, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tvContent, item);
    }
}
