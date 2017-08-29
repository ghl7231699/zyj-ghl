package com.nciebt.zyj.presenter.contract;

import com.nciebt.zyj.entity.FileDetails;
import com.nciebt.zyj.entity.ProfitBookEntity;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * 类名称：AppContract
 * 类描述：Contract契约类
 * 创建人：ghl
 * 创建时间：2017/4/24 15:15
 * 修改人：ghl
 * 修改时间：2017/4/24 15:15
 *
 * @version v1.0
 */

public interface AppContract {
    /**
     * 产品详情界面Contract
     */
    interface ProductView extends BaseView<ProductPresenter> {
        void loadDetailContent(File file, String s);//加载详情页面的源文件
    }

    interface ProductPresenter {
        void LoadProduct();
    }

    /**
     * 产品Contract
     */
    interface ProductInfoView extends BaseView<ProductInfoPresenter> {
        void showProductLists(Object model);//将网络请求得到的用户信息回调
    }

    interface ProductInfoPresenter {
        void loadProductLists();//加载产品列表
    }

    /**
     * 添加产品Contract
     */
    interface AddProductView extends BaseView<AddProductPresenter> {

        void loadAll(List<FileDetails> list);//加载详情页面的源文件

    }

    interface AddProductPresenter {
        void LoadProduct();
    }

    /**
     * 其他分红宝典
     */
    interface ProfitBookView extends BaseView<ProfitBooksPresenter> {
        void loadBook(Map<String, List<ProfitBookEntity>> map, List<String> list);
    }

    interface ProfitBooksPresenter {
        void loadProfitBook();
    }

}
