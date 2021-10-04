package com.example.alarmclock.Fragment.CountDown

import android.content.*
import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.NumberPicker
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.alarmclock.R

import com.example.alarmclock.Service.CountDownService
//import com.example.alarmclock.ViewModel.CountDownViewModel

class CountDownFragment : Fragment() {

    lateinit var btStart :Button
    lateinit var btStop :Button
    lateinit var npHour :NumberPicker
    lateinit var npMinute:NumberPicker
    lateinit var npSecond :NumberPicker
    lateinit var prefTime:SharedPreferences
    var checkRunning = false
    var time :Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        var v = inflater.inflate(R.layout.fragment_countdown, container, false)
        return v
    }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prefTime = view.context.getSharedPreferences("timeCountDown", MODE_PRIVATE)
        btStart = view.findViewById(R.id.bt_start)
        btStop = view.findViewById(R.id.bt_stop)
        npHour = view.findViewById(R.id.np_hour)
        npMinute = view.findViewById(R.id.np_minute)
        npSecond = view.findViewById<NumberPicker>(R.id.np_second)

        //format 2 digits numpicker
        val formatter : NumberPicker.Formatter = NumberPicker.Formatter { String.format("%02d",it) }
        npSecond.setFormatter (formatter)
        npHour.setFormatter (formatter)
        npMinute.setFormatter (formatter)
        settingNumPicker()

        btStart.setOnClickListener {
            if(checkRunning == false)
            {
                checkRunning = true
                btStart.text = "Pause"
                var intent = Intent(this.context,CountDownService::class.java)
                intent.putExtra("time",getTime())
                context?.startService(intent)
            }

            else if(checkRunning == true && btStart.text == "Pause")
            {
                checkRunning = false
                btStart.text="Continue"
                var intent = Intent(this.context,CountDownService::class.java)
                prefTime.edit().putLong("timePause",getTime())
                context?.stopService(intent)
            }
        }

        btStop.setOnClickListener {
            btStart.text = "Start"
            checkRunning=false
            var intent = Intent(this.context,CountDownService::class.java)
            context?.stopService(intent)
            updateUI(0)
        }
    }

    override fun onPause() {
        Log.e(this.javaClass.simpleName,"pause")
        super.onPause()
        prefTime.edit().putLong("timePause",getTime()).commit()
        Log.e(this.javaClass.simpleName,"pause"+getTime().toString())
        this.context?.let { LocalBroadcastManager.getInstance(it).unregisterReceiver(countDownReceiver) }
    }

    override fun onResume() {
        Log.e(this.javaClass.simpleName,"resume")
        super.onResume()
        val filter = IntentFilter(CountDownService.START_ACTION)
        this.context?.let { LocalBroadcastManager.getInstance(it).registerReceiver(countDownReceiver, filter) }
        var intent = Intent(this.context,CountDownService::class.java)
        updateUI(prefTime.getLong("timePause",0))
    }

    @JvmName("getTime1")
    private fun getTime(): Long
    {
        return (npHour.value*60*60+ npMinute.value*60+ npSecond.value).toLong()
    }

    private fun settingNumPicker() {
        npHour.maxValue = 23
        npHour.minValue=0
        npMinute.maxValue = 59
        npMinute.minValue=0
        npSecond.maxValue=59
        npSecond.minValue=0
    }

     fun updateUI(time: Long?) {
        Log.e(this.javaClass.simpleName,time.toString())
         if (time != null) {
             npHour.value = ((time/3600).toInt())
             npMinute.value = ((time - npHour.value*3600)/60).toInt()
             npSecond.value = ((time - npHour.value*3600 - npMinute.value*60).toInt())
             if(time<=0)
             {
                 btStart.text="Start"
                 checkRunning = false
             }
         }
    }


    private val countDownReceiver :BroadcastReceiver = object :BroadcastReceiver()
    {
        override fun onReceive(context: Context?, intent: Intent?) {
            var time = intent?.getLongExtra("time",0)
            when(intent?.action)
            {
                CountDownService.START_ACTION -> updateUI(time)
            }
        }
    }


}