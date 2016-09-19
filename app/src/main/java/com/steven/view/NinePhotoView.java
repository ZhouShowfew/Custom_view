package com.steven.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.steven.view.util.Utils;

import java.util.ArrayList;

/**
 * Created by Steven on 2016/8/28.
 *
 */
public class NinePhotoView extends ViewGroup {

    public static final int MAX_PHOTO_NUMBER = 9;

    // horizontal space among children views
    int hSpace = Utils.dpToPx(10);
    // vertical space among children views
    int vSpace = Utils.dpToPx(10);

    // every child view width and height.
    int childWidth = 0;
    int childHeight = 0;

    // store images res id
    ArrayList<Integer> mImageResArrayList = new ArrayList<Integer>(9);
    private View addPhotoView;

    private int[] constImageIds = { R.mipmap.ic_launcher, R.mipmap.ic_launcher,
            R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher,
            R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher,
            R.mipmap.ic_launcher };

    public NinePhotoView(Context context) {
        super(context);
    }

    public NinePhotoView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public NinePhotoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray t = context.obtainStyledAttributes(attrs, R.styleable.NinePhotoView, 0, 0);
        hSpace = t.getDimensionPixelSize(R.styleable.NinePhotoView_NinePhotoView_ninephoto_hspace, hSpace);
        vSpace = t.getDimensionPixelSize(R.styleable.NinePhotoView_NinePhotoView_ninephoto_vspace, vSpace);
        t.recycle();

        addPhotoView = new View(context);
        addView(addPhotoView);
        mImageResArrayList.add(new Integer(1));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int rw = MeasureSpec.getSize(widthMeasureSpec);
        int rh = MeasureSpec.getSize(heightMeasureSpec);
        //我们的子View三个一排，而且都是正方形，所以我们上面通过循环很好去得到所有子View的位置，注意我们
        //上面把子View的左上角坐标存储到我们自定义的LayoutParams 的left和top二个字段中
        childWidth = (rw - 2 * hSpace) / 3;
        childHeight = childWidth;

        int childCount = this.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = this.getChildAt(i);
            //this.measureChild(child, widthMeasureSpec, heightMeasureSpec);

            LayoutParams lParams = (LayoutParams) child.getLayoutParams();
            lParams.left = (i % 3) * (childWidth + hSpace);
            lParams.top = (i / 3) * (childWidth + vSpace);
        }
        //最后我们算得整个ViewGroup的宽高，调用setMeasuredDimension设置。
        int vw = rw;
        int vh = rh;
        if (childCount < 3) {
            vw = childCount * (childWidth + hSpace);
        }
        vh = ((childCount + 3) / 3) * (childWidth + vSpace);
        setMeasuredDimension(vw, vh);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = this.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = this.getChildAt(i);
            LayoutParams lParams = (LayoutParams) child.getLayoutParams();
            child.layout(lParams.left, lParams.top, lParams.left + childWidth,
                    lParams.top + childHeight);

            if (i == mImageResArrayList.size() - 1 && mImageResArrayList.size() != MAX_PHOTO_NUMBER) {
                child.setBackgroundResource(R.mipmap.ic_launcher);
                child.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        addPhotoBtnClick();
                    }
                });
            }else {
                child.setBackgroundResource(constImageIds[i]);
                child.setOnClickListener(null);
            }
        }
    }

    public void addPhoto() {
        if (mImageResArrayList.size() < MAX_PHOTO_NUMBER) {
            View newChild = new View(getContext());
            addView(newChild);
            mImageResArrayList.add(new Integer(1));
            requestLayout();
            invalidate();
        }
    }

    public void addPhotoBtnClick() {
        final CharSequence[] items = { "Take Photo", "Photo from gallery" };

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                addPhoto();
            }

        });
        builder.show();
    }


    @Override
    public android.view.ViewGroup.LayoutParams generateLayoutParams(
            AttributeSet attrs) {
        return new NinePhotoView.LayoutParams(getContext(), attrs);
    }

    /**
     * 那么现在新的LayoutParams类已经有了，如何让我们自定义的ViewGroup使用我们自定义的LayoutParams类来添加
     * 子View呢，ViewGroup同样提供了下面这几个方法供我们重写，我们重写返回我们自定义的LayoutParams对象即可。
     * */
    @Override
    protected android.view.ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected android.view.ViewGroup.LayoutParams generateLayoutParams(
            android.view.ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    @Override
    protected boolean checkLayoutParams(android.view.ViewGroup.LayoutParams p) {
        return p instanceof NinePhotoView.LayoutParams;
    }

    /**
     * ViewGroup还有一个很重要的知识LayoutParams，LayoutParams存储了子View在加入ViewGroup中时的一些参数信息，
     * 在继承ViewGroup类时，一般也需要新建一个新的LayoutParams类，就像SDK中我们熟悉的LinearLayout.LayoutParams
     * ，RelativeLayout.LayoutParams类等一样，那么可以这样做，在你定义的ViewGroup子类中，新建一个LayoutParams类
     * 继承与ViewGroup.LayoutParams。
            * */
    public static class LayoutParams extends ViewGroup.LayoutParams {

        public int left = 0;
        public int top = 0;

        public LayoutParams(Context arg0, AttributeSet arg1) {
            super(arg0, arg1);
        }

        public LayoutParams(int arg0, int arg1) {
            super(arg0, arg1);
        }

        public LayoutParams(android.view.ViewGroup.LayoutParams arg0) {
            super(arg0);
        }

    }
}
