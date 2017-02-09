package com.jia.seven.view.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.StringRes;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.jia.seven.R;
import com.jia.seven.utils.AppUtil;


/**
 * Created by Andy on 2015/11/21.
 */
public class CommonDialog extends Dialog implements View.OnClickListener, DialogInterface.OnCancelListener {

    public FrameLayout mContainer;
    private TextView mTitleView;
    private ImageView mCloseView;
    private Context mContext;
    private View mRootView;

    public CommonDialog(Context context) {
        super(context, R.style.CommonDialogStyle);
        init(context);
    }

    public CommonDialog(Context context, int themeResId) {
        super(context, themeResId);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCanceledOnTouchOutside(true);
        mRootView = LayoutInflater.from(context).inflate(R.layout.dialog_common, null);
        mCloseView = (ImageView) mRootView.findViewById(R.id.dialog_close);
        mCloseView.setOnClickListener(this);
        mTitleView = (TextView) mRootView.findViewById(R.id.dialog_title);
        mContainer = (FrameLayout) mRootView.findViewById(R.id.dialog_container);
//        view.setPadding(0, 0, 0, 0);
        setContentView(mRootView);
        setOnCancelListener(this);
    }

    public void setShowTitle(boolean showTitle) {
        if (showTitle) {
            mTitleView.setVisibility(View.VISIBLE);
        } else {
            mTitleView.setVisibility(View.GONE);
        }
    }

    public void setShowClose(boolean showCLose) {
        if (showCLose) {
            mCloseView.setVisibility(View.VISIBLE);
        } else {
            mCloseView.setVisibility(View.GONE);
        }
    }


    /**
     * 设置显示视图
     *
     * @param view
     */
    public void setContainer(View view) {
        mContainer.addView(view);
    }

    /**
     * 设置标题
     *
     * @param title
     */
    public void setTitle(String title) {
        mTitleView.setText(title);
    }


    public void showDialog() {
        showDialog(0);
    }

    public void showDialog(int width) {
        windowDeploy(0, 0, width);
        super.show();
    }

    // 设置窗口显示
    public void windowDeploy(int x, int y, int width) {
        Window window = getWindow(); // 得到对框

        if (width <= 0) {
            width = AppUtil.dpToPx(mContext, 280);
        } else {
            width = AppUtil.dpToPx(mContext, width);
        }

        WindowManager.LayoutParams params = window.getAttributes();
        params.width = width;
        params.height = android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
        window.setWindowAnimations(R.style.EditorDialog); // 设置窗口弹出动画
        // window.setWindowAnimations(R.style.toast_anim); // 设置窗口弹出动画
        // // window.setBackgroundDrawableResource(R.color.vifrification);
        // // //设置对话框背景为透明
        // WindowManager.LayoutParams wl = window.getAttributes();
        // // 根据x，y坐标设置窗口需要显示的位置
        // wl.x = x; // x小于0左移，大于0右移
        // wl.y = y; // y小于0上移，大于0下移
        // // wl.alpha = 0.6f; //设置透明度
        // // wl.gravity = Gravity.BOTTOM; //设置重力
        // window.setAttributes(wl);
    }

    /**
     * @param titleId the title's text resource identifier
     */
    public void setTitle(@StringRes int titleId) {
        mTitleView.setText(mContext.getText(titleId));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_close:
                dismiss();
                if (mListener != null)
                    mListener.onCancel();
                break;
        }
    }

    private CommonDialogListener mListener;

    public void setCommonDialogListener(CommonDialogListener listener) {
        mListener = listener;
    }

    @Override
    public void onCancel(DialogInterface dialog) {
//        Log.e("CommonDialog", "onCancel");
        if (mListener != null)
            mListener.onCancel();
    }

    public interface CommonDialogListener {
        void onCancel();
    }
}
