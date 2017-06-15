package com.softnet.manatv2;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import static com.softnet.manatv2.R.id.toolbar;

public class AboutUsActivity extends AppCompatActivity {
    public Toolbar toolbar;
    public android.support.v7.app.ActionBar my_ActionBar;
    public ActionBarDrawerToggle drawer_toggle;
    public ListView list_view_layout;
    private DrawerLayout parent_layout;
    private String[] list_of_actions={"Live","Browse","Ask a Query","About Us"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        parent_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        my_ActionBar=getSupportActionBar();
        my_ActionBar.setTitle("About Us");
        list_view_layout= (ListView) findViewById(R.id.left_drawer);
        list_view_layout.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, list_of_actions));
        list_view_layout.setOnItemClickListener(new AboutUsActivity.DrawerItemClickListener());
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

        Toast toast = Toast.makeText(parent_layout.getContext(),"text has been clicked",Toast.LENGTH_SHORT);
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
        if (drawer_toggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);

    }
}
