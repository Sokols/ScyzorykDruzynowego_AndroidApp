package pl.sokols.scyzorykdruzynowego.ui.main.stamps;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import pl.sokols.scyzorykdruzynowego.data.entity.Stamp;
import pl.sokols.scyzorykdruzynowego.utils.Utils;

public class StampsViewModel extends AndroidViewModel {

    private MutableLiveData<List<Stamp>> stampsLiveData;

    public StampsViewModel(@NonNull Application application) {
        super(application);
        this.stampsLiveData = new MutableLiveData<>();
        initStampsList();
    }

    public MutableLiveData<List<Stamp>> getStampsLiveData() {
        return stampsLiveData;
    }

    private void initStampsList() {
        Gson gson = new GsonBuilder().create();
        Type listType = new TypeToken<ArrayList<Stamp>>(){}.getType();
        String jsonFileString = Utils.getJsonFromAssets(getApplication().getApplicationContext(), "stamps_db.json");
        List<Stamp> stamps = gson.fromJson(jsonFileString, listType);
        stampsLiveData.setValue(stamps);
    }
}
