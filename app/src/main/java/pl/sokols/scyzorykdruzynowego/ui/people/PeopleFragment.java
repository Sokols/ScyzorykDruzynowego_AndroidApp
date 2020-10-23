package pl.sokols.scyzorykdruzynowego.ui.people;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import pl.sokols.scyzorykdruzynowego.R;
import pl.sokols.scyzorykdruzynowego.databinding.FragmentPeopleBinding;
import pl.sokols.scyzorykdruzynowego.ui.people.adapters.OneTeamAdapter;
import pl.sokols.scyzorykdruzynowego.ui.people.adapters.PeopleAdapter;

public class PeopleFragment extends Fragment {

    private PeopleViewModel viewModel;
    private FragmentPeopleBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        this.viewModel = new ViewModelProvider(requireActivity()).get(PeopleViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_people, container, false);
        binding.setPeopleViewModel(viewModel);
        binding.setLifecycleOwner(this);
        init();
        return binding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.getItem(0).setVisible(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.actionEdit) {
            Snackbar.make(requireView(), "do a flip faggot", BaseTransientBottomBar.LENGTH_SHORT).show();
            return true;
        } else {
            return false;
        }
    }

    private void init() {
        // init recyclerview
        PeopleAdapter peopleAdapter = new PeopleAdapter(getContext(), getOnItemClickListener());
        binding.allPeopleRecyclerView.setAdapter(peopleAdapter);
        binding.allPeopleRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // init observers
        viewModel.getPeopleByTeamList().observe(getViewLifecycleOwner(), peopleAdapter::setAllPeopleList);

        viewModel.getTeamList().observe(getViewLifecycleOwner(), peopleAdapter::setTeamList);

        binding.addPeopleFloatingActionButton.setOnClickListener(view1 -> Navigation.findNavController(requireView()).navigate(R.id.action_people_to_select));
    }

    // get onItemClickListener for recyclerview elements
    private OneTeamAdapter.OnItemClickListener getOnItemClickListener() {
        return item -> {
            PeopleFragmentDirections.ActionPeopleToEditPerson action = PeopleFragmentDirections.actionPeopleToEditPerson(item);
            Navigation.findNavController(requireView()).navigate(action);
        };
    }
}