package pl.sokols.scyzorykdruzynowego.ui.people.adapters;

import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import pl.sokols.scyzorykdruzynowego.R;
import pl.sokols.scyzorykdruzynowego.data.entity.Person;
import pl.sokols.scyzorykdruzynowego.data.entity.Team;
import pl.sokols.scyzorykdruzynowego.data.repository.PersonRepository;
import pl.sokols.scyzorykdruzynowego.databinding.ListitemTeamBinding;
import pl.sokols.scyzorykdruzynowego.utils.Utils;

public class PeopleAdapter extends RecyclerView.Adapter<PeopleAdapter.PeopleViewHolder> {

    public static class PeopleViewHolder extends RecyclerView.ViewHolder {

        private ListitemTeamBinding mBinding;
        private Context mContext;
        private OneTeamAdapter mAdapter;
        private boolean isPermitForAnimation;

        public PeopleViewHolder(ListitemTeamBinding binding, Context context) {
            super(binding.getRoot());
            this.mContext = context;
            this.mBinding = binding;
        }

        public void bind(Team currentTeam, OneTeamAdapter.OnItemClickListener listener, List<Person> peopleList) {
            this.mAdapter = new OneTeamAdapter(peopleList, listener);
            this.isPermitForAnimation = true;
            setRecyclerView();
            enableSwipeToDeleteAndUndo();
            mBinding.setTeam(currentTeam);
            mBinding.setPeopleViewHolder(this);
        }

        public void handleMoreButton() {
            if (isPermitForAnimation) {
                isPermitForAnimation = false;
                if (mBinding.oneTeamRecyclerView.getVisibility() == View.VISIBLE) {
                    mBinding.oneTeamRecyclerView.setVisibility(View.GONE);
                } else {
                    mBinding.oneTeamRecyclerView.setVisibility(View.VISIBLE);
                }
                mBinding.moreImageButton
                        .animate()
                        .rotationBy(mBinding.oneTeamRecyclerView.getVisibility() == View.VISIBLE ? -180 : 180)
                        .withEndAction(() -> isPermitForAnimation = true);
            }
        }

        private void setRecyclerView() {
            LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
            mBinding.oneTeamRecyclerView.setLayoutManager(layoutManager);
            mBinding.oneTeamRecyclerView.setAdapter(mAdapter);
            mBinding.oneTeamRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
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
                            mBinding.oneTeamRecyclerView,
                            String.format(mContext.getString(R.string.person_removed), item.getName(), item.getSurname()),
                            Snackbar.LENGTH_LONG);
                    snackbar.setAction(mBinding.getRoot().getContext().getString(R.string.cancel), view -> {
                        mAdapter.restoreItem(item, position);
                        personRepository.insert(item);
                        mBinding.oneTeamRecyclerView.scrollToPosition(position);
                    });
                    snackbar.setActionTextColor(mContext.getColor(R.color.colorAccent));
                    snackbar.show();
                }
            };

            ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
            itemTouchhelper.attachToRecyclerView(mBinding.oneTeamRecyclerView);
        }
    }

    private List<Team> mTeamList = new ArrayList<>();
    private List<Person> mAllPeopleList = new ArrayList<>();
    private OneTeamAdapter.OnItemClickListener mListener;

    public PeopleAdapter(OneTeamAdapter.OnItemClickListener listener) {
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
        holder.bind(currentTeam, mListener, getPeopleListByTeam(currentTeam.getTeamName()));
    }

    @Override
    public int getItemCount() {
        return mTeamList == null ? 0 : mTeamList.size();
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
