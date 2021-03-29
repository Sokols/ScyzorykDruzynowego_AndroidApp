package pl.sokols.scyzorykdruzynowego.ui.main.people.people.adapters;

import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import pl.sokols.scyzorykdruzynowego.R;
import pl.sokols.scyzorykdruzynowego.data.entity.Person;
import pl.sokols.scyzorykdruzynowego.data.entity.Team;
import pl.sokols.scyzorykdruzynowego.data.firebase.FirebaseUtils;
import pl.sokols.scyzorykdruzynowego.data.repository.PersonRepository;
import pl.sokols.scyzorykdruzynowego.databinding.ListitemTeamBinding;

public class PeopleAdapter extends RecyclerView.Adapter<PeopleAdapter.PeopleViewHolder> {

    public interface OnTeamClickListener {
        void onTeamClick(Team item);
    }

    public static class PeopleViewHolder extends RecyclerView.ViewHolder {

        private final ListitemTeamBinding mBinding;
        private final Context mContext;
        private OneTeamAdapter mAdapter;
        private boolean isPermitForAnimation;

        public PeopleViewHolder(ListitemTeamBinding binding, Context context) {
            super(binding.getRoot());
            this.mContext = context;
            this.mBinding = binding;
        }

        public void bind(Team currentTeam, OneTeamAdapter.OnPersonClickListener personListener, OnTeamClickListener teamListener, Application application) {
            this.mAdapter = new OneTeamAdapter(currentTeam, personListener, application);
            this.isPermitForAnimation = true;
            setRecyclerView();
            enableSwipeToDeleteAndUndo();
            mBinding.setTeam(currentTeam);
            mBinding.setPeopleViewHolder(this);

            // do not enable listener in "-" team
            // to ensure the inability to edit and delete
            if (!mBinding.getTeam().getTeamName().equals(mContext.getString(R.string.blank))) {
                mBinding.titleTeamTextView.setOnClickListener(view -> teamListener.onTeamClick(currentTeam));
            }
        }

        // expanding and collapsing recyclerview
        public void handleMoreButton() {
            // wait until previous animation finish
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
            mBinding.oneTeamRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
            mBinding.oneTeamRecyclerView.setAdapter(mAdapter);
            mBinding.oneTeamRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
            // necessary for correct scrolling in nested recyclerview
            ViewCompat.setNestedScrollingEnabled(mBinding.oneTeamRecyclerView, true);
        }

        // set the swiping to delete item from recyclerview
        private void enableSwipeToDeleteAndUndo() {
            ItemTouchHelper.Callback simpleItemTouchHelperCallback = new SimpleItemTouchHelperCallback(mContext) {
                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                    int position = viewHolder.getAdapterPosition();
                    Person item = mAdapter.getData().get(position);
                    PersonRepository personRepository = new PersonRepository((Application) mContext.getApplicationContext(), FirebaseUtils.getUserId());
                    personRepository.delete(item);
                    mAdapter.removeItem(position);

                    // snackbar allows to cancel item deletion
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

            ItemTouchHelper itemTouchhelper = new ItemTouchHelper(simpleItemTouchHelperCallback);
            itemTouchhelper.attachToRecyclerView(mBinding.oneTeamRecyclerView);
        }
    }

    private final OneTeamAdapter.OnPersonClickListener mPersonListener;
    private final PeopleAdapter.OnTeamClickListener mTeamListener;
    private final Application mApplication;
    private List<Team> mTeamList = new ArrayList<>();

    public PeopleAdapter(OneTeamAdapter.OnPersonClickListener personListener, PeopleAdapter.OnTeamClickListener teamListener, Application application) {
        this.mPersonListener = personListener;
        this.mTeamListener = teamListener;
        this.mApplication = application;
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
        holder.bind(currentTeam, mPersonListener, mTeamListener, mApplication);
    }

    @Override
    public int getItemCount() {
        return mTeamList == null ? 0 : mTeamList.size();
    }

    public void setTeamList(List<Team> teamList) {
        this.mTeamList = teamList;
        notifyDataSetChanged();
    }
}