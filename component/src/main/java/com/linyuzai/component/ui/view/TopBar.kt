package com.linyuzai.component.ui.view

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.linyuzai.kotlinextension.centerInside
import com.linyuzai.kotlinextension.gone
import com.linyuzai.kotlinextension.visible
import org.jetbrains.anko.*

/**
 * Created by linyuzai on 2017/10/9.
 * @author linyuzai
 */
class TopBar private constructor(context: Context) : RelativeLayout(context) {

    private var currentIndex: Int = 0
    private lateinit var titleText: TextView
    private lateinit var leftImage: ImageView
    private lateinit var rightImage: ImageView
    private var items: Array<out Item>? = null

    class Item {
        internal var title: String? = null
        internal var leftIcon: Drawable? = null
        internal var rightIcon: Drawable? = null
        internal var translationZ: Int = 0
        internal var leftClick: ((view: View) -> Unit)? = null
        internal var centerClick: ((view: View) -> Unit)? = null
        internal var rightClick: ((view: View) -> Unit)? = null
        fun title(title: String): Item = apply { this.title = title }
        fun icon(left: Drawable? = null, right: Drawable? = null) = apply { this.leftIcon = left;this.rightIcon = right }
        fun click(leftClick: ((view: View) -> Unit)? = null, centerClick: ((view: View) -> Unit)? = null,
                  rightClick: ((view: View) -> Unit)? = null): Item = apply {
            this.leftClick = leftClick
            this.centerClick = centerClick
            this.rightClick = rightClick
        }

        fun translationZ(z: Int = 0): Item = apply { this.translationZ = z }
    }

    class Builder {
        private var context: Context? = null
        private var id: Int? = null
        private var items: Array<out Item>? = null
        private var height: Int = 0
        private var backgroundColor: Int = Color.TRANSPARENT

        fun with(context: Context): Builder = apply { this.context = context }
        fun id(id: Int): Builder = apply { this.id = id }
        fun items(vararg items: Item): Builder = apply { this.items = items }
        fun height(height: Int): Builder = apply { this.height = height }
        fun backgroundColor(color: Int): Builder = apply { this.backgroundColor = color }

        fun build(): TopBar = TopBar(context!!).apply {
            id = this@Builder.id!!
            backgroundColor = this@Builder.backgroundColor
            val titleText = textView {
                //text = items!![0].title
                textColor = Color.WHITE
                textSize = 18f
                gravity = Gravity.CENTER_VERTICAL
                layoutParams = LayoutParams(wrapContent, matchParent).apply { centerHorizontally() }
            }
            val leftImage = imageView {
                gone()
                centerInside()
                padding = (this@Builder.height * 0.3).toInt()
                layoutParams = LayoutParams(this@Builder.height, this@Builder.height).apply { alignParentLeft() }
                //if (items!![0].leftIcon == null) gone() else setImageDrawable(items!![0].leftIcon)
            }
            val rightImage = imageView {
                gone()
                centerInside()
                padding = (this@Builder.height * 0.3).toInt()
                layoutParams = LayoutParams(this@Builder.height, this@Builder.height).apply { alignParentRight() }
                //if (items!![0].leftIcon == null) gone() else setImageDrawable(items!![0].leftIcon)
            }
            layoutParams = ViewGroup.LayoutParams(matchParent, this@Builder.height)
            bind(titleText, leftImage, rightImage, this@Builder.items!!)
            //addView(titleText)
            //addView(leftImage)
            //addView(rightImage)
            switch(0)
        }
    }

    private fun bind(titleText: TextView, leftImage: ImageView, rightImage: ImageView, items: Array<out Item>) {
        this.titleText = titleText
        this.leftImage = leftImage
        this.rightImage = rightImage
        this.items = items
    }

    fun switch(index: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            translationZ = dip(items!![index].translationZ).toFloat()
        }
        titleText.text = items!![index].title
        if (items!![index].leftIcon == null)
            leftImage.gone()
        else {
            leftImage.image = items!![index].leftIcon
            //leftImage.setImageDrawable(items!![index].leftIcon)
            leftImage.visible()
        }
        if (items!![index].rightIcon == null)
            rightImage.gone()
        else {
            rightImage.image = items!![index].rightIcon
            //rightImage.setImageDrawable(items!![index].rightIcon)
            rightImage.visible()
        }
        leftImage.setOnClickListener {
            items!![index].leftClick?.invoke(leftImage)
        }
        rightImage.setOnClickListener {
            items!![index].rightClick?.invoke(rightImage)
        }
        titleText.setOnClickListener {
            items!![index].centerClick?.invoke(titleText)
        }
    }

    fun updateBackgroundColor(color: Int) {
        this.backgroundColor = color
    }
}