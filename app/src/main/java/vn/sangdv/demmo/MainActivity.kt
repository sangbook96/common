package vn.sangdv.demmo

import android.os.Bundle
import vn.sangdv.common.BaseActivity


class MainActivity : BaseActivity() {
    override fun getLayoutID(): Int {
        return R.layout.activity_main
    }

    override fun onCreateActivity(savedInstanceState: Bundle?) {
        if (isOnline()){
            showToast("Connected")
        }else{
            showToast("Disconnected")
        }
    }
}
