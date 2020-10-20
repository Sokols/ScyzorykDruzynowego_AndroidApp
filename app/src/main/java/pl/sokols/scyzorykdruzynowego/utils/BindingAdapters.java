package pl.sokols.scyzorykdruzynowego.utils;

import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.databinding.BindingAdapter;

import pl.sokols.scyzorykdruzynowego.R;

public class BindingAdapters {

    @BindingAdapter("adapter")
    public static void setAdapter(AutoCompleteTextView view, String[] adapter) {
        ArrayAdapter<String> newAdapter = new ArrayAdapter<>(view.getContext(), R.layout.dropdown_menu_popup_item, adapter);
        view.setAdapter(newAdapter);
    }
}
