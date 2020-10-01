package pl.sokols.scyzorykdruzynowego.activities.main.ui.meetings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MeetingsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MeetingsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}