package com.louisgeek.louismultselectclassfiy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;

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
         id_csv.setupClassfiyBeanList(mClassfiyBeanList);
        id_csv.setupClassfiyByKey("key_4tuckey_2");

        //id_csv.setupClassfiyByKey("");
       // id_csv.getSelectParentAndChildPosByKey()
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

    void  bgChange(){
        /*ObjectAnimator.ofFloat(idllClassfiySeletView,"alpha",0f,0.5f)
                .setDuration(300)
                .start();*/

        idllClassfiySeletView.setAlpha(0.5f);
    }
    void  bgClear(){
       /* ObjectAnimator.ofFloat(idllClassfiySeletView,"alpha",0.5f,0f)
                .setDuration(300)
                .start();*/
        idllClassfiySeletView.setAlpha(1.0f);
    }

    private void initData() {
        mClassfiyBeanList=new ArrayList<>();
        for (int i = 0; i <10 ; i++) {
            ClassfiyBean classfiyBean=new ClassfiyBean();
            classfiyBean.setID(i);
            classfiyBean.setBeanID("key_"+i);
            classfiyBean.setName("蔬菜"+i);
            classfiyBean.setSelected(false);

            List<ClassfiyBean.ChildClassfiyBean> cccs=new ArrayList<>();
            for (int j = 0; j < (int)(Math.random()*10+3); j++) {//Math.random():获取0~1随机数
                ClassfiyBean.ChildClassfiyBean ccc=new ClassfiyBean.ChildClassfiyBean();
                ccc.setID(j);
                ccc.setBeanID("key_"+j);
                ccc.setName("蔬菜"+i+"下面的菜"+j);
                ccc.setCount(""+(int)(Math.random()*20+1));//Math.random():获取0~1随机数
                cccs.add(ccc);
                classfiyBean.setSelected(false);
            }
            classfiyBean.setChildClassfiyBeanList(cccs);
            mClassfiyBeanList.add(classfiyBean);
        }
    }
}
