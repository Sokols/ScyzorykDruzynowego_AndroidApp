package pl.sokols.scyzorykdruzynowego.ui.main.stamps;

import android.app.Activity;
import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou;

import pl.sokols.scyzorykdruzynowego.R;
import pl.sokols.scyzorykdruzynowego.data.entity.Stamp;
import pl.sokols.scyzorykdruzynowego.databinding.DialogStampBinding;
import pl.sokols.scyzorykdruzynowego.ui.main.stamps.adapters.StampTasksAdapter;

public class StampDialog extends Dialog {

    public Stamp stamp;

    public StampDialog(@NonNull Activity activity, Stamp stamp) {
        super(activity);
        this.stamp = stamp;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        DialogStampBinding binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.dialog_stamp, null, false);
        setContentView(binding.getRoot());
        binding.setStamp(stamp);
        binding.stampTasksDialogRecyclerView.setAdapter(new StampTasksAdapter(stamp.getStampTasks()));
        binding.stampTasksDialogRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        GlideToVectorYou.init().with(getContext()).load(Uri.parse(stamp.getStampImageURL()), binding.stampIconDialogImageView);
    }
}
