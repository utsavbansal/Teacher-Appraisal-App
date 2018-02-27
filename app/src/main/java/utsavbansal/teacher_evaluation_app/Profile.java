package utsavbansal.teacher_evaluation_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static utsavbansal.teacher_evaluation_app.Login.student_coll;
import static utsavbansal.teacher_evaluation_app.Login.student_contact;
import static utsavbansal.teacher_evaluation_app.Login.student_image;
import static utsavbansal.teacher_evaluation_app.Login.student_name;
import static utsavbansal.teacher_evaluation_app.Login.student_roll;

public class Profile extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    ImageView imageView;
    Image_Parser image_parser;
    Spinner sp;
    TextView tv1,tv2,tv3,tv4,tvd;
    EditText cmnt,teacher;
    RatingBar rb;
    Button sub;
    RecyclerView recyclerView;
    String sirname;
    RequestQueue requestQueue;
    String url="https://teacherevaluation.000webhostapp.com/login_teacher.php";
    RecyclerView.LayoutManager manager;
    recycleradapterstudent adp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        imageView=(ImageView)findViewById(R.id.studentprofile_pic);
        image_parser=new Image_Parser();
        sp=(Spinner)findViewById(R.id.myspinner);
        tvd=(TextView)findViewById(R.id.tvs);
      //  cmnt=(EditText)findViewById(R.id.edtcmnt);
        tv1=(TextView)findViewById(R.id.tvpf);
        tv2=(TextView)findViewById(R.id.tvpf1);
        tv3=(TextView)findViewById(R.id.tvpf2);
        tv4=(TextView)findViewById(R.id.tvpf3);
        imageView.setImageBitmap(image_parser.String_to_Bitmap(student_image));
        recyclerView=(RecyclerView)findViewById(R.id.teacher_list);
        requestQueue= Volley.newRequestQueue(this);

        manager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        adp=new recycleradapterstudent();


        //teacher=(EditText)findViewById(R.id.edtt);
        //rb=(RatingBar)findViewById(R.id.pfrb);
        tv1.setText("Roll Number:"+student_roll);
        tv2.setText("Name :"+student_name);
        tv3.setText("College Name:"+student_coll);
        tv4.setText("Contact  :"+student_contact);

     //   sub=(Button)findViewById(R.id.btncmnt);
        tvd.setText("Add comments to following teacher:");

        ArrayList button=new ArrayList();
       // button.add("setting");
        button.add("Select");
        button.add("Edit Profile");
        button.add("logout");
        ArrayAdapter adapter=new ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line,button);
        sp.setAdapter(adapter);
        sp.setOnItemSelectedListener(this);
        /*
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = cmnt.getText().toString();
                if(data.isEmpty()){
                    Toast.makeText(Profile.this,"comment not submit ",Toast.LENGTH_SHORT).show();

                }
                else
                {
                    Toast.makeText(Profile.this,"comment  submitted ",Toast.LENGTH_SHORT).show();
                    tvd.setText("Student Comment :"+data);
                }
            }
        });

        */
        JsonArrayRequest request=new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try{
                    for(int i=0;i<response.length();i++)
                    {
                        JSONObject object=(JSONObject)response.get(i);
                        if(object.getInt("eject")==0){
                            if(object.getString("university").equals(student_coll)) {
                                if(object.getString("gender").equals("Female"))
                                    sirname="Mrs.";
                                else
                                    sirname="Mr.";
                                adp.addContents(sirname+object.getString("name"), object.getString("university"), R.drawable.arpit, object.getInt("id"),object.getString("image"));
                                adp.notifyDataSetChanged();
                            }
                        }

                    }

                }
                catch (Exception ex){
                        Toast.makeText(Profile.this,""+ex,Toast.LENGTH_SHORT).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Profile.this,""+error,Toast.LENGTH_SHORT).show();

            }
        }
        ){};

        requestQueue.add(request);
        recyclerView.setAdapter(adp);


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String data=parent.getItemAtPosition(position).toString();
        if(data=="logout"){

            Intent intent=new Intent(Profile.this,StudentPortal.class);

            startActivity(intent);




            Toast.makeText(this,"logout successfully", Toast.LENGTH_SHORT).show();
            finish();

        }

        else if(data=="Edit Profile"){
            Intent intent=new Intent(Profile.this,Edit_student_profile.class);
            intent.putExtra("name",student_name);
            intent.putExtra("roll",student_roll);
            intent.putExtra("coll",student_coll);
            intent.putExtra("contact",student_contact);
            intent.putExtra("student_image",student_image);
            startActivity(intent);
            sp.setSelection(0);
        }

}

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }}