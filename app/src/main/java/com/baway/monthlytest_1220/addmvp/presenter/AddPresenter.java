package com.baway.monthlytest_1220.addmvp.presenter;

import com.baway.monthlytest_1220.addmvp.AddCallBack;
import com.baway.monthlytest_1220.addmvp.model.AddModel;
import com.baway.monthlytest_1220.addmvp.view.AddView;
import com.baway.monthlytest_1220.bean.AddCarBean;

/**
 * 作者:  方诗康
 * 描述:
 */
public class AddPresenter {
    private AddView mAddView;
    private AddModel mAddModel;

    public AddPresenter(AddView addView) {
        mAddView = addView;
        mAddModel = new AddModel();
    }

    public void datechView() {
        mAddView = null;
    }

    public void datas(AddCarBean bean, int pid) {
        mAddModel.data(bean, pid, new AddCallBack() {
            @Override
            public void onSuccesss(AddCarBean beans) {
                mAddView.onAddSuccesss(beans);
            }

            @Override
            public void onFailed(String error) {
                mAddView.onAddFailed(error);
            }
        });
    }
}
