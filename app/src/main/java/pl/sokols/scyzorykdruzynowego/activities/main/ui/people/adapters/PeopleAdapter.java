package pl.sokols.scyzorykdruzynowego.activities.main.ui.people.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.sokols.scyzorykdruzynowego.R;
import pl.sokols.scyzorykdruzynowego.data.entities.Person;
import pl.sokols.scyzorykdruzynowego.data.entities.Team;

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
    private LayoutInflater mInflater;
    private Context mContext;

    public PeopleAdapter(Context context) {
        this.mContext = context;
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
        LinearLayoutManager layoutManager = new LinearLayoutManager(holder.oneTeamRecyclerView.getContext(), LinearLayoutManager.VERTICAL, false);
        layoutManager.setInitialPrefetchItemCount(peopleByTeamNameList.size());
        OneTeamAdapter oneTeamAdapter = new OneTeamAdapter(getPeopleListByTeam(currentTeam.getTeamName()), mContext);
        holder.oneTeamRecyclerView.setLayoutManager(layoutManager);
        holder.oneTeamRecyclerView.setAdapter(oneTeamAdapter);
        holder.oneTeamRecyclerView.setRecycledViewPool(viewPool);
    }

    @Override
    public int getItemCount() {
        return mTeamList.size();
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
