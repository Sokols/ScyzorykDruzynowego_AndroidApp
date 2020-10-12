package pl.sokols.scyzorykdruzynowego.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import pl.sokols.scyzorykdruzynowego.data.viewmodel.PersonViewModel;
import pl.sokols.scyzorykdruzynowego.data.viewmodel.TeamViewModel;

public class Utils {

    public static final String SHARED_PREFS_KEY_NAME = "all_prefs_key";
    public static final String REMEMBER_ME_SHARED_PREFS_KEY = "remember_me_key";
    public static final String USER_LOGIN_SHARED_PREFS_KEY = "user_login_key";
    public static final String USER_ID_SHARED_PREFS_KEY = "user_id_key";

    @SuppressLint("SimpleDateFormat")
    public static Date getDateFromString(String date) throws ParseException {
        return new SimpleDateFormat("dd-MM-yyyy").parse(date);
    }

    public static void startNewActivity(Activity activityToClose, Activity activityToOpen) {
        activityToClose.startActivity(new Intent(activityToClose, activityToOpen.getClass()));
        activityToClose.finish();
    }

    public static int getUserId(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Utils.SHARED_PREFS_KEY_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(Utils.USER_ID_SHARED_PREFS_KEY, 0);
    }

    public static TeamViewModel getTeamViewModel(Fragment fragment) {
        return new ViewModelProvider(fragment.getViewModelStore(),
                new TeamViewModel.TeamViewModelFactory(
                        fragment.requireActivity().getApplication(),
                        Utils.getUserId(fragment.requireContext())))
                .get(TeamViewModel.class);
    }

    public static PersonViewModel getPersonViewModel(Fragment fragment) {
        return new ViewModelProvider(fragment.getViewModelStore(),
                new PersonViewModel.PersonViewModelFactory(
                        fragment.requireActivity().getApplication(),
                        Utils.getUserId(fragment.requireContext())))
                .get(PersonViewModel.class);
    }
}
