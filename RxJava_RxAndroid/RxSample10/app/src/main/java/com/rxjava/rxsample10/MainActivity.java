package com.rxjava.rxsample10;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.AsyncSubject;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;

/*
* This is a sample to demonstrate Subjects.
* Below example contains Example of AsyncSubject
* */
public class MainActivity extends AppCompatActivity {

    TextView textView = null;
    CompositeDisposable mCompositeDisposable = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        mCompositeDisposable = new CompositeDisposable();

        //Sample method to demonstrate usage of AsyncSubject
        //Un-Comment below code to see the demo of asyncSubject
        //textView.append("AYSNC_SUBJECT\n");
        //asyncSubjectDemo1();

        /*textView.append("\n------------------------");
        textView.append("\nBEHAVIOR SUBJECT");
        //behaviorSubjectDemo();
        behaviorSubjectDemo2();*/

        textView.append("\n------------------------");
        textView.append("\nPUBLISH SUBJECT");
        publishSubjectDemo();
    }

    private void asyncSubjectDemo1(){
        //Create an observable that emits data using the just operator
        Observable<String> observable = Observable.just("Java","Kotlin","NodeJs","XML","JSON")
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread());

        //Create an async Subject object.
        AsyncSubject<String> asyncSubject = AsyncSubject.create();

        //Async Subject is used in the scenarios where we want just the last item emitted by the observable
        //A subject can act as both Observable and Observer. In here we will se it act as an observer
        observable.subscribe(asyncSubject);//That is we are subscribing to the observable to receive data emitted by the observable. That is AsyncSubject is acting as an Observer in this case

        //Now we also know that AsyncSubject can also act as an Observable. Below is how we can use it as an observable
        asyncSubject.subscribe(getObserver("first"));//That is we are making 3 observers subscribe to the AsyncSubject. Hence Async subject is acting as an Observable in this case.
        asyncSubject.subscribe(getObserver("second"));
        asyncSubject.subscribe(getObserver("third"));
    }

    private void behaviorSubjectDemo(){

        //Creating a source observable which is create using the just operator
        Observable<String> observable = Observable.just("Java","Kotlin","NodeJs","XML","JSON")
                                                .subscribeOn(Schedulers.io())
                                                .observeOn(AndroidSchedulers.mainThread());

        //Create the BehaviorSubject Object
        BehaviorSubject<String> behaviorSubject = BehaviorSubject.create();

        //Making behavior Subject subscribe to the source observable
        observable.subscribe(behaviorSubject);

        //Make new Observers subscribe to the behaviorSubject
        behaviorSubject.subscribe(getObserver("first"));
        behaviorSubject.subscribe(getObserver("second"));
        behaviorSubject.subscribe(getObserver("third"));

    }

    private void behaviorSubjectDemo2(){
        BehaviorSubject<String> behaviorSubject = BehaviorSubject.create();

        //Here we are making the observer 1 to subscribe on behaviorSubject.
        //Which means observer 1 will receive all items emitted by the behaviorSubject
        behaviorSubject.subscribe(getObserver("first"));

        //Emitting 2 items using behavior subject
        behaviorSubject.onNext("Item1");
        behaviorSubject.onNext("Item2");

        //Now make observer 2 subscribe to the behaviorSubject. Hence, observer 2 will receive the last emitted item i.e. it will receive Item2 from above line and all subsequent items emitted bu the behavior subject
        behaviorSubject.subscribe(getObserver("second"));
        behaviorSubject.onNext("Item3");
        behaviorSubject.onNext("Item4");

        //Now make Observer 3 subscribe to behaviorSubject. Hence, Observer3 will receive only the last item emitted i.e Item4 followed by all subsequent items emitted by behaviorSubject
        behaviorSubject.subscribe(getObserver("third"));
        behaviorSubject.onNext("Item5");
        behaviorSubject.onNext("Item6");

    }

    private void publishSubjectDemo(){
        //Create the Publish subject object
        PublishSubject<String> publishSubject = PublishSubject.create();

        //Make Observer1 subscribe to the PublshSubject
        publishSubject.subscribe(getObserver("first"));

        //Start emitting data from the publishSubject
        publishSubject.onNext("Item1");
        publishSubject.onNext("Item2");

        //Make Observer2 subscribe to PublishSubject
        publishSubject.subscribe(getObserver("second"));

        //Start emitting subsequent data
        publishSubject.onNext("Item3"); // Observer 2 will start receiving data from Item3 onwards

        //Make Observer3 subscribe to PublishSubject
        publishSubject.subscribe(getObserver("third"));

        //Contionue emitting subsequent items
        publishSubject.onNext("Item4");//Observer3 will start receiving data from Item4 onwards. Observer 2 will receive Item3 along with subsequent items i.e item4
    }

    private Observer<String> getObserver(final String name){
        return new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                if(name.equalsIgnoreCase("first"))
                {
                    Log.d("MY_APP","First Observer onSubscribe");
                    textView.append("\nFirst Observer onSubscribe");
                }
                else if(name.equalsIgnoreCase("second"))
                {
                    Log.d("MY_APP","Second Observer onSubscribe");
                    textView.append("\nSecond Observer onSubscribe");
                }
                else
                {
                    Log.d("MY_APP","Third Observer onSubscribe");
                    textView.append("\nThird Observer onSubscribe");
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
