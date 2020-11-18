package pl.sokols.scyzorykdruzynowego.ui.main.stamps;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import pl.sokols.scyzorykdruzynowego.R;
import pl.sokols.scyzorykdruzynowego.databinding.FragmentStampsBinding;
import pl.sokols.scyzorykdruzynowego.ui.main.stamps.adapters.StampsAdapter;

public class StampsFragment extends Fragment {

    private StampsViewModel viewModel;
    private FragmentStampsBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.viewModel = new ViewModelProvider(requireActivity()).get(StampsViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_stamps, container, false);
        binding.setStampsViewModel(viewModel);
        binding.setLifecycleOwner(this);
        initComponents();
        return binding.getRoot();
    }

    private void initComponents() {
        // init observer and recyclerview
        viewModel.getStampsLiveData().observe(getViewLifecycleOwner(), stamps -> {
            StampsAdapter adapter = new StampsAdapter(stamps, this, getOnStampClickListener());
            binding.allStampsRecyclerView.setAdapter(adapter);
            binding.allStampsRecyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 3, RecyclerView.VERTICAL, false));
        });
    }

    private StampsAdapter.OnStampClickListener getOnStampClickListener() {
      return item -> new StampDialog(requireActivity(), item).show();
    }
}