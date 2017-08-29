package com.nciebt.zyj.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.nciebt.zyj.R;
import com.nciebt.zyj.activity.base.BaseActivity;
import com.nciebt.zyj.adapter.AddProductAdapter;
import com.nciebt.zyj.data.DataRepository;
import com.nciebt.zyj.entity.FileDetails;
import com.nciebt.zyj.presenter.AddProductPresenter;
import com.nciebt.zyj.presenter.contract.AppContract;
import com.nciebt.zyj.utils.ToastUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * 类名称：AddProductActivity
 * 类描述：添加产品详情列表界面
 * 创建人：ghl
 * 创建时间：2017/4/24 14:41
 * 修改人：ghl
 * 修改时间：2017/4/24 14:41
 *
 * @version v1.0
 */
public class AddProductActivity extends BaseActivity implements AppContract.AddProductView {
    @BindView(R.id.add_product_fragment_edit_search)
    EditText mEditText;
    @BindView(R.id.add_product_fragment_btn_search)
    Button mButton;
    @BindView(R.id.add_product_fragment_recycler)
    RecyclerView mRecyclerView;
    private AddProductAdapter mAdapter;
    private AppContract.AddProductPresenter mPresenter;

    @Override
    public int getLayoutId() {
        return R.layout.ebt_zyj_add_product_fragment_layout;
    }


    @Override
    public void initData() {
        new AddProductPresenter(this);
        mPresenter.LoadProduct();
    }

    /**
     * 作者：ghl
     * 描述：搜索功能
     * 创建时间：2017/4/25 14:15
     *
     * @Params:
     * @Return:
     */
    private void searchData() {
        DataRepository.searchNetWorkData(mEditText).subscribe(new Consumer<List<FileDetails>>() {
            @Override
            public void accept(@NonNull List<FileDetails> list) throws Exception {
                mAdapter.setData(list);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                Log.d("AddProductActivity", "apply: " + throwable.getMessage());
                ToastUtils.toast("未查询到结果");
            }
        });
    }

    @OnClick({R.id.profit_book_back, R.id.add_product_fragment_btn_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.profit_book_back:
                this.finish();
                break;
            case R.id.add_product_fragment_btn_search:
                searchData();
                break;
            default:
                break;
        }
    }

    @Override
    public void setPresenter(AppContract.AddProductPresenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void showLoading() {
//        DialogUtils.showPleaseWaitDialog();
    }

    @Override
    public void disMissLoading() {
//        DialogUtils.dismissHProgressDialog();
    }

    @Override
    public void showError(String s) {
        ToastUtils.toast(s);
    }

    @Override
    public void loadAll(List<FileDetails> list) {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        mAdapter = new AddProductAdapter(this);
        mAdapter.setData(list);
        mRecyclerView.setAdapter(mAdapter);
    }
}
