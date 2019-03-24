package com.goldze.common.dmvvm.utils;

import android.graphics.Color;

import java.util.Random;

/**
 * @author GuoFeng
 * @date : 2019/1/23 14:15
 * @description:
 */
public class ColorUtil {

    private static Integer[] colors = {Color.parseColor("#757575"), Color.parseColor("#242524"), Color.parseColor("#49617e"),
            Color.parseColor("#965e75"), Color.parseColor("#3b9a58"), Color.parseColor("#05596e"),
            Color.parseColor("#943e4f"), Color.parseColor("#0a5d17")};


    public static int getRandomColor(Random random) {
        return colors[random.nextInt(colors.length)];
    }

 /*   public static String getVersionName() {
        return BuildConfig.VERSION_NAME;
    }*/
}
