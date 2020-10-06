package pl.sokols.scyzorykdruzynowego.activities.main.ui.people;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.sokols.scyzorykdruzynowego.R;


public class CreateNewTeamFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_new_team, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.confirmNewTeamButton)
    public void setConfirmAddPersonButton() {
        Navigation.findNavController(getView()).navigate(R.id.action_new_team_to_people);
    }
}