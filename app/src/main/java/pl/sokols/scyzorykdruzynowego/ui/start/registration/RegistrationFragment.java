package pl.sokols.scyzorykdruzynowego.ui.start.registration;

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
import pl.sokols.scyzorykdruzynowego.databinding.FragmentRegistrationBinding;
import pl.sokols.scyzorykdruzynowego.ui.main.MainActivity;

public class RegistrationFragment extends Fragment {

    private RegistrationViewModel viewModel;
    private FragmentRegistrationBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(RegistrationViewModel.class);
        viewModel.getUserLiveData().observe(this, firebaseUser -> {
            if (firebaseUser != null) {
                startActivity(new Intent(requireActivity(), MainActivity.class));
                requireActivity().finish();
            }
        });
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
        viewModel.getErrorMessageLiveData()
                .observe(getViewLifecycleOwner(), s -> Snackbar.make(view, s, BaseTransientBottomBar.LENGTH_SHORT).show());

        binding.loginRegistrationButton
                .setOnClickListener(view1 -> Navigation.findNavController(view).navigate(R.id.action_registration_to_login));
    }
}