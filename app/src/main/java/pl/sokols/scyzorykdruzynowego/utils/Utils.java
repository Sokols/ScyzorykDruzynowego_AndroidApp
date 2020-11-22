package pl.sokols.scyzorykdruzynowego.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class Utils {
    public static final String SHARED_PREFS_KEY_NAME = "all_prefs_key";

    public static final String REMEMBER_ME_SHARED_PREFS_KEY = "remember_me_key";
    public static final String USER_LOGIN_SHARED_PREFS_KEY = "user_login_key";
    public static final String USER_ID_SHARED_PREFS_KEY = "user_id_key";

    @SuppressLint("SimpleDateFormat")
    public static Date getDateFromString(String date) throws ParseException {
        return new SimpleDateFormat("dd-MM-yyyy").parse(date);
    }

    @SuppressLint("SimpleDateFormat")
    public static String getStringFromDate(Date date) {
        if (date != null) {
            return new SimpleDateFormat("dd-MM-yyyy").format(date);
        } else {
            return "";
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static long getAgeFromDateDifference(Date start) {
        if (start != null) {
            LocalDate from = start.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate to = LocalDate.now();
            return ChronoUnit.YEARS.between(from, to);
        }
        return 0;
    }

    public static int getUserId(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Utils.SHARED_PREFS_KEY_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(Utils.USER_ID_SHARED_PREFS_KEY, 0);
    }

    public static String getJsonFromAssets(Context context, String fileName) {
        String jsonString;
        try {
            InputStream inputStream = context.getAssets().open(fileName);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            jsonString = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return jsonString;
    }

    public static Bitmap fromUri(@NonNull Context context, @NonNull String path) {
        InputStream inputStream = null;
        try {
            inputStream = context.getAssets().open(path);
            return BitmapFactory.decodeStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
