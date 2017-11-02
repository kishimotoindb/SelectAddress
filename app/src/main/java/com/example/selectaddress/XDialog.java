package com.example.selectaddress;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
    @Bind(R.id.pbar)
    ProgressBar mPbar;

    private int mLevelCount;                        //横向可以选择的层级数量
    private int mCurrentLevel = 0;                  //当前正在操作的层级
    private List<LevelContent> mLevelContents;       //各个层级下对应的数据
    private List<String> mResult;                   //最终选择的结果
    private XDialogLevelAdapter mLevelAdapter;
    private XDialogContentAdapter mContentAdapter;


    public XDialog(Context context, int levelCount, List<LevelContent> levelContents) {
        super(context);

        mLevelCount = levelCount;

        mLevelContents = new ArrayList<>();
        if (levelContents != null && levelContents.size() != 0) {

            for (LevelContent content : levelContents) {
                mLevelContents.add(content);
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
        mResult.add("请选择");
        mLevelAdapter = new XDialogLevelAdapter(mResult);
        mRvLevel.setAdapter(mLevelAdapter);
        mRvLevel.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (position != adapter.getData().size() - 1) {

                    mCurrentLevel = position;

                    //1.清空高层级的结果
                    for (int i = position; i < adapter.getData().size() - 1; i++) {
                        adapter.remove(i);
                    }
                    adapter.notifyItemMoved(position, adapter.getData().size() - 1);

                    //将内容变更为当前层级的数据
                    mContentAdapter.setNewData(mLevelContents.get(position).getContent());

                }
            }
        });
    }

    private void initRvContent() {
        mRvContent.setLayoutManager(new LinearLayoutManager(getContext()));
        mContentAdapter = new XDialogContentAdapter(null);
        mRvContent.setAdapter(mContentAdapter);
        mRvContent.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                String resultContent = (String) adapter.getItem(position);

                if (mCurrentLevel < mLevelCount - 1) {

                    //将当前层级的结果展示在层级列表中
                    mLevelAdapter.getData().add(mCurrentLevel, resultContent);
                    mLevelAdapter.notifyItemInserted(mCurrentLevel);

                    /*
                     * 展示下一层级的数据
                     * 1.如果有数据，直接从本地拿
                     * 2.如果没有，请求网络
                     */
                    //增加一级
                    mCurrentLevel++;
                    // TODO: 02/11/2017 这里不嫩只比对数量，还要比较上一级的id
                    if (mCurrentLevel >= mLevelContents.size()) {
                        //没有数据，请求网络
                        mContentAdapter.setNewData(null);
                        mPbar.setVisibility(View.VISIBLE);
                        loadData();

                    } else {
                        //有数据，直接从本地拿
                        mContentAdapter.setNewData(mLevelContents.get(mCurrentLevel).getContent());
                    }


                } else {
                    //最后一个层级，选完信息直接退出，并返回数据
                    mLevelAdapter.getData().add(mCurrentLevel, resultContent);
                    mLevelAdapter.getData().remove(mLevelAdapter.getData().size() - 1);
                    mLevelAdapter.notifyItemInserted(mCurrentLevel);

                    if (mOnSelectionCompleteListener != null) {
                        mOnSelectionCompleteListener.onSelectionComplete(mLevelAdapter.getData());
                    }

                    dismiss();

                }
            }
        });
    }

    private void loadData() {
        //成功，将数据放入内容栏
        mContentAdapter.setNewData(null);
        //失败弹toast

        //最后
        mPbar.setVisibility(View.GONE);
    }

    @OnClick({R.id.ivCancel})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivCancel:
                dismiss();
                break;
        }
    }

    private OnSelectionCompleteListener mOnSelectionCompleteListener;

    public void setOnSelectionCompleteListener(OnSelectionCompleteListener onSelectionCompleteListener) {
        mOnSelectionCompleteListener = onSelectionCompleteListener;
    }

    public interface OnSelectionCompleteListener {
        void onSelectionComplete(List<String> result);
    }
}
