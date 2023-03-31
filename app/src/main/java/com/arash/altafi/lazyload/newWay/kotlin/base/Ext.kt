package com.arash.altafi.lazyload.newWay.kotlin.base

import android.content.Context
import android.text.Editable
import android.text.Spannable
import android.text.SpannableString
import android.text.TextWatcher
import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.ColorInt
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.ArrayList


inline fun <reified NEW> Any.cast(): NEW? {
    return if (this.isCastable<NEW>())
        this as NEW
    else null
}

inline fun <reified NEW> Any.isCastable(): Boolean {
    return this is NEW
}

fun TextView.highlightAll(
    target: String,
    @ColorInt textColor: Int
) {

    val raw: Spannable = SpannableString(this.text)

    //remove background spans
    val spansBackground = raw.getSpans(
        0,
        raw.length,
        BackgroundColorSpan::class.java
    )
    for (span in spansBackground) {
        raw.removeSpan(span)
    }

    //remove foreground spans
    val spansForeground = raw.getSpans(
        0,
        raw.length,
        ForegroundColorSpan::class.java
    )
    for (span in spansForeground) {
        raw.removeSpan(span)
    }

    //set spans
    raw.toString().indexAll(target).forEach {
        raw.setSpan(
            ForegroundColorSpan(textColor),
            it,
            it + target.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }

    this.text = raw

}

fun String.indexAll(
    target: String
): List<Int> {

    if (this.length < 0)
        return emptyList()

    val result = ArrayList<Int>()
    var lastSeekIndex = -1
    while (true) {
        val indexTemp = this.indexOf(target, lastSeekIndex + 1, ignoreCase = true)
        if (indexTemp < 0)
            break
        result.add(indexTemp)
        lastSeekIndex = indexTemp
        if (indexTemp >= this.length)
            break
    }
    return result
}

fun View.showKeyboard() {
    this.requestFocus()
    try {
        val inputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
    } catch (e: java.lang.Exception) {}
}

fun View.hideKeyboard() {
    val inputMethodManager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
}

fun TextView.clear() {
    this.text = ""
}

fun EditText.onChange(
    scope: CoroutineScope,
    destinationFunction: (String) -> Unit,
): TextWatcher = afterTextChange(debounce(800L, scope, destinationFunction))

fun EditText?.afterTextChange(afterTextChanged: (String) -> Unit): TextWatcher {
    var beforeText = ""
    val watcher = object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            if (beforeText == editable.toString())
                return

            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            beforeText = s.toString()
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    }

    this?.addTextChangedListener(watcher)

    return watcher
}

fun <T> debounce(
    waitMs: Long = 300L,
    scope: CoroutineScope,
    destinationFunction: (T) -> Unit
): (T) -> Unit {
    var debounceJob: Job? = null
    return { param: T ->
        debounceJob?.cancel()
        debounceJob = scope.launch {
            delay(waitMs)
            destinationFunction(param)
        }
    }
}

fun <T> debounceCancelable(
    waitMs: Long = 300L,
    scope: CoroutineScope,
    destinationFunction: (T) -> Unit
): (T?) -> Unit {
    var debounceJob: Job? = null
    return { param: T? ->
        debounceJob?.cancel()
        if (param != null)
            debounceJob = scope.launch {
                delay(waitMs)
                destinationFunction(param)
            }
    }
}


fun View.toShow() {
    this.visibility = View.VISIBLE
}

fun View.isShow(): Boolean {
    return this.visibility == View.VISIBLE
}

fun View.toHide() {
    this.visibility = View.INVISIBLE
}

fun View.isHide(): Boolean {
    return this.visibility == View.INVISIBLE
}

fun View.toGone() {
    this.visibility = View.GONE
}

fun View.isGone(): Boolean {
    return this.visibility == View.GONE
}