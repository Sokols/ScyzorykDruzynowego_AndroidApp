package pl.sokols.scyzorykdruzynowego.activities.main.ui.people.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
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

    private List<Team> mTeamList = new ArrayList<>();
    private List<Person> mAllPeopleList = new ArrayList<>();
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
        OneTeamAdapter oneTeamAdapter = new OneTeamAdapter(getPeopleListByTeam(currentTeam.getTeamName()), mContext);
        holder.titleTeamTextView.setText(currentTeam.getTeamName());
        holder.oneTeamRecyclerView.setAdapter(oneTeamAdapter);
    }

    @Override
    public int getItemCount() {
        return mTeamList.size();
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

    public void setAllPeopleList(List<Person> allPeopleList) {
        this.mAllPeopleList = allPeopleList;
        notifyDataSetChanged();
    }
}
