package me.mfathy.airlinesbook.ui.search

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.*

/**
 * Created by Mohammed Fathy on 21/12/2018.
 * dev.mfathy@gmail.com
 *
 * A simple date picker to select flight date.
 */
class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

    lateinit var listener: DatePickerListener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current date as the default date in the picker
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        // Create a new instance of DatePickerDialog and return it
        return DatePickerDialog(activity, this, year, month, day)
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        listener.onDateSet(view, year, month.plus(1), day)
    }
}

interface DatePickerListener {
    fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int)
}