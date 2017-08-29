package com.nciebt.zyj.fragment;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.nciebt.zyj.R;
import com.nciebt.zyj.adapter.ProductAdapter;
import com.nciebt.zyj.data.DataCenter;
import com.nciebt.zyj.entity.FileDetails;
import com.nciebt.zyj.fragment.base.BaseFullScreenFragment;
import com.nciebt.zyj.presenter.ProductPresenter;
import com.nciebt.zyj.presenter.contract.AppContract;
import com.nciebt.zyj.utils.ToastUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 类名称：ProductFragment
 * 类描述：产品界面
 * 创建人：ghl
 * 创建时间：2017/4/11 14:14
 * 修改人：ghl
 * 修改时间：2017/4/11 14:14
 *
 * @version v1.0
 */
public class ProductFragment extends BaseFullScreenFragment implements AppContract.ProductInfoView {
    @BindView(R.id.product_fragment_recycle_view)
    RecyclerView mProductFragmentRecycleView;
    @BindView(R.id.product_fragment_radio_button)
    RadioButton mRadioButton;
    @BindView(R.id.product_fragment_content)
    TextView mTextView;
    private AppContract.ProductInfoPresenter mPresenter;


    @Override
    public int getLayoutId() {
        return R.layout.ebt_zyj_product_fragment_layout;
    }

    @Override
    public void init() {
        String mainName = DataCenter.getInstance().getMainName();
        mTextView.setText("当前的位置：" + mainName +"："+ getSubName());
        FragmentActivity activity = getActivity();
        GridLayoutManager manager = new GridLayoutManager(activity, 4);
        mProductFragmentRecycleView.setLayoutManager(manager);
    }

    @Override
    public void initData() {
        //获取对应的文件id
        String s = getmGroupID();
        if (s != null) {
            new ProductPresenter(s, this);
            mPresenter.loadProductLists();
        }
    }

    @Override
    public void setPresenter(AppContract.ProductInfoPresenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void showError(String s) {
        ToastUtils.toast(s);
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
    public void showProductLists(Object model) {
        if (model != null) {
            List<FileDetails> fileDetailses = (List<FileDetails>) model;
            ProductAdapter<FileDetails> adapter = new ProductAdapter<>(fileDetailses, getActivity());
            if (mProductFragmentRecycleView != null) {
                mProductFragmentRecycleView.setAdapter(adapter);
            }
        } else
            showError("获取数据失败了");
    }

    @OnClick(R.id.product_fragment_radio_button)
    public void onViewClicked() {
        startFullScreenMod(mRadioButton);
    }
}
