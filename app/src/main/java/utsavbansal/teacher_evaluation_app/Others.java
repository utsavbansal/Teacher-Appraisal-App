package utsavbansal.teacher_evaluation_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

public class Others extends AppCompatActivity {
    ImageView imageView;
    Image_Parser image_parser;
    RequestQueue requestQueue,requestQueue1;
    ListView listView;
    RatingBar rb;
    ArrayAdapter adp;
    float rating=0;
    String url1="https://teacherevaluation.000webhostapp.com/comment_fetch.php";
    String url="https://teacherevaluation.000webhostapp.com/login_teacher.php";
    TextView name,age,gender,contact,email,qualifications;
    int id;
    int count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        image_parser=new Image_Parser();
        setContentView(R.layout.activity_others);
        imageView=(ImageView)findViewById(R.id.imageview_other);
        name=(TextView)findViewById(R.id.name_other);
        age=(TextView)findViewById(R.id.age_other);
        gender=(TextView)findViewById(R.id.gender_other);
        contact=(TextView)findViewById(R.id.contact_other);
        email=(TextView)findViewById(R.id.email_other);
        qualifications=(TextView)findViewById(R.id.qualifications_other);

        requestQueue= Volley.newRequestQueue(this);
        requestQueue1= Volley.newRequestQueue(this);

        adp=new ArrayAdapter(this,android.R.layout.simple_list_item_1);


        Bundle bd=getIntent().getExtras();
        id=bd.getInt("u_id");

        listView=(ListView)findViewById(R.id.list);
        rb=(RatingBar)findViewById(R.id.ratingbar);

        listView.setAdapter(adp);

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
                            age.setText("Age: "+object.getString("age"));
                            gender.setText("Gender: "+object.getString("gender"));
                            contact.setText("Contact: "+object.getString("contact"));
                            qualifications.setText("Qualification: "+object.getString("qualification"));
                            email.setText("Email: "+object.getString("email"));
                            imageView.setImageBitmap(image_parser.String_to_Bitmap(object.getString("image")));
                        }

                    }
                }
                catch (Exception ex){
                    Toast.makeText(Others.this,""+ex.getMessage(),Toast.LENGTH_SHORT).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Others.this,""+error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }

        ){



        };

        requestQueue.add(request);


        JsonArrayRequest request1=new JsonArrayRequest(url1, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try
                {
                    for (int i = 0; i < response.length(); i++)
                    {
                        JSONObject object=(JSONObject)response.get(i);

                        if(object.getInt("teacher_id")==id){

                            rating+=object.getDouble("rating");
                           // Toast.makeText(Others.this,""+object.getDouble("rating"),Toast.LENGTH_SHORT).show();
                           // Toast.makeText(Others.this,""+rating,Toast.LENGTH_SHORT).show();

                            if(object.getInt("eject")==1){
                                count++;
                                adp.add(object.getString("comment"));
                                adp.notifyDataSetChanged();
                            }
                        }

                    }
                    if(count==0)
                    {
                        adp.add("No comments Available");
                        adp.notifyDataSetChanged();
                    }
                    rb.setRating((rating/5));

                }
                catch (Exception ex){
                    Toast.makeText(Others.this,""+ex.getMessage(),Toast.LENGTH_SHORT).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Others.this,""+error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }

        ){



        };

        requestQueue1.add(request1);






    }
}
