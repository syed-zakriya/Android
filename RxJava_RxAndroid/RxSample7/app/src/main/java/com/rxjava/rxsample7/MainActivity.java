package com.rxjava.rxsample7;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    TextView textView = null;
    CompositeDisposable mCompositeDisposable = null;
    Observable<Student> mObservable = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.txt_view);
        mCompositeDisposable = new CompositeDisposable();

        //Creating an observable that will emit a list of students
        mObservable = Observable.create(new ObservableOnSubscribe<Student>() {
            @Override
            public void subscribe(ObservableEmitter<Student> emitter) throws Exception {
                ArrayList<Student> students = Student.getAllStudents();
                for(Student student : students){
                    emitter.onNext(student);
                    Log.d("MY_APP","create() Operator. onNext() method called");
                }
                emitter.onComplete();
            }
        });

        //Make an Observer subscribe to the observable
        mCompositeDisposable.add(
                mObservable.subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .flatMap(new Function<Student, ObservableSource<Student>>() {
                                @Override
                                public ObservableSource<Student> apply(Student student) throws Exception {
                                    //In this method similar to map operator we can modify / transform the data inside this method
                                    student.setName(student.getName().toUpperCase());
                                    Log.d("MY_APP","Inside apply method of student:"+student.getName());
                                    //In the above step we have transformed the data as per our need.
                                    //Now that the FlatMap returns an Observable of type Student and not an object of type student
                                    //the best way to create an observable is using just() operator
                                    Student newStudent = new Student(student.getRegNum()+10,"New "+student.getName(),student.getAge());
                                    return Observable.just(student, newStudent);
                                }
                            })
                            .subscribeWith(getObserver())
        );
    }

    private DisposableObserver<Student> getObserver(){
        return new DisposableObserver<Student>() {
            @Override
            public void onNext(Student student) {
                textView.append(student.toString());
                textView.append("\n\n---------------------------\n\n");
                Log.d("My_APP",student.toString());
                Log.d("My_APP","------------------------");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                Log.d("My_APP","onComplete of Observer");
                textView.append("\nData receiving completed");
            }
        };
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.dispose();
    }
}
