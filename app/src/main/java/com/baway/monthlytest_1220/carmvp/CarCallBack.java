package com.baway.monthlytest_1220.carmvp;

import com.baway.monthlytest_1220.bean.GetCartBean;

import java.util.List;

/**
 * 作者:  方诗康
 * 描述:
 */
public interface CarCallBack {
        void onSuccess(List<GetCartBean.DataBean> group, List<List<GetCartBean.DataBean.ListBean>> child);
        void onFailed(String error);
}
