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


public class vaidate_students extends Fragment {
    JSONArray response_name;
    Input_checker input;
    EditText name_stud,univ_stud;
    Button studname,studuniv,refresh;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager manager;
    RequestQueue requestQueue;
    String url="https://teacherevaluation.000webhostapp.com/login_student.php";
    myrecyleradpater adapter,adapter_name;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vaidate_students, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        input=new Input_checker();

        recyclerView=(RecyclerView)getActivity().findViewById(R.id.myrecycler_student);
        manager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);
        adapter=new myrecyleradpater();
        requestQueue= Volley.newRequestQueue(getContext());

        name_stud=(EditText)getActivity().findViewById(R.id.name_search_student);
        univ_stud=(EditText)getActivity().findViewById(R.id.univ_search_student);
        refresh=(Button)getActivity().findViewById(R.id.refresh_stud);
        studname=(Button)getActivity().findViewById(R.id.search_name_student);
        studuniv=(Button)getActivity().findViewById(R.id.search_univ_student);
        JsonArrayRequest request=new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try
                {
                    response_name=response;
                    for (int i = 0; i < response.length(); i++)
                    {
                        JSONObject object=(JSONObject)response.get(i);

                        adapter.addContents(object.getString("name"),object.getString("clg"),R.drawable.arpit,object.getInt("id"),object.getString("image"));

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

        studname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter_name=new myrecyleradpater();
                if(input.check_name(name_stud.getText().toString()))
                {
                    try {
                        for(int i=0;i<response_name.length();i++)
                        {
                            JSONObject object=(JSONObject)response_name.get(i);

                            if(object.getInt("eject")==0) {

                                if(object.getString("name").startsWith(name_stud.getText().toString())
                                        ||object.getString("name").toLowerCase().startsWith(name_stud.getText().toString().toLowerCase())
                                        ||object.getString("name").toUpperCase().startsWith(name_stud.getText().toString().toUpperCase())
                                        )
                                {

                                    adapter_name.addContents(object.getString("name"),object.getString("clg"),R.drawable.arpit,object.getInt("id"),object.getString("image"));

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
                    Toast.makeText(getActivity(),input.error,Toast.LENGTH_SHORT).show();





            }
        });

        studuniv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter_name=new myrecyleradpater();
                if(input.check_univ(univ_stud.getText().toString()))
                {
                    try {
                        for(int i=0;i<response_name.length();i++)
                        {
                            JSONObject object=(JSONObject)response_name.get(i);

                            if(object.getInt("eject")==0) {

                                if(object.getString("clg").startsWith(univ_stud.getText().toString())
                                        ||object.getString("clg").toLowerCase().startsWith(univ_stud.getText().toString().toLowerCase())
                                        ||object.getString("clg").toUpperCase().startsWith(univ_stud.getText().toString().toUpperCase())
                                        )
                                {

                                    adapter_name.addContents(object.getString("name"),object.getString("clg"),R.drawable.arpit,object.getInt("id"),object.getString("image"));

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
                    Toast.makeText(getActivity(),input.error,Toast.LENGTH_SHORT).show();






            }
        });


        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter_name=new myrecyleradpater();


                try {
                    for(int i=0;i<response_name.length();i++)
                    {
                        JSONObject object=(JSONObject)response_name.get(i);

                        adapter_name.addContents(object.getString("name"), object.getString("clg"), R.drawable.arpit, object.getInt("id"),object.getString("image"));

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
