package com.chensuworks.button;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chensuworks.button.view.CreateUserDialogFragment;

public class MainActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textview);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCreateUserDialog();
            }
        });
    }

    private void showCreateUserDialog() {
        CreateUserDialogFragment dialog = CreateUserDialogFragment.create();
        dialog.show(getFragmentManager(), "CreateUserDialogFragmentTAG");
    }
}


