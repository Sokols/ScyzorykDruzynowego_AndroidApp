package pl.sokols.scyzorykdruzynowego.activities.start.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.sokols.scyzorykdruzynowego.R;
import pl.sokols.scyzorykdruzynowego.activities.main.MainActivity;

public class LoginFragment extends Fragment {

    @BindView(R.id.usernameEditText) EditText usernameEditText;
    @BindView(R.id.passwordEditText) EditText passwordEditText;
    @BindView(R.id.rememberCheckBox) CheckBox rememberMeCheckBox;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @OnClick(R.id.registerButton)
    public void setRegisterButton() {
        Navigation.findNavController(getView()).navigate(R.id.action_login_to_registration);
    }

    @OnClick(R.id.loginButton)
    public void setLoginButton() {
        startActivity(new Intent(getContext(), MainActivity.class));
        getActivity().finish();
    }
}