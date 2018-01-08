package utsavbansal.teacher_evaluation_app;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class vaidate_professors extends Fragment {
    String sirname;
    JSONArray response_name;
    Input_checker input;
    EditText name_profess,univ_profess;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager manager;
    RequestQueue requestQueue;
    String url="https://teacherevaluation.000webhostapp.com/login_teacher.php";
    MyRecyclerAdapter adapter,adapter_name;

    Button search_byname,search_byuniv,refresh;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vaidate_professors, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        input=new Input_checker();
        recyclerView=(RecyclerView)getActivity().findViewById(R.id.myrecycler_profess);
        manager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);
        adapter=new MyRecyclerAdapter();
        requestQueue= Volley.newRequestQueue(getContext());
        search_byname=(Button)getActivity().findViewById(R.id.search_name_profess);
        search_byuniv=(Button)getActivity().findViewById(R.id.search_univ_profess);
        name_profess=(EditText)getActivity().findViewById(R.id.name_search_profess);
        univ_profess=(EditText)getActivity().findViewById(R.id.univ_search_profess);
        refresh=(Button)getActivity().findViewById(R.id.refresh_profess);
        JsonArrayRequest request=new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try
                {
                    response_name=response;
                    for (int i = 0; i < response.length(); i++)
                    {
                        JSONObject object=(JSONObject)response.get(i);
                        if(object.getString("gender").equals("Female"))
                            sirname="Mrs.";
                        else
                            sirname="Mr.";

                        adapter.addContents(sirname+object.getString("name"),object.getString("university"),R.drawable.arpit,object.getInt("id"),object.getString("image"));

                        adapter.notifyDataSetChanged();
                    }
                }
                catch (Exception ex){
                    Toast.makeText(getActivity(),""+ex.getMessage(),Toast.LENGTH_SHORT).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(),""+error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }

        ){



        };

        requestQueue.add(request);



        recyclerView.setAdapter(adapter);


        search_byname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter_name=new MyRecyclerAdapter();
                if(input.check_name(name_profess.getText().toString()))
                {
                    try {
                        for(int i=0;i<response_name.length();i++)
                        {
                            JSONObject object=(JSONObject)response_name.get(i);
                            if(object.getString("gender").equals("Female"))
                                sirname="Mrs.";
                            else
                                sirname="Mr.";


                                if(object.getString("name").startsWith(name_profess.getText().toString())
                                        ||object.getString("name").toLowerCase().startsWith(name_profess.getText().toString().toLowerCase())
                                        ||object.getString("name").toUpperCase().startsWith(name_profess.getText().toString().toUpperCase())
                                        )
                                {

                                    adapter_name.addContents(sirname+object.getString("name"),object.getString("university"),R.drawable.arpit,object.getInt("id"),object.getString("image"));

                                    adapter_name.notifyDataSetChanged();
                                }


                        }
                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }

                    recyclerView.setAdapter(adapter_name);
                }
                else
                    Toast.makeText(getActivity(),input.error,Toast.LENGTH_SHORT).show();

            }
        });

        search_byuniv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter_name=new MyRecyclerAdapter();
                if(input.check_univ(univ_profess.getText().toString())
                        )
                {
                    try {
                        for(int i=0;i<response_name.length();i++)
                        {
                            JSONObject object=(JSONObject)response_name.get(i);

                            if(object.getString("gender").equals("Female"))
                                sirname="Mrs.";
                            else
                                sirname="Mr.";

                                if(object.getString("university").startsWith(univ_profess.getText().toString())
                                        ||object.getString("university").toLowerCase().startsWith(univ_profess.getText().toString().toLowerCase())
                                        ||object.getString("university").toUpperCase().startsWith(univ_profess.getText().toString().toUpperCase())
                                        )
                                {

                                    adapter_name.addContents(sirname+object.getString("name"),object.getString("university"),R.drawable.arpit,object.getInt("id"),object.getString("image"));

                                    adapter_name.notifyDataSetChanged();
                                }


                        }
                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }
                    recyclerView.setAdapter(adapter_name);
                }
                else
                    Toast.makeText(getActivity(),input.error,Toast.LENGTH_SHORT).show();





            }
        });

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter_name=new MyRecyclerAdapter();


                try {
                    for(int i=0;i<response_name.length();i++)
                    {
                        JSONObject object=(JSONObject)response_name.get(i);
                        if(object.getString("gender").equals("Female"))
                            sirname="Mrs.";
                        else
                            sirname="Mr.";
                            adapter_name.addContents(sirname+object.getString("name"), object.getString("university"), R.drawable.arpit, object.getInt("id"),object.getString("image"));

                            adapter_name.notifyDataSetChanged();

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
