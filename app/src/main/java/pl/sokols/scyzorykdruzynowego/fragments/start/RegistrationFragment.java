package pl.sokols.scyzorykdruzynowego.fragments.start;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.sokols.scyzorykdruzynowego.R;

public class RegistrationFragment extends Fragment {

    @BindView(R.id.usernameRegistrationEditText) EditText usernameEditText;
    @BindView(R.id.passwordRegistrationEditText) EditText passwordEditText;
    @BindView(R.id.repeatPasswordRegistrationEditText) EditText repeatPasswordEditText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registration, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.registerRegistrationButton)
    public void setRegisterButton() {

    }

    @OnClick(R.id.loginRegistrationButton)
    public void setLoginButton() {
        Navigation.findNavController(getView()).navigate(R.id.action_registration_to_login);
    }
}