package com.jia.seven.view.base;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.jia.seven.R;
import com.jia.seven.network.ApiCallback;
import com.jia.seven.network.ApiStores;
import com.jia.seven.network.AppClient;
import com.jia.seven.network.parse.HttpBase;
import com.jia.seven.view.widget.CommonDialog;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * by tiny
 */
@SuppressWarnings("All")
public class BaseActivity extends FragmentActivity{
    private static final String TAG = BaseActivity.class.getSimpleName();

    public ApiStores apiStores = AppClient.retrofit().create(ApiStores.class);
    private CompositeDisposable mCompositeDisposable;
    private Dialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        onUnsubscribe();
    }

    public <T> void addSubscription(Flowable flowable, final ApiCallback<T> apiCallback) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        if (apiCallback == null) {
            Log.e(TAG, "callback can not be null");
            return;
        }

        Disposable disposable = flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<HttpBase<T>>() {
                    @Override
                    public void accept(HttpBase<T> o) throws Exception {
                        apiCallback.onNext(o);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        apiCallback.onError(throwable);
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        apiCallback.onComplete();
                    }
                });
        mCompositeDisposable.add(disposable);
    }

    public void addSubscription(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    private void onUnsubscribe() {
        if (mCompositeDisposable != null)
            mCompositeDisposable.clear();
    }

    public void addFragment(int containerViewId, Fragment fragment) {
        addFragment(containerViewId, fragment, false);
    }

    public void addFragment(int containerViewId, Fragment fragment, boolean addBackStack) {
        FragmentTransaction fragmentTransaction = null;
        fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(containerViewId, fragment, fragment.getClass().getSimpleName());
        if (addBackStack)
            fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    /**
     * 显示进度框
     *
     * @param resId      信息资源ID
     * @param cancelAble 是否可取消
     */
    public void showProgress(int resId, boolean cancelAble) {
        showProgress(getString(resId), cancelAble, null);
    }

    /**
     * 显示进度框
     *
     * @param message    信息
     * @param cancelAble 是否可取消
     */
    public void showProgress(String message, boolean cancelAble) {
        showProgress(message, cancelAble, null);
    }

    /**
     * 显示进度框
     *
     * @param resId      信息资源ID
     * @param cancelAble 是否可取消
     * @param listener   DialogInterface.OnKeyListener
     */
    public void showProgress(int resId, boolean cancelAble, DialogInterface.OnKeyListener listener) {
        showProgress(getString(resId), cancelAble, listener);
    }

    /**
     * 显示进度框
     *
     * @param message    信息
     * @param cancelAble 是否可取消
     * @param listener   DialogInterface.OnKeyListener
     */
    public void showProgress(String message, boolean cancelAble, DialogInterface.OnKeyListener listener) {
        if (mProgressDialog == null) {
            mProgressDialog = new CommonDialog(this);
            View view = LayoutInflater.from(this).inflate(R.layout.dialog_progress, null);
            if (!TextUtils.isEmpty(message)) {
                ((TextView) view.findViewById(R.id.progress_text)).setText(message);
            }
            mProgressDialog.setContentView(view);
            mProgressDialog.setCancelable(cancelAble);
            mProgressDialog.setOnKeyListener(listener);
        }
        if (!mProgressDialog.isShowing()) {
            try {
                mProgressDialog.show();
            } catch (Exception e) {
            }
        }
    }

    /**
     * 进度框是否正显示
     *
     * @return
     */
    protected boolean progressShowing() {
        return mProgressDialog == null && mProgressDialog.isShowing();
    }

    /**
     * 隐藏进度框
     */
    public void dismissProgress() {
        if (null != mProgressDialog && mProgressDialog.isShowing()) {
            try {
                mProgressDialog.dismiss();
                mProgressDialog = null;
            } catch (Exception e) {
            }
        }
    }
}
