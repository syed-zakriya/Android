package com.rxjava.rxsample5;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    CompositeDisposable mCompositeDisposable = null;
    DisposableObserver<Employee> mObserver = null;
    Observable<Employee> mObservable = null;
    TextView textView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initializing components
        textView = findViewById(R.id.txtRes);
        mCompositeDisposable = new CompositeDisposable();

        //Create(): This operator helps us create a default Observable where we can customize and control how much data to be passed, and we notify the observer on each of the data emitted .
        // Once data is exhausted, we need to notify the observer using the onComplete being called on the emitter instance
        mObservable = Observable.create(new ObservableOnSubscribe<Employee>() {
            @Override
            public void subscribe(ObservableEmitter<Employee> emitter) throws Exception {
                //Fetch all the employee's data
                ArrayList<Employee> employees = Employee.getAllEmployees();

                //Now we can either send the entire array list at once to the Observer or we can send it one by on
                //We have code for observer which receive a single Employee rather that an arrayList of employees. Hence we will emit one employees at a atime
                for(Employee employee : employees){
                    //For each employee in the list, emitter will call onNext of the observer and send the passed data to the observer
                    emitter.onNext(employee);
                }
                //Once all data is emitted, we need to notify the Observer that the data has been completed. Hence calling the onComplete method on the emitter instance
                emitter.onComplete();
            }
        });

        //Subscribing the observer to the observable, so that the observable starts emitting the data. Also we are adding the same to composite disposable to avoid memoryLeaks
        mCompositeDisposable.add(
                mObservable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(getObserver())
        );

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //This will destroy all the Disposable Observers in the Composite Disposable
        mCompositeDisposable.dispose();
    }

    private DisposableObserver<Employee> getObserver(){
        return new DisposableObserver<Employee>() {
            @Override
            public void onNext(Employee employee) {
                textView.append(employee.toString());
                textView.append("\n\n---------------------\n\n");
                Log.d("MY_APP","Employee:"+employee.toString());
                Log.d("MY_APP","-----------------------------");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                Log.d("MY_APP","Data receiving completed");
            }
        };
    }
}
