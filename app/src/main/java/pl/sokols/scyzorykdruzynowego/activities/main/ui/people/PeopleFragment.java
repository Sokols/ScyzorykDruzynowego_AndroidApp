package pl.sokols.scyzorykdruzynowego.activities.main.ui.people;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import pl.sokols.scyzorykdruzynowego.R;


public class PeopleFragment extends Fragment {

    private PeopleViewModel peopleViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        peopleViewModel =
                ViewModelProviders.of(this).get(PeopleViewModel.class);

        return inflater.inflate(R.layout.fragment_people, container, false);
    }
}