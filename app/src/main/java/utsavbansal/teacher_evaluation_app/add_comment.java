package utsavbansal.teacher_evaluation_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
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

import static utsavbansal.teacher_evaluation_app.Login.student_id;
import static utsavbansal.teacher_evaluation_app.Login.student_name;

public class add_comment extends AppCompatActivity {
    RequestQueue requestQueue,requestQueue1;
    String url="https://teacherevaluation.000webhostapp.com/login_teacher.php";
    String save_comment="https://teacherevaluation.000webhostapp.com/comment_save.php";
    TextView t_name,t_age,t_gender,t_qual,t_con,t_univ;
    Button sub;
    EditText comment;
    RatingBar rb;
    String t_id;
    Bundle bd;
    String teacher_name;
    Input_checker input;
    ImageView imageView;
    Image_Parser image_parser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_comment);
        t_name=(TextView)findViewById(R.id.student_teacher_profile_comment);
        t_age=(TextView)findViewById(R.id.student_teacher_age_comment);
        t_gender=(TextView)findViewById(R.id.student_teacher_gender_comment);
        t_qual=(TextView)findViewById(R.id.student_teacher_quali_comment);
        t_con=(TextView)findViewById(R.id.student_teacher_contact_comment);
        t_univ=(TextView)findViewById(R.id.student_teacher_univ_comment);
        imageView=(ImageView)findViewById(R.id.teacher_pic_comment);
        image_parser=new Image_Parser();
        input=new Input_checker();
        bd=getIntent().getExtras();
        t_id=String.valueOf(bd.getInt("t_id"));


        sub=(Button)findViewById(R.id.submitcomment);

        comment=(EditText)findViewById(R.id.teacher_comment);

        rb=(RatingBar)findViewById(R.id.teacher_rating_comment);

        requestQueue= Volley.newRequestQueue(this);
        requestQueue1= Volley.newRequestQueue(this);

        JsonArrayRequest request=new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try
                {
                    for (int i = 0; i < response.length(); i++)
                    {
                        JSONObject object=(JSONObject)response.get(i);
                        if(object.getInt("id")==bd.getInt("t_id"))
                        {
                            teacher_name=object.getString("name");
                            t_name.setText("Name: "+object.getString("name"));
                            t_age.setText("Age: "+object.getString("age"));
                            t_gender.setText("Gender: "+object.getString("gender"));
                            t_con.setText("Contact: "+object.getString("contact"));
                            t_qual.setText("Qualification: "+object.getString("qualification"));
                            t_univ.setText("University: "+object.getString("university"));
                            imageView.setImageBitmap(image_parser.String_to_Bitmap(object.getString("image")));
                        }

                    }
                }
                catch (Exception ex){
                    Toast.makeText(add_comment.this,""+ex.getMessage(),Toast.LENGTH_SHORT).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(add_comment.this,""+error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }

        ){



        };

        requestQueue.add(request);


        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if(input.check_comment(comment.getText().toString()))
            {
                StringRequest request_save = new StringRequest(Request.Method.POST, save_comment, new Response.Listener<String>() {//creating object of String class 1st para method,2nd para url,3rd para response (use listener for response)
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(add_comment.this, " " + response, Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {  //4th parameter to generate error msg if server dosent response
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(add_comment.this, " " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
                ) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> param = new HashMap<String, String>(); //key+value
                        if (comment.getText().toString() != " ") {

                            param.put("teacher_id", t_id); //to save the id edittext value to id attribute of table
                            param.put("student_id", student_id);
                            param.put("comment", comment.getText().toString());
                            param.put("t_name", teacher_name);
                            param.put("s_name", student_name);
                            param.put("rating", String.valueOf(rb.getRating()));// to convert float to string
                        } else {
                            Toast.makeText(add_comment.this, "Please Enter all details", Toast.LENGTH_SHORT).show();
                        }
                        return param;

                    }
                };
                requestQueue1.add(request_save); //to add the request in queue
            }
            else
                Toast.makeText(add_comment.this,input.error,Toast.LENGTH_SHORT).show();



            }
        });




    }
}
