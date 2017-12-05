package com.linyuzai.component.ui.adapter

import android.app.Fragment
import android.app.FragmentManager
import android.app.FragmentTransaction
import android.os.Build
import android.os.Parcelable
import android.support.v4.view.PagerAdapter
import android.util.Log
import android.view.View
import android.view.ViewGroup

/**
 * Created by linyuzai on 2017/11/9.
 * @author linyuzai
 */
abstract class FragmentPagerAdapter2 constructor(fm: FragmentManager) : PagerAdapter() {
    private val TAG = "FragmentPagerAdapter2"
    private val DEBUG = false

    private var mFragmentManager: FragmentManager = fm
    private var mCurTransaction: FragmentTransaction? = null
    private var mCurrentPrimaryItem: Fragment? = null

    /**
     * Return the Fragment associated with a specified position.
     */
    abstract fun getItem(position: Int): Fragment

    override fun startUpdate(container: ViewGroup) {
        if (container.id == View.NO_ID) {
            throw IllegalStateException("ViewPager with adapter " + this
                    + " requires a view id")
        }
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        if (mCurTransaction == null) {
            mCurTransaction = mFragmentManager.beginTransaction()
        }

        val itemId = getItemId(position)

        // Do we already have this fragment?
        val name = makeFragmentName(container.id, itemId)
        var fragment: Fragment? = mFragmentManager.findFragmentByTag(name)
        if (fragment != null) {
            if (DEBUG) Log.v(TAG, "Attaching item #$itemId: f=$fragment")
            mCurTransaction!!.attach(fragment)
        } else {
            fragment = getItem(position)
            if (DEBUG) Log.v(TAG, "Adding item #$itemId: f=$fragment")
            mCurTransaction!!.add(container.id, fragment,
                    makeFragmentName(container.id, itemId))
        }
        if (fragment !== mCurrentPrimaryItem) {
            fragment.setMenuVisibility(false)
            fragment.userVisibleHint = false
        }

        return fragment
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        if (mCurTransaction == null) {
            mCurTransaction = mFragmentManager.beginTransaction()
        }
        if (DEBUG)
            Log.v(TAG, "Detaching item #" + getItemId(position) + ": f=" + `object`
                    + " v=" + (`object` as Fragment).view)
        mCurTransaction!!.detach(`object` as Fragment)
    }

    override fun setPrimaryItem(container: ViewGroup, position: Int, `object`: Any) {
        val fragment = `object` as? Fragment?
        if (fragment !== mCurrentPrimaryItem) {
            if (mCurrentPrimaryItem != null) {
                mCurrentPrimaryItem!!.setMenuVisibility(false)
                mCurrentPrimaryItem!!.userVisibleHint = false
            }
            if (fragment != null) {
                fragment.setMenuVisibility(true)
                fragment.userVisibleHint = true
            }
            mCurrentPrimaryItem = fragment
        }
    }

    override fun finishUpdate(container: ViewGroup) {
        if (mCurTransaction != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                mCurTransaction!!.commitNowAllowingStateLoss()
            } else {
                mCurTransaction!!.commitAllowingStateLoss()
            }
            mCurTransaction = null
        }
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean =
            (`object` as Fragment).view === view

    override fun saveState(): Parcelable? = null

    override fun restoreState(state: Parcelable?, loader: ClassLoader?) {}

    /**
     * Return a unique identifier for the item at the given position.
     *
     *
     * The default implementation returns the given position.
     * Subclasses should override this method if the positions of items can change.
     *
     * @param position Position within this adapter
     * @return Unique identifier for the item at position
     */
    fun getItemId(position: Int): Long = position.toLong()

    private fun makeFragmentName(viewId: Int, id: Long): String = "android:switcher:$viewId:$id"
}