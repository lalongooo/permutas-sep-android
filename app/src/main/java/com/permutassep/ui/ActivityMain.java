package com.permutassep.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.permutassep.R;
import com.permutassep.config.Config;
import com.permutassep.model.User;
import com.permutassep.utils.PrefUtils;

import br.kots.mob.complex.preferences.ComplexPreferences;

public class ActivityMain extends ActionBarActivity{

    public enum DrawerItems {
        HOME(1000),
        SETTINGS(1001);

        public int id;

        private DrawerItems(int id) {
            this.id = id;
        }
    }

    public Drawer.Result result;
    private Toolbar toolbar;

    private OnFilterChangedListener onFilterChangedListener;

    public void setOnFilterChangedListener(OnFilterChangedListener onFilterChangedListener) {
        this.onFilterChangedListener = onFilterChangedListener;
    }

    public interface OnFilterChangedListener {
        public void onFilterChanged(int filter);
    }

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

        toolbar= (Toolbar) findViewById(R.id.activity_main_toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        result = new Drawer()
                .withActivity(this)
                .withToolbar(toolbar)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(getString(R.string.app_nav_drawer_1)).withIdentifier(DrawerItems.HOME.id).withIcon(GoogleMaterial.Icon.gmd_home),
                        new DividerDrawerItem()
                        // new SecondaryDrawerItem().withName(getString(R.string.app_nav_drawer_2)).withIdentifier(DrawerItems.SETTINGS.id).withIcon(GoogleMaterial.Icon.gmd_settings)
                )
                .withSelectedItem(0)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l, IDrawerItem drawerItem) {
                        if(drawerItem != null){
                            replaceFragment(drawerItem.getIdentifier());
                        }
                    }
                })
                .build();
        result.getListView().setVerticalScrollBarEnabled(false);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new FragmentNewsFeed()).commit();
	}
	
	private void showTOSDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(getString(R.string.tos_dialog_text))
				.setPositiveButton(getString(R.string.tos_dialog_accept),dialogClickListener)
				.setNegativeButton(getString(R.string.tos_dialog_cancel),dialogClickListener).show();
	}

	DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			switch (which) {
			case DialogInterface.BUTTON_POSITIVE:
				// Save the state
			    getSharedPreferences(Config.APP_PREFERENCES_NAME, MODE_PRIVATE)
			        .edit()
			        .putBoolean("tos_accepted", false)
			        .commit();
				break;

			case DialogInterface.BUTTON_NEGATIVE:
				finish();
				break;
			}
		}
	};
    public boolean onCreateOptionsMenu(Menu menu) {

        Fragment f = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
        if (f instanceof FragmentNewsFeed){
            getMenuInflater().inflate(R.menu.main, menu);

            User user = ComplexPreferences.getComplexPreferences(this, Config.APP_PREFERENCES_NAME, Context.MODE_PRIVATE).getObject(PrefUtils.PREF_USER_KEY, User.class);
            if(user !=  null){
                menu.findItem(R.id.action_post).setIcon(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_add).color(Color.WHITE).actionBarSize()).setVisible(true);
            }

            menu.findItem(R.id.action_search).setIcon(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_search).color(Color.WHITE).actionBarSize());
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        replaceFragment(item.getItemId());
        return false;
    }

    public void replaceFragment(int id){

        if(id == DrawerItems.HOME.id && !(getSupportFragmentManager().findFragmentById(R.id.fragmentContainer) instanceof FragmentNewsFeed)){

            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new FragmentNewsFeed()).commit();

        }else if(id == DrawerItems.SETTINGS.id){

            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new FragmentSettings()).addToBackStack("settings").commit();

        }else if(id == R.id.action_post){

            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new FragmentCreatePost()).addToBackStack("create_post").commit();
            result.setSelection(-1);

        }else if(id == R.id.action_search){

            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new FragmentSearch()).addToBackStack("search").commit();
            result.setSelection(-1);
        }
    }

    @Override
    public void onBackPressed() {
        toolbar.setTitle(R.string.app_name);
        super.onBackPressed();
    }
}
