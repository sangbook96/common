package vn.sangdv.demmo

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_test.*
import vn.sangdv.common.BaseActivity
import vn.sangdv.common.adapter.BaseAdapter
import vn.sangdv.common.adapter.ViewHolder

class TestRecyclerView : BaseActivity(){
    private var mDataList= ArrayList<String>()
    override fun getLayoutID(): Int {
        return R.layout.activity_test
    }

    override fun onCreateActivity(savedInstanceState: Bundle?) {
        mDataList.add("SangDv")
        mDataList.add("SangDv")
        mDataList.add("SangDv")
        mDataList.add("SangDv")
        mDataList.add("SangDv")
        mDataList.add("SangDv")
        mRecyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        val adapter =
            object : BaseAdapter<String>(this, mDataList, R.layout.item_test) {
                override fun onPostBindViewHolder(holder: ViewHolder<*>?, t: String) {
                    holder!!.setViewText(R.id.mItemTextTitle,t.toString())
                }
            }
        adapter.setOnItemClickListener(object : BaseAdapter.OnItemClickListener {
            override fun onItemClick(view: View?, position: Int) {
                Toast.makeText(view!!.context, "Clicked " + (position + 1), Toast.LENGTH_SHORT)
                    .show()
            }
        })
        mRecyclerView.adapter = adapter
    }

}