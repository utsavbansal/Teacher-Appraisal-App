package utsavbansal.teacher_evaluation_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class HomePage extends AppCompatActivity {

    String sirname;
    JSONArray response_name;
    Input_checker input;
    ImageView search1,search2;
    Button login;
    EditText edt1,edt2;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager manager;
    adpaterclass adapter,adapter_name,adapte_univ;
    Button name_search,univ_search,refresh;
    RequestQueue requestQueue,requestQueue_name;
    String url="https://teacherevaluation.000webhostapp.com/login_teacher.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        input=new Input_checker();
        login=(Button)findViewById(R.id.login);
        refresh=(Button)findViewById(R.id.refresh);
        edt1=(EditText) findViewById(R.id.edt1);
        edt2=(EditText) findViewById(R.id.edt2);
        requestQueue= Volley.newRequestQueue(this);
        requestQueue_name= Volley.newRequestQueue(this);
        name_search=(Button)findViewById(R.id.btnsearchname_home);
        univ_search=(Button)findViewById(R.id.btnsearchuniv_home);
        recyclerView=(RecyclerView)findViewById(R.id.myrecycler_home);
        manager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
         adapter=new adpaterclass();



        JsonArrayRequest request=new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try
                {
                    response_name=response;

                    for (int i = 0; i < response.length(); i++)
                    {
                        JSONObject object=(JSONObject)response.get(i);
                        if(object.getInt("eject")==0)
                        {
                            if(object.getString("gender").equals("Female"))
                                sirname="Mrs.";
                            else
                                sirname="Mr.";
                        adapter.addContents(sirname+object.getString("name"),object.getString("university"),R.drawable.arpit,object.getInt("id"),object.getString("image"));

                        adapter.notifyDataSetChanged();
                        }
                    }
                }
                catch (Exception ex){
                    Toast.makeText(HomePage.this,""+ex.getMessage(),Toast.LENGTH_SHORT).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(HomePage.this,""+error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }

        ){



        };
        requestQueue.add(request);
        recyclerView.setAdapter(adapter);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PopupMenu popupMenu=new PopupMenu(HomePage.this,login);
                popupMenu.getMenuInflater().inflate(R.menu.menu_main,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getTitle().equals("Student"))
                        {
                            Intent callstudent=new Intent(HomePage.this,StudentPortal.class);
                            startActivity(callstudent);
                        }
                        else if(item.getTitle().equals("Teacher"))
                        {
                            Intent callteacher=new Intent(HomePage.this,Login_teacher.class);
                            startActivity(callteacher);
                        }
                        else if(item.getTitle().equals("Admin"))
                        {
                            Intent calladmin=new Intent(HomePage.this,login_admin.class);
                            startActivity(calladmin);
                        }

                        return true;
                    }
                });
                popupMenu.show();

            }
        });


        name_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter_name=new adpaterclass();
                    if(input.check_name(edt1.getText().toString()))
                    {
                        try {
                            for(int i=0;i<response_name.length();i++)
                            {
                                JSONObject object=(JSONObject)response_name.get(i);

                                if(object.getInt("eject")==0) {

                                     if(object.getString("name").startsWith(edt1.getText().toString())||object.getString("name").toLowerCase().startsWith(edt1.getText().toString().toLowerCase())
                                             ||object.getString("name").toUpperCase().startsWith(edt1.getText().toString().toUpperCase())
                                             )
                                    {

                                        adapter_name.addContents(sirname+object.getString("name"),object.getString("university"),R.drawable.arpit,object.getInt("id"),object.getString("image"));

                                        adapter_name.notifyDataSetChanged();
                                    }
                                 }

                            }
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                        recyclerView.setAdapter(adapter_name);
                    }
                    else
                        Toast.makeText(HomePage.this,input.error,Toast.LENGTH_SHORT).show();







            }
        });
        univ_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter_name=new adpaterclass();

                if(input.check_univ(edt2.getText().toString()))
                {
                    try {
                        for(int i=0;i<response_name.length();i++)
                        {
                            JSONObject object=(JSONObject)response_name.get(i);

                            if(object.getInt("eject")==0) {
                                if(object.getString("university").startsWith(edt2.getText().toString())
                                        ||object.getString("university").toLowerCase().startsWith(edt2.getText().toString().toLowerCase())
                                        ||object.getString("university").toUpperCase().startsWith(edt2.getText().toString().toUpperCase())
                                        )
                                {

                                    adapter_name.addContents(sirname+object.getString("name"),object.getString("university"),R.drawable.arpit,object.getInt("id"),object.getString("image"));

                                    adapter_name.notifyDataSetChanged();
                                }
                            }

                        }
                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }

                    recyclerView.setAdapter(adapter_name);
                }
                else
                    Toast.makeText(HomePage.this,input.error,Toast.LENGTH_SHORT).show();




            }
        });


        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter_name=new adpaterclass();


                    try {
                        for(int i=0;i<response_name.length();i++)
                        {
                            JSONObject object=(JSONObject)response_name.get(i);
                            if(object.getInt("eject")==0) {
                                adapter_name.addContents(sirname+object.getString("name"), object.getString("university"), R.drawable.arpit, object.getInt("id"),object.getString("image"));

                                adapter_name.notifyDataSetChanged();
                            }
                        }
                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }




                recyclerView.setAdapter(adapter_name);

            }
        });


    }
}
