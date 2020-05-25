package vn.sangdv.common.utils

import android.view.View

object PreventTwoClick {
    fun onClick(view: View) {
        view.isEnabled = false
        view.postDelayed({ view.isEnabled = true }, 500)
    }
}