package vn.sangdv.common.utils

import android.view.View

class RecyclerViewItemClickListener: View.OnClickListener{
    private var position: Int = 0
    private var onItemClickCallBack: OnItemClickCallBack? = null

    override fun onClick(view: View?) {
        onItemClickCallBack?.onItemClicked(view!!, position)
    }

    constructor(position: Int, onItemClickCallBack: OnItemClickCallBack) {
        this.position = position
        this.onItemClickCallBack = onItemClickCallBack
    }

    interface OnItemClickCallBack {
        fun onItemClicked(view: View, position: Int)
    }
}