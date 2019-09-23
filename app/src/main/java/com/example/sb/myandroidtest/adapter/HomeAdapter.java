package com.example.sb.myandroidtest.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sb.myandroidtest.R;
import com.example.sb.myandroidtest.bean.TencentList;

import java.util.List;
import java.util.Timer;

/**
 * Created by shishaobin on 2019/8/27
 */
public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.mViewHolder>{
    private Context mcontext;
    private List<TencentList.DataBean> mList;
    private OnItemClickListener monItemClickListener;
    private int mposition;

    public interface OnItemClickListener{
        void onItemClick(View view,int position);
        void onItemLongClick(View view,int position);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.monItemClickListener = onItemClickListener;
    }

    public HomeAdapter(Context mcontext, List<TencentList.DataBean> mList) {
        this.mcontext = mcontext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public mViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new mViewHolder(LayoutInflater.from(mcontext).inflate(R.layout.item_homeadapter,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final mViewHolder mViewHolder, final int i) {
        mViewHolder.mtx_name.setText(mList.get(i).getName());
        mViewHolder.mtx_order.setText(mList.get(i).getOrder()+"");
        if(monItemClickListener != null){

            mViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mposition = mViewHolder.getLayoutPosition();//增加 删除后postion不变问题，传入页面上的postion
                    monItemClickListener.onItemClick(mViewHolder.itemView,mposition);
                }
            });

            mViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mposition = mViewHolder.getLayoutPosition();//增加 删除后postion不变问题，传入页面上的postion
                    monItemClickListener.onItemLongClick(mViewHolder.itemView,mposition);
                    return true;
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public class mViewHolder extends RecyclerView.ViewHolder{

        private final TextView mtx_name;
        private final TextView mtx_order;

        public mViewHolder(@NonNull View itemView) {
            super(itemView);
            mtx_name = itemView.findViewById(R.id.text_name);
            mtx_order = itemView.findViewById(R.id.text_order);
        }
    }
    public void addItem(int pos){
        TencentList.DataBean dataBean = new TencentList.DataBean();
        dataBean.setName("新增的名字");
        dataBean.setOrder(456);
        mList.add(pos,dataBean);
        notifyItemInserted(pos);
    }
    public void deleteItem(int pos){
        mList.remove(pos);
        notifyItemRemoved(pos);
    }
}
