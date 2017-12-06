package com.linyuzai.component.ui.listener

import android.support.v4.widget.DrawerLayout
import android.view.View

/**
 * Created by linyuzai on 2017/12/5.
 * @author linyuzai
 */
abstract class AbsDrawerListener : DrawerLayout.DrawerListener {
    override fun onDrawerStateChanged(newState: Int) {}

    override fun onDrawerSlide(drawerView: View?, slideOffset: Float) {}

    override fun onDrawerClosed(drawerView: View?) {}

    override fun onDrawerOpened(drawerView: View?) {}
}