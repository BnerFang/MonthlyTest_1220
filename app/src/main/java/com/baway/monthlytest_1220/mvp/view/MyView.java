package com.baway.monthlytest_1220.mvp.view;

import com.baway.monthlytest_1220.bean.GoodsBean;

import java.util.List;

/**
 * 作者:  方诗康
 * 描述:
 */
public interface MyView {
    void onMySuccess(List<GoodsBean.DataBean> dataBeans);
    void onMyFailed(String error);
}
