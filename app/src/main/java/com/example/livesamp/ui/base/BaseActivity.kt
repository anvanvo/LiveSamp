package com.example.livesamp.ui.base

import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    /**
     * Toast shown for short period of time
     *
     * @param message - String
     */
    protected fun showToast(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    /**
     * Toast shown for short period of time
     *
     * @param message - int
     */
    protected fun showToast(message: Int) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    /**
     * Called to process touch screen events.
     *
     * @param event the touch screen event
     * @return boolean Return true if this event was consumed.
     */
    @SuppressWarnings("ComplexCondition")
    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        val v = currentFocus
        if (v is EditText) {
            val scoops = IntArray(2)
            v.getLocationOnScreen(scoops)
            val x = event.rawX + v.getLeft() - scoops[0]
            val y = event.rawY + v.getTop() - scoops[1]
            if (event.action == MotionEvent.ACTION_UP &&
                (x < v.getLeft() || x >= v.getRight() || y < v.getTop() || y > v.getBottom())
            ) {
                v.clearFocus()
                hideSoftKeyboard(v)
            }
        }
        return super.dispatchTouchEvent(event)
    }

    fun hideSoftKeyboard(view: View) {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
