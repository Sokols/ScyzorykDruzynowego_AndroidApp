package pl.sokols.scyzorykdruzynowego.ui.start;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import pl.sokols.scyzorykdruzynowego.R;
import pl.sokols.scyzorykdruzynowego.ui.main.MainActivity;
import pl.sokols.scyzorykdruzynowego.utils.Utils;

import static pl.sokols.scyzorykdruzynowego.utils.Utils.REMEMBER_ME_SHARED_PREFS_KEY;

public class StartFragment extends Fragment {

    private final int SPLASH_TIME_OUT = 3000;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_start, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        new Handler().postDelayed(() -> {
            SharedPreferences sharedPreferences = requireContext().getSharedPreferences(Utils.SHARED_PREFS_KEY_NAME, Context.MODE_PRIVATE);

            // if checkbox checked open main activity
            if (sharedPreferences.getBoolean(REMEMBER_ME_SHARED_PREFS_KEY, false)) {
                Utils.startNewActivity(requireActivity(), new MainActivity());
            } else {
                Navigation.findNavController(view).navigate(R.id.action_start_to_login);
            }
        }, SPLASH_TIME_OUT);
    }
}