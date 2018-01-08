package utsavbansal.teacher_evaluation_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

public class Login extends AppCompatActivity {

    static String student_roll,student_name,student_coll,student_contact,student_id,student_password,student_image;
    Button submit;
    RequestQueue requestQueue;
    TextView id,password,stat;
    EditText edtloginid,edtloginpassword;
    String url="http://teacherevaluation.000webhostapp.com/login_student.php";
    int match=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        requestQueue = Volley.newRequestQueue(this);
        edtloginid=(EditText)findViewById(R.id.edtloginid);
        edtloginpassword=(EditText)findViewById(R.id.edtloginpassword);
        id=(TextView)findViewById(R.id.txtid);
        password=(TextView)findViewById(R.id.txtpassword);
        submit=(Button)findViewById(R.id.btnsubmit);
        stat=(TextView)findViewById(R.id.ejected);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject object = (JSONObject)response.get(i);
                                if(object.getString("rollno").equals(edtloginid.getText().toString())&& object.getString("pass").equals(edtloginpassword.getText().toString()))
                                {
                                    match=1;
                                    student_password=object.getString("pass");
                                    if(object.getInt("eject")==1)
                                    {
                                        stat.setText("Sorry you have been disabled by admin .Please contact admin");
                                    }
                                    else {
                                        Intent profile = new Intent(Login.this, Profile.class);
                                        startActivity(profile);
                                        student_id=object.getString("id");
                                        student_roll = object.getString("rollno");
                                        student_name = object.getString("name");
                                        student_coll = object.getString("clg");
                                        student_contact = object.getString("contact");
                                        student_image=object.getString("image");
                                        finish();
                                    }

                                }
                                if(match==0 && i==response.length()-1)
                                {
                                    stat.setText("Invalid login credentials");
                                }
                            }
                        } catch (Exception ex) {
                            Toast.makeText(Login.this, "" + ex.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(Login.this, "" + volleyError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                );

                requestQueue.add(request);

            }

        });

    }
}
