package pl.sokols.scyzorykdruzynowego.ui.main.stamps;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
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
    private StampsAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.viewModel = new ViewModelProvider(requireActivity()).get(StampsViewModel.class);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_stamps, container, false);
        binding.setStampsViewModel(viewModel);
        binding.setLifecycleOwner(this);
        initComponents();
        return binding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem searchItem = menu.findItem(R.id.actionSearch);
        searchItem.setVisible(true);
        SearchView searchView = (SearchView) searchItem.getActionView();
        EditText searchEditText = (EditText) searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        searchEditText.setTextColor(Color.WHITE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    private void initComponents() {
        // init observer and recyclerview
        viewModel.getStampsLiveData().observe(getViewLifecycleOwner(), stamps -> {
            adapter = new StampsAdapter(stamps, this, getOnStampClickListener());
            binding.allStampsRecyclerView.setAdapter(adapter);
            binding.allStampsRecyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 3, RecyclerView.VERTICAL, false));
        });
    }

    private StampsAdapter.OnStampClickListener getOnStampClickListener() {
        return item -> new StampDialog(requireActivity(), item).show();
    }
}