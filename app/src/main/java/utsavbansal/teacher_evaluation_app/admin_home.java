package utsavbansal.teacher_evaluation_app;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class admin_home extends AppCompatActivity {
    public static int user_id;
    AppBarLayout appBarLayout;
    TabLayout tabLayout;
    ViewPager viewPager;
  //  Helper helper;
    TextView email,name,contact,pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        appBarLayout=(AppBarLayout)findViewById(R.id.myappbarlayout);
        tabLayout=(TabLayout)findViewById(R.id.mytablayout);
        viewPager=(ViewPager)findViewById(R.id.myviewpager);
        Bundle bd=getIntent().getExtras();
        int id=bd.getInt("unique_id");
    //    helper=new Helper(this);
      //  Cursor cv=helper.getObject(id);
        MyPagerAdapter adapter=new MyPagerAdapter(getSupportFragmentManager());
        adapter.addContents(new admin_profile(),"Profile");
        adapter.addContents(new vaidate_professors(),"Professors");
        adapter.addContents(new vaidate_students(),"Students");
        adapter.addContents(new validate_comment(),"Comments");
        adapter.addContents(new fragment_register_admin(),"New Admin");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        user_id=id;
        //user_id=cv.getInt(0);

    }


}

