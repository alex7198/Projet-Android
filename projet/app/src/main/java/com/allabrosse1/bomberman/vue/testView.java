package com.allabrosse1.bomberman.vue;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.allabrosse1.bomberman.R;

/**
 * Created by allabrosse1 on 28/02/18.
 */

public class testView extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.game_layout, container, false);
    }

    @Override
    public void onPause() {

        super.onPause();
    }
}
