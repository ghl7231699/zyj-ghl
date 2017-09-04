package com.nciebt.zyj.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.lzy.okhttputils.OkHttpUtils;
import com.nciebt.zyj.R;
import com.nciebt.zyj.activity.base.BaseActivity;
import com.nciebt.zyj.entity.FileDetails;
import com.nciebt.zyj.presenter.ProductDetailsPresenter;
import com.nciebt.zyj.presenter.contract.AppContract;
import com.nciebt.zyj.utils.DialogUtils;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 类名称：PdfActivity
 * 类描述：pdf详情界面
 * 创建人：ghl
 * 创建时间：2017/4/13 9:14
 * 修改人：ghl
 * 修改时间：2017/4/13 9:14
 *
 * @version v1.0
 */

public class PdfActivity extends BaseActivity implements OnPageChangeListener, AppContract.ProductView {
    @BindView(R.id.pdf_fragment_pdf_content)
    PDFView mPDFView;
    @BindView(R.id.pdf_fragment_text_page)
    TextView mTextView;
    private AppContract.ProductPresenter mPresenters;

    @Override
    public int getLayoutId() {
        return R.layout.ebt_zyj_pdf_activity_layout;
    }

    @Override
    public void initData() {
        Bundle key = getIntent().getExtras();
        if (key != null) {
            FileDetails f = (FileDetails) key.getSerializable("key");
            new ProductDetailsPresenter(this, f);
            mPresenters.LoadProduct();
        }
    }

    @Override
    public void onPageChanged(int page, int pageCount) {
        if (page == 0) {
            mTextView.setVisibility(View.INVISIBLE);
        } else {
            mTextView.setVisibility(View.VISIBLE);
            mTextView.setText(page + "/" + (pageCount - 1));
        }
    }

    @OnClick(R.id.pad_fragment_content_fab)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void setPresenter(AppContract.ProductPresenter presenter) {
        this.mPresenters = presenter;
    }


    @Override
    public void showError(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {
        DialogUtils.showPleaseWaitDialog();
    }

    @Override
    public void loadDetailContent(File file, String s) {
//        File f = new File(App.getLocalDataPath() + "00179000-尊逸人生养老年金保险-费率表.PDF.pdf");
        mPDFView.fromFile(file)
//                .pages(0, 0, 0, 0, 0, 0) // 默认全部显示，pages属性可以过滤性显示
                .defaultPage(0)//默认展示第一页
                .onPageChange(this)//监听页面切换
                .load();
    }

    @Override
    public void disMissLoading() {
        DialogUtils.dismissHProgressDialog();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(this);
    }
}
