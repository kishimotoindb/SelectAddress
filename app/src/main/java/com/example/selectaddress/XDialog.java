package com.example.selectaddress;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by BigFaceBear on 2017.11.02
 */

public class XDialog extends AbstractBottomClickableDialog {

    @Bind(R.id.ivBack)
    ImageView mIvBack;
    @Bind(R.id.ivCancel)
    ImageView mIvCancel;
    @Bind(R.id.rvLevel)
    RecyclerView mRvLevel;
    @Bind(R.id.rvContent)
    RecyclerView mRvContent;
    @Bind(R.id.tvTitle)
    TextView mTvTitle;

    private String mDialogTitle;

    private int mLevelCount;                     //横向可以选择的层级数量
    private int mCurrentLevel = 0;          //当前正在操作的层级
    private List<Object> mLevelData;        //各个层级的名称
    private List<Object> mLevelContent;     //各个层级下对应的数据
    private List<Object> mResult;           //最终选择的结果


    public XDialog(Context context, String dialogTitle, int levelCount, List<Object> levelData, List<Object> levelContent) {
        super(context);

        mDialogTitle = dialogTitle;

        mLevelCount = levelCount;

        mLevelData = new ArrayList<>();
        mLevelContent = new ArrayList<>();
        if (levelData != null && levelData.size() != 0) {

            for (Object title : levelData) {
                mLevelData.add(title);
            }
        }

        if (levelContent != null && levelContent.size() != 0) {

            for (Object content : levelContent) {
                mLevelContent.add(content);
            }
        }


        mResult = new ArrayList<>();

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.dialog_x;
    }

    @Override
    protected void initView() {
        initRvLevel();
        initRvContent();
    }

    private void initRvLevel() {
        mRvLevel.setLayoutManager(new GridLayoutManager(getContext(), 1, LinearLayoutManager.HORIZONTAL, false));
        mRvLevel.setAdapter(new XDialogLevelAdapter(null));
        mRvLevel.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
    }

    private void initRvContent() {
        mRvContent.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvContent.setAdapter(new XDialogContentAdapter(null));
        mRvContent.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
    }

    @OnClick({R.id.ivBack, R.id.ivCancel})
    @Override
    public void onClick(View v) {
        if (!ClickUtil.isRealClick()) {
            return;
        }

        switch (v.getId()) {
            case R.id.ivBack:

                break;
            case R.id.ivCancel:

                break;
        }


    }
}
