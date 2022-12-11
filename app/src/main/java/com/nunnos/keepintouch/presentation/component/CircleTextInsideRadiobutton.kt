package com.nunnos.keepintouch.presentation.component

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.nunnos.keepintouch.R
import com.nunnos.keepintouch.databinding.ComponentCircleTextInsideRadiobuttonBinding

class CircleTextInsideRadiobutton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) :
    ConstraintLayout(context, attrs) {

    enum class Status(val value: Int) {
        SELECTED(0),
        UNSELECTED(1),
        DISABLED(2);

        companion object {
            fun fromInt(value: Int) = values().first { it.value == value }
        }
    }

    private var binding: ComponentCircleTextInsideRadiobuttonBinding? = null
    private var listener: CustomListener? = null
    var disabledDrawable: Drawable? = null
    var selectedDrawable: Drawable? = null
    var unselectedDrawable: Drawable? = null
    private var status: Status = Status.UNSELECTED

    init {
        bindView()
        setListener()
        parseAttributes(attrs)
    }

    private fun bindView() {
        binding = ComponentCircleTextInsideRadiobuttonBinding.inflate(
            LayoutInflater.from(context),
            this,
            true
        )
    }

    private fun setListener() {
        binding!!.circleTextInsideRadiobutton.setOnClickListener {
            listener?.onItemClick()
            when (status) {
                Status.UNSELECTED -> {
                    binding!!.circleTextInsideRadiobutton.background =
                        selectedDrawable
                    status = Status.SELECTED
                }
                Status.SELECTED -> {
                    binding!!.circleTextInsideRadiobutton.background =
                        unselectedDrawable
                    status = Status.UNSELECTED
                }
                Status.DISABLED -> {}//Do nothing
            }
        }
    }

    private fun parseAttributes(attrs: AttributeSet?) {
        if (attrs == null) {
            return
        }
        val attributes =
            context.obtainStyledAttributes(attrs, R.styleable.CircleTextInsideRadiobutton, 0, 0)
        val text = attributes.getText(R.styleable.CircleTextInsideRadiobutton_CTIR_text)
        disabledDrawable =
            attributes.getDrawable(R.styleable.CircleTextInsideRadiobutton_CTIR_drawable_disabled)
        selectedDrawable =
            attributes.getDrawable(R.styleable.CircleTextInsideRadiobutton_CTIR_drawable_selected)
        unselectedDrawable =
            attributes.getDrawable(R.styleable.CircleTextInsideRadiobutton_CTIR_drawable_unselected)
        status = Status.fromInt(
            attributes.getInt(
                R.styleable.CircleTextInsideRadiobutton_CTIR_status,
                0
            )
        )

        if (!TextUtils.isEmpty(text))
            binding?.circleTextInsideRadiobutton?.text = text
        else
            binding?.circleTextInsideRadiobutton?.text = ""

        setStatus(status)
    }

    fun setStatus(status: Status){
        this.status = status
        when (status) {
            Status.UNSELECTED -> binding!!.circleTextInsideRadiobutton.background =
                unselectedDrawable
            Status.SELECTED -> binding!!.circleTextInsideRadiobutton.background = selectedDrawable
            Status.DISABLED -> binding!!.circleTextInsideRadiobutton.background = disabledDrawable
        }
    }
    fun getStatus(): Status{
        return status
    }

    fun setListener(l: CustomListener?) {
        listener = l
    }

    /*TODO: adaptar a estilo kotlin*/
    fun setText(text: String?) {
        binding?.circleTextInsideRadiobutton?.text = text ?: ""
    }

    interface CustomListener {
        fun onItemClick()
    }
}