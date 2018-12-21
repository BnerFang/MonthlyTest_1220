package com.baway.monthlytest_1220.addmvp;

import com.baway.monthlytest_1220.bean.AddCarBean;

import java.util.List;

/**
 * 作者:  方诗康
 * 描述:
 */
public interface AddCallBack {
    void onSuccesss(AddCarBean beans);
    void onFailed(String error);

}
