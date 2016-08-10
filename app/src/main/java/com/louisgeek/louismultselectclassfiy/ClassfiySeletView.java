package com.louisgeek.louismultselectclassfiy;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.louisgeek.louismultselectclassfiy.tool.SizeTool;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by louisgeek on 2016/8/9.
 */
public class ClassfiySeletView extends TextView implements View.OnClickListener{
    Context mContext;
    List<ClassfiyBean> mClassfiyBeanList;
    int parentPos;
    int childPos;

    public ClassfiySeletView(Context context) {
        this(context,null,0);
    }

    public ClassfiySeletView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ClassfiySeletView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext=context;
        initInnerData();
        initView();
    }
    private void initInnerData() {
        mClassfiyBeanList=new ArrayList<>();
        for (int i = 0; i <10 ; i++) {
            ClassfiyBean classfiyBean=new ClassfiyBean();
            classfiyBean.setID(i);
            classfiyBean.setBeanID(""+i);
            classfiyBean.setName("父分类"+i);
            classfiyBean.setSelected(false);

            List<ClassfiyBean.ChildClassfiyBean> cccs=new ArrayList<>();
            for (int j = 0; j < (int)(Math.random()*10+1); j++) {//Math.random():获取0~1随机数
                ClassfiyBean.ChildClassfiyBean ccc=new ClassfiyBean.ChildClassfiyBean();
                ccc.setID(j);
                ccc.setBeanID(""+j);
                ccc.setName("父分类"+i+"下面的子项"+j);
                ccc.setCount(""+(int)(Math.random()*20+1));//Math.random():获取0~1随机数
                cccs.add(ccc);
                classfiyBean.setSelected(false);
            }
            classfiyBean.setChildClassfiyBeanList(cccs);
            mClassfiyBeanList.add(classfiyBean);
        }
    }
    private void initView() {
        if (this.getText()==null||this.getText().equals("")||this.getText().equals("null")) {
            this.setText("请选择");
        }
        if (this.getPaddingTop()==0&&this.getPaddingBottom()==0&&this.getPaddingLeft()==0&&this.getPaddingRight()==0) {
            int paddingLeft_Right = SizeTool.dp2px(mContext, 8);
            int paddingTop_Bottom = SizeTool.dp2px(mContext, 5);
            this.setPadding(paddingLeft_Right, paddingTop_Bottom, paddingLeft_Right, paddingTop_Bottom);
        }
        this.setOnClickListener(this);
        this.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_keyboard_arrow_down_blue_grey_400_18dp,0);
        this.setBackgroundResource(R.drawable.shape_list);
        this.setSingleLine();
    }

    @Override
    public void onClick(final View v) {
       // bgShow();

        onContentViewChangeListener.onContentViewShow();

        ClassfiySeletPopupWindow myPopupwindow=new ClassfiySeletPopupWindow(mContext,mClassfiyBeanList);

      //  myPopupwindow.set

    //
        DisplayMetrics metric = new DisplayMetrics();
        ((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;     // 屏幕宽度（像素）
       // int height = metric.heightPixels;   // 屏幕高度（像素）

        myPopupwindow.setWidth(width);
        myPopupwindow.setOnItemSelectedListener(new ClassfiySeletPopupWindow.OnItemSelectedListener() {
            @Override
            public void onItemSelected(String key, String name) {
                ClassfiySeletView.this.setText(name);
                ClassfiySeletView.this.setTag(key);
            }
        });
        myPopupwindow.showAsDropDown(v);
        myPopupwindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
               // bgClear();

                onContentViewChangeListener.onContentViewDismiss();
            }
        });

    }

    // 设置背景颜色变暗
    void  bgShow(){
        WindowManager.LayoutParams lp = ((Activity) mContext).getWindow().getAttributes();
        lp.alpha = 0.5f; // 0.0-1.0
        ((Activity) mContext).getWindow().setAttributes(lp);
    }

    void  bgClear(){
        WindowManager.LayoutParams lp = ((Activity) mContext).getWindow()
                .getAttributes();
        lp.alpha = 1.0f; // 0.0-1.0
        ((Activity) mContext).getWindow().setAttributes(lp);
    }

    public  interface  OnContentViewChangeListener{
        void onContentViewShow();
        void onContentViewDismiss();
    }

    public void setOnContentViewChangeListener(OnContentViewChangeListener onContentViewChangeListener) {
        this.onContentViewChangeListener = onContentViewChangeListener;
    }

    private  OnContentViewChangeListener onContentViewChangeListener;


    /**
     *
     * @param key
     */
    public void setupClassfiyByKey(String key){

    }

    /**
     *
     * @param parentPos
     * @param childPos
     */
    public void setupClassfiyByPosition(int parentPos,int childPos){
        //childPos -1时代表 选中全部
        if (parentPos<0||childPos<-1){
            return;
        }
        //clear
        for (int i = 0; i < mClassfiyBeanList.size(); i++) {
            mClassfiyBeanList.get(i).setSelected(false);
            for (int j = 0; j <mClassfiyBeanList.get(i).getChildClassfiyBeanList().size() ; j++) {
                mClassfiyBeanList.get(i).getChildClassfiyBeanList().get(j).setSelected(false);
            }
        }
        //
         mClassfiyBeanList.get(parentPos).setSelected(true);
        if (childPos>=0) {
            mClassfiyBeanList.get(parentPos).getChildClassfiyBeanList().get(childPos).setSelected(true);
        }
    }

    /**
     *
     * @return
     */
    public String  getClassfiyKey(){
        String key="";
        if (this.getTag()!=null)
        {
            key=String.valueOf(this.getTag());
        }
        return  key;
    }
    public String  getSelectParentAndChildPosByKey(String key){
        String parentAndChildPosStr="";

        if (key==null||key.equals("")){
            return parentAndChildPosStr;
        }
        int parentPos=-1;
        int childPos=-1;
        String key_parent="";
        String key_child="";
        if (key.contains(ClassfiySeletPopupWindow.CUT_TAG)){
            String[] keys=key.split(ClassfiySeletPopupWindow.CUT_TAG);
            if (keys!=null&&keys.length>0){
                key_parent=keys[0];
                if (keys.length>1){
                    key_child=keys[1];
                }
            }
        }
        if (key_parent.equals("")||key_child.equals("")){
            return parentAndChildPosStr;
        }
        for (int i = 0; i < mClassfiyBeanList.size(); i++) {
            if (key_parent.equals(mClassfiyBeanList.get(i).getBeanID())){
                parentPos=i;
                break;
            }
        }
        List<ClassfiyBean.ChildClassfiyBean> cbccbs=mClassfiyBeanList.get(parentPos).getChildClassfiyBeanList();
        for (int j = 0; j < cbccbs.size(); j++) {
            if (key_child.equals(cbccbs.get(j).getBeanID())){
                childPos=j;
                break;
            }
        }
        parentAndChildPosStr=parentPos+"cut"+childPos;
        return  parentAndChildPosStr;
    }

}
