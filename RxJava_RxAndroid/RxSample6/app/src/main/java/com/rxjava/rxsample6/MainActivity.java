package com.rxjava.rxsample6;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    CompositeDisposable mCompositeDisposable = null;
    Observable<Person> mObservable = null;
    TextView textView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        mCompositeDisposable = new CompositeDisposable();
        
        //Using the create operator, create the observable that emits Person data
        mObservable = Observable.create(new ObservableOnSubscribe<Person>() {
            @Override
            public void subscribe(ObservableEmitter<Person> emitter) throws Exception {
                Log.d("MY_APP","Inside Subscribe...");
                ArrayList<Person> persons = Person.getPersonList();
                for (Person person : persons)
                    emitter.onNext(person);
                emitter.onComplete();
            }
        });

        mCompositeDisposable.add(
                mObservable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .map(new Function<Person, Person>() {
                        @Override
                        public Person apply(Person person) throws Exception {
                            person.setAge(person.getAge()+5);//Transforming age to age+5
                            person.setFirstName(person.getFirstName().toUpperCase());//Transforming from Lowercase to uppercase
                            person.setLastName(person.getLastName().toUpperCase());
                            return person;
                        }
                    })
                    .subscribeWith(getObserver())
        );

    }

    private DisposableObserver<Person> getObserver(){
        return new DisposableObserver<Person>() {
            @Override
            public void onNext(Person person) {
                Log.d("MY_APP",person.toString()+"\n");
                Log.d("MY_APP","-----------------------------");
                textView.append(person.toString()+"\n");
                textView.append("\n\n----------------------------\n\n");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                Log.d("MY_APP","Data retrieval completed");
            }
        };
    }
}
