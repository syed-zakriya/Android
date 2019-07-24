package com.rxjava.rxsample9;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    TextView textView = null;
    CompositeDisposable mCompositeDisposable = null;
    Observable<Integer> mObservable = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textview);
        mCompositeDisposable = new CompositeDisposable();
        mObservable = Observable.range(1,20);

        mCompositeDisposable.add(
                mObservable.subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .filter(new Predicate<Integer>() {
                                @Override
                                public boolean test(Integer integer) throws Exception {

                                    return integer % 2==0;
                                }
                            })
                            .subscribeWith(new DisposableObserver<Integer>(){
                                @Override
                                public void onNext(Integer integer) {
                                    textView.append("\t\t"+integer+"\n\n");
                                }

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onComplete() {
                                    textView.append("onCompleted from Observer");
                                }
                            })
        );

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.dispose();
    }
}
