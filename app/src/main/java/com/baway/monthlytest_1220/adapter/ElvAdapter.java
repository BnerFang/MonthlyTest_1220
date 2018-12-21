package com.baway.monthlytest_1220.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.baway.monthlytest_1220.R;
import com.baway.monthlytest_1220.activity.CarActivity;
import com.baway.monthlytest_1220.bean.GetCartBean;
import com.baway.monthlytest_1220.carmvp.PriceAndCount;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * 作者:  方诗康
 * 描述:
 */
public class ElvAdapter extends BaseExpandableListAdapter {
    private final LayoutInflater inflater;
    private Context context;
    private List<GetCartBean.DataBean> group;
    private List<List<GetCartBean.DataBean.ListBean>> child;


    public ElvAdapter(Context context, List<GetCartBean.DataBean> group, List<List<GetCartBean.DataBean.ListBean>> child) {
        this.context = context;
        this.group = group;
        this.child = child;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getGroupCount() {
        return group.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return child.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return group.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return child.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View view;
        final GroupViewHolder holder;
        if (convertView == null) {
            view = inflater.inflate(R.layout.elv_group, null);
            holder = new GroupViewHolder();
            holder.tv = view.findViewById(R.id.tvGroup);
            holder.cbGroup = view.findViewById(R.id.cbGroup);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (GroupViewHolder) view.getTag();
        }
        final GetCartBean.DataBean dataBean = group.get(groupPosition);
        holder.tv.setText(dataBean.getSellerName());
        holder.cbGroup.setChecked(dataBean.isChecked());

        holder.cbGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //需要改变三个checkbox的状态值
                //1.一级列表的checkbox状态值
                dataBean.setChecked(holder.cbGroup.isChecked());
                //2.二级列表的checkbox状态值
                setChildrenCb(groupPosition, holder.cbGroup.isChecked());
                //3.全选的checkbox状态值
                ((CarActivity) context).setAllChecked(isAllGroupCbChecked());
                //计算钱和数量并显示
                setPriceAndCount();
                //刷新界面
                notifyDataSetChanged();
            }
        });
        return view;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, final View convertView, ViewGroup parent) {
        View view;
        final ChildViewHolder holder;
        if (convertView == null) {
            view = inflater.inflate(R.layout.elv_child, null);
            holder = new ChildViewHolder();
            holder.iv = view.findViewById(R.id.iv);
            holder.tvTitle = view.findViewById(R.id.tvTitle);
            holder.tvSubhead = view.findViewById(R.id.tvSubhead);
            holder.tvPrice = view.findViewById(R.id.tvPrice);
            holder.cbChild = view.findViewById(R.id.cbChild);
            holder.btDel = view.findViewById(R.id.btDel);
            holder.tvNum = view.findViewById(R.id.tvNum);
            holder.ivDel = view.findViewById(R.id.ivDel);
            holder.ivAdd = view.findViewById(R.id.ivAdd);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ChildViewHolder) view.getTag();
        }

        final GetCartBean.DataBean.ListBean listBean = child.get(groupPosition).get(childPosition);
        String images = listBean.getImages();
        Glide.with(context).load(images.split("\\|")[0]).into(holder.iv);
        holder.tvTitle.setText(listBean.getTitle());
        holder.cbChild.setChecked(child.get(groupPosition).get(childPosition).isChecked());
        holder.tvSubhead.setText(listBean.getSubhead());
        holder.tvPrice.setText("￥" + listBean.getPrice());
        holder.tvNum.setText(listBean.getCount() + "");
        //给checkbox设置点击事件
        holder.cbChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //需要改变三个checkbox的状态值
                //1.二级列表的checkbox状态值
                listBean.setChecked(holder.cbChild.isChecked());
                //2.一级列表的checkbox状态值
                group.get(groupPosition).setChecked(isAllChildCbChecked(groupPosition));
                //3.全选的checkbox状态值
                ((CarActivity) context).setAllChecked(isAllGroupCbChecked());
                //计算钱和数量并显示
                setPriceAndCount();
                //刷新界面
                notifyDataSetChanged();
            }
        });

        holder.ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取目前显示的值
                int count = listBean.getCount();
                count++;
                //改变JavaBean里的状态值
                listBean.setCount(count);
                //计算钱和数量并显示
                setPriceAndCount();
                //刷新列表
                notifyDataSetChanged();
            }
        });
        holder.ivDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取目前显示的值
                int count = listBean.getCount();
                if (count <= 1) {
                    count = 1;
                } else {
                    count--;
                }
                //改变JavaBean里的状态值
                listBean.setCount(count);
                //计算钱和数量并显示
                setPriceAndCount();
                //刷新列表
                notifyDataSetChanged();
            }
        });
        holder.btDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //其实就是删除集合
                List<GetCartBean.DataBean.ListBean> listBeans = child.get(groupPosition);
                if (listBeans.size() > 0) {
                    listBeans.remove(childPosition);
                }
                if (listBeans.size() == 0) {
                    child.remove(groupPosition);
                    group.remove(groupPosition);
                }
                //计算钱和数量并显示
                setPriceAndCount();
                //改变全选状态
                ((CarActivity) context).setAllChecked(isAllGroupCbChecked());
                //刷新列表
                notifyDataSetChanged();
            }
        });
        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    class GroupViewHolder {
        TextView tv;
        CheckBox cbGroup;
    }

    class ChildViewHolder {
        ImageView iv;
        TextView tvTitle;
        TextView tvSubhead;
        TextView tvPrice;
        CheckBox cbChild;
        Button btDel;
        TextView tvNum;
        ImageView ivDel;
        ImageView ivAdd;
    }

    /**
     * 设置一级列表对应的二级列表checkbox状态
     *
     * @param groupPosition
     * @param bool
     */
    private void setChildrenCb(int groupPosition, boolean bool) {
        List<GetCartBean.DataBean.ListBean> listBeans = child.get(groupPosition);
        for (int i = 0; i < listBeans.size(); i++) {
            listBeans.get(i).setChecked(bool);
        }
    }

    /**
     * 判断一级列表checkbox状态
     *
     * @return
     */
    private boolean isAllGroupCbChecked() {
        if (group.size() == 0) {
            return false;
        }
        for (int i = 0; i < group.size(); i++) {
            if (!group.get(i).isChecked()) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断二级列表checkbox状态
     *
     * @return
     */
    private boolean isAllChildCbChecked(int groupPosition) {
        List<GetCartBean.DataBean.ListBean> listBeans = child.get(groupPosition);
        for (int i = 0; i < listBeans.size(); i++) {
            if (!listBeans.get(i).isChecked()) {
                return false;
            }
        }
        return true;
    }

    /**
     * 设置钱和数量
     */
    private void setPriceAndCount() {
        ((CarActivity) context).setPriceAndCount(compute());
    }

    /**
     * 计算钱和数量
     */
    private PriceAndCount compute() {
        double price = 0;
        int count = 0;
        for (int i = 0; i < group.size(); i++) {
            List<GetCartBean.DataBean.ListBean> listBeans = child.get(i);
            for (int j = 0; j < listBeans.size(); j++) {
                if (listBeans.get(j).isChecked()) {
                    price += listBeans.get(j).getPrice() * listBeans.get(j).getCount();
                    count += listBeans.get(j).getCount();
                }
            }
        }
        return new PriceAndCount(price, count);
    }

    /**
     * 全选或者全不选
     *
     * @param bool
     */
    public void AllOrNone(boolean bool) {
        for (int i = 0; i < group.size(); i++) {
            group.get(i).setChecked(bool);
            setChildrenCb(i, bool);
        }
        setPriceAndCount();
        notifyDataSetChanged();
    }
}
