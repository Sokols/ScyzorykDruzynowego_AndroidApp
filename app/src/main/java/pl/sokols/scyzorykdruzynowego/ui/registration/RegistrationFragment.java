package pl.sokols.scyzorykdruzynowego.ui.registration;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import pl.sokols.scyzorykdruzynowego.data.entity.User;
import pl.sokols.scyzorykdruzynowego.data.viewmodel.UserViewModel;

public class RegistrationFragment extends Fragment {

    @BindView(R.id.usernameRegistrationEditText)
    EditText usernameEditText;
    @BindView(R.id.passwordRegistrationEditText)
    EditText passwordEditText;
    @BindView(R.id.repeatPasswordRegistrationEditText)
    EditText repeatPasswordEditText;
    @BindView(R.id.usernameRegistrationTextInputLayout)
    TextInputLayout usernameTextInputLayout;
    @BindView(R.id.passwordRegistrationTextInputLayout)
    TextInputLayout passwordTextInputLayout;
    @BindView(R.id.repeatPasswordRegistrationTextInputLayout)
    TextInputLayout repeatPasswordTextInputLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registration, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.registerRegistrationButton)
    public void setRegisterButton() {
        // get typed data
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String password2 = repeatPasswordEditText.getText().toString();

        UserViewModel userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        // insert new user if every data is ok and return to the login fragment
        if (isAllDataCorrect(username, password, password2, userViewModel)) {
            userViewModel.insert(new User(username, password));
            Navigation.findNavController(requireView()).navigate(R.id.action_registration_to_login);
            Toast.makeText(getActivity(), getString(R.string.registration_completed), Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.loginRegistrationButton)
    public void setLoginButton() {
        Navigation.findNavController(requireView()).navigate(R.id.action_registration_to_login);
    }

    private boolean isAllDataCorrect(String username, String password, String password2, UserViewModel userViewModel) {
        // check that all data has been entered
        if (isAnyFieldEmpty(username, password, password2)) {
            Toast.makeText(getActivity(), getString(R.string.enter_all_data), Toast.LENGTH_SHORT).show();
            return false;
        }

        // check that both passwords are the same
        if (!password.equals(password2)) {
            Toast.makeText(getActivity(), getString(R.string.incorrect_second_password), Toast.LENGTH_SHORT).show();
            return false;
        }

        // check if username is unique
        if (userViewModel.checkItemByName(username) == 1) {
            Toast.makeText(getActivity(), getString(R.string.double_login), Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private boolean isAnyFieldEmpty(String username, String password, String password2) {
        // remove error texts
        usernameTextInputLayout.setError(null);
        passwordTextInputLayout.setError(null);
        repeatPasswordTextInputLayout.setError(null);

        // if yes - set error texts in empty required fields and return true
        if (username.isEmpty() || password.isEmpty() || password2.isEmpty()) {

            if (username.isEmpty()) {
                usernameTextInputLayout.setError(getString(R.string.required_error));
            }

            if (password.isEmpty()) {
                passwordTextInputLayout.setError(getString(R.string.required_error));
            }

            if (password2.isEmpty()) {
                repeatPasswordTextInputLayout.setError(getString(R.string.required_error));
            }

            return true;

        } else {
            return false;
        }
    }
}