package pl.sokols.scyzorykdruzynowego.ui.main.people.people.adapters;

import android.app.Application;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import pl.sokols.scyzorykdruzynowego.data.entity.Person;
import pl.sokols.scyzorykdruzynowego.data.entity.Team;
import pl.sokols.scyzorykdruzynowego.data.firebase.FirebaseUtils;
import pl.sokols.scyzorykdruzynowego.data.repository.PersonRepository;
import pl.sokols.scyzorykdruzynowego.databinding.ListitemPersonBinding;

public class OneTeamAdapter extends RecyclerView.Adapter<OneTeamAdapter.OneTeamViewHolder> {

    public interface OnPersonClickListener {
        void onPersonClick(Person item);
    }

    public static class OneTeamViewHolder extends RecyclerView.ViewHolder {

        private final ListitemPersonBinding binding;

        public OneTeamViewHolder(ListitemPersonBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Person currentPerson, OnPersonClickListener listener) {
            binding.setPerson(currentPerson);
            itemView.setOnClickListener(view -> listener.onPersonClick(currentPerson));
        }
    }

    private final List<Person> mPersonList;
    private final OnPersonClickListener mListener;
    private final PersonRepository mPersonRepository;

    public OneTeamAdapter(Team currentTeam, OnPersonClickListener listener, Application application) {
        this.mPersonRepository = new PersonRepository(application, FirebaseUtils.getUserId());
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
