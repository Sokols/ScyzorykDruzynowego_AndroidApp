package pl.sokols.scyzorykdruzynowego.activities.main.ui.people;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.sokols.scyzorykdruzynowego.R;
import pl.sokols.scyzorykdruzynowego.activities.main.ui.people.adapters.PeopleAdapter;
import pl.sokols.scyzorykdruzynowego.data.viewmodels.PersonViewModel;
import pl.sokols.scyzorykdruzynowego.data.viewmodels.TeamViewModel;
import pl.sokols.scyzorykdruzynowego.utils.Utils;

public class PeopleFragment extends Fragment {

    @BindView(R.id.allPeopleRecyclerView)
    RecyclerView allPeopleRecyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_people, container, false);
        ButterKnife.bind(this, view);

        // prepare viewmodels
        PersonViewModel personViewModel = Utils.getPersonViewModel(this);
        TeamViewModel teamViewModel = Utils.getTeamViewModel(this);

        // prepare adapter
        PeopleAdapter peopleAdapter = new PeopleAdapter(getContext(), this);

        // add observers to viewmodels
        personViewModel.getAllPeople().observe(requireActivity(), peopleAdapter::setPeopleByTeamNameList);
        teamViewModel.getAllTeams().observe(requireActivity(), peopleAdapter::setTeamList);

        // prepare recycler view
        allPeopleRecyclerView.setAdapter(peopleAdapter);
        allPeopleRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    @OnClick(R.id.addPeopleFloatingActionButton)
    public void setAddingFAB() {
        Navigation.findNavController(requireView()).navigate(R.id.action_people_to_select);
    }
}