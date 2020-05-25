package vn.sangdv.common.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


abstract class BaseAdapter<T>(
    context: Context,
    dataList: List<T>,
    layoutId: Int
) :
    RecyclerView.Adapter<ViewHolder<*>>() {
    private val mContext: Context
    private val mDataList: List<T>
    private val mLayoutId: Int
    private var mOnItemClickListener: OnItemClickListener? = null
    private var mOnItemLongClickListener: OnItemLongClickListener? = null
    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        mOnItemClickListener = onItemClickListener
    }

    fun setOnItemLongClickListener(onItemLongClickListener: OnItemLongClickListener?) {
        mOnItemLongClickListener = onItemLongClickListener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder<*> {
        val view =
            LayoutInflater.from(mContext).inflate(mLayoutId, parent, false)
        return ViewHolder<Any?>(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder<*>,
        position: Int
    ) {
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener { v ->
                mOnItemClickListener!!.onItemClick(
                    v,
                    holder.layoutPosition
                )
            }
        }
        if (mOnItemLongClickListener != null) {
            holder.itemView.setOnLongClickListener { v ->
                mOnItemLongClickListener!!.onItemLongClick(
                    v,
                    holder.layoutPosition
                )
            }
        }
        onPostBindViewHolder(holder, mDataList[position])
    }

    override fun getItemCount(): Int {
        return mDataList.size
    }

    abstract fun onPostBindViewHolder(
        holder: ViewHolder<*>?,
        t: T
    )

    interface OnItemClickListener {
        fun onItemClick(view: View?, position: Int)
    }

    interface OnItemLongClickListener {
        fun onItemLongClick(view: View?, position: Int): Boolean
    }

    init {
        mContext = context
        mDataList = dataList
        mLayoutId = layoutId
    }
}