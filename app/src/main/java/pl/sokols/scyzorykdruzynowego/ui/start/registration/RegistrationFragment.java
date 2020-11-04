package pl.sokols.scyzorykdruzynowego.ui.start.registration;

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

import com.google.android.material.snackbar.Snackbar;

import pl.sokols.scyzorykdruzynowego.R;
import pl.sokols.scyzorykdruzynowego.databinding.FragmentRegistrationBinding;

import static com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_SHORT;

public class RegistrationFragment extends Fragment {

    private RegistrationViewModel viewModel;
    private FragmentRegistrationBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(RegistrationViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_registration, container, false);
        binding.setRegistrationViewModel(viewModel);
        binding.setLifecycleOwner(this);

        View view = binding.getRoot();
        initObservers(view);
        return view;
    }

    private void initObservers(View view) {
        viewModel.getUser().observe(getViewLifecycleOwner(), user -> {
            if (viewModel.getUsername() == null) {
                binding.usernameRegistrationTextInputLayout.setError(getString(R.string.required_error));
            } else {
                binding.usernameRegistrationTextInputLayout.setError(null);
            }

            if (viewModel.getPassword() == null) {
                binding.passwordRegistrationTextInputLayout.setError(getString(R.string.required_error));
            } else {
                binding.passwordRegistrationTextInputLayout.setError(null);
            }

            if (viewModel.getPassword2() == null) {
                binding.repeatPasswordRegistrationTextInputLayout.setError(getString(R.string.required_error));
            } else {
                binding.repeatPasswordRegistrationTextInputLayout.setError(null);
            }
        });

        viewModel.getIsUsernameUnique().observe(getViewLifecycleOwner(), aBoolean -> {
            if (!aBoolean) {
                Snackbar.make(view, getString(R.string.double_login), LENGTH_SHORT).show();
            }
        });

        viewModel.getArePasswordsCorrect().observe(getViewLifecycleOwner(), aBoolean -> {
            if (!aBoolean) {
                Snackbar.make(view, getString(R.string.incorrect_second_password), LENGTH_SHORT).show();
            }
        });

        viewModel.getIsReadyToRegister().observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean) {
                Navigation.findNavController(requireView()).navigate(R.id.action_registration_to_login);
                Snackbar.make(view, getString(R.string.registration_completed), LENGTH_SHORT).show();
            }
        });

        binding.loginRegistrationButton.setOnClickListener(view1 -> Navigation.findNavController(view).navigate(R.id.action_registration_to_login));
    }
}