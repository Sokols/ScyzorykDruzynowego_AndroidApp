package pl.sokols.scyzorykdruzynowego.ui.people.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import pl.sokols.scyzorykdruzynowego.R;
import pl.sokols.scyzorykdruzynowego.data.entity.Person;
import pl.sokols.scyzorykdruzynowego.data.entity.Team;
import pl.sokols.scyzorykdruzynowego.databinding.ListitemTeamBinding;

public class PeopleAdapter extends RecyclerView.Adapter<PeopleAdapter.PeopleViewHolder> {

    static class PeopleViewHolder extends RecyclerView.ViewHolder {

        private ListitemTeamBinding binding;

        public PeopleViewHolder(ListitemTeamBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Team currentTeam, OneTeamAdapter.OnItemClickListener listener, DividerItemDecoration itemDecoration, List<Person> peopleList) {
            // bind team only
            if (peopleList != null) {
                setRecyclerView(listener, itemDecoration, peopleList);
                binding.setTeam(currentTeam);
                binding.executePendingBindings();
            }
        }

        private void setRecyclerView(OneTeamAdapter.OnItemClickListener listener, DividerItemDecoration itemDecoration, List<Person> peopleList) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(binding.oneTeamRecyclerView.getContext(), LinearLayoutManager.VERTICAL, false);
            binding.oneTeamRecyclerView.setLayoutManager(layoutManager);
            binding.oneTeamRecyclerView.setAdapter(new OneTeamAdapter(peopleList, listener));
            binding.oneTeamRecyclerView.addItemDecoration(itemDecoration);
            binding.oneTeamRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        }
    }

    private List<Team> mTeamList = new ArrayList<>();
    private List<Person> mAllPeopleList = new ArrayList<>();
    private Context mContext;
    private OneTeamAdapter.OnItemClickListener mListener;

    public PeopleAdapter(Context context, OneTeamAdapter.OnItemClickListener listener) {
        this.mContext = context;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public PeopleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ListitemTeamBinding binding = ListitemTeamBinding.inflate(layoutInflater, parent, false);
        return new PeopleViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PeopleViewHolder holder, int position) {
        Team currentTeam = mTeamList.get(position);
        holder.bind(currentTeam, mListener, getItemDecoration(), getPeopleListByTeam(currentTeam.getTeamName()));
    }

    @Override
    public int getItemCount() {
        return mTeamList == null ? 0 : mTeamList.size();
    }

    private DividerItemDecoration getItemDecoration() {
        DividerItemDecoration itemDecoration = new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(mContext, R.drawable.item_divider)));
        return itemDecoration;
    }

    private List<Person> getPeopleListByTeam(String teamName) {
        List<Person> personList = new ArrayList<>();
        for (Person person : mAllPeopleList) {
            if (person.getTeam().equals(teamName)) {
                personList.add(person);
            }
        }
        return personList;
    }

    public void setTeamList(List<Team> teamList) {
        this.mTeamList = teamList;
        notifyDataSetChanged();
    }

    public void setAllPeopleList(List<Person> peopleList) {
        this.mAllPeopleList = peopleList;
        notifyDataSetChanged();
    }
}
