package com.baway.monthlytest_1220.addmvp.model;

import com.baway.monthlytest_1220.addmvp.AddCallBack;
import com.baway.monthlytest_1220.api.Apis;
import com.baway.monthlytest_1220.bean.AddCarBean;
import com.baway.monthlytest_1220.utils.OkHttpUtil;
import com.google.gson.Gson;

import java.util.List;

/**
 * 作者:  方诗康
 * 描述:
 */
public class AddModel {
    public void data(AddCarBean beans, int pid, final AddCallBack callBack) {
        new OkHttpUtil().OkHttpGet(Apis.ADD_CAR_URL + pid).setOkHttpListener(new OkHttpUtil.OkHttpListener() {
            @Override
            public void OkHttpSuccess(String data) {
                Gson gson = new Gson();
                AddCarBean addCarBean = gson.fromJson(data, AddCarBean.class);
                if (addCarBean.getCode().equals("0")) {
                    callBack.onSuccesss(addCarBean);
                }else {
                    callBack.onFailed(addCarBean.getMsg());
                }
            }

            @Override
            public void OkHttpError(String error) {
                callBack.onFailed(error);
            }
        });
    }
}
