package vn.sangdv.demmo.footer;

import android.content.Context;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import vn.sangdv.demmo.R;


/**
 * Created by SangDv
 */

public class TabIndicator extends RelativeLayout {
    private Context mContext;
    private TextView mText;
    private ImageView icon;

    public TextView getmText() {
        return mText;
    }

    public ImageView getIcon() {
        return icon;
    }

    public TabIndicator(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    private void initView() {
        inflate(mContext, R.layout.item_footer, this);
        mText =findViewById(R.id.mText);
        icon =findViewById(R.id.mImage);
    }

    public void isCheckBackground(Boolean isCheck) {
        RelativeLayout mBackgroundImage = findViewById(R.id.mBackgroundImage);
        if (isCheck) {
            mBackgroundImage.setBackgroundResource(R.drawable.bg_selected);
            icon.setColorFilter(getResources().getColor(R.color.base_color_white));
        }
        else {
            mBackgroundImage.setBackgroundResource(R.drawable.bg_unselected);
            icon.setColorFilter(getResources().getColor(R.color.base_color_black));

        }
    }

    public void setIcon(int resId) {
        icon = findViewById(R.id.mImage);
        icon.setImageResource(resId);
    }

    public void setText(String string) {
        mText = findViewById(R.id.mText);
        mText.setText(string);
    }

    public void setIcon(String url) {
        Glide.with(mContext).load(url).into(icon);
    }
}
