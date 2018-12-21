package com.baway.monthlytest_1220.mvp.model;

import com.baway.monthlytest_1220.api.Apis;
import com.baway.monthlytest_1220.bean.GoodsBean;
import com.baway.monthlytest_1220.mvp.MyCallBack;
import com.baway.monthlytest_1220.utils.OkHttpUtil;
import com.google.gson.Gson;

import java.util.List;

/**
 * 作者:  方诗康
 * 描述:
 */
public class MyModel {
    public void data(List<GoodsBean.DataBean> dataBeans, String key, int pager, int sort, final MyCallBack callBack) {
        new OkHttpUtil().OkHttpGet("http://www.zhaoapi.cn/product/searchProducts?keywords=" + key + "&page=" + pager + "&sort=" + sort).setOkHttpListener(new OkHttpUtil.OkHttpListener() {
            @Override
            public void OkHttpSuccess(String data) {
                Gson gson = new Gson();
                GoodsBean goodsBean = gson.fromJson(data, GoodsBean.class);
                List<GoodsBean.DataBean> beans = goodsBean.getData();
                if (goodsBean.getCode().equals("0")) {
                    callBack.onSuccess(beans);
                } else {
                    callBack.onFailed(goodsBean.getMsg());
                }
            }

            @Override
            public void OkHttpError(String error) {
                callBack.onFailed(error);
            }
        });
    }
}
