package com.mmt.smartloan.http;


import android.text.TextUtils;
import android.util.Log;


import com.mmt.smartloan.http.bean.response.BaseResponse;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Devnan
 */

public class RxHelper {

    private static final String TAG = "RxHelper";

    private RxHelper() {
    }

    public static final <T> ObservableTransformer<BaseResponse<T>, T> handleResult() {
        return upstream -> upstream
                .flatMap((Function<BaseResponse<T>, ObservableSource<T>>) baseResponse -> {
                    Log.d(TAG, "Http code: " + baseResponse.getCode());
                    if (baseResponse.getCode() == APIStore.SUCCESS_CODE) {
                        if (baseResponse.getData() == null) {
                            return (ObservableSource<T>) Observable.just(new Object());//Rxjava2
                            // .x开始不支持传递null & Void
                        }
                        return Observable.just(baseResponse.getData());
                    } else if (baseResponse != null && !TextUtils.isEmpty(baseResponse.getMsg())) {
                        return Observable.error(new Exception(baseResponse.getMsg()));
                    } else {
                        return Observable.error(new Exception("unknown error"));
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                ;
    }

}
