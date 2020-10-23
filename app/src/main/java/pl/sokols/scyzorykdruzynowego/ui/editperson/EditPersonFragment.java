package pl.sokols.scyzorykdruzynowego.ui.editperson;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import pl.sokols.scyzorykdruzynowego.R;
import pl.sokols.scyzorykdruzynowego.databinding.FragmentEditPersonBinding;
import pl.sokols.scyzorykdruzynowego.utils.Utils;

public class EditPersonFragment extends Fragment {

    private EditPersonViewModel viewModel;
    private FragmentEditPersonBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_person, container, false);
        binding.setPerson(EditPersonFragmentArgs.fromBundle(requireArguments()).getPerson());
        binding.setAge(Utils.getAgeFromDateDifference(binding.getPerson().getDateOfBirth()));
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.viewModel = new ViewModelProvider(this).get(EditPersonViewModel.class);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.getItem(0).setVisible(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.actionEdit) {
            EditPersonFragmentDirections.ActionEditPersonToCreatePerson action = EditPersonFragmentDirections.actionEditPersonToCreatePerson();
            action.setPerson(binding.getPerson());
            Navigation.findNavController(requireView()).navigate(action);
            return true;
        } else {
            return false;
        }
    }
}