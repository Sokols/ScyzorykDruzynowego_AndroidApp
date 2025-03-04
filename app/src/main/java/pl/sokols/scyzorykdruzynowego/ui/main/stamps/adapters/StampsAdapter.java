package pl.sokols.scyzorykdruzynowego.ui.main.stamps.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import pl.sokols.scyzorykdruzynowego.R;
import pl.sokols.scyzorykdruzynowego.data.entity.Stamp;
import pl.sokols.scyzorykdruzynowego.databinding.ListitemStampBinding;
import pl.sokols.scyzorykdruzynowego.ui.main.stamps.StampsFragment;
import pl.sokols.scyzorykdruzynowego.utils.Utils;

public class StampsAdapter extends RecyclerView.Adapter<StampsAdapter.StampsViewHolder> implements Filterable {

    public interface OnStampClickListener {
        void onStampClick(Stamp stamp);
    }

    public static class StampsViewHolder extends RecyclerView.ViewHolder {

        private final ListitemStampBinding mBinding;
        private final StampsFragment mFragment;

        public StampsViewHolder(ListitemStampBinding binding, StampsFragment fragment) {
            super(binding.getRoot());
            this.mBinding = binding;
            this.mFragment = fragment;
        }

        public void bind(Stamp stamp, OnStampClickListener listener) {
            mBinding.setStamp(stamp);
            Glide.with(mFragment)
                    .load(Utils.fromUri(mFragment.requireContext(), "stamps/" + stamp.getStampId() + ".png"))
                    .error(R.drawable.ic_baseline_broken_image_24)
                    .into(mBinding.stampIcon);
            itemView.setOnClickListener(view -> listener.onStampClick(stamp));
        }
    }

    private final Filter mValueFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Stamp> filteredStampsList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredStampsList.addAll(mStampListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Stamp stamp : mStampListFull) {
                    if (stamp.getStampName().toLowerCase().contains(filterPattern)) {
                        filteredStampsList.add(stamp);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredStampsList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mStampList.clear();
            mStampList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
    private final List<Stamp> mStampList;
    private final List<Stamp> mStampListFull;
    private final StampsFragment mFragment;
    private final OnStampClickListener mListener;

    public StampsAdapter(List<Stamp> stampList, StampsFragment fragment, OnStampClickListener listener) {
        this.mStampList = stampList;
        this.mStampListFull = new ArrayList<>(stampList);
        this.mFragment = fragment;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public StampsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ListitemStampBinding binding = ListitemStampBinding.inflate(layoutInflater, parent, false);
        return new StampsViewHolder(binding, mFragment);
    }

    @Override
    public void onBindViewHolder(@NonNull StampsViewHolder holder, int position) {
        holder.bind(mStampList.get(position), mListener);
    }

    @Override
    public int getItemCount() {
        return mStampList == null ? 0 : mStampList.size();
    }

    @Override
    public Filter getFilter() {
        return mValueFilter;
    }
}
