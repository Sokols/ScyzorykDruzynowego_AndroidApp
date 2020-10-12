package pl.sokols.scyzorykdruzynowego.ui.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.sokols.scyzorykdruzynowego.R;
import pl.sokols.scyzorykdruzynowego.ui.start.StartActivity;
import pl.sokols.scyzorykdruzynowego.utils.Utils;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.nav_view)
    BottomNavigationView navView;
    @BindView(R.id.mainActivityTopAppBar)
    Toolbar mainToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(mainToolbar);
        setNavigation();
    }

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
                R.id.navigation_people, R.id.navigation_todo, R.id.navigation_meetings).build());
        NavigationUI.setupWithNavController(navView, navController);
    }

    private void logoutUser() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this, R.style.MyDialogTheme);
        dialogBuilder.setTitle(getString(R.string.are_you_sure_title));
        dialogBuilder.setMessage(getString(R.string.are_you_sure_logout_description));
        dialogBuilder.setPositiveButton(getString(R.string.yes),
                (dialogInterface, whichButton) -> {
                    SharedPreferences sharedPreferences = getSharedPreferences(Utils.SHARED_PREFS_KEY_NAME, MODE_PRIVATE);
                    sharedPreferences.edit().putBoolean(Utils.REMEMBER_ME_SHARED_PREFS_KEY, false).apply();
                    startActivity(new Intent(this, StartActivity.class));
                    finish();
                });
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