package pl.sokols.scyzorykdruzynowego.ui.people.adapters;

import android.app.Application;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import pl.sokols.scyzorykdruzynowego.data.entity.Person;
import pl.sokols.scyzorykdruzynowego.data.entity.Team;
import pl.sokols.scyzorykdruzynowego.data.repository.PersonRepository;
import pl.sokols.scyzorykdruzynowego.databinding.ListitemPersonBinding;
import pl.sokols.scyzorykdruzynowego.utils.Utils;

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
        }
    }

    private List<Person> mPersonList;
    private OnItemClickListener mListener;
    private PersonRepository mPersonRepository;

    public OneTeamAdapter(Team currentTeam, OnItemClickListener listener, Application application) {
        this.mPersonRepository = PersonRepository.getInstance(application, Utils.getUserId(application.getBaseContext()));
        this.mPersonList = mPersonRepository.getPeopleByTeamName(currentTeam.getTeamName());
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

    public void removeItem(int position) {
        mPersonRepository.delete(mPersonList.remove(position));
        notifyItemRemoved(position);
    }

    public void restoreItem(Person item, int position) {
        mPersonRepository.insert(item);
        mPersonList.add(position, item);
        notifyItemInserted(position);
    }

    public List<Person> getData() {
        return mPersonList;
    }
}
