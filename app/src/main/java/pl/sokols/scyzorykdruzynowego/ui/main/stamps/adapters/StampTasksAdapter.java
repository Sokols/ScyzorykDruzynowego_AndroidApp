package pl.sokols.scyzorykdruzynowego.ui.main.stamps.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import pl.sokols.scyzorykdruzynowego.databinding.ListitemStampTaskBinding;

public class StampTasksAdapter extends RecyclerView.Adapter<StampTasksAdapter.StampTasksViewHolder> {

    public static class StampTasksViewHolder extends RecyclerView.ViewHolder {

        ListitemStampTaskBinding mBinding;

        public StampTasksViewHolder(ListitemStampTaskBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        public void bind(String stampTask) {
            mBinding.setStampTask(stampTask);
        }
    }

    private List<String> mStampTasksList;

    public StampTasksAdapter(List<String> stampTasksList) {
        this.mStampTasksList = stampTasksList;
    }

    @NonNull
    @Override
    public StampTasksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ListitemStampTaskBinding binding = ListitemStampTaskBinding.inflate(layoutInflater, parent, false);
        return new StampTasksViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull StampTasksViewHolder holder, int position) {
        holder.bind(mStampTasksList.get(position));
    }

    @Override
    public int getItemCount() {
        return mStampTasksList == null ? 0 : mStampTasksList.size();
    }
}
