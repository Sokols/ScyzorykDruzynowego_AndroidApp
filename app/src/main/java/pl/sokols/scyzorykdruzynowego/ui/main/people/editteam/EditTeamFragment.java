package pl.sokols.scyzorykdruzynowego.ui.main.people.editteam;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import pl.sokols.scyzorykdruzynowego.R;
import pl.sokols.scyzorykdruzynowego.data.firebase.FirebaseUtils;
import pl.sokols.scyzorykdruzynowego.data.repository.PersonRepository;
import pl.sokols.scyzorykdruzynowego.data.repository.TeamRepository;
import pl.sokols.scyzorykdruzynowego.databinding.FragmentEditTeamBinding;

public class EditTeamFragment extends Fragment {

    private EditTeamViewModel viewModel;
    private FragmentEditTeamBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_team, container, false);
        binding.setLifecycleOwner(this);
        binding.setTeam(EditTeamFragmentArgs.fromBundle(requireArguments()).getTeam());
        binding.deleteEditTeamFAB.setOnClickListener(deleteOnClickListener);
        binding.editEditTeamFAB.setOnClickListener(editOnClickListener);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(EditTeamViewModel.class);
    }

    // ask user if really wants to delete team
    // if yes, remove team from db, move all team members to "-" team
    // and go back to PeopleFragment
    private final View.OnClickListener deleteOnClickListener = view -> {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(requireContext(), R.style.MyDialogTheme);
        dialogBuilder.setTitle(getString(R.string.are_you_sure_title));
        dialogBuilder.setMessage(getString(R.string.are_you_sure_remove_person_description));
        dialogBuilder.setPositiveButton(getString(R.string.yes),
                (dialogInterface, whichButton) -> deleteTeam());
        dialogBuilder.setNegativeButton(getString(R.string.no),
                (dialogInterface, i) -> { /* do nothing */ });
        dialogBuilder.create().show();
    };

    // go to CreateTeamFragment if user chose edit FAB
    private final View.OnClickListener editOnClickListener = view -> {
        EditTeamFragmentDirections.ActionEditTeamToCreateTeam action = EditTeamFragmentDirections.actionEditTeamToCreateTeam();
        action.setTeam(binding.getTeam());
        Navigation.findNavController(requireView()).navigate(action);
    };

    // changing team name in all team members into "-"
    private void deleteTeam() {
        new PersonRepository(requireActivity().getApplication(), FirebaseUtils.getUserId())
                .updateTeam(
                        getString(R.string.blank), // new team name
                        binding.getTeam().getTeamName()); // old team name
        new TeamRepository(requireActivity().getApplication(), FirebaseUtils.getUserId())
                .delete(binding.getTeam());
        Navigation.findNavController(requireView()).popBackStack();
    }
}