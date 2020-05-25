package vn.sangdv.demmo

import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_test_dialog.*
import vn.sangdv.common.BaseActivity
import vn.sangdv.common.customview.CustomDialog

class TestDialogCommon : BaseActivity(){
    override fun getLayoutID(): Int {
        return R.layout.activity_test_dialog
    }

    override fun onCreateActivity(savedInstanceState: Bundle?) {
        mBtnShowDialog.setOnClickListener {
            val customDialog = CustomDialog(
                this,
                R.layout.dialog_layout, intArrayOf(R.id.mTextDesception,R.id.mBtnNo,R.id.mBtnYes)
            )
            customDialog.setOnDialogItemClickListener { dialog, view ->
                when(view.id){
                    R.id.mBtnNo->{
                        customDialog.dismiss()
                    }
                    R.id.mBtnYes->{
                        customDialog.dismiss()
                    }
                }
            }
            customDialog.show()
           var  mTextDesception = customDialog.views.get(0) as TextView
            mTextDesception.text = "Test Thông báo!"

        }
    }

}