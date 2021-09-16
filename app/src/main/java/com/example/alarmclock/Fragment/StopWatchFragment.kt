package com.example.alarmclock.Fragment

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.alarmclock.R
import java.util.concurrent.TimeUnit

class StopWatchFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_stop_watch, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var tvStopWatch = view.findViewById<TextView>(R.id.tv_stopWatch)
        var bt_start = view.findViewById<Button>(R.id.bt_start)
        bt_start.setOnLongClickListener {
            var countDownTimer = object : CountDownTimer(1000*30,1000){
                override fun onTick(millisUntilFinished: Long) {
                    tvStopWatch.setText(""+millisUntilFinished/1000)
                }
                override fun onFinish() {
                    tvStopWatch.setText("Done")
                }
            }.start()
            true
        }




    }



}