package uk.ac.imperial.icfootballfantasy.controller;

import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by leszek on 9/4/17.
 */

public class Screenshot extends AppCompatActivity {
    public Bitmap getScreenShot(View view) {
        view.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);
        return bitmap;
    }
    public File store(Bitmap bm, String fileName){
        final String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Screenshots";
        File dir = new File(dirPath);
        if(!dir.exists())
            dir.mkdirs();
        File file = new File(dirPath, fileName);
        try {
            FileOutputStream fOut = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.PNG, 85, fOut);
            fOut.flush();
            fOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

}
