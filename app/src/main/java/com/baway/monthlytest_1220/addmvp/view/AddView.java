package com.baway.monthlytest_1220.addmvp.view;

import com.baway.monthlytest_1220.bean.AddCarBean;

import java.util.List;

/**
 * 作者:  方诗康
 * 描述:
 */
public interface AddView {
    void onAddSuccesss(AddCarBean beans);
    void onAddFailed(String error);
}
