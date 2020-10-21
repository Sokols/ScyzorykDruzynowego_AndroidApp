package pl.sokols.scyzorykdruzynowego.ui.createteam;

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
import pl.sokols.scyzorykdruzynowego.databinding.FragmentCreateTeamBinding;


public class CreateTeamFragment extends Fragment {

    private CreateTeamViewModel viewModel;
    private FragmentCreateTeamBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(CreateTeamViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_create_team, container, false);
        binding.setCreateTeamViewModel(viewModel);
        binding.setLifecycleOwner(this);

        View view = binding.getRoot();
        initObservers(view);
        return view;
    }

    private void initObservers(View view) {
        viewModel.getTeam().observe(getViewLifecycleOwner(), team -> {
            if (team.getTeamName() == null) {
                binding.nameNewTeamTextInputLayout.setError(getString(R.string.required_error));
            } else {
                binding.nameNewTeamTextInputLayout.setError(null);
            }
        });

        viewModel.getIsTeamNameUnique().observe(getViewLifecycleOwner(), aBoolean -> {
            if (!aBoolean) {
                Snackbar.make(view, getString(R.string.double_team_name), BaseTransientBottomBar.LENGTH_SHORT).show();
            }
        });

        viewModel.getIsReadyToAddTeam().observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean) {
                Navigation.findNavController(requireView()).navigate(R.id.action_new_team_to_people);
                Snackbar.make(view, getString(R.string.added_new_team_completed), BaseTransientBottomBar.LENGTH_SHORT).show();
            }
        });
    }
}