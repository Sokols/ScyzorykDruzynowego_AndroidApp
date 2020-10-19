package pl.sokols.scyzorykdruzynowego.ui.people.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.sokols.scyzorykdruzynowego.R;
import pl.sokols.scyzorykdruzynowego.data.entity.Person;
import pl.sokols.scyzorykdruzynowego.data.entity.Team;

public class PeopleAdapter extends RecyclerView.Adapter<PeopleAdapter.PeopleViewHolder> {

    static class PeopleViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.titleTeamTextView)
        TextView titleTeamTextView;
        @BindView(R.id.oneTeamRecyclerView)
        RecyclerView oneTeamRecyclerView;

        public PeopleViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private List<Team> mTeamList = new ArrayList<>();
    private List<Person> peopleByTeamNameList = new ArrayList<>();
    private Fragment mFragment;
    private LayoutInflater mInflater;
    private Context mContext;

    public PeopleAdapter(Context context, Fragment fragment) {
        this.mContext = context;
        this.mFragment = fragment;
        this.mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public PeopleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.listitem_team, parent, false);
        return new PeopleViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PeopleViewHolder holder, int position) {
        Team currentTeam = mTeamList.get(position);
        holder.titleTeamTextView.setText(mContext.getString(R.string.blank_team, currentTeam.getTeamName()));
        setTeamRecyclerView(currentTeam, holder);
    }

    @Override
    public int getItemCount() {
        return mTeamList == null ? 0 : mTeamList.size();
    }

    private void setTeamRecyclerView(Team currentTeam, PeopleViewHolder holder) {
        // prepare divider item decoration
        DividerItemDecoration itemDecoration = new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(mContext, R.drawable.item_divider)));

        // set recyclerview
        holder.oneTeamRecyclerView.setLayoutManager(new LinearLayoutManager(holder.oneTeamRecyclerView.getContext(), LinearLayoutManager.VERTICAL, false));
        holder.oneTeamRecyclerView.setAdapter(new OneTeamAdapter(getPeopleListByTeam(currentTeam.getTeamName()), mContext, getOnItemClickListener()));
        holder.oneTeamRecyclerView.addItemDecoration(itemDecoration);
        holder.oneTeamRecyclerView.setRecycledViewPool(viewPool);
    }

    private OneTeamAdapter.OnItemClickListener getOnItemClickListener() {
        return item -> {
            Bundle bundle = new Bundle();
            bundle.putParcelable(mContext.getString(R.string.person_data_key), item);
            Navigation.findNavController(mFragment.requireView()).navigate(R.id.action_people_to_edit_person, bundle);
        };
    }

    private List<Person> getPeopleListByTeam(String teamName) {
        List<Person> personList = new ArrayList<>();
        for (Person person : peopleByTeamNameList) {
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

    public void setPeopleByTeamNameList(List<Person> peopleList) {
        this.peopleByTeamNameList = peopleList;
        notifyDataSetChanged();
    }
}
