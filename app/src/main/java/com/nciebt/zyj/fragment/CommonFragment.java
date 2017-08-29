package com.nciebt.zyj.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.nciebt.zyj.R;
import com.nciebt.zyj.adapter.CommonAdapter;
import com.nciebt.zyj.common.rx.RxBus;
import com.nciebt.zyj.data.DataRepository;
import com.nciebt.zyj.data.sql.CommonFileDetailed;
import com.nciebt.zyj.entity.FileDetails;
import com.nciebt.zyj.fragment.base.BaseFullScreenFragment;
import com.nciebt.zyj.presenter.CommonPresenter;
import com.nciebt.zyj.presenter.contract.AppContract;
import com.nciebt.zyj.utils.ToastUtils;

import java.util.List;

import butterknife.BindView;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 类名称：CommonFragment
 * 类描述：常用产品界面
 * 创建人：ghl
 * 创建时间：2017/4/24 14:44
 * 修改人：ghl
 * 修改时间：2017/4/24 14:44
 *
 * @version v1.0
 */

public class CommonFragment extends BaseFullScreenFragment implements AppContract.AddProductView {
    @BindView(R.id.common_fragment_recycler)
    RecyclerView mRecycler;
    @BindView(R.id.common_fragment_content)
    TextView mTextView;
    private AppContract.AddProductPresenter mPresenter;
    private CommonAdapter mAdapter;
    Disposable mSub;

    @Override
    public int getLayoutId() {
        return R.layout.ebt_zyj_common_fragment_layout;
    }


    @Override
    public void init() {
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 5);
        mRecycler.setLayoutManager(manager);
    }

    @Override
    public void initData() {
        new CommonPresenter(this);
        mPresenter.LoadProduct();
    }

    @Override
    public void setPresenter(AppContract.AddProductPresenter presenter) {
        mPresenter = presenter;
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
        mAdapter = new CommonAdapter(getActivity());
        mAdapter.setDatas(list);
        if (mRecycler != null) {
            mRecycler.setAdapter(mAdapter);
        }
        refreshCommon(list);
    }

    /**
     * 添加某个产品后，常用界面数据刷新
     */
    private void refreshCommon(final List<FileDetails> list) {
        mSub = RxBus.getInstance()
                .toObserverable(CommonFileDetailed.class)
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<CommonFileDetailed>() {
                    @Override
                    public void accept(@NonNull CommonFileDetailed fileDetails) throws Exception {
                        if (fileDetails != null) {
                            List<FileDetails> fileDetailses = DataRepository.getClusterQuery("CONTENTID = ?", fileDetails.getContentId())
                                    .find(FileDetails.class);
                            list.add(list.size() - 1, fileDetailses.get(0));
                            mAdapter.setDatas(list);
                        }
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mSub != null) {
            if (!mSub.isDisposed()) {
                mSub.dispose();
            }
        }
    }
}
