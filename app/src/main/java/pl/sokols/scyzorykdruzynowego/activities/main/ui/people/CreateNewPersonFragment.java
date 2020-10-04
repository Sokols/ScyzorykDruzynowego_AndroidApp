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

public class CreateNewPersonFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_new_person, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.confirmAddPersonButton)
    public void setConfirmAddPersonButton() {
        Navigation.findNavController(getView()).navigate(R.id.action_new_person_to_people);
    }
}