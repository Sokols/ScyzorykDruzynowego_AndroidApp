package pl.sokols.scyzorykdruzynowego.activities.main.ui.people;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import pl.sokols.scyzorykdruzynowego.R;


public class CreateNewTeamFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_create_new_team, container, false);
    }
}