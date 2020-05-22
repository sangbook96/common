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
	        implementation 'com.github.sangbook96:common:1.0.0'
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
