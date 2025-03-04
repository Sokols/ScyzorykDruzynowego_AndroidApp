package pl.sokols.scyzorykdruzynowego.ui.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import pl.sokols.scyzorykdruzynowego.R;
import pl.sokols.scyzorykdruzynowego.data.firebase.FirebaseAuthRepository;
import pl.sokols.scyzorykdruzynowego.databinding.ActivityMainBinding;
import pl.sokols.scyzorykdruzynowego.ui.start.StartActivity;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private FirebaseAuthRepository firebaseAuthRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuthRepository = new FirebaseAuthRepository(getApplication());
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(binding.mainActivityTopAppBar);
        setNavigation();
    }

    private void logOut() {
        firebaseAuthRepository.logOut();
        startActivity(new Intent(this, StartActivity.class));
        finish();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            case R.id.actionLogout:
                logoutUser();
                return true;

            case R.id.actionExit:
                closeApp();

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.top_app_bar, menu);
        return true;
    }

    private void setNavigation() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, new AppBarConfiguration.Builder(
                R.id.peopleFragment, R.id.stampsFragment, R.id.meetingsFragment).build());
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

    private void logoutUser() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this, R.style.MyDialogTheme);
        dialogBuilder.setTitle(getString(R.string.are_you_sure_title));
        dialogBuilder.setMessage(getString(R.string.are_you_sure_logout_description));
        dialogBuilder.setPositiveButton(getString(R.string.yes),
                (dialogInterface, whichButton) -> logOut());
        dialogBuilder.setNegativeButton(getString(R.string.no),
                (dialogInterface, i) -> { /* do nothing */ });
        dialogBuilder.create().show();
    }

    private void closeApp() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this, R.style.MyDialogTheme);
        dialogBuilder.setTitle(getString(R.string.are_you_sure_title));
        dialogBuilder.setMessage(getString(R.string.are_you_sure_close_app_description));
        dialogBuilder.setPositiveButton(getString(R.string.yes),
                (dialogInterface, whichButton) -> finish());
        dialogBuilder.setNegativeButton(getString(R.string.no),
                (dialogInterface, i) -> { /* do nothing */ });
        dialogBuilder.create().show();
    }
}