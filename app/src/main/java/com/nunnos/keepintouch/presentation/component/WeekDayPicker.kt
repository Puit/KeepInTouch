package com.nunnos.keepintouch.presentation.component

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.nunnos.keepintouch.R
import com.nunnos.keepintouch.databinding.ComponentDayPickerBinding
import com.nunnos.keepintouch.presentation.component.WeekDayPicker.StartingDay.Companion.fromInt

class WeekDayPicker @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) :
    ConstraintLayout(context, attrs) {
    enum class StartingDay(val value: Int) {
        MONDAY(0),
        SUNDAY(1);

        companion object {
            fun fromInt(value: Int) = values().first { it.value == value }
        }
    }

    private var databinding: ComponentDayPickerBinding? = null
    var startingDay = StartingDay.MONDAY

    private lateinit var mondayCircle: CircleTextInsideRadiobutton
    private lateinit var tuesdayCircle: CircleTextInsideRadiobutton
    private lateinit var wednesdayCircle: CircleTextInsideRadiobutton
    private lateinit var thursdayCircle: CircleTextInsideRadiobutton
    private lateinit var fridayCircle: CircleTextInsideRadiobutton
    private lateinit var saturdayCircle: CircleTextInsideRadiobutton
    private lateinit var sundayCircle: CircleTextInsideRadiobutton

    private var selected: Drawable? = null
    private var unselected: Drawable? = null
    private var disabled: Drawable? = null

    private var selectedWeekend: Drawable? = null
    private var unselectedWeekend: Drawable? = null
    private var disabledWeekend: Drawable? = null

    init {
        bindView()
        parseAttributes(attrs)
    }

    private fun bindView() {
        databinding = ComponentDayPickerBinding.inflate(
            LayoutInflater.from(context),
            this,
            true
        )
    }

    private fun parseAttributes(attrs: AttributeSet?) {
        if (attrs == null) {
            return
        }
        val attributes =
            context.obtainStyledAttributes(attrs, R.styleable.WeekDayPicker, 0, 0)

        startingDay = fromInt(attributes.getInt(R.styleable.WeekDayPicker_WDP_starting_day, 0))

        initCircles()
        setTextToCircles(attributes)
        getDrawables(attributes)
        saveDrawables()
    }

    private fun initCircles() {
        if (startingDay == StartingDay.MONDAY) {
            mondayCircle = databinding!!.dayPicker1
            tuesdayCircle = databinding!!.dayPicker2
            wednesdayCircle = databinding!!.dayPicker3
            thursdayCircle = databinding!!.dayPicker4
            fridayCircle = databinding!!.dayPicker5
            saturdayCircle = databinding!!.dayPicker6
            sundayCircle = databinding!!.dayPicker7
        } else {
            sundayCircle = databinding!!.dayPicker1
            mondayCircle = databinding!!.dayPicker2
            tuesdayCircle = databinding!!.dayPicker3
            wednesdayCircle = databinding!!.dayPicker4
            thursdayCircle = databinding!!.dayPicker5
            fridayCircle = databinding!!.dayPicker6
            saturdayCircle = databinding!!.dayPicker7
        }
    }

    private fun setTextToCircles(attributes: TypedArray) {
        mondayCircle.setText(
            attributes.getString(R.styleable.WeekDayPicker_WDP_monday_text)
        )
        tuesdayCircle.setText(
            attributes.getString(R.styleable.WeekDayPicker_WDP_tuesday_text)
        )
        wednesdayCircle.setText(
            attributes.getString(R.styleable.WeekDayPicker_WDP_wednesday_text)
        )
        thursdayCircle.setText(
            attributes.getString(R.styleable.WeekDayPicker_WDP_thursday_text)
        )
        fridayCircle.setText(
            attributes.getString(R.styleable.WeekDayPicker_WDP_friday_text)
        )
        saturdayCircle.setText(
            attributes.getString(R.styleable.WeekDayPicker_WDP_saturday_text)
        )
        sundayCircle.setText(
            attributes.getString(R.styleable.WeekDayPicker_WDP_sunday_text)
        )
    }

    private fun getDrawables(attributes: TypedArray) {
        selected = attributes.getDrawable(R.styleable.WeekDayPicker_WDP_drawable_selected)
        unselected = attributes.getDrawable(R.styleable.WeekDayPicker_WDP_drawable_unselected)
        disabled = attributes.getDrawable(R.styleable.WeekDayPicker_WDP_drawable_disabled)

        selectedWeekend =
            attributes.getDrawable(R.styleable.WeekDayPicker_WDP_weekend_drawable_selected)
        unselectedWeekend =
            attributes.getDrawable(R.styleable.WeekDayPicker_WDP_weekend_drawable_unselected)
        disabledWeekend =
            attributes.getDrawable(R.styleable.WeekDayPicker_WDP_weekend_drawable_disabled)
    }

    private fun setDrawables(
        circle: CircleTextInsideRadiobutton,
        selected: Drawable?,
        unselected: Drawable?,
        disabled: Drawable?
    ) {
        circle.selectedDrawable = selected
        circle.unselectedDrawable = unselected
        circle.disabledDrawable = disabled
    }

    private fun saveDrawables() {
        setDrawables(mondayCircle, selected, unselected, disabled)
        setDrawables(tuesdayCircle, selected, unselected, disabled)
        setDrawables(wednesdayCircle, selected, unselected, disabled)
        setDrawables(thursdayCircle, selected, unselected, disabled)
        setDrawables(fridayCircle, selected, unselected, disabled)
        setDrawables(
            saturdayCircle,
            selectedWeekend ?: selected,
            unselectedWeekend ?: unselected,
            disabledWeekend ?: disabled
        )
        setDrawables(
            sundayCircle,
            selectedWeekend ?: selected,
            unselectedWeekend ?: unselected,
            disabledWeekend ?: disabled
        )
    }

    fun getStatusWeekStartingOnMonday(): List<CircleTextInsideRadiobutton.Status> {
        return listOf(
            mondayCircle.getStatus(),
            tuesdayCircle.getStatus(),
            wednesdayCircle.getStatus(),
            thursdayCircle.getStatus(),
            fridayCircle.getStatus(),
            saturdayCircle.getStatus(),
            sundayCircle.getStatus()
        )
    }

    fun setStatusWeekStartingOnMonday(week: List<CircleTextInsideRadiobutton.Status>) {
        mondayCircle.setStatus(week[0])
        tuesdayCircle.setStatus(week[1])
        wednesdayCircle.setStatus(week[2])
        thursdayCircle.setStatus(week[3])
        fridayCircle.setStatus(week[4])
        saturdayCircle.setStatus(week[5])
        sundayCircle.setStatus(week[6])
    }

    fun getStatusWeekStartingOnSunday(): List<CircleTextInsideRadiobutton.Status> {
        return listOf(
            sundayCircle.getStatus(),
            mondayCircle.getStatus(),
            tuesdayCircle.getStatus(),
            wednesdayCircle.getStatus(),
            thursdayCircle.getStatus(),
            fridayCircle.getStatus(),
            saturdayCircle.getStatus()
        )
    }

    fun getMondayStatus(): CircleTextInsideRadiobutton.Status {
        return mondayCircle.getStatus()
    }

    fun getTuesdayStatus(): CircleTextInsideRadiobutton.Status {
        return tuesdayCircle.getStatus()
    }

    fun getWednesdayStatus(): CircleTextInsideRadiobutton.Status {
        return wednesdayCircle.getStatus()
    }

    fun getThursdayStatus(): CircleTextInsideRadiobutton.Status {
        return thursdayCircle.getStatus()
    }

    fun getFridayStatus(): CircleTextInsideRadiobutton.Status {
        return fridayCircle.getStatus()
    }

    fun getSaturdayStatus(): CircleTextInsideRadiobutton.Status {
        return saturdayCircle.getStatus()
    }

    fun getSundayStatus(): CircleTextInsideRadiobutton.Status {
        return sundayCircle.getStatus()
    }

    fun isAnyDaySelected(): Boolean {
        return mondayCircle.getStatus() == CircleTextInsideRadiobutton.Status.SELECTED ||
                tuesdayCircle.getStatus() == CircleTextInsideRadiobutton.Status.SELECTED ||
                wednesdayCircle.getStatus() == CircleTextInsideRadiobutton.Status.SELECTED ||
                thursdayCircle.getStatus() == CircleTextInsideRadiobutton.Status.SELECTED ||
                fridayCircle.getStatus() == CircleTextInsideRadiobutton.Status.SELECTED ||
                saturdayCircle.getStatus() == CircleTextInsideRadiobutton.Status.SELECTED ||
                sundayCircle.getStatus() == CircleTextInsideRadiobutton.Status.SELECTED
    }
}