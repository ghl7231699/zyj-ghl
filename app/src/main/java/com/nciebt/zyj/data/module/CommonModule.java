package com.nciebt.zyj.data.module;

import com.nciebt.zyj.data.DataRepository;
import com.nciebt.zyj.entity.FileDetails;

import java.util.List;

import io.reactivex.Observable;

/**
 * 类名称：
 * 类描述：常用界面module
 * 创建人：ghl
 * 创建时间：2017/4/26 9:40
 * 修改人：ghl
 * 修改时间：2017/4/26 9:40
 *
 * @version v1.0
 */

public class CommonModule {

    public CommonModule() {
    }

    /**
     * 读取本地数据
     *
     * @return
     */
    public Observable<List<FileDetails>> readRecords() {
        return DataRepository.readCommonListRecords();
    }
}
