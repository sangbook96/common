package vn.sangdv.demmo.footer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;

import vn.sangdv.demmo.MyFragmentAdapter;
import vn.sangdv.demmo.R;
import vn.sangdv.demmo.footer.Footer;
import vn.sangdv.demmo.footer.TabIndicator;


//https://github.com/gengchenchina/SlidingTopTabs
public class TopSlidingTabs extends HorizontalScrollView {

    // @formatter:off
    private static final int[] ATTRS = new int[] {
            android.R.attr.textSize,
            android.R.attr.textColor
    };
    // @formatter:on

    private boolean shouldExpand = true;
    private boolean isShowUnderline = false;
    private boolean isSelectedBold = false;
    private boolean isSelectedCenter = true; // 选中项是否居中
    private int scrollOffset = 52;
    private int indicatorColor = 0xFF666666;
    private int indicatorHeight = 1;
    private int underlineHeight = 1;
    private int underlineColor = 0x1A000000;
    private int verticalPadding = 5; // dp
    private int horizontalTabMargin = 5; // dp

    private int tabTextSize = 4;
    private int tabTextColor = 0xFF666666;


    private LinearLayout.LayoutParams defaultTabLayoutParams;
    private LinearLayout.LayoutParams expandedTabLayoutParams;

    private LinearLayout tabsContainer;

    private int tabCount;

    private int currentPosition = 0;

    private Paint rectPaint;

    private int lastScrollX = 0;

    public TopSlidingTabs(Context context) {
        this(context, null);
    }

    public TopSlidingTabs(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TopSlidingTabs(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        setFillViewport(true);
        setWillNotDraw(false);

        tabsContainer = new LinearLayout(context);
        tabsContainer.setOrientation(LinearLayout.HORIZONTAL);
        tabsContainer.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        addView(tabsContainer);

        DisplayMetrics dm = getResources().getDisplayMetrics();

        scrollOffset = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, scrollOffset, dm);
        indicatorHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, indicatorHeight, dm);
        underlineHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, underlineHeight, dm);
        verticalPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, verticalPadding, dm);
        horizontalTabMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, horizontalTabMargin, dm);
        tabTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, tabTextSize, dm);

        // get system attrs (android:textSize and android:textColor)

        TypedArray a = context.obtainStyledAttributes(attrs, ATTRS);

        tabTextSize = a.getDimensionPixelSize(0, tabTextSize);
        tabTextColor = a.getColor(1, tabTextColor);

        a.recycle();

        // get custom attrs

        a = context.obtainStyledAttributes(attrs, R.styleable.TopSlidingTabs);

        indicatorColor = a.getColor(R.styleable.TopSlidingTabs_tstIndicatorColor, indicatorColor);
        indicatorHeight = a.getDimensionPixelSize(R.styleable.TopSlidingTabs_tstIndicatorHeight, indicatorHeight);
        verticalPadding = a.getDimensionPixelSize(R.styleable.TopSlidingTabs_tstVerticalPadding, verticalPadding);
        horizontalTabMargin = a.getDimensionPixelSize(R.styleable.TopSlidingTabs_tstHorizontalTabMargin, horizontalTabMargin);
        isShowUnderline = a.getBoolean(R.styleable.TopSlidingTabs_tstIsShowUnderline, isShowUnderline);
        if(isShowUnderline) {
            underlineColor = a.getColor(R.styleable.TopSlidingTabs_tstUnderlineColor, underlineColor);
            underlineHeight = a.getDimensionPixelSize(R.styleable.TopSlidingTabs_tstUnderlineHeight, underlineHeight);
        }
        isSelectedBold = a.getBoolean(R.styleable.TopSlidingTabs_tstIsSelectedBold, isSelectedBold);
        isSelectedCenter = a.getBoolean(R.styleable.TopSlidingTabs_tstIsSelectedCenter, isSelectedCenter);
        if(!isSelectedCenter) { // 如果不要求选中项居中，再去读取偏移量
            scrollOffset = a.getDimensionPixelSize(R.styleable.TopSlidingTabs_tstScrollOffset, scrollOffset);
        }
        a.recycle();

        tabsContainer.setPadding(0, verticalPadding, 0, verticalPadding);

        textPaint = new Paint();
        textPaint.setTextSize(tabTextSize);

        rectPaint = new Paint();
        rectPaint.setAntiAlias(true);
        rectPaint.setStyle(Paint.Style.FILL);

        defaultTabLayoutParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        expandedTabLayoutParams = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1.0f);

        defaultTabLayoutParams.leftMargin = defaultTabLayoutParams.rightMargin = horizontalTabMargin;
        expandedTabLayoutParams.leftMargin = expandedTabLayoutParams.rightMargin = horizontalTabMargin;

        if(isInEditMode()) {
            ArrayList<Footer>arrListFooter = new ArrayList();
            arrListFooter.add(new Footer("Home",R.drawable.ic_home));
            arrListFooter.add(new Footer("Home",R.drawable.ic_home));
            arrListFooter.add(new Footer("Home",R.drawable.ic_home));
            setTabs(arrListFooter);
            setCheckedIndex(0);
        }

    }


