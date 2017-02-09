package com.jia.seven.network;

import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.jia.seven.network.parse.HttpBase;

@SuppressWarnings("All")
public abstract class ApiCallback<M> {

    public abstract void onSuccess(M model);

    public abstract void onFailure(Error error);

    public void onFinish() {
    }

    public void onNext(HttpBase<M> model) {
        if (model == null) {
            onFailure(new Error(Error.S_ERROR_DATA));
            return;
        }
        if (model.getErrorCode() == 0) {
            try {
                M m = model.getData();
                onSuccess(m);
            } catch (Exception e) {
                onFailure(new Error(Error.S_ERROR_DATA));
            }

        } else {
            onFailure(new Error(Error.S_ERROR_DATA, model.getError_msg()));
        }
    }

    public void onError(Throwable e) {
        e.printStackTrace();
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            int code = httpException.code();
            String msg = httpException.getMessage();
            onFailure(new Error(code, msg));
        } else {
            onFailure(new Error(Error.S_UNKNOW_ERROR));
        }
    }

    public void onComplete() {
        onFinish();
    }
}