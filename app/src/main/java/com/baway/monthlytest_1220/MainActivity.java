package com.baway.monthlytest_1220;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baway.monthlytest_1220.activity.CarActivity;
import com.baway.monthlytest_1220.adapter.MyAdapter;
import com.baway.monthlytest_1220.addmvp.presenter.AddPresenter;
import com.baway.monthlytest_1220.addmvp.view.AddView;
import com.baway.monthlytest_1220.bean.AddCarBean;
import com.baway.monthlytest_1220.bean.GoodsBean;
import com.baway.monthlytest_1220.mvp.presenter.MyPresenter;
import com.baway.monthlytest_1220.mvp.view.MyView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, MyView ,AddView {

    private ImageView mNeiSys;
    /**
     * 扫一扫
     */
    private TextView mSySys;
    /**
     * 请输入搜索内容......
     */
    private EditText mNeiSs;
    /**
     * 搜索
     */
    private Button mNeiXx;
    private LinearLayout mSyTop;
    private XRecyclerView mSouRv;
    private MyAdapter mMyAdapter;
    private MyPresenter mMyPresenter;
    private List<GoodsBean.DataBean> mDataBeans = new ArrayList<>();
    private String key = "手机";
    private int pager = 1;
    private int sort;
    /**
     * 综合,销量,价格,筛选
     */
    private TextView mZh, mXl, mJg, mSx;
    private AddCarBean mAddCarBean;
    private AddPresenter mAddPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        mSouRv.setLoadingMoreEnabled(true);
        mSouRv.setPullRefreshEnabled(true);
        mSouRv.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mMyAdapter.setData(mDataBeans);
                mSouRv.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                mMyAdapter.setData(mDataBeans);
                mSouRv.refreshComplete();
            }
        });

    }

    private void initView() {
        mNeiSys = (ImageView) findViewById(R.id.nei_sys);
        mSySys = (TextView) findViewById(R.id.sy_sys);
        mNeiSs = (EditText) findViewById(R.id.nei_ss);
        mNeiXx = (Button) findViewById(R.id.nei_xx);
        mNeiXx.setOnClickListener(this);
        mSyTop = (LinearLayout) findViewById(R.id.sy_top);
        mSouRv = (XRecyclerView) findViewById(R.id.sou_rv);
        mSouRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mMyPresenter = new MyPresenter(this);
        mMyPresenter.datas(mDataBeans, key, pager, sort);
        mZh = (TextView) findViewById(R.id.zh);
        mZh.setOnClickListener(this);
        mXl = (TextView) findViewById(R.id.xl);
        mXl.setOnClickListener(this);
        mJg = (TextView) findViewById(R.id.jg);
        mJg.setOnClickListener(this);
        mSx = (TextView) findViewById(R.id.sx);
        mSx.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.nei_xx:
                mMyPresenter.datas(mDataBeans, mNeiSs.getText().toString().trim(), pager, sort);
                break;
            case R.id.zh:
                mMyPresenter.datas(mDataBeans, key, pager, sort);
                break;
            case R.id.xl:
                mMyPresenter.datas(mDataBeans, key, pager, 1);
                break;
            case R.id.jg:
                mMyPresenter.datas(mDataBeans, key, pager, 2);
                break;
            case R.id.sx:
                break;
        }
    }

    //访问数据成功
    @Override
    public void onMySuccess(final List<GoodsBean.DataBean> dataBeans) {
        mDataBeans = dataBeans;
        mMyAdapter = new MyAdapter(this, dataBeans);
        mMyAdapter.setData(mDataBeans);
        mSouRv.setAdapter(mMyAdapter);
        mMyAdapter.notifyDataSetChanged();
        //接口回调
        mMyAdapter.setGoodsClicked(new MyAdapter.goodsClicked() {
            @Override//条目点击事件
            public void onItemClicked(int position) {
                int pid = dataBeans.get(position).getPid();
                Intent intent = new Intent(MainActivity.this, CarActivity.class);
                intent.putExtra("pid",pid);
                startActivity(intent);
            }

            @Override        //加入购物车点击事件
            public void onClick(int position) {
                mAddPresenter = new AddPresenter(MainActivity.this);
                mAddPresenter.datas(mAddCarBean,dataBeans.get(position).getPid());
            }
        });

    }
    //访问数据失败
    @Override
    public void onMyFailed(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    //添加购物车成功
    @Override
    public void onAddSuccesss(AddCarBean beans) {
        Toast.makeText(this, beans.getMsg(), Toast.LENGTH_SHORT).show();
    }

    //添加购物车失败
    @Override
    public void onAddFailed(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    /**
     * 防止内存泄露
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMyPresenter.datechView();
        mAddPresenter.datechView();
    }
}
