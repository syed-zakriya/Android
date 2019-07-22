package com.rxjava.rxsample2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    Observable<String> mObservable = null;

    Observer<String> mObserver1 = null;
    Observer<String> mObserver2 = null;
    DisposableObserver<String> mObserver3 = null;
    Disposable mDisposable1 = null;
    Disposable mDisposable2 = null;
    CompositeDisposable mCompositeDisposable = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.text_view_1);

        //Composite disposable is a pool of disposable oabject
        mCompositeDisposable = new CompositeDisposable();

        mObservable = Observable.just("This is second Rx-Sample");

        //defining Observer 1
        mObserver1 = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                //In case of a normal Observer we need to explicitly save the disposable returned when the observer subscribes to an observable
                mDisposable1 = d;
            }

            @Override
            public void onNext(String s) {
                textView.append("mObserver1:"+s+"\n");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        mCompositeDisposable.add(mDisposable1);//Can be used to call all disposable at once

        //defining Observer2
        mObserver2 = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                //In case of a normal Observer we need to explicitly save the disposable returned when the observer subscribes to an observable
                mDisposable2 = d;
            }

            @Override
            public void onNext(String s) {
                textView.append("mObserver2:"+s+"\n");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        mCompositeDisposable.add(mDisposable2);

        //defining Observer 3
        //This observer is a Disposable Observer. We need not save the disposable object when a DisposableObserver subscribes to an observable
        //Because in case of DisposableObserver, we can directly call the dispose() method
        mObserver3 = new DisposableObserver<String>() {
            @Override
            public void onNext(String s) {
                textView.append("mObserver3:"+s+"\n");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        mCompositeDisposable.add(mObserver3);

        //The Observable emits data only if the obervers subscribe to it
        mObservable.subscribe(mObserver1);
        mObservable.subscribe(mObserver2);
        mObservable.subscribe(mObserver3);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mDisposable1.dispose();//Normal Observer, we need to explicitly handle the dispose
        mDisposable2.dispose();
        mObserver3.dispose();//Disposable observer itself has the dispose() functionality built in. We can directly call dispose on the object itself

        //Onstead of making above 3 different dispose calls, since we have the disposables of all observers in the compisite disposable object, we can call the dispose of this object just once
        //mCompositeDisposable.dispose();
    }
}
