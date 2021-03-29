package pl.sokols.scyzorykdruzynowego.ui.start;

import android.content.Intent;
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
import pl.sokols.scyzorykdruzynowego.data.firebase.FirebaseAuthRepository;
import pl.sokols.scyzorykdruzynowego.ui.main.MainActivity;

public class StartFragment extends Fragment {

    private final int SPLASH_TIME_OUT = 3000;
    private FirebaseAuthRepository firebaseAuthRepository;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuthRepository = new FirebaseAuthRepository(requireActivity().getApplication());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_start, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        new Handler().postDelayed(() -> firebaseAuthRepository.getUserLiveData().observe(requireActivity(), firebaseUser -> {
            if (firebaseUser != null) {
                startActivity(new Intent(requireActivity(), MainActivity.class));
                requireActivity().finish();
            } else {
                Navigation.findNavController(view).navigate(R.id.action_start_to_login);
            }
        }), SPLASH_TIME_OUT);
    }
}