//    private String[] tabsData;
    private ArrayList<Footer> tabsData;
    public void setTabs(ArrayList<Footer> data) {
        tabsData = data;
        notifyDataSetChanged();
    }

    public void notifyDataSetChanged() {

        tabsContainer.removeAllViews();

        tabCount = tabsData.size();

        for (int i = 0; i < tabCount; i++) {
            addTab(i, tabsData.get(i).title,tabsData.get(i).icon);
        }

        updateTabStyles();

        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @SuppressWarnings("deprecation")
            @SuppressLint("NewApi")
            @Override
            public void onGlobalLayout() {

                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                    getViewTreeObserver().removeGlobalOnLayoutListener(this);
                } else {
                    getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        scrollToChild(currentPosition, 0);
                    }
                }, 300);

            }
        });

    }


    public PagerAdapter setupAdapter(MyFragmentAdapter adapter, ArrayList<Fragment> arrFragment) {
        for (int i = 0; i < arrFragment.size(); i++) {
            adapter.addFragment(arrFragment.get(i));
        }
        return adapter;
    }
    public ViewPager viewPager;

    public void setViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
    }

    public ViewPager getViewPager() {
        return viewPager;
    }

    public void addOnPageChangeListener() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(final int position, final float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setCheckedIndex(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    private float lastLeft, lastRight;
    private float ratio = 1.0f;

    private final float INDICATOR_SPEED_RATE = 0.2f;

    private final int MSG_WHAT_SCROLL_INDICATOR = 0x000088;

    private Handler indicatorHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_WHAT_SCROLL_INDICATOR:
                    post(animateRunnable);
                    break;
            }
        }
    };

    private Runnable animateRunnable = new Runnable() {
        @Override
        public void run() {
            ratio += INDICATOR_SPEED_RATE;
            ratio = ratio > 1.0f ? 1.0f : ratio;
            invalidate();
            if (ratio < 1.0f) {
                indicatorHandler.sendEmptyMessage(MSG_WHAT_SCROLL_INDICATOR);
            }
        }
    };
//
//    private void addTab(final int position, String title) {
//
//        TextView tab = new TextView(getContext());
//        tab.setText(title);
//        tab.setGravity(Gravity.CENTER);
//        tab.setSingleLine();
//
//        tab.setFocusable(true);
//        tab.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(currentPosition == position) {
//                    return;
//                }
//
//                TextView lastTab = (TextView) tabsContainer.getChildAt(currentPosition);
//                lastLeft = lastTab.getLeft();
//                lastRight = lastTab.getRight();
//                if(shouldExpand) {
//                    float textLength = textPaint.measureText(lastTab.getText().toString());
//                    float textIndent = ((lastRight - lastLeft) - textLength)/2;
//                    lastLeft = lastLeft+textIndent;
//                    lastRight = lastRight-textIndent;
//                }
//
//                scrollToChild(position, 0);
//                currentPosition = position;
//                updateTabStyles();
//
//                ratio = 0f;
//                indicatorHandler.sendEmptyMessage(MSG_WHAT_SCROLL_INDICATOR);
//
//                if(onTabSelectedListener != null) {
//                    onTabSelectedListener.onTabSelected(currentPosition);
//                }
//
//            }
//        });
//
//        tab.setPadding(0, verticalPadding/2, 0, verticalPadding);
//        tabsContainer.addView(tab, position, shouldExpand ? expandedTabLayoutParams : defaultTabLayoutParams);
//    }
    TabIndicator tab;
    private void addTab(final int position, String title,Integer icon) {
        tab = new TabIndicator(getContext());
//        TextView tab = new TextView(getContext());
        tab.setText(title);
        tab.setIcon(icon);

        tab.setFocusable(true);
        tab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentPosition == position) {
                    return;
                }

                TabIndicator lastTab = (TabIndicator) tabsContainer.getChildAt(currentPosition);
                lastLeft = lastTab.getLeft();
                lastRight = lastTab.getRight();
                if(shouldExpand) {
                    float textLength = textPaint.measureText(lastTab.getmText().getText().toString());
                    float textIndent = ((lastRight - lastLeft) - textLength)/2;
                    lastLeft = lastLeft+textIndent;
                    lastRight = lastRight-textIndent;
                }

                scrollToChild(position, 0);
                currentPosition = position;
                updateTabStyles();

                ratio = 0f;
                indicatorHandler.sendEmptyMessage(MSG_WHAT_SCROLL_INDICATOR);

                if(onTabSelectedListener != null) {
                    onTabSelectedListener.onTabSelected(currentPosition);
                }

            }
        });

        tab.setPadding(0, verticalPadding/2, 0, verticalPadding);
        tabsContainer.addView(tab, position, shouldExpand ? expandedTabLayoutParams : defaultTabLayoutParams);
    }

    private void updateTabStyles() {

        for (int i = 0; i < tabCount; i++) {

            View v = tabsContainer.getChildAt(i);

            if (v instanceof TabIndicator) {
                TabIndicator tab = (TabIndicator) v;
//                tab.getmText().setTextSize(TypedValue.COMPLEX_UNIT_PX, tabTextSize);
                // boolean 来决定是否选中的需要加粗
                if(isSelectedBold && i == currentPosition) {
                    tab.getmText().setTypeface(Typeface.DEFAULT_BOLD);
                    tab.isCheckBackground(true);
                    viewPager.setCurrentItem(currentPosition);

                } else {
                    tab.getmText().setTypeface(Typeface.DEFAULT);
                    tab.isCheckBackground(false);
                }
                tab.getmText().setTextColor(tabTextColor);
            }
        }

    }

    private void scrollToChild(int position, int offset) {
        if (tabCount == 0) {
            return;
        }
        int newScrollX = tabsContainer.getChildAt(position).getLeft() + offset;
        if(position == 0) {
            newScrollX -= ((MarginLayoutParams)tabsContainer.getChildAt(position).getLayoutParams()).leftMargin;
        }
        if (position > 0 || offset > 0) {
            if(isSelectedCenter) {
                scrollOffset = getWidth()/2 - tabsContainer.getChildAt(position).getWidth()/2;
            }
            newScrollX -= scrollOffset;
        }
        if (newScrollX != lastScrollX) {
            lastScrollX = newScrollX;
            smoothScrollTo(newScrollX, 0);
        }

    }
