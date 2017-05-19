package specialmuffins.huji.hackathon.happyreminder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.provider.Settings.Secure;
/**
 * Created by reem on 18/05/17.
 */

public class Info {
    @SuppressLint("HardwareIds")
    public static String constId;
    public static void init(Context context) {
        constId = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
    }
}
