package com.goldze.common.dmvvm.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author 小斌
 * @data 2019/6/5
 **/
public class SaveImage {

    public void saveImageToGallery(Context context, Bitmap bmp) {
        saveImageToGallery(context, null, null, bmp);
    }

    public void saveImageToGallery(Context context, String fileName, Bitmap bmp) {
        saveImageToGallery(context, null, fileName, bmp);
    }

    public void saveImageToGallery(Context context, @Nullable String path, @Nullable String fileName, Bitmap bmp) {
        // 首先保存图片
        try {
            File file;
            if (path != null && fileName != null) {
                File paths = new File(path);
                if (!paths.exists()) {
                    Log.i("SaveImage", "创建目录");
                    paths.mkdirs();
                }
                file = new File(path + File.separator + fileName);
            } else if (fileName != null) {
                file = new File(fileName);
                if (!file.exists()) {
                    Log.i("SaveImage", "创建目录");
                    file.mkdirs();
                }
            } else {
                fileName = Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DCIM).toString()
                        + File.separator + System.currentTimeMillis() + ".jpg";
                file = new File(fileName);
            }

            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            // 把文件插入到系统图库
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
            String imagUrl = file.getAbsolutePath();
            // 最后通知图库更新
            Uri contentUri = Uri.fromFile(new File(imagUrl));
            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, contentUri);
            context.sendBroadcast(mediaScanIntent);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
