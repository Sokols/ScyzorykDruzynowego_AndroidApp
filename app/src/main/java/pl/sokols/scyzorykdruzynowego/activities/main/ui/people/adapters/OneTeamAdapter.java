package pl.sokols.scyzorykdruzynowego.activities.main.ui.people.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.sokols.scyzorykdruzynowego.R;
import pl.sokols.scyzorykdruzynowego.data.entities.Person;

public class OneTeamAdapter extends RecyclerView.Adapter<OneTeamAdapter.OneTimeViewHolder> {

    static class OneTimeViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.namePersonTextView)
        TextView nameTextView;
        @BindView(R.id.rankPersonTextView)
        TextView rankTextView;

        public OneTimeViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private List<Person> mPersonList;
    private LayoutInflater mInflater;
    private Context mContext;

    public OneTeamAdapter(List<Person> personList, Context context) {
        this.mPersonList = personList;
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public OneTimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.listitem_person, parent, false);
        return new OneTimeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OneTimeViewHolder holder, int position) {
        Person currentPerson = mPersonList.get(position);
        holder.nameTextView.setText(String.format("%s %s", currentPerson.getName(), currentPerson.getSurname()));
        holder.rankTextView.setText(currentPerson.getRank());
    }

    @Override
    public int getItemCount() {
        return mPersonList.size();
    }
}
