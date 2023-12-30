package com.sevenbitstudios.corelauncher;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import com.sevenbitstudios.corelauncher.databinding.FragmentHomeBinding;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    List<AppInfo> appList = new ArrayList<>();

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        FragmentHomeBinding homeBinding = FragmentHomeBinding.inflate(inflater, container, false);

        View rootView = homeBinding.getRoot();

        return rootView;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeGridDrawer(view);
    }


    private void initializeGridDrawer(View view) {
        GridView mHomeDrawerGridView = view.findViewById(R.id.homeDrawerGrid);

        for (int i = 0; i < 32; i++) {
            final AppInfo appSample = new AppInfo(String.valueOf(i), String.valueOf(i), ResourcesCompat.getDrawable(getResources(), R.drawable.ic_launcher_foreground, null));
            appList.add(appSample);
        }

        mHomeDrawerGridView.setAdapter(new DrawerGridViewAdapter(view.getContext(), appList));
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}