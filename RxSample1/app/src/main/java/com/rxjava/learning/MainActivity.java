package com.rxjava.learning;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView msgTextView;

    Observable<String> observable = null;
    Observer<String> observer = null;
    Disposable disposable = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        msgTextView = findViewById(R.id.msg_text_view);

        //Creating an oberservable object that emits a simple string
        //just() is an operator of Observable class
        observable = Observable.just("This is a sample message for RX JAVA");

        //Creating an Observer object.
        //Since Observer is an Interface, we need to implement all methods inside the Observer Interface
        observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                //This method is called whenever we subscribe our observer object to an observable object
                Log.d("MY_APP","onSubscribe() method called....");
                disposable = d;//get reference of the disposable returned by the subscribe method, so that it can later be disposed in activity onDestroy method
            }

            @Override
            public void onNext(String s) {
                //This method is called each time the Observable emits the data
                Log.d("MY_APP","onNext() method called.");
                Log.d("MY_APP","RECEIVED String is:"+s);

                //Display the data emitted by the observable object
                msgTextView.setText(s);
            }

            @Override
            public void onError(Throwable e) {
                //Is called whenever some exception occurs. There by eleminating the need to write try-catch blocks
                Log.d("MY_APP","onError() called.\n Error:"+e.getMessage());
            }

            @Override
            public void onComplete() {
                //This metod is called once the Observable is exhausted of all the data i.e after emitting all the data
                Log.d("MY_APP","onComplete() method called.");
            }
        };

        //We have the Observable and Observer ready
        //The Observable emits data if and only if there is atleast one observer subscribed to it

        observable.subscribe(observer);//This will trigger the onSubscribe() callback of observer object
        //After onSubscribe, the onNext() callback get triggered for each item emitted by the observable object

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(disposable!=null)
            disposable.dispose();//This is to prevent memory leak in cases where the ui is destroyed and the obsever tries to  update the ui
    }
}
