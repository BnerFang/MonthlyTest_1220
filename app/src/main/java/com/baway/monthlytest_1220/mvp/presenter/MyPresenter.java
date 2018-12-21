package com.baway.monthlytest_1220.mvp.presenter;

import com.baway.monthlytest_1220.bean.GoodsBean;
import com.baway.monthlytest_1220.mvp.MyCallBack;
import com.baway.monthlytest_1220.mvp.model.MyModel;
import com.baway.monthlytest_1220.mvp.view.MyView;

import java.util.List;

/**
 * 作者:  方诗康
 * 描述:
 */
public class MyPresenter {
    private MyView mMyView;
    private MyModel mMyModel;

    public MyPresenter(MyView myView) {
        mMyView = myView;
        mMyModel = new MyModel();
    }

    public void datechView() {
        mMyView = null;
    }

    public void datas(List<GoodsBean.DataBean> dataBeans, String key, int pager, int sort) {
        mMyModel.data(dataBeans, key, pager, sort, new MyCallBack() {
            @Override
            public void onSuccess(List<GoodsBean.DataBean> dataBeans) {
                mMyView.onMySuccess(dataBeans);
            }

            @Override
            public void onFailed(String error) {
                mMyView.onMyFailed(error);
            }
        });
    }
}
