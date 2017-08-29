package com.nciebt.zyj.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.nciebt.zyj.R;
import com.nciebt.zyj.adapter.ProfitAdapter;
import com.nciebt.zyj.entity.FileDetails;
import com.nciebt.zyj.fragment.base.BaseFullScreenFragment;
import com.nciebt.zyj.presenter.ProductPresenter;
import com.nciebt.zyj.presenter.contract.AppContract;

import java.util.List;

import butterknife.BindView;

/**
 * 类名称：ProfitBookFragment
 * 类描述：分红宝典fragment
 * 创建人：ghl
 * 创建时间：2017/7/7 11:32
 * 修改人：ghl
 * 修改时间：2017/7/7 11:32
 *
 * @version v1.0
 */

public class ProfitBookFragment extends BaseFullScreenFragment implements AppContract.ProductInfoView {

    @BindView(R.id.common_fragment_content)
    TextView mTextView;
    @BindView(R.id.common_fragment_recycler)
    RecyclerView mRecyclerView;
    private ProfitAdapter mAdapter;
    private AppContract.ProductInfoPresenter mPresenter;

    @Override
    public void init() {
    }

    @Override
    public int getLayoutId() {
        return R.layout.ebt_zyj_common_fragment_layout;
    }

    @Override
    public void initData() {
        mTextView.setText("当前位置：其他");
        //获取对应的文件id
        String s = getmGroupID();
        if (s != null) {
            new ProductPresenter(s, this);
            mPresenter.loadProductLists();
        }
    }

    @Override
    public void setPresenter(AppContract.ProductInfoPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void disMissLoading() {

    }

    @Override
    public void showError(String s) {

    }

    @Override
    public void showProductLists(Object model) {
        if (model != null) {
            List<FileDetails> fileDetailses = (List<FileDetails>) model;
            //加载分红宝典列表
            mAdapter = new ProfitAdapter(getActivity(), fileDetailses);
            GridLayoutManager manager = new GridLayoutManager(this.getContext(), 4);
            mRecyclerView.setLayoutManager(manager);
            mRecyclerView.setAdapter(mAdapter);
        }
    }
}
