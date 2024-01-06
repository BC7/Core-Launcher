package com.sevenbitstudios.corelauncher;

import android.app.ActionBar;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.sevenbitstudios.corelauncher.databinding.FragmentHomeBinding;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements GridViewAdapter.EventListener {

    MainActivity mainActivity;
    AppInfo focusedApp;
    ViewPager cViewPager;
    ViewPagerAdapter cViewPagerAdapter;
    List<AppInfo> appList = new ArrayList<>();
    ArrayList<PagerObj> homePages = new ArrayList<>();

    int cellHeight;
    int MAX_HOME_ROW_COUNT = 5;
    int DRAWER_PEEK_HEIGHT_DP = 105;
    int DRAWER_PEEK_HEIGHT = 112;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        FragmentHomeBinding homeBinding = FragmentHomeBinding.inflate(inflater, container, false);

        ActionBar actionBar = requireActivity().getActionBar();

        mainActivity = (MainActivity) getActivity();

        if (actionBar != null) {
            actionBar.hide();
        }

        return homeBinding.getRoot();
    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        DRAWER_PEEK_HEIGHT = convertDPtoPX(DRAWER_PEEK_HEIGHT_DP);

        final LinearLayout homeTopDrawerLayout = view.findViewById(R.id.topDrawerLayout);

        homeTopDrawerLayout.post( () -> {
            DRAWER_PEEK_HEIGHT = homeTopDrawerLayout.getHeight();
            initializeHome(view);
            initializeGridDrawer(view);
        });

        ImageButton settingsBtn = view.findViewById(R.id.settings);

        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), SettingsActivity.class));
            }
        });
        initializeHome(view);
        initializeGridDrawer(view);
    }

    private void initializeHome(View view) {

//      START SAMPLE DATA (ToDo - REMOVE)
        ArrayList<AppInfo> appDataList1 = new ArrayList<>();
        ArrayList<AppInfo> appDataList2 = new ArrayList<>();
        ArrayList<AppInfo> appDataList3 = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            final AppInfo appSample = new AppInfo("App #" + i, String.valueOf(i), AppCompatResources.getDrawable(requireContext().getApplicationContext(), R.drawable.ic_launcher_foreground));
            appDataList1.add(appSample);
        }
        for (int i = 0; i < 20; i++) {
            final AppInfo appSample = new AppInfo("App #" + i, String.valueOf(i), AppCompatResources.getDrawable(requireContext().getApplicationContext(), R.drawable.ic_launcher_foreground));
            appDataList2.add(appSample);
        }
        for (int i = 0; i < 20; i++) {
            final AppInfo appSample = new AppInfo("App #" + i, String.valueOf(i), AppCompatResources.getDrawable(requireContext().getApplicationContext(), R.drawable.ic_launcher_foreground));
            appDataList3.add(appSample);
        }

        homePages.add(new PagerObj(appDataList1));
        homePages.add(new PagerObj(appDataList2));
        homePages.add(new PagerObj(appDataList3));
//        END SAMPLE DATA

        cViewPager = view.findViewById(R.id.homePager);
        cellHeight = (getDisplayContentHeight() - DRAWER_PEEK_HEIGHT) / MAX_HOME_ROW_COUNT;

        cViewPagerAdapter =  new ViewPagerAdapter(view.getContext(), homePages, cellHeight, this);
        cViewPager.setAdapter(cViewPagerAdapter);
    }



    private void initializeHome(View view) {

//      START SAMPLE DATA (ToDo - REMOVE)
        ArrayList<AppInfo> appDataList1 = new ArrayList<>();
        ArrayList<AppInfo> appDataList2 = new ArrayList<>();
        ArrayList<AppInfo> appDataList3 = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            final AppInfo appSample = new AppInfo("App", String.valueOf(i), AppCompatResources.getDrawable(requireContext().getApplicationContext(), R.drawable.ic_launcher_foreground), false);
            appDataList1.add(appSample);
        }
        for (int i = 0; i < 20; i++) {
            final AppInfo appSample = new AppInfo("App", String.valueOf(i), AppCompatResources.getDrawable(requireContext().getApplicationContext(), R.drawable.ic_launcher_foreground), false);
            appDataList2.add(appSample);
        }
        for (int i = 0; i < 20; i++) {
            final AppInfo appSample = new AppInfo("App", String.valueOf(i), AppCompatResources.getDrawable(requireContext().getApplicationContext(), R.drawable.ic_launcher_foreground), false);
            appDataList3.add(appSample);
        }

        homePages.add(new PagerObj(appDataList1));
        homePages.add(new PagerObj(appDataList2));
        homePages.add(new PagerObj(appDataList3));
