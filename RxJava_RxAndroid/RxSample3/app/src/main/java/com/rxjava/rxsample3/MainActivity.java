package com.rxjava.rxsample3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/*
    Simple example to demonstrate just operator
 */
public class MainActivity extends AppCompatActivity {

    Observable<String[]> mObservable1 = null;
    Observable<String> mObservable2 = null;

    DisposableObserver<String[]> mObserver1 = null;
    DisposableObserver<String> mObserver2 = null;

    CompositeDisposable mCompositeDisposable = null;

    String[] mData = {"Message1","Message2","Message3","Message4"};

    TextView textView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);

        //Create the disposable pool to prevent memory leaks
        mCompositeDisposable = new CompositeDisposable();

        //Create an observer which emits a String arrray
        mObservable1 = Observable.just(mData);
        //Create an observer which accepts the string array emitted by the observable
        mObserver1 = new DisposableObserver<String[]>() {
            @Override
            public void onNext(String[] strings) {
                textView.append("Data From mObserver1:\n");
                for(String str : strings)
                    textView.append("\n"+str);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                textView.append("\nmObserver1 Completed receiving data\n\n");
            }
        };
        //Sucscribe the mObserver1 to the mObservable
        //Return type of subscribeWith on a DisposableObserver is Disposable, so we can directly add it to the compositeDisposable  pool
        mCompositeDisposable.add(
                mObservable1.subscribeOn(Schedulers.io())//Perform the pre-processing non-cpu intensive task on the io or background thread
                    .observeOn(AndroidSchedulers.mainThread()) //Perform the operations of the fetched results on the main thread
                    .subscribeWith(mObserver1)//perform all the above with the specified observer
        );

        mObservable2 = Observable.just("Msg1","Msg2","Msg3");//we can pass upto max 10 arguments and each argument will be treated as an input and will be emitted by the observable independently
        mObserver2 = new DisposableObserver<String>() {
            @Override
            public void onNext(String s) {
                textView.append("Data From mObserver2:\n");
                textView.append(s+"\n");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                textView.append("\nmObserver2 Completed receiving data");
            }
        };

        mCompositeDisposable.add(
                mObservable2.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(mObserver2)
        );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.dispose();
    }
}
