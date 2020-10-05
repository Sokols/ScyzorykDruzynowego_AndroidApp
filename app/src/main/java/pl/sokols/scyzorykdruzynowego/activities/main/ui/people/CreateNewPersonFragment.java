package pl.sokols.scyzorykdruzynowego.activities.main.ui.people;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.sokols.scyzorykdruzynowego.R;

public class CreateNewPersonFragment extends Fragment {

    @BindView(R.id.personRankAutoCompleteTextView)
    AutoCompleteTextView personRankAutoCompleteTextView;
    @BindView(R.id.personFunctionSpinner)
    Spinner personFunctionSpinner;
    @BindView(R.id.personTeamSpinner)
    Spinner personTeamSpinner;

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
        // prepare HINT adapters
        ArrayAdapter<String> rankAdapter = new ArrayAdapter<>(requireContext(), R.layout.autocompletetextview_item, getResources().getStringArray(R.array.ranks));
        ArrayAdapter<String> functionAdapter = new ArrayAdapter<>(requireContext(), R.layout.autocompletetextview_item, getResources().getStringArray(R.array.functions));

        functionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // set adapters on spinners
        personRankAutoCompleteTextView.setAdapter(rankAdapter);
        personFunctionSpinner.setAdapter(functionAdapter);
    }
}