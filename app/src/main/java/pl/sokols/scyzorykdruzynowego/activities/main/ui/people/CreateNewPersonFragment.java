package pl.sokols.scyzorykdruzynowego.activities.main.ui.people;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.sokols.scyzorykdruzynowego.R;
import pl.sokols.scyzorykdruzynowego.data.entities.Person;
import pl.sokols.scyzorykdruzynowego.data.viewmodels.PersonViewModel;
import pl.sokols.scyzorykdruzynowego.data.viewmodels.TeamViewModel;
import pl.sokols.scyzorykdruzynowego.utils.Utils;

public class CreateNewPersonFragment extends Fragment {

    @BindView(R.id.nameNewPersonEditText)
    EditText nameEditText;
    @BindView(R.id.surnameNewPersonEditText)
    EditText surnameEditText;
    @BindView(R.id.dateNewPersonEditText)
    EditText dateEditText;
    @BindView(R.id.rankNewPersonAutoCompleteTextView)
    AutoCompleteTextView rankAutoCompleteTextView;
    @BindView(R.id.teamNewPersonAutoCompleteTextView)
    AutoCompleteTextView teamAutoCompleteTextView;
    @BindView(R.id.functionNewPersonAutoCompleteTextView)
    AutoCompleteTextView functionAutoCompleteTextView;
    @BindViews({R.id.nameNewPersonTextInputLayout, R.id.surnameNewPersonTextInputLayout,
            R.id.dateNewPersonTextInputLayout, R.id.rankNewPersonTextInputLayout,
            R.id.teamNewPersonTextInputLayout, R.id.functonNewPersonTextInputLayout})
    List<TextInputLayout> textInputLayouts;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_new_person, container, false);
        ButterKnife.bind(this, view);
        setAdapters();
        return view;
    }

    @OnClick(R.id.confirmAddPersonButton)
    public void setConfirmAddPersonButton() {
        // get typed data
        String name = nameEditText.getText().toString();
        String surname = surnameEditText.getText().toString();
        String date = dateEditText.getText().toString();
        String rank = rankAutoCompleteTextView.getText().toString();
        String team = teamAutoCompleteTextView.getText().toString();
        String function = functionAutoCompleteTextView.getText().toString();

        PersonViewModel personViewModel = new ViewModelProvider(this).get(PersonViewModel.class);

        // insert new person if every data is ok and return to the people fragment
        if (isRequiredFieldsCorrect(name, surname) && isDateFormatCorrect(date)) {
            try {
                personViewModel.insert(new Person(name, surname, Utils.getDateFromString(date), rank, team, function));
            } catch (ParseException e) {
                e.printStackTrace();
                personViewModel.insert(new Person(name, surname, null, rank, team, function));
            } finally {
                Toast.makeText(getActivity(), getString(R.string.added_new_person_completed), Toast.LENGTH_SHORT).show();
                Navigation.findNavController(requireView()).navigate(R.id.action_new_person_to_people);
            }
        }
    }

    private boolean isRequiredFieldsCorrect(String name, String surname) {
        // remove error texts
        textInputLayouts.get(0).setError(null);
        textInputLayouts.get(1).setError(null);

        // if yes - set error texts in empty required fields and return true
        if (name.isEmpty() || surname.isEmpty()) {

            if (name.isEmpty()) {
                textInputLayouts.get(0).setError(getString(R.string.required_error));
            }

            if (surname.isEmpty()) {
                textInputLayouts.get(1).setError(getString(R.string.required_error));
            }

            Toast.makeText(getActivity(), getString(R.string.enter_all_data), Toast.LENGTH_SHORT).show();
            return false;

        } else {
            return true;
        }
    }

    private boolean isDateFormatCorrect(String date) {
        // remove error text
        textInputLayouts.get(2).setError(null);

        if (!date.isEmpty()) {
            try {
                Utils.getDateFromString(date);
            } catch (ParseException e) {
                e.printStackTrace();
                textInputLayouts.get(2).setError(getString(R.string.date_format_error));
                return false;
            }
        }
        return true;
    }

    private void setAdapters() {
        // prepare adapters
        ArrayAdapter<String> rankAdapter = new ArrayAdapter<>(requireContext(), R.layout.dropdown_menu_popup_item, getResources().getStringArray(R.array.ranks));
        ArrayAdapter<String> teamAdapter = new ArrayAdapter<>(requireContext(), R.layout.dropdown_menu_popup_item, getTeamsFromDB());
        ArrayAdapter<String> functionAdapter = new ArrayAdapter<>(requireContext(), R.layout.dropdown_menu_popup_item, getResources().getStringArray(R.array.functions));

        // set adapters on ACTV
        rankAutoCompleteTextView.setAdapter(rankAdapter);
        teamAutoCompleteTextView.setAdapter(teamAdapter);
        functionAutoCompleteTextView.setAdapter(functionAdapter);
    }

    private String[] getTeamsFromDB() {
        TeamViewModel teamViewModel = new ViewModelProvider(this).get(TeamViewModel.class);
        List<String> teams = teamViewModel.getAllTeamNames();
        String [] teamNames = new String[teams.size()];
        for (int i = 0; i < teams.size(); i++) {
            teamNames[i] = teams.get(i);
        }
        return teamNames;
    }
}