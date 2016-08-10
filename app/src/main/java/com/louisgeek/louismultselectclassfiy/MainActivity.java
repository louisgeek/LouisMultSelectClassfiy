package com.louisgeek.louismultselectclassfiy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    LinearLayout idllClassfiySeletView;
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         idllClassfiySeletView = (LinearLayout) findViewById(R.id.id_ll_ClassfiySeletView);
        ClassfiySeletView id_csv = (ClassfiySeletView) findViewById(R.id.id_csv);
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
       // initData();


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
}
