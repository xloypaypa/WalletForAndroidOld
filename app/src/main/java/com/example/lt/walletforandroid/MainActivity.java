package com.example.lt.walletforandroid;

import interfaceTool.DataLoader;

import java.io.File;
import java.util.Vector;

import database.DataBase;
import database.HHD;
import logic.User;
import android.app.Activity;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.widget.DrawerLayout;

public class MainActivity extends ActionBarActivity implements
        NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the
     * navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in
     * {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    final ActionDialog lgad=new ActionDialog();
    final ActionDialog rgad=new ActionDialog();

    private static MainActivity page;
    private static int pos=1;

    public static void repaint(){
        page.onSectionAttached(pos);
    }

    public static void repaint(int aim){
        page.onSectionAttached(aim);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        loadDB();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager()
                .findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        page=this;

        lgad.setActivity(this);
        lgad.setLayout(R.layout.login_layout);
        lgad.setTitle("log in");
        lgad.build();
        lgad.setCancelAble(false);
        lgad.create();

        rgad.setActivity(this);
        rgad.setLayout(R.layout.register_layout);
        rgad.setTitle("register");
        rgad.build();
        rgad.setCancelAble(false);
        rgad.create();

        loadLoginAction(lgad, rgad);

        loadRegisterAction(lgad, rgad);

        lgad.show();
    }

    public String getSDPath(){
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED);   //判断sd卡是否存在
        if   (sdCardExist)
        {
            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
        }

        return sdDir.toString();

    }

    private void loadDB() {
        if (!HHD.folderExiste(getSDPath()+"/Wallet/")){
            HHD.createFolder(getSDPath(), "Wallet");
        }
        if (!HHD.fileExiste(getSDPath()+"/Wallet/wallet root file.txt")){
            HHD.createFile(getSDPath()+"/Wallet/", "wallet root file.txt");
            HHD.writeFile(getSDPath()+"/Wallet/wallet root file.txt", getSDPath()+"/Wallet/");
        }

        Vector <String> root=HHD.readFile(getSDPath()+"/Wallet/wallet root file.txt");
        DataBase.Root=root.get(0);

        User.loadUser();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void loadRegisterAction(final ActionDialog lgad,
                                    final ActionDialog rgad) {
        Button submit=(Button) rgad.getView().findViewById(R.id.register_action_submit);
        Button back=(Button) rgad.getView().findViewById(R.id.register_action_back);
        final TextView name=(TextView) rgad.getView().findViewById(R.id.register_action_name);
        final TextView pass=(TextView) rgad.getView().findViewById(R.id.register_action_password);
        final TextView rept=(TextView) rgad.getView().findViewById(R.id.register_action_repeat);
        final Spinner type=(Spinner) rgad.getView().findViewById(R.id.register_action_type);
        DataLoader.loadAllUserType(this, type);

        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                rgad.getDialog().dismiss();
                lgad.show();
            }
        });

        submit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String sname,spass,srep,tp;
                sname=name.getText().toString();
                spass=pass.getText().toString();
                srep=rept.getText().toString();
                tp=type.getSelectedItem().toString();

                if (sname.indexOf("[")>=0){
                    Toast.makeText(MainActivity.this, "please not use '['", Toast.LENGTH_SHORT).show();
                }
                if (sname.length()==0){
                    Toast.makeText(MainActivity.this, "please input user name", Toast.LENGTH_SHORT).show();
                }
                if (spass.length()==0){
                    Toast.makeText(MainActivity.this, "please input password", Toast.LENGTH_SHORT).show();
                }
                if (!srep.equals(spass)){
                    Toast.makeText(MainActivity.this, "two password not same", Toast.LENGTH_SHORT).show();
                }

                if (!User.userExist(sname)){
                    User.addUser(sname, spass,tp);
                    Toast.makeText(MainActivity.this, "success", Toast.LENGTH_SHORT).show();
                    rgad.getDialog().dismiss();
                    lgad.show();
                }else{
                    Toast.makeText(MainActivity.this, "user exist", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void loadLoginAction(final ActionDialog lgad,
                                 final ActionDialog rgad) {
        Button login=(Button) lgad.getView().findViewById(R.id.user_action_login);
        Button register=(Button) lgad.getView().findViewById(R.id.user_action_register);
        final TextView name=(TextView) lgad.getView().findViewById(R.id.register_action_name);
        final TextView pass=(TextView) lgad.getView().findViewById(R.id.register_action_password);

        login.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String s1,s2;
                s1=name.getText().toString();
                s2=pass.getText().toString();
                if (User.checkUser(s1, s2)){
                    User.login(s1, s2);
                    onSectionAttached(1);
                    lgad.getDialog().dismiss();
                    StatusChartFragment.canDraw=true;
                    StatusChartFragment.draw();
                }else{
                    Toast.makeText(MainActivity.this, "user name or password wrong!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        register.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                lgad.getDialog().dismiss();
                rgad.show();
            }
        });
    };

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager
                .beginTransaction()
                .replace(R.id.container,
                        PlaceholderFragment.newInstance(position + 1)).commit();
    }

    public void onSectionAttached(int number) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_status);
                pos=1;
                StatusPageFragment spf=new StatusPageFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, spf).commit();
                break;
            case 2:
                mTitle = getString(R.string.title_debt);
                pos=2;
                DebtFragment df=new DebtFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, df).commit();
                break;
            case 3:
                mTitle = getString(R.string.title_detail);
                pos=3;
                DetailFragment detail=new DetailFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, detail).commit();
                break;
            case 4:
                mTitle = getString(R.string.title_reason);
                pos=4;
//                ReasonFragment rf=new ReasonFragment();
                ReasonPageFragment rp=new ReasonPageFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, rp).commit();
                break;
            case 5:
                mTitle = getString(R.string.title_setting);
                pos=5;
                SettingFragment st=new SettingFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, st).commit();
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container,
                    false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(getArguments().getInt(
                    ARG_SECTION_NUMBER));
        }
    }

}
