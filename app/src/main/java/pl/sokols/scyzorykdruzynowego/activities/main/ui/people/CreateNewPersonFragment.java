package pl.sokols.scyzorykdruzynowego.activities.main.ui.people;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.sokols.scyzorykdruzynowego.R;

public class CreateNewPersonFragment extends Fragment {

    @BindView(R.id.rankNewPersonAutoCompleteTextView)
    AutoCompleteTextView rankNewPersonAutoCompleteTextView;
    @BindView(R.id.teamNewPersonAutoCompleteTextView)
    AutoCompleteTextView teamNewPersonAutoCompleteTextView;
    @BindView(R.id.functionNewPersonAutoCompleteTextView)
    AutoCompleteTextView functionNewPersonAutoCompleteTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_new_person, container, false);
        ButterKnife.bind(this, view);
        setSpinnerAdapters();
        return view;
    }

    @OnClick(R.id.confirmAddPersonButton)
    public void setConfirmAddPersonButton() {
        Navigation.findNavController(requireView()).navigate(R.id.action_new_person_to_people);
    }

    private void setSpinnerAdapters() {
        // prepare adapters
        ArrayAdapter<String> rankAdapter = new ArrayAdapter<>(requireContext(), R.layout.dropdown_menu_popup_item, getResources().getStringArray(R.array.ranks));
        ArrayAdapter<String> teamAdapter = new ArrayAdapter<>(requireContext(), R.layout.dropdown_menu_popup_item, getTeamsFromDB());
        ArrayAdapter<String> functionAdapter = new ArrayAdapter<>(requireContext(), R.layout.dropdown_menu_popup_item, getResources().getStringArray(R.array.functions));

        // set adapters on spinners
        rankNewPersonAutoCompleteTextView.setAdapter(rankAdapter);
        teamNewPersonAutoCompleteTextView.setAdapter(teamAdapter);
        functionNewPersonAutoCompleteTextView.setAdapter(functionAdapter);
    }

    private List<String> getTeamsFromDB() {
        return new ArrayList<>();
    }
}