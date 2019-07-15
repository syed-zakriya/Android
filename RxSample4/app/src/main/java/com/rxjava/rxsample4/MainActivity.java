package com.rxjava.rxsample4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    /*
     *   Simple example to demonstrate usage of fromArray Operator in Rx-Java
     */

    Observable<String> mObservable1 = null;//fromArray will not emit entire array at a time. Rather it will emit one string at a time. Hence the type of data emitted by this observable will be  a String and not string[]
    DisposableObserver<String> mObserver = null;//Since the observable will emit data one string at a time, the consumer observer must be of type String

    Observable<Integer> mObservable2 = null;//To Handle Integer type arrray

    String[] array = {"Item1","Item2","Item3","Item4","Item5"};
    Integer[] intArray = {10,20,30,40,50};
    TextView resText = null;
    public static final String TAG = "MY_APP";
    CompositeDisposable mCompositeDisposable = null;//A composite disposable to collect all disposable observers and dispose it in onDestroy to prevent memoryLeak or other issues

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resText = findViewById(R.id.resText);
        mObservable1 = Observable.fromArray(array);//This will create an observable using an iterable. i.e it wlll emit strings one at a time. Hence, the observable should be of type String and not String[]
        mObservable2 = Observable.fromArray(intArray);

        mCompositeDisposable = new CompositeDisposable();

        //Adding String Observer to subscribe to mObservable1
        mCompositeDisposable.add(
                mObservable1
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(getStringObserver())
        );

        //Adding Integer Observer to subscribe to mObservable2
        mCompositeDisposable.add(
                mObservable2
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(getIntegerObserver())
        );
    }

    private DisposableObserver<String> getStringObserver(){
        return new DisposableObserver<String>() {
            @Override
            public void onNext(String s) {
                resText.append("\nString Observable OnNext():\tReceivedData:"+s+"\n");
                Log.d(TAG,"OnNext: ReceivedData:"+s);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG,e.getMessage());
            }

            @Override
            public void onComplete() {
                resText.append("\nString Observable OnComplete\n");
                resText.append("---------------------\n");
                Log.d(TAG,"OnComplete");
            }
        };
    }

    private DisposableObserver<Integer> getIntegerObserver(){
        return new DisposableObserver<Integer>() {
            @Override
            public void onNext(Integer integer) {
                resText.append("\nInteger Observer onNext():\t Received Integer:"+integer+"\n");
                Log.d(TAG,"Integer Observer onNext():\t Received Integer:"+integer);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG,e.getMessage());
            }

            @Override
            public void onComplete() {
                resText.append("\nInteger Observable OnComplete\n");
                resText.append("---------------------\n\n");
            }
        };
    }
}
