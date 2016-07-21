package com.chensuworks.button.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chensuworks.button.R;

public class CreateUserDialogFragment extends DialogFragment {

    private EditText mFirstName;
    private EditText mLastName;
    private TextView mError;
    private Button mPositiveButton;

    public static CreateUserDialogFragment create() {
        return new CreateUserDialogFragment();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.create_user_dialog, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("CREATE USER");
        builder.setView(layout);
        builder.setPositiveButton("CREATE", null);
        builder.setNegativeButton("CANCEL", null);

        AlertDialog dialog = builder.create();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        dialog.show();

        mPositiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        mPositiveButton.setEnabled(false);
        mPositiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "User created!", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });

        mFirstName = (EditText) layout.findViewById(R.id.first_name);
        mLastName = (EditText) layout.findViewById(R.id.last_name);
        mError = (TextView) layout.findViewById(R.id.error);

        mFirstName.addTextChangedListener(new EditTextWatch());
        mLastName.addTextChangedListener(new EditTextWatch());
        mLastName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
                if (mFirstName.getText().toString().isEmpty()) {
                    mError.setVisibility(View.VISIBLE);
                    mError.setText("First name should NOT be empty!");

                } else if (mLastName.getText().toString().isEmpty()) {
                    mError.setVisibility(View.VISIBLE);
                    mError.setText("Last name should NOT be empty!");

                } else {
                    Toast.makeText(getActivity(), "User created!", Toast.LENGTH_SHORT).show();
                    dismiss();
                }
                return true;
            }
        });

        return dialog;
    }

    class EditTextWatch implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (!mFirstName.getText().toString().isEmpty() && !mLastName.getText().toString().isEmpty()) {
                mPositiveButton.setEnabled(true);

            } else {
                mPositiveButton.setEnabled(false);
            }

        }
    }

}


