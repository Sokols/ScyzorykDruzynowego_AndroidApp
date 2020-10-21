package pl.sokols.scyzorykdruzynowego.ui.people;

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
import pl.sokols.scyzorykdruzynowego.ui.people.adapters.PeopleAdapter;

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

        View view = binding.getRoot();
        init(view);
        return view;
    }

    private void init(View view) {
        // init recyclerview
        PeopleAdapter peopleAdapter = new PeopleAdapter(getContext(), this);
        binding.allPeopleRecyclerView.setAdapter(peopleAdapter);
        binding.allPeopleRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // init observers
        viewModel.getPeopleByTeamList().observe(getViewLifecycleOwner(), peopleAdapter::setAllPeopleList);

        viewModel.getTeamList().observe(getViewLifecycleOwner(), peopleAdapter::setTeamList);

        binding.addPeopleFloatingActionButton.setOnClickListener(view1 -> Navigation.findNavController(view).navigate(R.id.action_people_to_select));
    }
}