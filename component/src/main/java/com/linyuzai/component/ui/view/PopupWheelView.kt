package com.linyuzai.component.ui.view

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.*
import com.aigestudio.wheelpicker.IWheelPicker
import com.aigestudio.wheelpicker.WheelPicker
import com.linyuzai.component.R
import com.linyuzai.component.common.addZero
import com.linyuzai.component.common.isLeap
import org.jetbrains.anko.*

/**
 * Created by linyuzai on 2017/12/26.
 * @author linyuzai
 */
class PopupWheelView private constructor(layout: LinearLayout, builder: Builder, outSideNotDismiss: Boolean = false) :
        PopupWindow(layout, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, true) {

    private var wheelPicker: WheelPicker? = null
    private var secondWheelPicker: WheelPicker? = null

    private var showText: TextView? = null
    private var secondShowText: TextView? = null

    private var dayWheelPicker: WheelPicker? = null
    private var monthWheelPicker: WheelPicker? = null
    private var yearWheelPicker: WheelPicker? = null

    private var yearText: TextView? = null
    private var monthText: TextView? = null
    private var dayText: TextView? = null

    init {
        wheelPicker = builder.wheelPicker
        secondWheelPicker = builder.secondWheelPicker
        showText = builder.showText
        secondShowText = builder.secondShowText
        dayWheelPicker = builder.dayWheelPicker
        monthWheelPicker = builder.monthWheelPicker
        yearWheelPicker = builder.yearWheelPicker
        dayText = builder.dayText
        monthText = builder.monthText
        yearText = builder.yearText
        isOutsideTouchable = !outSideNotDismiss
        isFocusable = true
        setBackgroundDrawable(ColorDrawable(Color.WHITE))
        animationStyle = R.style.popwin_anim_style
    }

    /*private constructor(layout: LinearLayout, builder: Builder) :
            super(layout, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, true) {
        wheelPicker = builder.wheelPicker
        secondWheelPicker = builder.secondWheelPicker
        showText = builder.showText
        secondShowText = builder.secondShowText

        dayWheelPicker = builder.dayWheelPicker
        monthWheelPicker = builder.monthWheelPicker
        yearWheelPicker = builder.yearWheelPicker
        dayText = builder.dayText
        monthText = builder.monthText
        yearText = builder.yearText

        //setContentView(wheelPicker);
        isOutsideTouchable = true
        setBackgroundDrawable(ColorDrawable(Color.WHITE))
        animationStyle = R.style.popwin_anim_style
    }*/

    fun getCurrentItem(): String = wheelPicker!!.data[wheelPicker!!.currentItemPosition] as String

    fun getWheelView(): IWheelPicker? = wheelPicker

    fun updateData(data: List<String>) = updateData(data, 0)

    fun updateData(data: List<String>, initialPosition: Int) {
        wheelPicker?.data = data
        showText?.text = data[initialPosition]
        wheelPicker?.selectedItemPosition = initialPosition
    }

    fun setSelectedItemPosition(position: Int) {
        wheelPicker!!.selectedItemPosition = position
        showText?.text = wheelPicker!!.data[wheelPicker!!.currentItemPosition] as String
    }

    fun setSecondWheelSelectedItemPosition(position: Int) {
        secondWheelPicker!!.selectedItemPosition = position
        secondShowText?.text = secondWheelPicker!!.data[secondWheelPicker!!.currentItemPosition] as String
    }

    fun setSelectedYearPosition(position: Int) {
        yearWheelPicker!!.selectedItemPosition = position
        yearText?.text = yearWheelPicker!!.data[yearWheelPicker!!.currentItemPosition] as String
    }

    fun setSelectedMonthPosition(position: Int) {
        monthWheelPicker!!.selectedItemPosition = position
        monthText?.text = monthWheelPicker!!.data[monthWheelPicker!!.currentItemPosition] as String
    }

    fun setSelectedDayPosition(position: Int) {
        dayWheelPicker!!.selectedItemPosition = position
        dayText?.text = dayWheelPicker!!.data[dayWheelPicker!!.currentItemPosition] as String
    }

    class Builder {

        private val yearData: MutableList<List<List<String>>>

        private var schema: Schema = Schema.SIMPLE

        var wheelPicker: WheelPicker? = null
        var secondWheelPicker: WheelPicker? = null
        var thirdWheelPicker: WheelPicker? = null

        var showText: TextView? = null
        var secondShowText: TextView? = null
        var thirdShowText: TextView? = null

        //date
        var dayWheelPicker: WheelPicker? = null
        var monthWheelPicker: WheelPicker? = null
        var yearWheelPicker: WheelPicker? = null

        var yearText: TextView? = null
        var monthText: TextView? = null
        var dayText: TextView? = null

        var startYear: Int = 0
        var endYear: Int = 0
        //
        var backgroundColor: Int = Color.WHITE

        private var visibleItemCount: Int = 0

        private var isTitleEnable: Boolean = false
        private var isFuncButtonEnable: Boolean = false
        private var isCancelButtonEnable: Boolean = false
        private var isSecondWheelEnable: Boolean = false
        private var isThirdWheelEnable: Boolean = false

        private var title: String = ""
        private var titleColor: Int = 0
        private var titleSize: Int = 0
        private var titleHeight: Int = 0

        private var funcButtonText: String = ""
        private var funcButtonTextColor: Int = 0
        private var funcButtonTextSize: Int = 0
        private var funcButtonWidth: Int = 0

        private var cancelButtonText: String = ""
        private var cancelButtonTextColor: Int = 0
        private var cancelButtonTextSize: Int = 0
        private var cancelButtonWidth: Int = 0

        private var textSize: Int = 0
        private var itemSpace: Int = 0
        private var dividerHeight: Int = 0

        private var isSameWidth: Boolean = false
        private var outSideNotDismiss: Boolean = false

        private var dataSource: List<String>? = null
        private var secondDataSource: List<String>? = null

        private var initialPosition: Int = 0

        private var clickListener: OnFuncButtonClickListener? = null
        private var selectedListener: OnItemSelectedListener? = null
        private var secondSelectedListener: OnSecondWheelItemSelectedListener? = null
        private var thirdSelectedListener: OnThirdWheelItemSelectedListener? = null
        private var dateSelectedListener: OnDateSelectedListener? = null
        private var dateChangeListener: OnDateChangeListener? = null
        private var cancelButtonClickListener: OnCancelButtonClickListener? = null

        init {

            yearData = arrayListOf()

            startYear = 1970
            endYear = 1970

            visibleItemCount = 5

            title = TAG
            titleColor = Color.BLACK
            titleSize = 20
            titleHeight = 120

            funcButtonText = TAG
            funcButtonTextColor = Color.BLACK
            funcButtonTextSize = 10

            textSize = 50
            itemSpace = 100
            dividerHeight = 2
            dataSource = ArrayList()
        }

        fun getSchema(): Schema = schema

        fun setSchema(schema: Schema): Builder = apply { this.schema = schema }

        fun setYearRange(startYear: Int, endYear: Int): Builder = apply {
            this.startYear = startYear
            this.endYear = endYear
        }

        fun setInitialPosition(position: Int): Builder = apply { this.initialPosition = position }

        fun getInitialPosition(): Int = initialPosition

        fun getOnFuncButtonClickListener(): OnFuncButtonClickListener? = clickListener

        fun setOnFuncButtonClickListener(listener: OnFuncButtonClickListener): Builder = apply { clickListener = listener }

        fun setOnCancelButtonClickListener(CancelButtonClickListener: OnCancelButtonClickListener): Builder = apply {
            this.cancelButtonClickListener = CancelButtonClickListener
        }


        fun getOnItemSelectedListener(): OnItemSelectedListener? = selectedListener

        fun setOnItemSelectedListener(listener: OnItemSelectedListener): Builder = apply { selectedListener = listener }

        fun getOnSecondWheelItemSelectedListener(): OnSecondWheelItemSelectedListener? = secondSelectedListener

        fun setOnSecondWheelItemSelectedListener(listener: OnSecondWheelItemSelectedListener): Builder = apply {
            secondSelectedListener = listener
        }

        fun getOnThirdWheelItemSelectedListener(): OnThirdWheelItemSelectedListener? = thirdSelectedListener

        fun setOnThirdWheelItemSelectedListener(listener: OnThirdWheelItemSelectedListener): Builder = apply {
            thirdSelectedListener = listener
        }

        fun getOnDateSelectedListener(): OnDateSelectedListener? = dateSelectedListener

        fun setOnDateSelectedListener(listener: OnDateSelectedListener): Builder = apply { dateSelectedListener = listener }

        fun setOnDataChangeListener(listener: OnDateChangeListener): Builder = apply { this.dateChangeListener = listener }

        fun getVisibleItemCount(): Int = visibleItemCount

        fun setVisibleItemCount(visibleItemCount: Int): Builder = apply { this.visibleItemCount = visibleItemCount }

        fun isTitleEnable(): Boolean = isTitleEnable

        fun setTitleEnable(titleEnable: Boolean): Builder = apply { isTitleEnable = titleEnable }

        fun isFuncButtonEnable(): Boolean = isFuncButtonEnable

        fun setFuncButtonEnable(funcButtonEnable: Boolean): Builder = apply { isFuncButtonEnable = funcButtonEnable }

        fun setCancelButtonEnable(cancelButtonEnable: Boolean): Builder = apply {
            this.isCancelButtonEnable = cancelButtonEnable
        }

        fun isSecondWheelEnable(): Boolean = isSecondWheelEnable

        fun setSecondWheelEnable(secondWheelEnable: Boolean): Builder = apply { isSecondWheelEnable = secondWheelEnable }

        fun isThirdWheelEnable(): Boolean = isThirdWheelEnable

        fun setThirdWheelEnable(thirdWheelEnable: Boolean): Builder = apply { isThirdWheelEnable = thirdWheelEnable }

        fun getTitle(): String = title

        fun setTitle(title: String): Builder = apply { this.title = title }

        fun getTitleColor(): Int = titleColor

        fun setTitleColor(titleColor: Int): Builder = apply { this.titleColor = titleColor }

        fun getTitleSize(): Int = titleSize

        fun setTitleSize(titleSize: Int): Builder = apply { this.titleSize = titleSize }

        fun getTitleHeight(): Int = titleHeight

        fun setTitleHeight(titleHeight: Int): Builder = apply { this.titleHeight = titleHeight }

        fun getFuncButtonText(): String = funcButtonText

        fun setFuncButtonText(funcButtonText: String): Builder = apply { this.funcButtonText = funcButtonText }

        fun setFuncButtonWidth(funcButtonWidth: Int): Builder = apply { this.funcButtonWidth = funcButtonWidth }

        fun getFuncButtonTextColor(): Int = funcButtonTextColor

        fun setFuncButtonTextColor(funcButtonTextColor: Int): Builder = apply { this.funcButtonTextColor = funcButtonTextColor }

        fun getFuncButtonTextSize(): Int = funcButtonTextSize

        fun setFuncButtonTextSize(funcButtonTextSize: Int): Builder = apply { this.funcButtonTextSize = funcButtonTextSize }

        fun getCancelButtonText(): String = cancelButtonText

        fun setCancelButtonText(cancelButtonText: String): Builder = apply { this.cancelButtonText = cancelButtonText }

        fun setcancelButtonWidth(cancelButtonWidth: Int): Builder = apply { this.cancelButtonWidth = cancelButtonWidth }

        fun getCancelButtonTextColor(): Int = cancelButtonTextColor

        fun setCancelButtonTextColor(cancelButtonTextColor: Int): Builder = apply {
            this.cancelButtonTextColor = cancelButtonTextColor
        }

        fun getCancelButtonTextSize(): Int = cancelButtonTextSize

        fun setCancelButtonTextSize(cancelButtonTextSize: Int): Builder = apply {
            this.cancelButtonTextSize = cancelButtonTextSize
        }

        fun getTextSize(): Int = textSize

        fun setTextSize(textSize: Int): Builder = apply { this.textSize = textSize }

        fun getItemSpace(): Int = itemSpace

        fun setItemSpace(itemSpace: Int): Builder = apply { this.itemSpace = itemSpace }

        fun getDividerHeight(): Int = dividerHeight

        fun setDividerHeight(dividerHeight: Int): Builder = apply { this.dividerHeight = dividerHeight }

        fun isSameWidth(): Boolean = isSameWidth

        fun setSameWidth(sameWidth: Boolean): Builder = apply { isSameWidth = sameWidth }

        fun outSideNotDismiss(): Boolean = outSideNotDismiss

        fun setOutSideNotDismiss(outSideNotDismiss: Boolean): Builder = apply { this.outSideNotDismiss = outSideNotDismiss }

        fun getDataSource(): List<String>? = dataSource

        fun setDataSource(dataSource: List<String>): Builder = apply { this.dataSource = dataSource }

        fun getSecondDataSource(): List<String>? = secondDataSource

        fun setSecondDataSource(secondDataSource: List<String>): Builder = apply { this.secondDataSource = secondDataSource }

        fun bindShowView(textView: TextView): Builder = apply { showText = textView }

        fun bindSecondShowView(textView: TextView): Builder = apply { secondShowText = textView }

        fun bindDateShowView(yearText: TextView, monthText: TextView, dayText: TextView): Builder = apply {
            this.yearText = yearText
            this.monthText = monthText
            this.dayText = dayText
        }

        fun create(context: Context): PopupWheelView {
            val linearLayout = LinearLayout(context)
            linearLayout.orientation = LinearLayout.VERTICAL
            //divider
            val view = View(context)
            view.setBackgroundColor(Color.LTGRAY)
            linearLayout.addView(view, LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, context.dip(0.5f)))
            //title
            if (isTitleEnable) {
                val titleLayout = _RelativeLayout(context).apply {
                    val textView = getTitleView(context)
                    addView(textView)
                    if (isFuncButtonEnable) {
                        //button
                        val button = getFuncButton(context)
                        addView(button)
                    }
                    if (isCancelButtonEnable) {
                        val button = getCancelButton(context)
                        addView(button)
                    }
                    view {
                        backgroundColor = Color.LTGRAY
                    }.lparams(width = matchParent, height = dip(0.5f)) {
                        alignParentTop()
                    }
                    view {
                        backgroundColor = Color.LTGRAY
                    }.lparams(width = matchParent, height = dip(0.5f)) {
                        alignParentBottom()
                    }
                    lparams(width = matchParent, height = titleHeight)
                }
                //title bar
                linearLayout.addView(titleLayout)
            }
            //wheel layout
            val wheelLayout = LinearLayout(context)
            wheelLayout.orientation = LinearLayout.HORIZONTAL
            val wheelParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT)
            wheelParams.weight = 1f
            when (schema) {
                PopupWheelView.Schema.SIMPLE -> {
                    //first wheel
                    wheelPicker = getWheelView(context).apply {
                        data = dataSource!!
                        selectedItemPosition = initialPosition
                        setOnItemSelectedListener { _, data, position ->
                            showText?.text = data as String
                            selectedListener?.onItemSelected(position, data)
                        }
                    }
                    wheelLayout.addView(wheelPicker, wheelParams)
                    //second wheel
                    if (isSecondWheelEnable) {
                        secondWheelPicker = getWheelView(context)
                        secondWheelPicker!!.data = secondDataSource!!
                        secondWheelPicker!!.setOnItemSelectedListener { _, data, position ->
                            secondShowText?.text = data as String
                            secondSelectedListener?.onItemSelected(position, data)
                        }
                        wheelLayout.addView(secondWheelPicker, wheelParams)
                    }
                }
                PopupWheelView.Schema.DATE -> {
                    val years = arrayListOf<String>()
                    for (year in startYear..endYear) {
                        if (year.isLeap())
                            yearData.add(days2)
                        else
                            yearData.add(days1)
                        years.add(year.toString())
                    }
                    //year
                    yearWheelPicker = getWheelView(context)
                    //yearWheelPicker.setCyclic(true);
                    yearWheelPicker!!.data = years
                    yearWheelPicker!!.setOnItemSelectedListener { _, data, position ->
                        /*Log.e(TAG + ":year", position + "");
                        Log.e(TAG + ":Current", monthWheelPicker.getCurrentItemPosition() + "");
                        Log.e(TAG + ":Selected", monthWheelPicker.getSelectedItemPosition() + "");*/
                        dayWheelPicker!!.data = yearData[position][monthWheelPicker!!.currentItemPosition]
                        yearText?.text = data as String
                        dateSelectedListener?.onDateSelected(position,
                                monthWheelPicker!!.currentItemPosition,
                                dayWheelPicker!!.currentItemPosition)
                        dateChangeListener?.onDateChangeListener(data,
                                monthWheelPicker!!.data[monthWheelPicker!!.currentItemPosition] as String,
                                dayWheelPicker!!.data[dayWheelPicker!!.currentItemPosition] as String)
                    }
                    wheelLayout.addView(yearWheelPicker, wheelParams)
                    //month
                    monthWheelPicker = getWheelView(context)
                    monthWheelPicker!!.isCyclic = true
                    monthWheelPicker!!.data = months
                    monthWheelPicker!!.setOnItemSelectedListener { _, data, position ->
                        dayWheelPicker!!.data = yearData[yearWheelPicker!!.currentItemPosition][position]
                        monthText?.text = data as String
                        dateSelectedListener?.onDateSelected(yearWheelPicker!!.currentItemPosition,
                                position, dayWheelPicker!!.currentItemPosition)
                        dateChangeListener?.onDateChangeListener(yearWheelPicker!!.data[yearWheelPicker!!.currentItemPosition] as String,
                                data, dayWheelPicker!!.data[dayWheelPicker!!.currentItemPosition] as String)
                    }
                    wheelLayout.addView(monthWheelPicker, wheelParams)
                    //day
                    dayWheelPicker = getWheelView(context)
                    dayWheelPicker!!.isCyclic = true
                    dayWheelPicker!!.data = yearData[0][0]
                    dayWheelPicker!!.setOnItemSelectedListener { _, data, position ->
                        dayText?.text = data as String
                        dateSelectedListener?.onDateSelected(yearWheelPicker!!.currentItemPosition,
                                monthWheelPicker!!.currentItemPosition, position)
                        dateChangeListener?.onDateChangeListener(yearWheelPicker!!.data[yearWheelPicker!!.currentItemPosition] as String,
                                monthWheelPicker!!.data[monthWheelPicker!!.currentItemPosition] as String, data)
                    }
                    wheelLayout.addView(dayWheelPicker, wheelParams)
                }
            }
            linearLayout.addView(wheelLayout, LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT))
            return if (outSideNotDismiss)
                PopupWheelView(linearLayout, this, true)
            else
                PopupWheelView(linearLayout, this)
        }

        private fun getTitleView(context: Context): TextView {
            val textView = TextView(context)
            val textParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                    if (titleHeight != 0) titleHeight else RelativeLayout.LayoutParams.WRAP_CONTENT)
            textParams.addRule(RelativeLayout.CENTER_IN_PARENT)
            textView.layoutParams = textParams
            textView.gravity = Gravity.CENTER
            textView.text = title
            textView.textSize = titleSize.toFloat()
            textView.setTextColor(titleColor)
            return textView
        }

        private fun getCancelButton(context: Context): Button {
            val button = Button(context)
            val buttonParams = RelativeLayout.LayoutParams(
                    if (cancelButtonWidth != 0) cancelButtonWidth else RelativeLayout.LayoutParams.WRAP_CONTENT,
                    if (titleHeight != 0) titleHeight else RelativeLayout.LayoutParams.WRAP_CONTENT)
            buttonParams.addRule(RelativeLayout.LEFT_OF, R.id.button_id)
            button.layoutParams = buttonParams
            button.text = cancelButtonText
            button.setTextColor(cancelButtonTextColor)
            button.textSize = cancelButtonTextSize.toFloat()
            button.setBackgroundColor(Color.TRANSPARENT)
            button.setOnClickListener {
                cancelButtonClickListener?.onClick(button)
            }
            return button
        }

        private fun getFuncButton(context: Context): Button {
            val button = Button(context)
            val buttonParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                    if (titleHeight != 0) titleHeight else RelativeLayout.LayoutParams.WRAP_CONTENT)
            buttonParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
            buttonParams.addRule(RelativeLayout.CENTER_VERTICAL)
            buttonParams.rightMargin = 20
            button.layoutParams = buttonParams
            button.text = funcButtonText
            button.setTextColor(funcButtonTextColor)
            button.textSize = funcButtonTextSize.toFloat()
            button.setBackgroundColor(Color.TRANSPARENT)
            button.setOnClickListener {
                clickListener?.onClick(button)
            }
            button.id = R.id.button_id
            return button
        }

        private fun getWheelView(context: Context): WheelPicker {
            val wheelPicker = WheelPicker(context)
            wheelPicker.setAtmospheric(true)
            wheelPicker.isCurved = true
            wheelPicker.itemTextSize = textSize
            wheelPicker.itemSpace = itemSpace
            wheelPicker.setSameWidth(isSameWidth)
            wheelPicker.setIndicator(true)
            wheelPicker.indicatorColor = Color.parseColor("#BBBBBB")
            wheelPicker.indicatorSize = dividerHeight
            wheelPicker.selectedItemTextColor = Color.BLACK
            wheelPicker.visibleItemCount = visibleItemCount
            wheelPicker.itemAlign = WheelPicker.ALIGN_CENTER
            return wheelPicker
        }
    }

    fun show(v: View) {
        showAtLocation(v, Gravity.BOTTOM, 0, 0)
    }

    enum class Schema {
        SIMPLE, DATE
    }

    @FunctionalInterface
    interface OnFuncButtonClickListener {
        fun onClick(func: Button)
    }

    @FunctionalInterface
    interface OnCancelButtonClickListener {
        fun onClick(func: Button)
    }

    @FunctionalInterface
    interface OnItemSelectedListener {
        fun onItemSelected(position: Int, content: String)
    }

    @FunctionalInterface
    interface OnSecondWheelItemSelectedListener {
        fun onItemSelected(position: Int, content: String)
    }

    @FunctionalInterface
    interface OnThirdWheelItemSelectedListener {
        fun onItemSelected(position: Int, content: String)
    }

    @FunctionalInterface
    interface OnDateSelectedListener {
        fun onDateSelected(yearPosition: Int, monthPosition: Int, dayPosition: Int)
    }

    @FunctionalInterface
    interface OnDateChangeListener {
        fun onDateChangeListener(year: String, month: String, day: String)
    }

    companion object {
        val TAG = PopupWheelView::class.java.simpleName

        private var days28: MutableList<String> = arrayListOf()
        private var days29: MutableList<String> = arrayListOf()
        private var days30: MutableList<String> = arrayListOf()
        private var days31: MutableList<String> = arrayListOf()
        private var months: MutableList<String> = arrayListOf()

        private var days1: MutableList<List<String>> = arrayListOf()
        private var days2: MutableList<List<String>> = arrayListOf()

        init {

            for (day28 in 1..28)
                days28.add(day28.addZero())
            for (day29 in 1..29)
                days29.add(day29.addZero())
            for (day30 in 1..30)
                days30.add(day30.addZero())
            for (day31 in 1..31)
                days31.add(day31.addZero())
            for (month in 1..12) {
                months.add(month.addZero())
                if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
                    days1.add(days31)
                    days2.add(days31)
                } else if (month == 4 || month == 6 || month == 9 || month == 11) {
                    days1.add(days30)
                    days2.add(days30)
                } else if (month == 2) {
                    days1.add(days28)
                    days2.add(days29)
                }
            }
        }
    }
}
