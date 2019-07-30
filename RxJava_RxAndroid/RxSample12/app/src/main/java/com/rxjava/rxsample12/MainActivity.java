package com.rxjava.rxsample12;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.jakewharton.rxbinding3.view.RxView;
import com.jakewharton.rxbinding3.widget.RxTextView;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import kotlin.Unit;

public class MainActivity extends AppCompatActivity {

    TextInputLayout textInputLayout = null;
    TextView textView = null;
    Button clearButton = null;
    CompositeDisposable mCompositeDisposable = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textInputLayout = findViewById(R.id.txtInputLayout);//TextInput layout to read user input
        textView = findViewById(R.id.msgTextView);//TextView to display the text entered in textInputLayout by user in realtime
        clearButton = findViewById(R.id.clearBtn);//Button to clear the above two fields
        mCompositeDisposable = new CompositeDisposable();

        mCompositeDisposable.add(RxTextView.textChanges(textInputLayout.getEditText())//Since we are watching text changes, we need to use RxTextView
                .subscribe(new Consumer<CharSequence>() {
                    @Override
                    public void accept(CharSequence charSequence) throws Exception {
                        textView.setText(charSequence);
                    }
                }));

        mCompositeDisposable.add(RxView.clicks(clearButton) //RxView is used to handle button clicks
                .subscribe(new Consumer<Unit>() {
                    @Override
                    public void accept(Unit unit) throws Exception {
                        textInputLayout.getEditText().setText("");
                        textView.setText("");
                    }
                }));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.dispose();
    }
}
