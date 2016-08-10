package com.louisgeek.louismultselectclassfiy;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by louisgeek on 2016/8/9.
 */
public class MyRecylerViewLeftAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

   // private Map<Integer,Boolean> selectedStateMap=new HashMap<>();
    private List<ClassfiyBean> mClassfiyBeanList;
  //  int mParentSelectedPos;
    public MyRecylerViewLeftAdapter(List<ClassfiyBean> classfiyBeanList) {
        mClassfiyBeanList = classfiyBeanList;
      //  mParentSelectedPos=parentSelectedPos;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.left_item,parent,false);
        return new MyRecyclerViewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        MyRecyclerViewViewHolder myRecyclerViewViewHolder= (MyRecyclerViewViewHolder) holder;
        myRecyclerViewViewHolder.mTextViewName.setText(mClassfiyBeanList.get(position).getName());
        myRecyclerViewViewHolder.mTextViewCount.setText(""+mClassfiyBeanList.get(position).getChildClassfiyBeanList().size());
        myRecyclerViewViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelectedState(position);
                onItemClickListener.onItemClick(v,position);
            }
        });
        myRecyclerViewViewHolder.itemView.setSelected(getSeletedState(position));


    }

    @Override
    public int getItemCount() {
        return mClassfiyBeanList.size();
    }

    public void clearAllSelectedState(){
        for (int i = 0; i < mClassfiyBeanList.size(); i++) {
            mClassfiyBeanList.get(i).setSelected(false);
        }
        this.notifyDataSetChanged();
    }
    private void setSelectedState(int position){
        clearAllSelectedState();
        mClassfiyBeanList.get(position).setSelected(true);
        //selectedStateMap.put(position,true);
        this.notifyDataSetChanged();
    }
    private  boolean  getSeletedState(int position){
      boolean isSelected=mClassfiyBeanList.get(position).isSelected();
      return isSelected;
  }

   private class MyRecyclerViewViewHolder extends RecyclerView.ViewHolder{

        TextView mTextViewName;
        TextView mTextViewCount;

        public MyRecyclerViewViewHolder(View itemView) {
            super(itemView);
            mTextViewName= (TextView) itemView.findViewById(R.id.id_tv_name);

            mTextViewCount= (TextView) itemView.findViewById(R.id.id_tv_count);
        }
    }

    public interface OnItemClickListener{
        void  onItemClick(View v,int pos);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public  OnItemClickListener onItemClickListener;
}
