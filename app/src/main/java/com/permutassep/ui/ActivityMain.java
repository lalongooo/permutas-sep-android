package com.permutassep.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;
import com.permutassep.R;
import com.permutassep.config.Config;

public class ActivityMain extends ActionBarActivity {

    public enum DrawerItems {
        HOME(1000),
        SETTINGS(1001);

        public int id;

        private DrawerItems(int id) {
            this.id = id;
        }
    }

    public Drawer.Result result;

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

        final Toolbar toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        result = new Drawer()
                .withActivity(this)
                .withToolbar(toolbar)
                .withHeader(R.layout.header)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("Home").withIdentifier(DrawerItems.HOME.id).withIcon(GoogleMaterial.Icon.gmd_home),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withName("Settings").withIdentifier(DrawerItems.SETTINGS.id).withIcon(GoogleMaterial.Icon.gmd_settings)
                )
                .withSelectedItem(1)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l, IDrawerItem drawerItem) {
                        if (drawerItem != null) {
                            if (drawerItem instanceof Nameable) {
                                toolbar.setTitle(((Nameable) drawerItem).getName());
                            }
                        }
                        replaceFragment(drawerItem.getIdentifier());
                    }
                })
                .build();
        result.getListView().setVerticalScrollBarEnabled(false);
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        menu.findItem(R.id.action_post).setIcon(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_add).color(Color.WHITE).actionBarSize());

        return true;
    }

    private void replaceFragment(int id){
        if(id == DrawerItems.HOME.id){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new FragmentNewsFeed()).commit();
        }else if(id == DrawerItems.SETTINGS.id){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new FragmentSettings()).commit();
        }
    }
}
