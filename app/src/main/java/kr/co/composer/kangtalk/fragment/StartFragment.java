package kr.co.composer.kangtalk.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kr.co.composer.kangtalk.R;


/**
 * A placeholder fragment containing a simple view.
 */
public class StartFragment extends Fragment {

    public StartFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_intro, container, false);
        return view;
    }
}
