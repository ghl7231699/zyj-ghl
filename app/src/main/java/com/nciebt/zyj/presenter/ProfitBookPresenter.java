package com.nciebt.zyj.presenter;

import com.nciebt.zyj.data.module.ProfitBookModule;
import com.nciebt.zyj.entity.ProfitBookEntity;
import com.nciebt.zyj.presenter.contract.AppContract;

import java.util.List;
import java.util.Map;

/**
 * 类名称：ProfitBooksPresenter
 * 类描述：分红宝典详情页p层
 * 创建人：ghl
 * 创建时间：2017/7/5 9:53
 * 修改人：ghl
 * 修改时间：2017/7/5 9:53
 *
 * @version v1.0
 */

public class ProfitBookPresenter implements AppContract.ProfitBooksPresenter {
    private AppContract.ProfitBookView mView;

    public ProfitBookPresenter(AppContract.ProfitBookView view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void loadProfitBook() {
        mView.showLoading();
        loadBookList();
    }

    private void loadBookList() {
        ProfitBookModule module = new ProfitBookModule();
        Map<String, List<ProfitBookEntity>> books = module.getBooks();
        List<String> parentBooks = module.getParentBooks();
        mView.loadBook(books, parentBooks);
    }
}
