package com.example.presensi

import android.icu.text.DecimalFormat
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import com.example.presensi.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val months = resources.getStringArray(R.array.months)

        with(binding) {
            var selectedTime ="${if (timePicker.hour % 12 ==0) 12 else timePicker.hour % 12}:" +
                    "${DecimalFormat("00").format(timePicker.minute)} " +
                    "${if (timePicker.hour >=12) "PM" else "AM"}"
            val theDate : Calendar = Calendar.getInstance()
            var selectedDate = "${theDate.get(Calendar.DAY_OF_MONTH)} ${months[theDate.get(Calendar.MONTH)]} ${theDate.get(Calendar.YEAR)}"

            datePicker.setOnDateChangeListener { _, year, month, dayOfMonth ->
                selectedDate = "$dayOfMonth ${months[month]} $year"
            }

            timePicker.setOnTimeChangedListener { _, hourOfDay, minute ->
                val hour = if (hourOfDay % 12 == 0) 12 else hourOfDay % 12
                val minute = DecimalFormat("00").format(minute)
                val ampm = if (hourOfDay >= 12) "PM" else "AM"
                selectedTime = "$hour:$minute $ampm"
            }

            status.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    if (position > 0) {
                        editKeterangan.visibility = View.VISIBLE
                    } else {
                        editKeterangan.visibility = View.GONE
                    }
                }
                override fun onNothingSelected(p0: AdapterView<*>?) {}
            }

            submitBtn.setOnClickListener {
                Toast.makeText(
                    this@MainActivity,
                    "Presensi berhasil $selectedDate jam $selectedTime",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}