package utsavbansal.teacher_evaluation_app;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Utsav Bansal on 16-08-2017.
 */

public class commentadapter extends RecyclerView.Adapter<commentadapter.MyContentHolder> {
    ArrayList<String> Comment=new ArrayList<>();
    ArrayList<String> To_teacher=new ArrayList<>();
    ArrayList<String> From_student=new ArrayList<>();
    ArrayList<Integer> Id_comment=new ArrayList<>();

    RequestQueue requestQueue;
    String url="https://teacherevaluation.000webhostapp.com/eject_comment.php";


    public void addContents(String comment,String to_teacher,String from_student,int id)             // Added by user to add content in array list
    {
        Id_comment.add(id);
        Comment.add(comment);
        To_teacher.add(to_teacher);
        From_student.add(from_student);
    }

    @Override
    public MyContentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_layout_comment,null);
        MyContentHolder holder=new MyContentHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyContentHolder holder, final int position) {
        holder.comment_text.setText(Comment.get(position));
        holder.to_.setText("To teacher: "+To_teacher.get(position));
        holder.from_.setText("From Student: "+From_student.get(position));


        holder.enable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                requestQueue= Volley.newRequestQueue(view.getContext());

                StringRequest request1=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {     // override response listener by typing new listener
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(view.getContext(),""+response.toString(),Toast.LENGTH_SHORT).show();

                    }
                }, new Response.ErrorListener() {                   // Fourth paramater
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(view.getContext(),""+error.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }

                        //Add curly brace to add new method here {    }
                )/*here   Add {}   and override getParams Map type   */ {


                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> param=new HashMap<String, String>();

                        param.put("id",Id_comment.get(position).toString());
                        param.put("eject","1");


                        return param;
                    }
                };

                requestQueue.add(request1);      // Add request in queue




            }
        });

        holder.disable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                requestQueue= Volley.newRequestQueue(view.getContext());

                StringRequest request1=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {     // override response listener by typing new listener
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(view.getContext(),response.toString(),Toast.LENGTH_SHORT).show();

                    }
                }, new Response.ErrorListener() {                   // Fourth paramater
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(view.getContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }

                        //Add curly brace to add new method here {    }
                )/*here   Add {}   and override getParams Map type   */ {


                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> param=new HashMap<String, String>();

                        param.put("id",Id_comment.get(position).toString());
                        param.put("eject","0");


                        return param;
                    }
                };

                requestQueue.add(request1);      // Add request in queue



            }
        });



    }

    @Override
    public int getItemCount() {
        return Comment.size();
    }


    public class MyContentHolder extends RecyclerView.ViewHolder {
        TextView comment_text,to_,from_;
        Button enable,disable;
        public MyContentHolder(View itemView) {
            super(itemView);

            comment_text=(TextView)itemView.findViewById(R.id.comment_text);
            to_=(TextView)itemView.findViewById(R.id.comment_to);
            from_=(TextView)itemView.findViewById(R.id.comment_from);
            enable=(Button)itemView.findViewById(R.id.comment_enable);
            disable=(Button)itemView.findViewById(R.id.comment_disable);
        }
    }



}
