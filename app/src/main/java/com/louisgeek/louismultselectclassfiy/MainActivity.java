package com.louisgeek.louismultselectclassfiy;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.louisgeek.louismultselectclassfiy.tool.MySSQTool;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    LinearLayout idllClassfiySeletView;
    private static final String TAG = "MainActivity";
    List<ClassfiyBean> mClassfiyBeanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        idllClassfiySeletView = (LinearLayout) findViewById(R.id.id_ll_ClassfiySeletView);

        initData();
        ClassfiySeletView id_csv = (ClassfiySeletView) findViewById(R.id.id_csv);
        List<ClassfiyBean> classfiyBeanList=new ArrayList<>();
        for (int i = 0; i <15 ; i++) {
            ClassfiyBean classfiyBean=new ClassfiyBean();
            classfiyBean.setID(i);
            classfiyBean.setBeanID("key_"+i);
            classfiyBean.setName("选项"+i);
            classfiyBean.setChildClassfiyBeanList(null);
            classfiyBeanList.add(classfiyBean);
        }

        //
        DisplayMetrics dm = getResources().getDisplayMetrics();
        //float density1 = dm.density;
        // int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;
        Log.d(TAG, "onCreate: screenHeight:" + screenHeight);
      /*  方法二 DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;*/
        id_csv.setListMaxHeight(screenHeight / 2);

        //设置备选数据
        //### id_csv.setupClassfiyBeanList(mClassfiyBeanList);
        //默认选择的key  -1代表选择父类的全部
       //### id_csv.setupClassfiyByKey("0_5tuc-1");//key1tuckey2

        /**
         * 设置备选数据  单列表
         */
       //### id_csv.setupClassfiyBeanList(classfiyBeanList);
       //###  id_csv.setupClassfiyByKey("key_3");

        id_csv.setOnContentViewChangeListener(new ClassfiySeletView.OnContentViewChangeListener() {
            @Override
            public void onContentViewShow() {
                Log.d(TAG, "onContentViewShow: ");
                changeBgColor(idllClassfiySeletView,false);
            }

            @Override
            public void onContentViewDismiss() {
                Log.d(TAG, "onContentViewDismiss: ");
               changeBgColor(idllClassfiySeletView,true);
            }
        });


    }

    private void changeBgColor(View view,boolean isBack) {
        int defaultColor_start=0xff818080;
        int defaultColor_end=0xffffffff;
        int startColor =isBack?defaultColor_start:defaultColor_end;//0xffff0000
        int endColor = isBack?defaultColor_end:defaultColor_start;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ObjectAnimator anim= ObjectAnimator.ofArgb(view,"backgroundColor",startColor,endColor);
            anim.setDuration(300);
            anim.start();
        }else{
            view.setBackgroundColor(endColor);
        }
    }

    void bgClear() {
       /* ObjectAnimator.ofFloat(idllClassfiySeletView,"alpha",0.5f,0f)
                .setDuration(300)
                .start();*/
        idllClassfiySeletView.setAlpha(1.0f);
    }
    /**
     * ObjectAnimator实现view旋转Rotation属性动画
     * @param view
     */
    private void doViewRotationAnim(final View view) {
        ObjectAnimator anim=ObjectAnimator.ofFloat(view,"rotation",view.getRotation(),view.getRotation()+180);
        anim.setDuration(500);
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                //动画启动后不监听,防止位置由于动画未完毕形成的错乱
                view.setClickable(false);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.setClickable(true);
            }
        });
        anim.start();
    }
    private void initData() {
        String pro_cate_json = MySSQTool.getStringFromRaw(this, R.raw.pro_cate);
        ProCate proCate = JSON.parseObject(pro_cate_json, ProCate.class);
        List<ProCate.CatesBean> cbList = proCate.getCates();


        mClassfiyBeanList = new ArrayList<>();
        for (int i = 0; i < cbList.size(); i++) {
            ProCate.CatesBean catesBean = cbList.get(i);
            ClassfiyBean classfiyBean = new ClassfiyBean();
            classfiyBean.setID(i);
            classfiyBean.setBeanID(catesBean.getCateid());
            classfiyBean.setName(catesBean.getCatename());
            classfiyBean.setSelected(false);
//
            List<ProCate.CatesBean.ChildrenBean> childrenBeanList = catesBean.getChildren();
            List<ClassfiyBean.ChildClassfiyBean> cccs = new ArrayList<>();
            for (int j = 0; j < childrenBeanList.size(); j++) {//Math.random():获取0~1随机数
                ProCate.CatesBean.ChildrenBean childrenBean = childrenBeanList.get(j);
                ClassfiyBean.ChildClassfiyBean ccc = new ClassfiyBean.ChildClassfiyBean();
                ccc.setID(j);
                ccc.setBeanID(childrenBean.getCateid());
                ccc.setName(childrenBean.getCatename());
                ccc.setCount("" + (int) (Math.random() * 20 + 1));//Math.random():获取0~1随机数
                cccs.add(ccc);
                classfiyBean.setSelected(false);
            }
            classfiyBean.setChildClassfiyBeanList(cccs);
            mClassfiyBeanList.add(classfiyBean);
        }
    }
}
