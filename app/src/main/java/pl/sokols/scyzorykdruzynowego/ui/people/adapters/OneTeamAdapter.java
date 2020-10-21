package pl.sokols.scyzorykdruzynowego.ui.people.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import pl.sokols.scyzorykdruzynowego.data.entity.Person;
import pl.sokols.scyzorykdruzynowego.databinding.ListitemPersonBinding;

public class OneTeamAdapter extends RecyclerView.Adapter<OneTeamAdapter.OneTeamViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Person item);
    }

    static class OneTeamViewHolder extends RecyclerView.ViewHolder {

        private ListitemPersonBinding binding;

        public OneTeamViewHolder(ListitemPersonBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Person currentPerson, OnItemClickListener listener) {
            binding.setPerson(currentPerson);
            itemView.setOnClickListener(view -> listener.onItemClick(currentPerson));
            binding.executePendingBindings();
        }
    }

    private List<Person> mPersonList;
    private OnItemClickListener mListener;

    public OneTeamAdapter(List<Person> personList, OnItemClickListener listener) {
        this.mPersonList = personList;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public OneTeamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ListitemPersonBinding binding = ListitemPersonBinding.inflate(layoutInflater, parent, false);
        return new OneTeamViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull OneTeamViewHolder holder, int position) {
        holder.bind(mPersonList.get(position), mListener);
    }

    @Override
    public int getItemCount() {
        return mPersonList == null ? 0 : mPersonList.size();
    }
}