//
    private Paint textPaint;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (tabCount == 0) {
            return;
        }

        final int height = getHeight();

        // draw indicator line
        rectPaint.setColor(indicatorColor);

        View currentTab = tabsContainer.getChildAt(currentPosition);
        float lineLeft = currentTab.getLeft();
        float lineRight = currentTab.getRight();

        if(shouldExpand) {
            float textLength = textPaint.measureText(tab.getmText().getText().toString());
            float textIndent = ((lineRight - lineLeft) - textLength)/2;
            lineLeft = lineLeft+textIndent;
            lineRight = lineRight-textIndent;
        }

        lineLeft = (lineLeft - lastLeft) * ratio + lastLeft;
        lineRight = (lineRight - lastRight) * ratio + lastRight;

        canvas.drawRect(lineLeft, height - verticalPadding - indicatorHeight, lineRight, height - verticalPadding, rectPaint);

        // 是否有underline
        if(isShowUnderline) {
            rectPaint.setColor(underlineColor);
            canvas.drawRect(0, height - underlineHeight, tabsContainer.getWidth(), height, rectPaint);
        }


    }

    public void setCheckedIndex(int index){
        currentPosition = index;
        updateTabStyles();
        if(onTabSelectedListener != null) {
            onTabSelectedListener.onTabSelected(currentPosition);
        }
    }

    public void setIndicatorColor(int indicatorColor) {
        this.indicatorColor = indicatorColor;
        invalidate();
    }

    public void setIndicatorColorResource(int resId) {
        this.indicatorColor = getResources().getColor(resId);
        invalidate();
    }

    public void setIndicatorHeight(int indicatorLineHeightPx) {
        this.indicatorHeight = indicatorLineHeightPx;
        invalidate();
    }

    public void setUnderlineHeight(int underlineHeightPx) {
        this.underlineHeight = underlineHeightPx;
        invalidate();
    }

    public void setScrollOffset(int scrollOffsetPx) {
        this.scrollOffset = scrollOffsetPx;
        invalidate();
    }

    public int getScrollOffset() {
        return scrollOffset;
    }

    /**
     * 选项少的时候，是否需要平分宽度
     * @param shouldExpand
     */
    public void setShouldExpand(boolean shouldExpand) {
        this.shouldExpand = shouldExpand;
        requestLayout();
    }

    public boolean isShouldExpand() {
        return shouldExpand;
    }

    public void setTextSize(int textSizePx) {
        this.tabTextSize = textSizePx;
//        textPaint.setTextSize(tabTextSize);
        updateTabStyles();
    }


    public void setTextColor(int textColor) {
        this.tabTextColor = textColor;
        updateTabStyles();
    }

    public void setTextColorResource(int resId) {
        this.tabTextColor = getResources().getColor(resId);
        updateTabStyles();
    }

    public int getTextColor() {
        return tabTextColor;
    }


    @Override
    public void onRestoreInstanceState(Parcelable state) {
        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        currentPosition = savedState.currentPosition;
        requestLayout();
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState savedState = new SavedState(superState);
        savedState.currentPosition = currentPosition;
        return savedState;
    }

    static class SavedState extends BaseSavedState {
        int currentPosition;

        public SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            currentPosition = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(currentPosition);
        }

        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            @Override
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }

    private OnTabSelectedListener onTabSelectedListener;

    public void setOnTabSelectedListener(OnTabSelectedListener onTabSelectedListener) {
        this.onTabSelectedListener = onTabSelectedListener;
    }

    public interface OnTabSelectedListener {
        void onTabSelected(int selectedTabIndex);
    }
}
