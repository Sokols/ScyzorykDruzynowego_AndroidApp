package pl.sokols.scyzorykdruzynowego.ui.main.people.selectpersonteam;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import pl.sokols.scyzorykdruzynowego.R;
import pl.sokols.scyzorykdruzynowego.databinding.FragmentSelectPersonTeamBinding;

public class SelectPersonTeamFragment extends Fragment {

    private FragmentSelectPersonTeamBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_select_person_team, container, false);
        binding.setSelectPersonTeamFragment(this);
        binding.setLifecycleOwner(this);

        View view = binding.getRoot();
        initListeners(view);
        return view;
    }

    private void initListeners(View view) {
        binding.newPersonButton.setOnClickListener(view1 -> Navigation.findNavController(view).navigate(R.id.action_select_to_new_person));

        binding.newTeamButton.setOnClickListener(view1 -> Navigation.findNavController(view).navigate(R.id.action_select_to_new_team));
    }
}