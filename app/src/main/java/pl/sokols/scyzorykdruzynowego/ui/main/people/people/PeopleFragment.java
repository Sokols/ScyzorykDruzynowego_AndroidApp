package pl.sokols.scyzorykdruzynowego.ui.main.people.people;

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
import androidx.recyclerview.widget.LinearLayoutManager;

import pl.sokols.scyzorykdruzynowego.R;
import pl.sokols.scyzorykdruzynowego.databinding.FragmentPeopleBinding;
import pl.sokols.scyzorykdruzynowego.ui.main.people.people.adapters.OneTeamAdapter;
import pl.sokols.scyzorykdruzynowego.ui.main.people.people.adapters.PeopleAdapter;

public class PeopleFragment extends Fragment {

    private PeopleViewModel viewModel;
    private FragmentPeopleBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.viewModel = new ViewModelProvider(requireActivity()).get(PeopleViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_people, container, false);
        binding.setPeopleViewModel(viewModel);
        binding.setLifecycleOwner(this);
        initComponents();
        return binding.getRoot();
    }

    private void initComponents() {
        // init recyclerview
        PeopleAdapter peopleAdapter = new PeopleAdapter(getOnScoutClickListener(), getOnTeamCLickListener(), requireActivity().getApplication());
        binding.allPeopleRecyclerView.setAdapter(peopleAdapter);
        binding.allPeopleRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // init observers
        viewModel.getTeamList().observe(getViewLifecycleOwner(), peopleAdapter::setTeamList);

        // init listeners
        binding.addPersonPeopleFAB.setOnClickListener(view1 -> Navigation.findNavController(requireView()).navigate(R.id.action_people_to_create_person));
        binding.addTeamPeopleFAB.setOnClickListener(view1 -> Navigation.findNavController(requireView()).navigate(R.id.action_people_to_create_team));
    }

    // get onItemClickListener for recyclerview elements - scouts
    private OneTeamAdapter.OnPersonClickListener getOnScoutClickListener() {
        return item -> {
            PeopleFragmentDirections.ActionPeopleToEditPerson action = PeopleFragmentDirections.actionPeopleToEditPerson(item);
            Navigation.findNavController(requireView()).navigate(action);
        };
    }

    // get onItemClickListener for recyclerview elements - teams
    private PeopleAdapter.OnTeamClickListener getOnTeamCLickListener() {
        return item -> {
            PeopleFragmentDirections.ActionPeopleToEditTeam action = PeopleFragmentDirections.actionPeopleToEditTeam();
            action.setTeam(item);
            Navigation.findNavController(requireView()).navigate(action);
        };
    }
}