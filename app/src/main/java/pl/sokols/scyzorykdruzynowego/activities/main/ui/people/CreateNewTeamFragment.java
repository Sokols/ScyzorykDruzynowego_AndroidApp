package pl.sokols.scyzorykdruzynowego.activities.main.ui.people;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.sokols.scyzorykdruzynowego.R;
import pl.sokols.scyzorykdruzynowego.data.entities.Team;
import pl.sokols.scyzorykdruzynowego.data.viewmodels.TeamViewModel;


public class CreateNewTeamFragment extends Fragment {

    @BindView(R.id.nameNewTeamEditText)
    EditText nameEditText;
    @BindView(R.id.dateNewPersonTextInputLayout)
    TextInputLayout nameTextInputLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_new_team, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.confirmNewTeamButton)
    public void setConfirmAddPersonButton() {
        // get typed data
        String teamName = nameEditText.getText().toString();

        TeamViewModel teamViewModel = new ViewModelProvider(this).get(TeamViewModel.class);

        // insert new team if every data is ok an return to the login fragment
        if (isAllDataCorrect(teamName)) {
            teamViewModel.insert(new Team(teamName));
            Navigation.findNavController(getView()).navigate(R.id.action_new_team_to_people);
            Toast.makeText(getActivity(), getString(R.string.added_new_team_completed), Toast.LENGTH_SHORT).show();;
        }
    }

    private boolean isAllDataCorrect(String teamName) {
        // remove error text
        nameTextInputLayout.setError(null);

        // check that all data has been entered
        if (teamName.isEmpty()) {
            Toast.makeText(getActivity(), getString(R.string.enter_all_data), Toast.LENGTH_SHORT).show();
            nameTextInputLayout.setError(getString(R.string.required_error));
            return false;
        }

        return true;
    }
}