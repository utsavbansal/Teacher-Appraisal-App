package utsavbansal.teacher_evaluation_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

public class Login_teacher extends AppCompatActivity {
    Button login,Mregister;
    RequestQueue requestQueue;
    TextView tvmain;
    EditText h_id,h_password;

    String url="http://teacherevaluation.000webhostapp.com/login_teacher.php";
    static  String teacher_image,teacher_name,teacher_id,teacher_university,teacher_age,teacher_qualification,teacher_password,teacher_gender,teacher_email,teacher_contact;
    static int oid;
    int match=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_teacher);

        login=(Button)findViewById(R.id.btnlogin);
        tvmain=(TextView)findViewById(R.id.hometxt);
        h_id=(EditText)findViewById(R.id.edtidhome);
        h_password=(EditText)findViewById(R.id.edtpwdhome);
        Mregister=(Button)findViewById(R.id.btnregister);
        requestQueue= Volley.newRequestQueue(this);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                JsonArrayRequest request=new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try{
                            for(int i=0;i<response.length();i++)  //loop to fetch data
                            {
                                JSONObject object=(JSONObject)response.get(i);
                                if(h_id.getText().toString().equals(object.getString("email")) && h_password.getText().toString().equals(object.getString("password")))
                                {

                                    tvmain.setText("Login Sucessfull :)");

                                    Intent calllogin=new Intent(Login_teacher.this,loginModule.class);
                                    startActivity(calllogin);

                                    teacher_age=object.getString("age");
                                    teacher_email=object.getString("email");
                                    teacher_gender=object.getString("gender");
                                    teacher_qualification=object.getString("qualification");
                                    teacher_contact=object.getString("contact");
                                    teacher_password=object.getString("password");
                                    teacher_name=object.getString("name");
                                    teacher_university=object.getString("university");
                                    teacher_id=object.getString("id");  //problem
                                    teacher_image=object.getString("image");
                                    match=1;
                                    finish();

                                }
                                     if(match==0 && i==response.length()-1) {
                                         tvmain.setText("Invalid E-mail or password");
                                   }

                            }

                        }
                        catch (Exception ex)
                        {
                            tvmain.setText(ex.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        tvmain.setText(error.getMessage());
                    }
                }){};
                requestQueue.add(request);

            }
        });

        Mregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callregister=new Intent(Login_teacher.this,registerModule.class);
                startActivity(callregister);
            }
        });


    }
}
