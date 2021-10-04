package com.example.alarmclock.Fragment.CountUp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.NumberPicker
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.alarmclock.R
//import com.example.alarmclock.ViewModel.CountDownViewModel
import com.example.alarmclock.ViewModel.CountUpViewModel
import java.util.*

class CountUpFragment : Fragment() {

    lateinit var btStart :Button
    lateinit var btStop :Button
    lateinit var npHour :NumberPicker
    lateinit var npMinute:NumberPicker
    lateinit var npSecond :NumberPicker
    var checkRunning = false

    lateinit var timerTask:TimerTask
    var timer:Timer = Timer()
    var time :Int = 0

    lateinit var countUpViewModel: CountUpViewModel

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

        //format 2 digits numpicker
        val formatter : NumberPicker.Formatter = NumberPicker.Formatter { String.format("%02d",it) }
        npSecond.setFormatter (formatter)
        npHour.setFormatter (formatter)
        npMinute.setFormatter (formatter)
        settingNumPicker()


        countUpViewModel = ViewModelProvider(this).get(CountUpViewModel::class.java)

        countUpViewModel.liveDataTime.observe(viewLifecycleOwner, androidx.lifecycle.Observer { updateUI(it) })
        countUpViewModel.liveDataTimeoutCheck.observe(viewLifecycleOwner, androidx.lifecycle.Observer { cancelCountUp(it) })

        btStart.setOnClickListener {
            if(checkRunning ==false)
            {
                checkRunning=true
                btStart.text="Pause"
                time = (npHour.value*3600+ npMinute.value*60+npSecond.value)*1000
                countUpViewModel.time = time.toLong()
                countUpViewModel.liveDataTime.value = countUpViewModel.time
                countUpViewModel.startCountUp()
            }
            else if(checkRunning ==true)
            {
                checkRunning =false
                btStart.text="Start"
                countUpViewModel.pauseCountUp()
            }

        }

        btStop.setOnClickListener {
            countUpViewModel.cancelCountUp()
            checkRunning=false
        }

    }

    private fun cancelCountUp(it: Boolean?) {
        if(it==true) {
            countUpViewModel.cancelCountUp()
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