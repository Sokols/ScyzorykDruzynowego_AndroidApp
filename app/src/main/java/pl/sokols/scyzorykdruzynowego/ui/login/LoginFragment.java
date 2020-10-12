package pl.sokols.scyzorykdruzynowego.ui.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.sokols.scyzorykdruzynowego.R;
import pl.sokols.scyzorykdruzynowego.ui.main.MainActivity;
import pl.sokols.scyzorykdruzynowego.data.viewmodel.UserViewModel;
import pl.sokols.scyzorykdruzynowego.utils.Utils;

import static pl.sokols.scyzorykdruzynowego.utils.Utils.USER_ID_SHARED_PREFS_KEY;
import static pl.sokols.scyzorykdruzynowego.utils.Utils.USER_LOGIN_SHARED_PREFS_KEY;
import static pl.sokols.scyzorykdruzynowego.utils.Utils.REMEMBER_ME_SHARED_PREFS_KEY;

public class LoginFragment extends Fragment {

    @BindView(R.id.usernameLoginEditText)
    EditText usernameEditText;
    @BindView(R.id.passwordLoginEditText)
    EditText passwordEditText;
    @BindView(R.id.rememberLoginCheckBox)
    CheckBox rememberMeCheckBox;
    @BindView(R.id.usernameLoginTextInputLayout)
    TextInputLayout usernameTextInputLayout;
    @BindView(R.id.passwordLoginTextInputLayout)
    TextInputLayout passwordTextInputLayout;

    private SharedPreferences sharedPreferences;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = requireContext().getSharedPreferences(Utils.SHARED_PREFS_KEY_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences.getBoolean(REMEMBER_ME_SHARED_PREFS_KEY, false)) {
            Utils.startNewActivity(requireActivity(), new MainActivity());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.registerLoginButton)
    public void setRegisterButton() {
        Navigation.findNavController(requireView()).navigate(R.id.action_login_to_registration);
    }

    @OnClick(R.id.loginLoginButton)
    public void setLoginButton() {
        if (isAllDataCorrect()) {
            Utils.startNewActivity(requireActivity(), new MainActivity());
        }
    }

    private boolean isAllDataCorrect() {
        // get data typed
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        UserViewModel userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        // check if all data typed
        if (isAnyFieldEmpty(username, password)) {
            Toast.makeText(getActivity(), getString(R.string.enter_all_data), Toast.LENGTH_SHORT).show();
            return false;
        }

        // check if typed username exists
        if (userViewModel.checkItemByName(username) != 1) {
            Toast.makeText(getActivity(), getString(R.string.incorrect_login), Toast.LENGTH_SHORT).show();
            return false;
        }

        // check if password is correct
        if (!userViewModel.getItemByName(username).getPassword().equals(password)) {
            Toast.makeText(getActivity(), getString(R.string.incorrect_password), Toast.LENGTH_SHORT).show();
            return false;
        }

        setSharedPreferences(userViewModel.getItemByName(username).getUserId());
        return true;
    }

    private boolean isAnyFieldEmpty(String username, String password) {
        // remove all error texts
        usernameTextInputLayout.setError(null);
        passwordTextInputLayout.setError(null);

        if (username.isEmpty() || password.isEmpty()) {

            if (username.isEmpty()) {
                usernameTextInputLayout.setError(getString(R.string.required_error));
            }

            if (password.isEmpty()) {
                passwordTextInputLayout.setError(getString(R.string.required_error));
            }

            return true;

        } else {
            return false;
        }
    }

    private void setSharedPreferences(int userId) {
        sharedPreferences.edit().putBoolean(REMEMBER_ME_SHARED_PREFS_KEY, rememberMeCheckBox.isChecked()).apply();
        sharedPreferences.edit().putString(USER_LOGIN_SHARED_PREFS_KEY, usernameEditText.getText().toString()).apply();
        sharedPreferences.edit().putInt(USER_ID_SHARED_PREFS_KEY, userId).apply();
    }
}