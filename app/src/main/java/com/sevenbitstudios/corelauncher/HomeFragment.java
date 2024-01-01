package com.sevenbitstudios.corelauncher;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
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
        View mBottomSheet = view.findViewById((R.id.homeBottomSheet));
        BottomSheetBehavior mbottomSheetBehavior = BottomSheetBehavior.from(mBottomSheet);

        appList = getInstalledApps();


        mHomeDrawerGridView.setAdapter(new DrawerGridViewAdapter(view.getContext(), appList));
    }

    private List<AppInfo> getInstalledApps() {
        List<AppInfo> appList = new ArrayList<>();


        Intent i = new Intent(Intent.ACTION_MAIN, null);
        i.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> rawAppData = getContext().getPackageManager().queryIntentActivities(i, PackageManager.ResolveInfoFlags.of(0));

        for (ResolveInfo appData : rawAppData ) {
            String appName = appData.activityInfo.loadLabel(getContext().getPackageManager()).toString();
            String appPackageName = appData.activityInfo.packageName;
            Drawable appIcon = appData.activityInfo.loadIcon(getContext().getPackageManager());

            AppInfo app = new AppInfo(appName, appPackageName, appIcon);

            if (!appList.contains(app)){
                appList.add(app);
            }
        }

        return appList;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}