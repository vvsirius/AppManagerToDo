package com.example.vv.appmanager;


import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/* Информация о приложении */
public class AppInfo {

    private String mName;
    private String mInstallAt;
    private Drawable mIcon;
    private Intent mLaunch;

    public AppInfo(String mName, String mInstallAt, Drawable mIcon, Intent mLaunch) {
        this.mName = mName;
        this.mInstallAt = mInstallAt;
        this.mIcon = mIcon;
        this.mLaunch = mLaunch;
    }

    public String getName() {
        return mName;
    }

    public String getInstallAt() {
        return mInstallAt;
    }

    public Drawable getIcon() {
        return mIcon;
    }

    public Intent getLaunch() {
        return mLaunch;
    }

    // префикс используется для фильтра приложений по пакетам
    static public List<AppInfo> parseApps(Context context, String package_prefix) {

		/* Запрос списка приложений, установленных в системе */
        PackageManager pm = context.getPackageManager();
        List<ApplicationInfo> packages = pm
                .getInstalledApplications(PackageManager.GET_META_DATA);

		/* Обработка полученных данных */
        List<AppInfo> result = new ArrayList<>();
        for (ApplicationInfo info : packages) {
            try {
                if (info.packageName.startsWith(package_prefix)
                        && !isSystemPackage(pm.getPackageInfo(info.packageName,
                        0))) {
                    Intent launch = pm
                            .getLaunchIntentForPackage(info.packageName);
                    String name = pm.getApplicationLabel(info).toString();
                    Drawable icon = pm.getApplicationIcon(info);
                    // дата хранится в виде строки
                    String installedAt = new SimpleDateFormat("dd/MM/yyyy")
                            .format(new Date(pm.getPackageInfo(
                                    info.packageName, 0).firstInstallTime));
                    result.add(new AppInfo(name, installedAt, icon, launch));
                }
            } catch (PackageManager.NameNotFoundException e) {
                Log.e("Main", e.toString());
            }
        }

        return result;
    }

    private static boolean isSystemPackage(PackageInfo packageInfo) {
        return ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0);
    }
}
