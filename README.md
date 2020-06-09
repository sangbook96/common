# [![](https://jitpack.io/v/sangbook96/common.svg)](https://jitpack.io/#sangbook96/common)
Config in android
```gradle
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  
  dependencies {
	        implementation 'com.github.sangbook96:common:version'
	}
 ```

Load image 
```kotlin
ImageUtils.loadImageBitmap(applicationContext,"https://png.pngtree.com/png-vector/20200420/ourlarge/pngtree-stay-at-home-vector-design-for-banner-and-background-png-image_2191124.jpg",mLoadVideoView)
```

Create base activity
```kotlin
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
```
Create base fragment
```kotlin
class DetailFragment : BaseFragment(){
    override fun initView() {
        //process data
        mMainActivity!!.showToast("I'm SangDv")
    }

    override fun layoutView(): Int {
        return R.layout.fragment_detail
    }
}
```
Common adapter using recyclerview
![image](https://github.com/sangbook96/common/blob/master/screen/test_common_recyclerview.png)
```kotlin
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
```
Image Boder
```xml
    <vn.sangdv.common.customview.CircleImageView
        android:id="@+id/mImageAvatar"
        android:layout_width="250dp"
        android:layout_height="250dp"
        android:src="@drawable/ic_avatar"
        app:civ_border_color="#EEEEEE"
        app:civ_border_width="4dp" />
```
Custom dialog
```kotlin
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
                PreventTwoClick.onClick(view)
            }
            customDialog.show()
           var  mTextDesception = customDialog.views.get(0) as TextView
            mTextDesception.text = "Test Thông báo!"

        }
    }
```

Format vnd 
```
mEdtDesiredPrice.addTextChangedListener(MoneyTextWatcher(mEdtDesiredPrice))
```



