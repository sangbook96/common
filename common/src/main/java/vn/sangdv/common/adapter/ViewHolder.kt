package vn.sangdv.common.adapter

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.SparseArray
import android.view.View
import android.view.View.OnLongClickListener
import androidx.recyclerview.widget.RecyclerView
import vn.sangdv.common.adapter.ReflectHelper.invokeMethodIfExists


class ViewHolder<T>(private val view:View): RecyclerView.ViewHolder(view){
     private val mViews: SparseArray<View> = SparseArray()
    fun getView():View{
        return view
    }
     fun <T : View> getView(viewId: Int): T {
         var view = mViews[viewId]
         if (view == null) {
             view = itemView.findViewById(viewId)
             mViews.put(viewId, view)
         }
         return view as T
     }
    fun setViewText(
        viewId: Int,
        text: CharSequence?
    ): ViewHolder<*>? {
        invokeMethodIfExists(
            "setText", getView(viewId), arrayOf(
                CharSequence::class.java
            ), text
        )
        return this
    }
    fun setViewText(viewId: Int, resId: Int): ViewHolder<*>? {
        invokeMethodIfExists(
            "setText",
            getView(viewId),
            arrayOf(Int::class.javaPrimitiveType),
            resId
        )
        return this
    }
    fun setViewTextColor(viewId: Int, color: Int): ViewHolder<*>? {
        invokeMethodIfExists(
            "setTextColor", getView(viewId), arrayOf(
                Int::class.javaPrimitiveType
            ), color
        )
        return this
    }

    fun setViewTextSize(
        viewId: Int,
        size: Float
    ): ViewHolder<*>? {
        invokeMethodIfExists(
            "setTextSize", getView(viewId), arrayOf(
                Float::class.javaPrimitiveType
            ), size
        )
        return this
    }

    fun setViewTextSize(
        viewId: Int,
        unit: Int,
        size: Float
    ): ViewHolder<*>? {
        invokeMethodIfExists(
            "setTextSize", getView(viewId), arrayOf(
                Int::class.javaPrimitiveType,
                Float::class.javaPrimitiveType
            ), unit, size
        )
        return this
    }

    fun setViewImageResource(
        viewId: Int,
        resId: Int
    ): ViewHolder<*>? {
        invokeMethodIfExists(
            "setImageResource", getView(viewId), arrayOf(
                Int::class.javaPrimitiveType
            ), resId
        )
        return this
    }

    fun setViewImageBitmap(
        viewId: Int,
        bitmap: Bitmap?
    ): ViewHolder<*>? {
        invokeMethodIfExists(
            "setImageBitmap", getView(viewId), arrayOf(
                Bitmap::class.java
            ), bitmap
        )
        return this
    }

    fun setViewImageDrawable(
        viewId: Int,
        drawable: Drawable?
    ): ViewHolder<*>? {
        invokeMethodIfExists(
            "setImageDrawable", getView(viewId), arrayOf(
                Drawable::class.java
            ), drawable
        )
        return this
    }

    fun setViewImageURI(viewId: Int, uri: Uri?): ViewHolder<*>? {
        invokeMethodIfExists(
            "setImageURI", getView(viewId), arrayOf(
                Uri::class.java
            ), uri
        )
        return this
    }

    fun setViewChecked(
        viewId: Int,
        checked: Boolean
    ): ViewHolder<*>? {
        invokeMethodIfExists(
            "setChecked", getView(viewId), arrayOf(
                Boolean::class.javaPrimitiveType
            ), checked
        )
        return this
    }

    fun setViewOnClickListener(
        viewId: Int,
        listener: View.OnClickListener?
    ): ViewHolder<*>? {
        invokeMethodIfExists(
            "setOnClickListener", getView(viewId), arrayOf(
                View.OnClickListener::class.java
            ), listener
        )
        return this
    }

    fun setViewOnLongClickListener(
        viewId: Int,
        listener: OnLongClickListener?
    ): ViewHolder<*>? {
        invokeMethodIfExists(
            "setOnLongClickListener", getView(viewId), arrayOf(
                OnLongClickListener::class.java
            ), listener
        )
        return this
    }

    fun setViewVisibility(
        viewId: Int,
        visibility: Int
    ): ViewHolder<*>? {
        invokeMethodIfExists(
            "setVisibility", getView(viewId), arrayOf(
                Int::class.javaPrimitiveType
            ), visibility
        )
        return this
    }

    fun setViewProperty(
        viewId: Int,
        propertyName: String,
        vararg params: Any?
    ): ViewHolder<*>? {
        var propertyName = propertyName
        propertyName =
            Character.toUpperCase(propertyName[0]).toString() + propertyName.substring(
                1
            )
        invokeMethodIfExists("set$propertyName", getView(viewId), params)
        return this
    }

}