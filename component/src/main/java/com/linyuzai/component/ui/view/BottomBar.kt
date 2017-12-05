package com.linyuzai.component.ui.view

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.linyuzai.component.R
import com.linyuzai.kotlinextension.centerInside
import com.linyuzai.kotlinextension.gone
import com.linyuzai.kotlinextension.horizontal
import com.linyuzai.kotlinextension.visible
import com.linyuzai.mvp.theme.MicroThemeIcon
import org.jetbrains.anko.*

/**
 * Created by linyuzai on 2017/10/9.
 * @author linyuzai
 */
class BottomBar private constructor(context: Context) : LinearLayout(context) {
    private lateinit var currentItem: Item
    private lateinit var currentText: TextView
    private lateinit var currentImage: ImageView
    private lateinit var dataSource: Array<out Item>
    private val redTips: MutableList<TextView> = arrayListOf()

    class Item {
        var index: Int = -1
        internal var text: String? = null
        internal var iconDefault: Drawable? = null
        internal var iconSelected: Drawable? = null
        internal var colorDefault: Int = Color.BLACK
        internal var colorSelected: Int = Color.BLACK
        fun text(text: String): Item = apply {
            this.text = text
        }

        fun icon(select: Drawable? = null, default: Drawable? = null): Item = apply {
            this.iconSelected = select
            this.iconDefault = default
        }

        fun color(select: Int = Color.BLACK, default: Int = Color.BLACK): Item = apply {
            this.colorSelected = select;this.colorDefault = default
        }
    }

    class Builder {
        private var context: Context? = null
        private var id: Int? = null
        private var items: Array<out Item>? = null
        private var height: Int = 0
        private var backgroundColor: Int = Color.WHITE
        private var onClick: ((view: View, item: Item) -> Unit)? = null
        fun with(context: Context): Builder = apply { this.context = context }
        fun id(id: Int): Builder = apply { this.id = id }
        fun items(vararg items: Item): Builder = apply { this.items = items; }
        fun height(height: Int): Builder = apply { this.height = height }
        fun backgroundColor(color: Int): Builder = apply { this.backgroundColor = color }
        fun click(onClick: (view: View, item: Item) -> Unit): Builder = apply { this.onClick = onClick }
        fun build(): BottomBar = BottomBar(context!!).apply {
            dataSource = items!!
            id = this@Builder.id!!
            horizontal()
            topPadding = (this@Builder.height * 0.04).toInt()
            backgroundColor = this@Builder.backgroundColor
            for (index in items!!.indices) {
                val item = items!![index]
                if (index == 0)
                    currentItem = item
                item.index = index
                addView(getItem(items!![index], this@Builder.height, onClick))
            }
            layoutParams = RelativeLayout.LayoutParams(matchParent, this@Builder.height).apply { alignParentBottom() }
        }
    }

    private fun getItem(item: Item, height: Int, onClick: ((view: View, item: Item) -> Unit)?): LinearLayout = with(context!!) {
        linearLayout {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            val image = relativeLayout {
                imageView {
                    centerInside()
                    if (item.index == 0) {
                        image = item.iconSelected
                        //setImageDrawable(item.iconSelected)
                        currentImage = this
                    } else
                        setImageDrawable(item.iconDefault)
                }.lparams(width = (height * 0.5).toInt(), height = (height * 0.5).toInt()) {
                    centerInParent()
                }
                val redTip = textView {
                    textSize = 10f
                    textColor = Color.WHITE
                    gravity = Gravity.CENTER
                    backgroundResource = R.drawable.red_tip
                    gone()
                }.lparams(width = (height * 0.3).toInt(), height = (height * 0.3).toInt()) {
                    alignParentRight()
                }
                redTips.add(redTip)
            }.lparams(width = (height * 0.9).toInt(), height = (height * 0.5).toInt())
            val text = textView {
                gravity = Gravity.CENTER
                text = item.text
                textSize = 12f
                if (item.index == 0) {
                    textColor = item.colorSelected
                    currentText = this
                } else
                    textColor = item.colorDefault
            }.lparams(width = matchParent, height = (height * 0.4).toInt())
            setOnClickListener {
                if (currentItem.index != item.index) {
                    doClick(image.getChildAt(0) as ImageView, text, item)
                    onClick?.invoke(this, item)
                }
            }
            lparams(width = 0, height = height) {
                weight = 1f
            }
        }
    }

    private fun doClick(image: ImageView, text: TextView, item: Item) {
        currentImage.setImageDrawable(currentItem.iconDefault)
        currentText.textColor = currentItem.colorDefault
        image.setImageDrawable(item.iconSelected)
        text.textColor = item.colorSelected
        currentImage = image
        currentText = text
        currentItem = item
    }

    fun updateRedTip(index: Int, number: Int) {
        if (number == 0) {
            redTips[index].gone()
        } else {
            redTips[index].visible()
            redTips[index].text = number.toString()
        }
    }

    fun updateIcon(index: Int, icon: Drawable) {
        dataSource[index].iconSelected = icon
        if (currentItem.index == index)
            currentImage.setImageDrawable(icon)
    }

    fun updateTextColor(color: Int) {
        dataSource.forEach { it.colorSelected = color }
        currentText.textColor = color
    }

    fun updateIcons(icons: Array<MicroThemeIcon>) {
        for (index in icons.indices) {
            updateIcon(index, icons[index].select)
        }
    }
}