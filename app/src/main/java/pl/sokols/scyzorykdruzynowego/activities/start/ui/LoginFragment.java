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

import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.sokols.scyzorykdruzynowego.R;
import pl.sokols.scyzorykdruzynowego.activities.main.MainActivity;
import pl.sokols.scyzorykdruzynowego.data.viewmodels.UserViewModel;

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
            startActivity(new Intent(getContext(), MainActivity.class));
            requireActivity().finish();
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
}