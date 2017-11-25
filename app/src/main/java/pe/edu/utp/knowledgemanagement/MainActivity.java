package pe.edu.utp.knowledgemanagement;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;

import ClassModel.AdapterPoll;
import ClassModel.DataConnection;
import ClassModel.Networks;
import DataModel.DataPoll;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private MenuItem itemMenuItem;
    private DataConnection conexion;
    private String function;
    private RecyclerView recycler;
    private RecyclerView.LayoutManager lManager;
    private Handler handler = new Handler();
    private ArrayList<DataPoll> listPoll = new ArrayList();
    private ProgressBar progressBar;
    private Networks networks;
    private boolean Network;
    private SwipeRefreshLayout swipeRefreshLayout;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        recycler = (RecyclerView) findViewById(R.id.recyclerView_Poll);
        recycler.setHasFixedSize(true);
        lManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(lManager);

        function = "getPoll";
        networks= new Networks(this);
        Network = networks.verificaConexion();
        conexion = new DataConnection(this, function, "", "", "");
        ejecutarPoll();
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        itemMenuItem = menu.findItem(R.id.action_clear);
        itemMenuItem.setVisible(false);
        itemMenuItem = menu.findItem(R.id.action_send);
        itemMenuItem.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
/*
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_createPoll) {
            Intent mainIntent1 = new Intent().setClass(MainActivity.this, CreatePoll.class);
            startActivity(mainIntent1);

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void ejecutarPoll(){
        final boolean[] run = {true};
        final int[] pStatus = {0};
        final int[] count = {0};
        new Thread(() -> {

                while (run[0]){
                    handler.post(() -> {

                            if (function.equals("getPoll")){
                                listPoll = conexion.getPoll();
                                count[0] = listPoll.size();

                            }
                            if (pStatus[0] < count[0]){
                                 run[0] = false;
                                if (function.equals("getPoll")){
                                    mostrarPoll();
                                }
                            }


                    });
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

        }).start();
    }
    private void mostrarPoll(){
        listPoll = conexion.getPoll();
        recycler.setAdapter(new AdapterPoll(listPoll, (v, position)-> {

        }));
    }

}
