package com.softnet.manatv2;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.support.v7.app.ActionBarActivity;


import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import static android.R.attr.duration;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerView;

public class MainActivity extends AppCompatActivity {
    private ListView list_view_layout;
    private String[] list_of_actions={"Live","Browse","Ask a Query","About Us"};
    private DrawerLayout parent_layout;
    private final String DEVELOPER_KEY="AIzaSyDf3hOYFhHOxddHjScznC84yIrRhxNgDLg";
    private static final int RECOVERY_DIALOG_REQUEST = 1;
    private static final String VIDEO_ID = "Hb6dyye7qfg";
    public Toolbar toolbar;
    public android.support.v7.app.ActionBar my_ActionBar;
    public ActionBarDrawerToggle drawer_toggle;
    public Intent open_AboutUsActivity;
    YouTubePlayerSupportFragment myYouTubePlayerFragment;
    public class youtubeListener implements YouTubePlayer.OnInitializedListener{
        @Override
        public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult errorReason) {
            if (errorReason.isUserRecoverableError()) {
                errorReason.getErrorDialog(MainActivity.this, RECOVERY_DIALOG_REQUEST).show();
            } else {
                String errorMessage = String.format(
                        "There was an error initializing the YouTubePlayer (%1$s)",
                        errorReason.toString());
                Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_LONG).show();
            }
        }
        @Override
        public void onInitializationSuccess(Provider provider, YouTubePlayer player, boolean wasRestored) {
            if (!wasRestored) {
                player.cueVideo(VIDEO_ID);
            }
        }
    }
    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return (YouTubePlayerView)findViewById(R.id.youtube_fragment);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        open_AboutUsActivity=new Intent(this,AboutUsActivity.class);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        parent_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        list_view_layout= (ListView) findViewById(R.id.left_drawer);
        list_view_layout.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, list_of_actions));
        list_view_layout.setOnItemClickListener(new DrawerItemClickListener());
        my_ActionBar=getSupportActionBar();
        my_ActionBar.setTitle("Mana tv");
        myYouTubePlayerFragment=(YouTubePlayerSupportFragment)(getSupportFragmentManager().findFragmentById(R.id.youtube_fragment));

        myYouTubePlayerFragment.initialize(DEVELOPER_KEY, new youtubeListener());



        drawer_toggle = new ActionBarDrawerToggle(this, parent_layout ,R.string.drawer_open, R.string.drawer_close) {


            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                Log.i("hi","you entered");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }


            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                Log.i("hi","you entered");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        // Set the drawer toggle as the DrawerListener
        parent_layout.addDrawerListener(drawer_toggle);

        my_ActionBar.setDisplayHomeAsUpEnabled(true);
        my_ActionBar.setHomeButtonEnabled(true);
        my_ActionBar.setHomeAsUpIndicator(R.drawable.drawer_icon);
    }



    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(view,position,id);
        }
    }
    public void selectItem( View view, int position, long id){
        if(position==3){
            startActivity(open_AboutUsActivity);
        }
        Toast toast = Toast.makeText(toolbar.getContext(),"text has been clicked",Toast.LENGTH_SHORT);
        toast.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        /*if (drawer_toggle.onOptionsItemSelected(item)) {
            return true;
        }*/
        // Handle your other action bar items...
        drawer_toggle.onOptionsItemSelected(item);
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);

    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawer_toggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawer_toggle.onConfigurationChanged(newConfig);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == RECOVERY_DIALOG_REQUEST) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(DEVELOPER_KEY, new youtubeListener());
        }
    }
}
