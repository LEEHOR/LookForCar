package com.cyht.wykc.receive;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import com.cyht.wykc.common.Constants;
import com.cyht.wykc.utils.PreferenceUtils;

import java.io.File;

public class DownloadReceive extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(intent.getAction())){
//            DownloadManager manager = (DownloadManager)context.getSystemService(Context.DOWNLOAD_SERVICE);
//            long id = PreferenceUtils.getPrefLong(context, Constants.ID, 0);
//            Uri uri = manager.getUriForDownloadedFile(id);
            String url = PreferenceUtils.getPrefString(context, Constants.URL,"");
            if (!TextUtils.isEmpty(url)){
//            if (uri != null){
                Intent intentApk = new Intent();
                intentApk.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intentApk.setAction(Intent.ACTION_VIEW);
//                intentApk.setDataAndType(uri,"application/vnd.android.package-archive");
                intentApk.setDataAndType(Uri.fromFile(new File(url)),
                        "application/vnd.android.package-archive");
                context.startActivity(intentApk);
            }
//        }else if(DownloadManager.ACTION_NOTIFICATION_CLICKED.equals(intent.getAction())){
            //点击通知栏取消下载
//            manager.remove(ids);
        }
    }
}
