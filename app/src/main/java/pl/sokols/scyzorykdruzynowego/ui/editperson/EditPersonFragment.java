package pl.sokols.scyzorykdruzynowego.ui.editperson;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import pl.sokols.scyzorykdruzynowego.R;
import pl.sokols.scyzorykdruzynowego.databinding.FragmentEditPersonBinding;

public class EditPersonFragment extends Fragment {

    private EditPersonViewModel viewModel;
    private FragmentEditPersonBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_person, container, false);
        binding.setEditPersonViewModel(viewModel);
        binding.setPerson(EditPersonFragmentArgs.fromBundle(requireArguments()).getPerson());
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.viewModel = new ViewModelProvider(this).get(EditPersonViewModel.class);
    }
}