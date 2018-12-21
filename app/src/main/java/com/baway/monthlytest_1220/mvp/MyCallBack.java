package com.baway.monthlytest_1220.mvp;

import com.baway.monthlytest_1220.bean.GoodsBean;

import java.util.List;

/**
 * 作者:  方诗康
 * 描述:
 */
public interface MyCallBack {
    void onSuccess(List<GoodsBean.DataBean> dataBeans);
    void onFailed(String error);
}
