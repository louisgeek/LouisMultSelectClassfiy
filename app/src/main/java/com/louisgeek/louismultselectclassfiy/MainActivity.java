package com.louisgeek.louismultselectclassfiy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
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
        //  id_csv.setupClassfiyBeanList(mClassfiyBeanList);
        //默认选择的key  -1代表选择父类的全部
        //  id_csv.setupClassfiyByKey("0_5tuc-1");//key1tuckey2

        id_csv.setOnContentViewChangeListener(new ClassfiySeletView.OnContentViewChangeListener() {
            @Override
            public void onContentViewShow() {
                Log.d(TAG, "onContentViewShow: ");
                bgChange();
            }

            @Override
            public void onContentViewDismiss() {
                Log.d(TAG, "onContentViewDismiss: ");
                bgClear();
            }
        });


    }

    void bgChange() {
        /*ObjectAnimator.ofFloat(idllClassfiySeletView,"alpha",0f,0.5f)
                .setDuration(300)
                .start();*/

        idllClassfiySeletView.setAlpha(0.5f);
    }

    void bgClear() {
       /* ObjectAnimator.ofFloat(idllClassfiySeletView,"alpha",0.5f,0f)
                .setDuration(300)
                .start();*/
        idllClassfiySeletView.setAlpha(1.0f);
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
