package com.example.alarmclock.Fragment

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.get
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.alarmclock.R
import com.example.alarmclock.ViewModel.CountDownViewModel
import java.sql.Time
import java.util.*

class CountDownFragment : Fragment() {

    lateinit var btStart :Button
    lateinit var btStop :Button
    lateinit var npHour :NumberPicker
    lateinit var npMinute:NumberPicker
    lateinit var npSecond :NumberPicker
    var checkRunning = false

    lateinit var timerTask:TimerTask
    var timer:Timer = Timer()
    var time :Int = 0

    lateinit var countDownViewModel: CountDownViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_countdown, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btStart = view.findViewById<Button>(R.id.bt_start)
        btStop = view.findViewById<Button>(R.id.bt_stop)
        npHour = view.findViewById<NumberPicker>(R.id.np_hour)
        npMinute = view.findViewById<NumberPicker>(R.id.np_minute)
        npSecond = view.findViewById<NumberPicker>(R.id.np_second)
        settingNumPicker()

        countDownViewModel = ViewModelProvider(this).get(CountDownViewModel::class.java)

        countDownViewModel.liveDataTime.observe(viewLifecycleOwner, androidx.lifecycle.Observer { updateUI(it) })
        countDownViewModel.liveDataTimeoutCheck.observe(viewLifecycleOwner, androidx.lifecycle.Observer { cancelCountDown(it) })

        btStart.setOnClickListener {
            if(checkRunning ==false)
            {
                checkRunning=true
                btStart.text="Pause"
                time = (npHour.value*3600+ npMinute.value*60+npSecond.value)*1000
                countDownViewModel.time = time.toLong()
                countDownViewModel.liveDataTime.value = countDownViewModel.time
                countDownViewModel.startCountDown()
            }
            else if(checkRunning ==true)
            {
                checkRunning =false
                btStart.text="Start"
                countDownViewModel.pauseCountDown()
            }

        }

        btStop.setOnClickListener {
            countDownViewModel.cancelCountDown()
            checkRunning=false
        }

    }

    private fun cancelCountDown(it: Boolean?) {
        if(it==true) {
            countDownViewModel.cancelCountDown()
            Toast.makeText(this.context, "Done", Toast.LENGTH_SHORT).show()

        }
    }

    private fun updateUI(it: Long?) {
        time = it?.toInt()!!/1000
        npHour.value = time/3600
        npMinute.value = (time - npHour.value*3600)/60
        npSecond.value = (time - npHour.value*3600 - npMinute.value*60)
    }


    private fun settingNumPicker() {
        npHour.maxValue = 23
        npHour.minValue=0
        npMinute.maxValue = 59
        npMinute.minValue=0
        npSecond.maxValue=59
        npSecond.minValue=0
    }


}