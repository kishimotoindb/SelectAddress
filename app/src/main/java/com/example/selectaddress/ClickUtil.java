package com.example.selectaddress;

import android.os.SystemClock;

/**
 * Created by haichen.cui on 17-1-9.
 */

public class ClickUtil {
    public static long sLastClickTime;

    public static boolean isRealClick() {
        if (SystemClock.elapsedRealtime() - sLastClickTime < 400) {
            return false;
        }
        sLastClickTime = SystemClock.elapsedRealtime();
        return true;
    }
}
