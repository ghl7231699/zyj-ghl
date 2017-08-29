package com.nciebt.zyj.data.module;

import com.nciebt.zyj.constant.Constant;
import com.nciebt.zyj.entity.ProfitBookEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类名称：ProfitBookModule
 * 类描述：分红宝典module
 * 创建人：ghl
 * 创建时间：2017/7/5 10:04
 * 修改人：ghl
 * 修改时间：2017/7/5 10:04
 *
 * @version v1.0
 */

public class ProfitBookModule {
    private static final String[] books = {Constant.PROFIT_BOOK_COVERAGE, Constant.PROFIT_BOOK_CRASH};

    public Map<String, List<ProfitBookEntity>> getBooks() {
        Map<String, List<ProfitBookEntity>> map = new HashMap<>();
        List<ProfitBookEntity> mList = new ArrayList<>();
        mList.add(new ProfitBookEntity(Constant.PROFIT_BOOK_COVERAGE_BASIC, Constant.URL_PROFIT_BOOK_COVERAGE_BASIC));
        mList.add(new ProfitBookEntity(Constant.PROFIT_BOOK_COVERAGE_BONUS, Constant.URL_PROFIT_BOOK_COVERAGE_BONUS));
        mList.add(new ProfitBookEntity(Constant.PROFIT_BOOK_COVERAGE_PRACTICE, Constant.URL_PROFIT_BOOK_COVERAGE_PRACTICE));
        mList.add(new ProfitBookEntity(Constant.PROFIT_BOOK_COVERAGE_NOTE, Constant.URL_PROFIT_BOOK_COVERAGE_NOTE));
        map.put(books[0], mList);
        List<ProfitBookEntity> list2 = new ArrayList<>();
        list2.add(new ProfitBookEntity(Constant.PROFIT_BOOK_COVERAGE_BASIC, Constant.URL_PROFIT_BOOK_CRASH_BASIC));
        list2.add(new ProfitBookEntity(Constant.PROFIT_BOOK_COVERAGE_BONUS, Constant.URL_PROFIT_BOOK_CRASH_BONUS));
        list2.add(new ProfitBookEntity(Constant.PROFIT_BOOK_COVERAGE_PRACTICE, Constant.URL_PROFIT_BOOK_CRASH_PRACTICE));
        list2.add(new ProfitBookEntity(Constant.PROFIT_BOOK_COVERAGE_NOTE, Constant.URL_PROFIT_BOOK_CRASH_NOTE));
        map.put(books[1], list2);
        return map;
    }

    public List<String> getParentBooks() {
        List<String> list = new ArrayList<>();
        list.add(books[0]);
        list.add(books[1]);
        return list;
    }

}
