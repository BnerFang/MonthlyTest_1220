package com.baway.monthlytest_1220.carmvp.view;

import com.baway.monthlytest_1220.bean.GetCartBean;

import java.util.List;

/**
 * 作者:  方诗康
 * 描述:
 */
public interface CarView {
    void onCarSuccess(List<GetCartBean.DataBean> group, List<List<GetCartBean.DataBean.ListBean>> child);
    void onCarFailed(String error);
}
