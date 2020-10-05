package pl.sokols.scyzorykdruzynowego.activities.start.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.sokols.scyzorykdruzynowego.R;
import pl.sokols.scyzorykdruzynowego.data.viewmodels.UserViewModel;
import pl.sokols.scyzorykdruzynowego.activities.main.MainActivity;

public class LoginFragment extends Fragment {

    @BindView(R.id.usernameLoginEditText)
    EditText usernameEditText;
    @BindView(R.id.passwordLoginEditText)
    EditText passwordEditText;
    @BindView(R.id.rememberLoginCheckBox)
    CheckBox rememberMeCheckBox;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.registerLoginButton)
    public void setRegisterButton() {
        Navigation.findNavController(getView()).navigate(R.id.action_login_to_registration);
    }

    @OnClick(R.id.loginLoginButton)
    public void setLoginButton() {
        if (isAllDataCorrect()) {
            startActivity(new Intent(getContext(), MainActivity.class));
            getActivity().finish();
        }
    }

    private boolean isAllDataCorrect() {
        // get data typed
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        UserViewModel userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        // check if all data typed
        if (username.isEmpty() || password.isEmpty()) {
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

        return true;
    }
}