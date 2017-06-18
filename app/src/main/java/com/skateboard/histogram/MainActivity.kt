package com.skateboard.histogram

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.skateboard.histogram.widget.Histogram

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val histogram=findViewById(R.id.histogram) as Histogram
        histogram.setDatas(floatArrayOf(1f,2f,3f,4f,5f,6f,7f,8f))
    }
}
