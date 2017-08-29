package com.nciebt.zyj.presenter;

import com.nciebt.zyj.data.module.AddProductModule;
import com.nciebt.zyj.entity.FileDetails;
import com.nciebt.zyj.presenter.contract.AppContract;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * 类名称：AddProductPresenter
 * 类描述：添加产品界面 p 层
 * 创建人：ghl
 * 创建时间：2017/4/24 14:14
 * 修改人：ghl
 * 修改时间：2017/4/24 14:14
 *
 * @version v1.0
 */

public class AddProductPresenter implements AppContract.AddProductPresenter {
    private AppContract.AddProductView mView;
    private List<FileDetails> mList = new ArrayList<>();

    public AddProductPresenter(AppContract.AddProductView view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void LoadProduct() {
        mView.showLoading();
        LoadSqlData(new AddProductModule().LoadAllData());
    }

    private void LoadSqlData(Observable<List<FileDetails>> o) {
        o.subscribe(new Consumer<List<FileDetails>>() {
            @Override
            public void accept(@NonNull List<FileDetails> list) throws Exception {
                for (FileDetails f : list
                        ) {
                    String type = f.getType();
                    if (!type.equals("OTHER")) {
                        mList.add(f);
                    }
                }
                mView.disMissLoading();
                mView.loadAll(mList);
            }
        });
    }
}
