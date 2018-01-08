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
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;


public class validate_comment extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager manager;
    RequestQueue requestQueue;
    String url="https://teacherevaluation.000webhostapp.com/comment_fetch.php";
    commentadapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_validate_comment, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        manager=new LinearLayoutManager(getActivity());
        recyclerView=(RecyclerView)getActivity().findViewById(R.id.comment_recycler);
        recyclerView.setLayoutManager(manager);
        adapter=new commentadapter();
        requestQueue= Volley.newRequestQueue(getContext());


        JsonArrayRequest request=new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try
                {
                    for (int i = 0; i < response.length(); i++)
                    {
                        JSONObject object=(JSONObject)response.get(i);

                        adapter.addContents(object.getString("comment"),object.getString("teacher_name"),object.getString("student_name"),object.getInt("id"));

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




    }
}
