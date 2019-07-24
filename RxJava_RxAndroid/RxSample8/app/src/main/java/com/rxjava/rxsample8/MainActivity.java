package com.rxjava.rxsample8;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
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

        textView = findViewById(R.id.textView);
        mCompositeDisposable = new CompositeDisposable();
        //Make the observable emit integers in the range of 1 to 20
        mObservable = Observable.range(1,20);

        //using buffer operator to emit 4 integers at a time instead of one integer at a time.
        mCompositeDisposable.add(
                mObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .buffer(4)//here we are indicating that buffer and keep 4 integers and then emit 4 integers at a time using the buffer operator
                .subscribeWith(new DisposableObserver<List<Integer>>(){

                    @Override
                    public void onNext(List<Integer> integers) {
                        for(Integer i : integers)
                            textView.append(i+"\t\t");
                        textView.append("\n--------------------------\n\n");;
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        textView.append("\nonComplete of observer");
                    }
                })
        );
    }
}
