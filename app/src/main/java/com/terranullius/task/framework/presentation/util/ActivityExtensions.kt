package com.terranullius.task.framework.presentation.util

import android.widget.Toast
import com.terranullius.task.framework.presentation.MainActivity

fun MainActivity.showToast(msg: String){
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}