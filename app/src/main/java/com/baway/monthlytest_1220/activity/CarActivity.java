package com.baway.monthlytest_1220.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baway.monthlytest_1220.R;
import com.baway.monthlytest_1220.adapter.ElvAdapter;
import com.baway.monthlytest_1220.bean.GetCartBean;
import com.baway.monthlytest_1220.carmvp.PriceAndCount;
import com.baway.monthlytest_1220.carmvp.presenter.CarPresenter;
import com.baway.monthlytest_1220.carmvp.view.CarView;

import java.util.List;


public class CarActivity extends AppCompatActivity implements CarView {


    private ImageView mBack;
    private LinearLayout mTopBar;
    private TextView mEdit;
    private ExpandableListView mElv;
    private CheckBox mCb;
    private TextView mTvTotal;
    private TextView mTvCount;
    private ElvAdapter elvAdapter;
    private CarPresenter mCarPresenter;
    private List<GetCartBean.DataBean> mDataBeans;
    private List<List<GetCartBean.DataBean.ListBean>> mLists;
    private int mPid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car);
        mPid = getIntent().getIntExtra("pid", 1);
        initView();
    }

    private void initView() {
        mBack = (ImageView) findViewById(R.id.back);
        mEdit = (TextView) findViewById(R.id.edit);
        mTopBar = (LinearLayout) findViewById(R.id.top_bar);
        mElv = (ExpandableListView) findViewById(R.id.elv);
        mCb = (CheckBox) findViewById(R.id.cb);
        mTvTotal = (TextView) findViewById(R.id.tvTotal);
        mTvCount = (TextView) findViewById(R.id.tvCount);
        mCarPresenter = new CarPresenter(this);
        mCarPresenter.carDatas(mDataBeans,mLists,mPid);
    }


    @Override
    public void onCarSuccess(List<GetCartBean.DataBean> group, List<List<GetCartBean.DataBean.ListBean>> child) {
        mDataBeans = group;
        mLists = child;
        for (int i = 0; i < group.size(); i++) {
            List<GetCartBean.DataBean.ListBean> list = group.get(i).getList();
            if (list.size() == 0) {
                group.remove(i);
                child.remove(i);
            }
        }
        elvAdapter = new ElvAdapter(this,group,child);
        mElv.setGroupIndicator(null);
        mElv.setAdapter(elvAdapter);
        for (int i = 0; i < group.size(); i++) {
            mElv.expandGroup(i);
        }
    }

    @Override
    public void onCarFailed(String error) {

    }

    public void setPriceAndCount(PriceAndCount priceAndCount) {
        mTvTotal.setText("合计："+"￥" + priceAndCount.getPrice());
        mTvCount.setText("去结算(" + priceAndCount.getCount() + ")");
    }
    public void setAllChecked(boolean bool) {
        mCb.setChecked(bool);
    }

}