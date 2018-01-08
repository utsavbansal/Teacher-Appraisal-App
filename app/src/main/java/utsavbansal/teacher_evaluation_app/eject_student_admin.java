package utsavbansal.teacher_evaluation_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class eject_student_admin extends AppCompatActivity {
    RequestQueue requestQueue;
    String url="https://teacherevaluation.000webhostapp.com/login_student.php";
    String url1="https://teacherevaluation.000webhostapp.com/student_eject_admin.php";
    int id;
    Button valid,eject;
    TextView name,age,roll,contact,univ1;
    Image_Parser image_parser;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eject_student_admin);

        image_parser=new Image_Parser();
        imageView=(ImageView)findViewById(R.id.student_pic);
        requestQueue= Volley.newRequestQueue(this);
        Bundle bd=getIntent().getExtras();
        id=bd.getInt("u_id");

        name=(TextView)findViewById(R.id.eject_student_profile);
        age=(TextView)findViewById(R.id.eject_student_age);

        roll=(TextView)findViewById(R.id.eject_student_roll);

        contact=(TextView)findViewById(R.id.eject_student_contact);
        univ1=(TextView)findViewById(R.id.eject_student_univ123);

        valid=(Button)findViewById(R.id.validate_student);
        eject=(Button)findViewById(R.id.eject_student);
        JsonArrayRequest request=new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try
                {
                    for (int i = 0; i < response.length(); i++)
                    {
                        JSONObject object=(JSONObject)response.get(i);
                        if(object.getInt("id")==id)
                        {
                            name.setText("Name: "+object.getString("name"));
                            age.setText("Password: "+object.getString("pass"));

                            contact.setText("Contact: "+object.getString("contact"));
                            roll.setText("RollNo: "+object.getString("rollno".toString()));
                           univ1.setText("University: "+object.getString("clg").toString());
                            imageView.setImageBitmap(image_parser.String_to_Bitmap(object.getString("image")));
                        }

                    }
                }
                catch (Exception ex){
                    Toast.makeText(eject_student_admin.this,""+ex.getMessage(),Toast.LENGTH_SHORT).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(eject_student_admin.this,""+error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }

        ){



        };

        requestQueue.add(request);


        valid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringRequest request1=new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {     // override response listener by typing new listener
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(eject_student_admin.this,response.toString(),Toast.LENGTH_SHORT).show();

                    }
                }, new Response.ErrorListener() {                   // Fourth paramater
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(eject_student_admin.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }

                        //Add curly brace to add new method here {    }
                )/*here   Add {}   and override getParams Map type   */ {


                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> param=new HashMap<String, String>();

                        param.put("id",Integer.toString(id));
                        param.put("eject","0");


                        return param;
                    }
                };

                requestQueue.add(request1);      // Add request in queue
            }
        });

        eject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StringRequest request1=new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {     // override response listener by typing new listener
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(eject_student_admin.this,response.toString(),Toast.LENGTH_SHORT).show();

                    }
                }, new Response.ErrorListener() {                   // Fourth paramater
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(eject_student_admin.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }

                        //Add curly brace to add new method here {    }
                )/*here   Add {}   and override getParams Map type   */ {


                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> param=new HashMap<String, String>();

                        param.put("id",Integer.toString(id));
                        param.put("eject","1");


                        return param;
                    }
                };

                requestQueue.add(request1);      // Add request in queue

            }
        });
    }
}
