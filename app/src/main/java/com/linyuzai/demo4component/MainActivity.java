package com.linyuzai.demo4component;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bettershine.landscape2.common.view.LoadingDialog;
import com.linyuzai.component.ui.view.HorizontalLoadingView;
import com.linyuzai.component.ui.view.menu.MenuView;

public class MainActivity extends AppCompatActivity {

    MenuView menuView;

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
        //new LoadingDialog.Builder(this).setShowMessage(false).create().show();
        menuView = MenuView.Builder()
                .setTitle("popup window")
                .setMenuExtraHeight(35)
                .setAllMenuBackground(Color.WHITE)
                .addMenu("menu1", new MenuView.OnMenuItemClickListener() {
                    @Override
                    public void onMenuItemClick() {

                    }
                })
                .addMenu("menu2", new MenuView.OnMenuItemClickListener() {
                    @Override
                    public void onMenuItemClick() {

                    }
                })
                .addMenu("menu3", new MenuView.OnMenuItemClickListener() {
                    @Override
                    public void onMenuItemClick() {

                    }
                }).create(this);
        horizontalLoadingView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuView.showFromBottom(v);
            }
        });
    }
}
