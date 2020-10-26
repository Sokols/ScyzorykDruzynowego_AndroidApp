package pl.sokols.scyzorykdruzynowego.ui.people.adapters;

import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import pl.sokols.scyzorykdruzynowego.R;
import pl.sokols.scyzorykdruzynowego.data.entity.Person;
import pl.sokols.scyzorykdruzynowego.data.entity.Team;
import pl.sokols.scyzorykdruzynowego.data.repository.PersonRepository;
import pl.sokols.scyzorykdruzynowego.databinding.ListitemTeamBinding;
import pl.sokols.scyzorykdruzynowego.utils.Utils;

public class PeopleAdapter extends RecyclerView.Adapter<PeopleAdapter.PeopleViewHolder> {

    static class PeopleViewHolder extends RecyclerView.ViewHolder {

        private ListitemTeamBinding binding;
        private Context mContext;
        private OneTeamAdapter mAdapter;
        private DividerItemDecoration mItemDecoration;

        public PeopleViewHolder(ListitemTeamBinding binding, Context context) {
            super(binding.getRoot());
            this.mContext = context;
            this.binding = binding;
        }

        public void bind(Team currentTeam, OneTeamAdapter.OnItemClickListener listener, DividerItemDecoration itemDecoration, List<Person> peopleList) { ;
            this.mAdapter = new OneTeamAdapter(peopleList, listener);
            this.mItemDecoration = itemDecoration;

            if (peopleList != null) {
                setRecyclerView();
                enableSwipeToDeleteAndUndo();
                binding.setTeam(currentTeam);
                binding.executePendingBindings();
            }
        }

        private void setRecyclerView() {
            LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
            binding.oneTeamRecyclerView.setLayoutManager(layoutManager);
            binding.oneTeamRecyclerView.setAdapter(mAdapter);
            binding.oneTeamRecyclerView.addItemDecoration(mItemDecoration);
            binding.oneTeamRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        }

        private void enableSwipeToDeleteAndUndo() {
            SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(mContext) {
                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                    final int position = viewHolder.getAdapterPosition();
                    final Person item = mAdapter.getData().get(position);
                    PersonRepository personRepository = PersonRepository.getInstance((Application) mContext.getApplicationContext(), Utils.getUserId(mContext));
                    personRepository.delete(item);
                    mAdapter.removeItem(position);

                    Snackbar snackbar = Snackbar.make(
                            binding.oneTeamRecyclerView,
                            String.format(mContext.getString(R.string.person_removed), item.getName(), item.getSurname()),
                            Snackbar.LENGTH_LONG);
                    snackbar.setAction(binding.getRoot().getContext().getString(R.string.cancel), view -> {
                        mAdapter.restoreItem(item, position);
                        personRepository.insert(item);
                        binding.oneTeamRecyclerView.scrollToPosition(position);
                    });
                    snackbar.setActionTextColor(mContext.getColor(R.color.colorAccent));
                    snackbar.show();
                }
            };

            ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
            itemTouchhelper.attachToRecyclerView(binding.oneTeamRecyclerView);
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
        return new PeopleViewHolder(binding, parent.getContext());
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
            if (person.getTeam() != null && person.getTeam().equals(teamName)) {
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
