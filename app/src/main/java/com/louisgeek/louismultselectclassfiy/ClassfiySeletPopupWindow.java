package com.louisgeek.louismultselectclassfiy;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.List;

/**
 * Created by louisgeek on 2016/8/9.
 */
public class ClassfiySeletPopupWindow extends PopupWindow{

    private static final String TAG = "ClassfiySeletPopWin";
    public static final String CUT_TAG = "tuc";
    Context mContext;
    List<ClassfiyBean> mClassfiyBeanList;
    //List<ClassfiyBean.ChildClassfiyBean> mChildClassfiyBeanList;

    MyRecylerViewLeftAdapter myRecylerViewAdapter;
    MyRecylerViewRightAdapter myRecylerViewRightAdapter;

    public ClassfiySeletPopupWindow(Context context,List<ClassfiyBean> classfiyBeanList) {
        super(context);
        mContext=context;
        mClassfiyBeanList=classfiyBeanList;
        initView();
    }

    private void initView() {


        View view= LayoutInflater.from(mContext).inflate(R.layout.layout_popupwindow_selectview,null);
        RecyclerView id_rv_left = (RecyclerView) view.findViewById(R.id.id_rv_left);
        RecyclerView id_rv_right = (RecyclerView) view.findViewById(R.id.id_rv_right);
        id_rv_left.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext).build());
        id_rv_right.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext).build());

        myRecylerViewAdapter=new MyRecylerViewLeftAdapter(mClassfiyBeanList);

        myRecylerViewAdapter.setOnItemClickListener(new MyRecylerViewLeftAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v,int pos) {
                myRecylerViewRightAdapter.updateParentPos(pos);
                myRecylerViewRightAdapter.clearTheAllAndNormalSelectedState();
                Log.d(TAG, "QQQ onItemClick: pp pos:"+pos);
            }
        });
        id_rv_left.setAdapter(myRecylerViewAdapter);
        id_rv_left.setLayoutManager(new LinearLayoutManager(mContext));

        myRecylerViewRightAdapter=new MyRecylerViewRightAdapter(mClassfiyBeanList,0);
        //  mChildClassfiyBeanList=new ArrayList<>(mClassfiyBeanList.get(0).getChildClassfiyBeanList());
        myRecylerViewRightAdapter.setOnItemClickListener(new MyRecylerViewRightAdapter.OnItemClickListener() {

            @Override
            public void onItemClickNormal(View v, List<ClassfiyBean> classfiyBeanList, int parentPos, int childPos) {
                List<ClassfiyBean.ChildClassfiyBean>  childClassfiyBeanList=classfiyBeanList.get(parentPos).getChildClassfiyBeanList();
                //Toast.makeText(mContext, "xxx parentPos:"+parentPos+",child name:"+childClassfiyBeanList.get(childPos).getName(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "QQQ onItemClickNormal: parentPos:"+parentPos+",childPos:"+childPos);
                String key=classfiyBeanList.get(parentPos).getBeanID()+CUT_TAG+childClassfiyBeanList.get(childPos).getBeanID();
                onItemSelectedListener.onItemSelected(key,childClassfiyBeanList.get(childPos).getName());
                //ClassfiySeletPopupWindow.this.dismiss();

                Log.d(TAG, "fff onItemClickNormal: key:"+key);
            }


            @Override
            public void onItemClickAll(View v, List<ClassfiyBean> classfiyBeanList, int parentPos) {
                //Toast.makeText(mContext, "xxx parentPos:"+parentPos+",parent name:"+classfiyBeanList.get(parentPos).getName(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "QQQ onItemClickAll:  parentPos:"+parentPos);
                String key=classfiyBeanList.get(parentPos).getBeanID()+CUT_TAG+"-1";
                onItemSelectedListener.onItemSelected(key,"全部"+classfiyBeanList.get(parentPos).getName());
                //ClassfiySeletPopupWindow.this.dismiss();

                Log.d(TAG, "fff onItemClickAll: key:"+key);
            }
        });
        id_rv_right.setAdapter(myRecylerViewRightAdapter);
        id_rv_right.setLayoutManager(new LinearLayoutManager(mContext));


        //设置PopupWindow的View
        this.setContentView(view);
        //设置PopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置PopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setBackgroundDrawable(new ColorDrawable(0x00000000));//必须设置  ps:xml bg和这个不冲突
        this.setFocusable(true);//设置后  达到返回按钮先消失popupWindow
        //id_pop_tv.setOnClickListener(this);
    }






    public  interface  OnItemSelectedListener{
        void  onItemSelected(String key,String name);
    }

    public void setOnItemSelectedListener(OnItemSelectedListener onItemSelectedListener) {
        this.onItemSelectedListener = onItemSelectedListener;
    }

    OnItemSelectedListener onItemSelectedListener;
}
