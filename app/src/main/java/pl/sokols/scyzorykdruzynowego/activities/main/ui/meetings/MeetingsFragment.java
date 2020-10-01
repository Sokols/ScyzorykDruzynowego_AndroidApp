package pl.sokols.scyzorykdruzynowego.activities.main.ui.meetings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import pl.sokols.scyzorykdruzynowego.R;


public class MeetingsFragment extends Fragment {

    private MeetingsViewModel meetingsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        meetingsViewModel =
                ViewModelProviders.of(this).get(MeetingsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_meetings, container, false);
        final TextView textView = root.findViewById(R.id.text_meetings);
        meetingsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}