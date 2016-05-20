package com.chensuworks.navigationtabs.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ListContentFragment extends Fragment {

    private String mText;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mText = getTag();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TextView text = new TextView(getActivity());
        text.setText(mText);
        return text;
    }
}
