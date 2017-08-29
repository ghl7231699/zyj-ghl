package com.nciebt.zyj.activity;

import android.view.View;
import android.webkit.WebView;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.nciebt.zyj.R;
import com.nciebt.zyj.activity.base.BaseActivity;
import com.nciebt.zyj.adapter.ProfitBookAdapter;
import com.nciebt.zyj.constant.Constant;
import com.nciebt.zyj.entity.ProfitBookEntity;
import com.nciebt.zyj.presenter.ProfitBookPresenter;
import com.nciebt.zyj.presenter.contract.AppContract;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 类名称：ProfitBookActivity
 * 类描述：分红宝典详情界面
 * 创建人：ghl
 * 创建时间：2017/7/4 15:27
 * 修改人：ghl
 * 修改时间：2017/7/4 15:27
 *
 * @version v1.0
 */

public class ProfitBookActivity extends BaseActivity implements AppContract.ProfitBookView {
    @BindView(R.id.profit_book_back)
    TextView mBack;
    @BindView(R.id.profit_book_activity_ev)
    ExpandableListView mEv;
    @BindView(R.id.profit_book_activity_wv)
    WebView mWebView;
    private AppContract.ProfitBooksPresenter mPresenter;
    private ProfitBookAdapter mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.profit_book_activity_layout;
    }

    @Override
    public void initData() {
        new ProfitBookPresenter(this);
        mPresenter.loadProfitBook();
    }

    @Override
    public void setPresenter(AppContract.ProfitBooksPresenter presenter) {
        this.mPresenter = presenter;
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
    public void loadBook(final Map<String, List<ProfitBookEntity>> map, final List<String> list) {
        if (map != null && list != null) {
            mAdapter = new ProfitBookAdapter(this, map, list);
            mAdapter.onGroupExpanded(0);
            mEv.setAdapter(mAdapter);
            mEv.expandGroup(0);
            mWebView.loadUrl(Constant.URL_PROFIT_BOOK_COVERAGE_BASIC);
            mEv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView parent, View v, int groupPosition,
                                            int childPosition, long id) {
                    ProfitBookEntity entity = map.get(list.get(groupPosition)).get(childPosition);
                    mWebView.loadUrl(entity.getUrl());
                    mAdapter.setPaDefSelect(groupPosition, childPosition);
                    return false;
                }
            });
        }
    }

    @OnClick(R.id.profit_book_back)
    public void onViewClicked() {
        this.finish();
    }
}
