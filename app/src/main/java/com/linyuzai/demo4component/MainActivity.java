package com.linyuzai.demo4component;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bettershine.landscape2.common.view.LoadingDialog;
import com.linyuzai.component.ui.view.HorizontalLoadingView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        LinearLayout linearLayout = new LinearLayout(this);
        setContentView(linearLayout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        HorizontalLoadingView horizontalLoadingView = new HorizontalLoadingView(this);
        linearLayout.addView(horizontalLoadingView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100));
        horizontalLoadingView.setBackgroundColor(Color.BLACK);
        horizontalLoadingView.setColor(Color.LTGRAY);
        horizontalLoadingView.setMax(10);
        horizontalLoadingView.autoIncrease(0);
        new LoadingDialog.Builder(this).setShowMessage(false).create().show();
    }
}
