package com.nciebt.zyj.data.module;

import com.nciebt.zyj.data.DataRepository;
import com.nciebt.zyj.entity.FileDetails;

import java.util.List;

import io.reactivex.Observable;

/**
 * 类名称：AddProductModule
 * 类描述：添加产品module（数据源）
 * 创建人：ghl
 * 创建时间：2017/4/27 10:37
 * 修改人：ghl
 * 修改时间：2017/4/27 10:37
 *
 * @version v1.0
 */

public class AddProductModule {

    /**
     * 查询本地数据库
     */
    public Observable<List<FileDetails>> LoadAllData() {
        return DataRepository.readAllRecords();
    }
}
