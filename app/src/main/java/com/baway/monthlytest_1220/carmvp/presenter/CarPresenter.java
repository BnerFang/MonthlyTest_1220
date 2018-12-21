package com.baway.monthlytest_1220.carmvp.presenter;

import com.baway.monthlytest_1220.bean.GetCartBean;
import com.baway.monthlytest_1220.carmvp.CarCallBack;
import com.baway.monthlytest_1220.carmvp.model.CarModel;
import com.baway.monthlytest_1220.carmvp.view.CarView;

import java.util.List;

/**
 * 作者:  方诗康
 * 描述:
 */
public class CarPresenter {
    private CarView mICarView;
    private CarModel mCarModel;

    public CarPresenter(CarView ICarView) {
        mICarView = ICarView;
        mCarModel = new CarModel();
    }

    public void datechView() {
        mICarView = null;
    }

    public void carDatas(List<GetCartBean.DataBean> group, List<List<GetCartBean.DataBean.ListBean>> child, int pid) {
        mCarModel.carData(group, child, pid, new CarCallBack() {
            @Override
            public void onSuccess(List<GetCartBean.DataBean> group, List<List<GetCartBean.DataBean.ListBean>> child) {
                mICarView.onCarSuccess(group, child);
            }

            @Override
            public void onFailed(String error) {
                mICarView.onCarFailed(error);
            }
        });
    }
}
