package vn.sangdv.common.customview;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyboardShortcutGroup;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import vn.sangdv.common.R;

public class CustomDialog extends Dialog implements View.OnClickListener {
    private Context context;      // context
    private int mLayoutResId;      // file id
    private int[] mIds = new int[]{};  // The id of the control to monitor
    private int mAnimationResId = 0;//Theme style
    private OnCustomDialogItemClickListener listener;
    private boolean mIsDismiss = true;//Whether to cancel the dialog display after all buttons are clicked by default, when false, you need to manually call dismiss after the click event
    private boolean mIsDismissTouchOut = true;//Whether to allow touching the area outside dialog to cancel displaying dialog
    private int mPosition = 0; //Dialog position relative to page display
    private List<View> mViews = new ArrayList<>();//Collection of Views that are listening

    public void setOnDialogItemClickListener(OnCustomDialogItemClickListener listener) {
        this.listener = listener;
    }

    public CustomDialog(Context context, int layoutResID) {
        super(context, R.style.Custom_Dialog_Style);
        this.context = context;
        this.mLayoutResId = layoutResID;

    }

    public CustomDialog(Context context, int layoutResID, int[] listenedItems) {
        super(context, R.style.Custom_Dialog_Style); //dialog style
        this.context = context;
        this.mLayoutResId = layoutResID;
        this.mIds = listenedItems;
    }

    public CustomDialog(Context context, int layoutResID, int[] listenedItems, int animationResId) {
        super(context, R.style.Custom_Dialog_Style); //dialog style
        this.context = context;
        this.mLayoutResId = layoutResID;
        this.mIds = listenedItems;
        this.mAnimationResId = animationResId;
    }

    public CustomDialog(Context context, int layoutResID, int[] listenedItems, boolean isDismiss) {
        super(context, R.style.Custom_Dialog_Style); //dialog style
        this.context = context;
        this.mLayoutResId = layoutResID;
        this.mIds = listenedItems;
        this.mIsDismiss = isDismiss;
    }

    public CustomDialog(Context context, int layoutResID, int[] listenedItems, boolean isDismiss, boolean isDismissTouchOut) {
        super(context, R.style.Custom_Dialog_Style); //dialog style
        this.context = context;
        this.mLayoutResId = layoutResID;
        this.mIds = listenedItems;
        this.mIsDismiss = isDismiss;
        this.mIsDismissTouchOut = isDismissTouchOut;
    }

    public CustomDialog(Context context, int layoutResID, int[] listenedItems, boolean isDismiss, int position) {
        super(context, R.style.Custom_Dialog_Style); //dialog style
        this.context = context;
        this.mLayoutResId = layoutResID;
        this.mIds = listenedItems;
        this.mPosition = position;
    }


    /**
     * @param context
     * @param layoutResID       layout Id
     * @param ids               View id collection to be monitored
     * @param animationResId    animation resource id
     * @param isDismiss         whether to click all views by default to cancel dialog display
     * @param isDismissTouchOut whether to touch the dialog outside area disappears dialog display
     * @param position          dialog display position
     */
    public CustomDialog(Context context,
                        int layoutResID,
                        int[] ids,
                        int animationResId,
                        boolean isDismiss,
                        boolean isDismissTouchOut,
                        int position) {
        super(context, R.style.Custom_Dialog_Style);
        this.context = context;
        this.mLayoutResId = layoutResID;
        this.mIds = ids;
        this.mAnimationResId = animationResId;
        this.mIsDismiss = isDismiss;
        this.mIsDismissTouchOut = isDismissTouchOut;
        this.mPosition = position;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        if (0 == mPosition) {
            window.setGravity(Gravity.CENTER); // The default display position of the dialog is centered
        } else {
            window.setGravity(mPosition);// Set a custom dialog position
        }
        if (mAnimationResId == 0) {
            window.setWindowAnimations(R.style.bottom_animation); // Add default animation
        } else {
            window.setWindowAnimations(mAnimationResId);//Add default animation
        }
        setContentView(mLayoutResId);

        WindowManager windowManager = ((Activity) context).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = display.getWidth() * 4 / 5; // Set the dialog width to 4/5 of the screen
        getWindow().setAttributes(lp);
        setCanceledOnTouchOutside(mIsDismissTouchOut);
        //Traverse control id, add click event, add resource to collection
        for (int id : mIds) {
            View view = findViewById(id);
            view.setOnClickListener(this);
            mViews.add(view);
        }
    }

    /**
     * Get the View collection that needs to be monitored
     *
     * @return
     */
    public List<View> getViews() {
        return mViews;
    }

    @Override
    public void onProvideKeyboardShortcuts(List<KeyboardShortcutGroup> data, @Nullable Menu menu, int deviceId) {

    }

    public interface OnCustomDialogItemClickListener {
        void OnCustomDialogItemClick(CustomDialog dialog, View view);
    }


    @Override
    public void onClick(View view) {
        //Whether the default is to cancel the dialog display after all buttons are clicked, false is to manually call dismiss after the click event
        if (mIsDismiss) {
            dismiss();
        }
        listener.OnCustomDialogItemClick(this, view);
    }
}