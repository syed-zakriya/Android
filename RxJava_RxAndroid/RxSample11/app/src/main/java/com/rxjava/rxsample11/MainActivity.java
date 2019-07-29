package com.rxjava.rxsample11;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.ReplaySubject;

public class MainActivity extends AppCompatActivity {

    TextView textView = null;
    CompositeDisposable mCompositeDisposable = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textview);
        mCompositeDisposable = new CompositeDisposable();

        textView.append("\nREPLAY SUBJECT DEMO");
        textView.append("\n____________________________");
        replaySubjectDemo();
    }

    private void replaySubjectDemo(){
        //Create the replaySubject Object
        ReplaySubject<String> replaySubject = ReplaySubject.create();

        //Make Observer1 subscribe to replaySubject
        replaySubject.subscribe(getObserver("first"));

        //Start Emitting data from replaySubject
        replaySubject.onNext("Item1");
        replaySubject.onNext("Item2");

        //Make Observer2 subscribe to replayObject
        replaySubject.subscribe(getObserver("second"));

        //Continue emitting data from replaySubject
        replaySubject.onNext("Item3");


        //Make Observer3 subscribe to replayObject
        replaySubject.subscribe(getObserver("third"));

        /*
        * In this demo the Observers Observer1,2,3 will all receive Item1,Item2,Item3,Item4,
        * irrespective of when they had subscribed to the ReplaySubject
        * */

    }

    private Observer<String> getObserver(final String name){
        return new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                if(name.equalsIgnoreCase("first"))
                {
                    Log.d("MY_APP","First Observer onSubscribe");
                    textView.append("\n\nFirst Observer onSubscribe");
                }
                else if(name.equalsIgnoreCase("second"))
                {
                    Log.d("MY_APP","Second Observer onSubscribe");
                    textView.append("\n\nSecond Observer onSubscribe");
                }
                else
                {
                    Log.d("MY_APP","Third Observer onSubscribe");
                    textView.append("\n\nThird Observer onSubscribe");
                }
                mCompositeDisposable.add(d);
            }

            @Override
            public void onNext(String s) {
                if(name.equalsIgnoreCase("first"))
                {
                    Log.d("MY_APP","First Observer onNext(): "+s);
                    textView.append("\n\nFirst Observer onNext: "+s);
                }
                else if(name.equalsIgnoreCase("second"))
                {
                    Log.d("MY_APP","Second Observer onNext(): "+s);
                    textView.append("\n\nSecond Observer onNext: "+s);

                }
                else
                {
                    Log.d("MY_APP","Third Observer onNext(): "+s);
                    textView.append("\n\nThird Observer onNext: "+s);
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                if(name.equalsIgnoreCase("first"))
                {
                    Log.d("MY_APP","First Observer Completed");
                    textView.append("\nFirst Observer Completed");
                }
                else if(name.equalsIgnoreCase("second"))
                {
                    Log.d("MY_APP","Second Observer Completed");
                    textView.append("\nSecond Observer Completed");
                }
                else
                {
                    Log.d("MY_APP","Third Observer Completed");
                    textView.append("\nThird Observer Completed");
                }
            }
        };
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.dispose();
    }
}
