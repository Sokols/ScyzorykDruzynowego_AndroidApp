package pl.sokols.scyzorykdruzynowego.utils;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import pl.sokols.scyzorykdruzynowego.data.repository.TeamRepository;

public class Utils {
    public static final String SHARED_PREFS_KEY_NAME = "all_prefs_key";

    public static final String REMEMBER_ME_SHARED_PREFS_KEY = "remember_me_key";
    public static final String USER_LOGIN_SHARED_PREFS_KEY = "user_login_key";
    public static final String USER_ID_SHARED_PREFS_KEY = "user_id_key";

    @SuppressLint("SimpleDateFormat")
    public static Date getDateFromString(String date) throws ParseException {
        return new SimpleDateFormat("dd-MM-yyyy").parse(date);
    }

    public static int getUserId(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Utils.SHARED_PREFS_KEY_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(Utils.USER_ID_SHARED_PREFS_KEY, 0);
    }

    public static String[] getTeamNames(Application application) {
        return (String []) new TeamRepository(application, getUserId(application)).getAllTeamNames().toArray();
    }
}
