package neuromancers.iitbbs.iitbhubaneswar;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private InstiAppUtil instiAppUtil = new InstiAppUtil();

    private ProgressBar progressBar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                setNavFragment(R.layout.settings);
                break;
            case R.id.action_about:
                setNavFragment(R.layout.about);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_map:
                setTitle("Campus Map");
                setNavFragment(R.layout.map_layout);
                break;
            case R.id.nav_gymkhana:
                setTitle("Students' Gymkhana");
                setNavFragment(R.layout.gymkhana);
                break;
            case R.id.nav_timetable:
                setTitle("Academic Time Table");
                setNavFragment(R.layout.timetable);
                break;
            case R.id.nav_calendar:
                setTitle("Academic Calendar");
                setNavFragment(R.layout.calendar);
                break;
            case R.id.nav_messMenu:
                setTitle("Mess Menu");
                setNavFragment(R.layout.mess_menu);
                break;
            case R.id.nav_transport:
                setTitle("Transportation Services");
                setNavFragment(R.layout.transport);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void redirectToWeb(View view) {
        instiAppUtil.onClick(view);
    }

    public void downloadFromWeb(View view) {
        switch (view.getId()) {
            case R.id.transport_pdf:
                String fileName = "transport";
                String fileExtension = "pdf";
                String website = "http://www.iitbbs.ac.in/transportation.php";

                IITBbsScraping iitBbsScraping = new IITBbsScraping(website, fileName, fileExtension, progressBar);
                iitBbsScraping.execute();

                break;
            case R.id.transport_xlsx:
                //application/vnd.ms-excel
//                String fileName = "transport";
//                String fileExtension = "xls";
//                String website = "http://www.iitbbs.ac.in/transportation.php";
//
//                ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
//                progressBar.setVisibility(View.VISIBLE);
//                IITBbsScraping iitBbsScraping = new IITBbsScraping(website, fileName, fileExtension, progressBar);
//                iitBbsScraping.execute();
        }
    }



    private void setNavFragment(int navLayout) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        NavFragment navFragment = new NavFragment();
        navFragment.setNewLayout(navLayout);

        fragmentTransaction.replace(R.id.content_frame, navFragment);
        fragmentTransaction.commit();
    }
}
