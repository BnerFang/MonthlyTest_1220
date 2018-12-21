package com.baway.monthlytest_1220.carmvp.model;

import com.baway.monthlytest_1220.api.Apis;
import com.baway.monthlytest_1220.bean.GetCartBean;
import com.baway.monthlytest_1220.carmvp.CarCallBack;
import com.baway.monthlytest_1220.utils.OkHttpUtil;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 作者:  方诗康
 * 描述:
 */
public class CarModel {

    public void carData(List<GetCartBean.DataBean> group, List<List<GetCartBean.DataBean.ListBean>> child, int pid, final CarCallBack carCallBack) {
        new OkHttpUtil().OkHttpGet(Apis.CAR_URL + pid).setOkHttpListener(new OkHttpUtil.OkHttpListener() {
            @Override
            public void OkHttpSuccess(String data) {
                Gson gson = new Gson();
                GetCartBean getCartBean = gson.fromJson(data, GetCartBean.class);
                List<GetCartBean.DataBean> dataBeans = getCartBean.getData();
                List<List<GetCartBean.DataBean.ListBean>> lists = new ArrayList<>();
                for (int i = 0; i < dataBeans.size(); i++){
                    lists.add(dataBeans.get(i).getList());
                }
                if (getCartBean.getCode().equals("0")) {
                    carCallBack.onSuccess(dataBeans, lists);
                } else {
                    carCallBack.onFailed(getCartBean.getMsg());
                }
            }

            @Override
            public void OkHttpError(String error) {
                carCallBack.onFailed(error);
            }
        });
    }
}
