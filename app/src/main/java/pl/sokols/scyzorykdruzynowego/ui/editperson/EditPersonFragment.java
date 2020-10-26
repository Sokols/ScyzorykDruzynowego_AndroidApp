package pl.sokols.scyzorykdruzynowego.ui.editperson;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import pl.sokols.scyzorykdruzynowego.R;
import pl.sokols.scyzorykdruzynowego.data.repository.PersonRepository;
import pl.sokols.scyzorykdruzynowego.databinding.FragmentEditPersonBinding;
import pl.sokols.scyzorykdruzynowego.utils.Utils;

public class EditPersonFragment extends Fragment {

    private EditPersonViewModel viewModel;
    private FragmentEditPersonBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_person, container, false);
        binding.setPerson(EditPersonFragmentArgs.fromBundle(requireArguments()).getPerson());
        binding.setAge(Utils.getAgeFromDateDifference(binding.getPerson().getDateOfBirth()));
        binding.setLifecycleOwner(this);
        binding.deleteEditPersonFAB.setOnClickListener(removeOnClickListener);
        binding.editEditPersonFAB.setOnClickListener(editOnClickListener);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.viewModel = new ViewModelProvider(this).get(EditPersonViewModel.class);
    }

    // ask user if really wants to delete person - if yes, remove person from db and go back to PeopleFragment
    private View.OnClickListener removeOnClickListener = view -> {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(requireContext(), R.style.MyDialogTheme);
        dialogBuilder.setTitle(getString(R.string.are_you_sure_title));
        dialogBuilder.setMessage(getString(R.string.are_you_sure_remove_person_description));
        dialogBuilder.setPositiveButton(getString(R.string.yes),
                (dialogInterface, whichButton) -> {
                    PersonRepository personRepository = PersonRepository.getInstance(requireActivity().getApplication(), Utils.getUserId(requireContext()));
                    personRepository.delete(binding.getPerson());
                    Navigation.findNavController(requireView()).popBackStack();
                });
        dialogBuilder.setNegativeButton(getString(R.string.no),
                (dialogInterface, i) -> { /* do nothing */ });
        dialogBuilder.create().show();
    };

    // go to CreatePersonFragment if user chose edit FAB
    private View.OnClickListener editOnClickListener = view -> {
        EditPersonFragmentDirections.ActionEditPersonToCreatePerson action = EditPersonFragmentDirections.actionEditPersonToCreatePerson();
        action.setPerson(binding.getPerson());
        Navigation.findNavController(requireView()).navigate(action);
    };
}