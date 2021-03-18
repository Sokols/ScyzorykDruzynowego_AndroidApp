package pl.sokols.scyzorykdruzynowego.ui.start.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import pl.sokols.scyzorykdruzynowego.R;
import pl.sokols.scyzorykdruzynowego.databinding.FragmentLoginBinding;
import pl.sokols.scyzorykdruzynowego.ui.main.MainActivity;

public class LoginFragment extends Fragment {

    private LoginViewModel viewModel;
    private FragmentLoginBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);
        viewModel.getUserLiveData().observe(this, firebaseUser -> {
            if (firebaseUser != null) {
                startActivity(new Intent(requireActivity(), MainActivity.class));
                requireActivity().finish();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
        binding.setLoginViewModel(viewModel);
        binding.setLifecycleOwner(this);

        View view = binding.getRoot();
        initObservers(view);
        return view;
    }

    private void initObservers(View view) {
        viewModel.getErrorMessageLiveData()
                .observe(getViewLifecycleOwner(), s -> Snackbar.make(view, s, BaseTransientBottomBar.LENGTH_SHORT).show());

        binding.registerLoginButton
                .setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.action_login_to_registration));
    }
}