//        END SAMPLE DATA

        cViewPager = view.findViewById(R.id.homePager);
        cellHeight = (getDisplayContentHeight() - DRAWER_PEEK_HEIGHT) / MAX_HOME_ROW_COUNT;

        cViewPagerAdapter =  new ViewPagerAdapter(view.getContext(), homePages, cellHeight, this);
        cViewPager.setAdapter(cViewPagerAdapter);
    }

    private void initializeGridDrawer(View view) {
        GridView mHomeDrawerGridView = view.findViewById(R.id.homeDrawerGrid);
        View mBottomSheet = view.findViewById((R.id.homeBottomSheet));
        final BottomSheetBehavior<View> mbottomSheetBehavior = BottomSheetBehavior.from(mBottomSheet);

        appList = getInstalledApps();

        mbottomSheetBehavior.setHideable(false);
        mbottomSheetBehavior.setPeekHeight(DRAWER_PEEK_HEIGHT);
        mHomeDrawerGridView.setAdapter(new GridViewAdapter(this.getContext(), appList, cellHeight, this));

        mbottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

                if(focusedApp != null ){
                    return;
                }

                if (newState == BottomSheetBehavior.STATE_COLLAPSED && mHomeDrawerGridView.getChildAt(0).getY() != 0){
                    mbottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
                if (newState == BottomSheetBehavior.STATE_DRAGGING && mHomeDrawerGridView.getChildAt(0).getY() != 0){
                    mbottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
    }




    private List<AppInfo> getInstalledApps() {
        List<AppInfo> appList = new ArrayList<>();


        Intent i = new Intent(Intent.ACTION_MAIN, null);
        i.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> rawAppData = requireContext().getPackageManager().queryIntentActivities(i, PackageManager.ResolveInfoFlags.of(0));

        for (ResolveInfo appData : rawAppData ) {
            String appName = appData.activityInfo.loadLabel(requireContext().getPackageManager()).toString();
            String appPackageName = appData.activityInfo.packageName;
            Drawable appIcon = appData.activityInfo.loadIcon(requireContext().getPackageManager());

            AppInfo app = new AppInfo(appName, appPackageName, appIcon, true);

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

    private int getDisplayContentHeight() {
        final WindowManager windowManager = requireActivity().getWindowManager();
        int resourceID = requireActivity().getResources().getIdentifier("status_bar_height", "dimen", "android");
        int contentTop = requireActivity().findViewById(android.R.id.content).getTop();
        int actionBarHeight = 0;
        int screenHeight = 0;
        int statusBarHeight = 0;

        if (requireActivity().getActionBar() != null){
            actionBarHeight = requireActivity().getActionBar().getHeight();
        }

        if (resourceID > 0) {
            statusBarHeight = requireActivity().getResources().getDimensionPixelSize(resourceID);
        }

        screenHeight = windowManager.getCurrentWindowMetrics().getBounds().height();

        return screenHeight - contentTop - actionBarHeight - statusBarHeight;
    }





    @Override
    public void appItemOnClick (AppInfo app){

        if (focusedApp != null ) {
            app.setName(focusedApp.getName());
            app.setPackageName(focusedApp.getPackageName());
            app.setIcon(focusedApp.getIcon());
            app.setIsInDrawer(false);
            if (!focusedApp.getIsInDrawer()){
                focusedApp.setName("");
                focusedApp.setPackageName("");
                focusedApp.setIcon(focusedApp.getIcon());
                focusedApp.setIsInDrawer(false);
            }
            focusedApp = null;
            cViewPagerAdapter.notifyGridChange();
        } else {

            Intent launAppIntent = mainActivity.getApplicationContext().getPackageManager().getLaunchIntentForPackage(app.getPackageName());

            if (launAppIntent != null){
                mainActivity.getApplicationContext().startActivity(launAppIntent);
            }

        }
    }
    public void appItemOnLongClick (AppInfo app){
        collapseDrawer();
        focusedApp = app;
    }

    private void collapseDrawer() {

        View homeBottomSheet = mainActivity.findViewById((R.id.homeBottomSheet));
        final BottomSheetBehavior<View> homeBottomSheetBehavior = BottomSheetBehavior.from(homeBottomSheet);

        homeBottomSheet.setY(DRAWER_PEEK_HEIGHT);
        homeBottomSheetBehavior.setState(homeBottomSheetBehavior.STATE_COLLAPSED);
    }

    private int convertDPtoPX(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, requireActivity().getResources().getDisplayMetrics());
    }

}