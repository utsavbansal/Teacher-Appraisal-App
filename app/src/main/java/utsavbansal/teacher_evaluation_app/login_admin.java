package utsavbansal.teacher_evaluation_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

public class login_admin extends AppCompatActivity {
    Button login;
    int match=0;
    TextView tv;
    RequestQueue requestQueue;      // get queue variable of volley Request Queue ,only can use after adding dependency in module_app gradle
    String url="https://teacherevaluation.000webhostapp.com/login_admin.php";          //url of api of server
    static String user_email,user_name,user_pass,user_contact;
  //  Helper helper;
    EditText id,pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin);


        login=(Button)findViewById(R.id.login);

        id=(EditText)findViewById(R.id.id);
        pass=(EditText)findViewById(R.id.pass);

        requestQueue= Volley.newRequestQueue(this);
    //    helper=new Helper(this);

        tv=(TextView)findViewById(R.id.status);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            /*          boolean status=helper.checkData(id.getText().toString(),pass.getText().toString());
                if(status==true)
                {
                    int unique_id=helper.getId(id.getText().toString(),pass.getText().toString());
                    Intent home=new Intent(login_admin.this,admin_home.class);
                    home.putExtra("unique_id",unique_id);
                    startActivity(home);
                }
                else if(status==false)
                    tv.setText("Invalid Login Credentials");

                    */

                JsonArrayRequest request=new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try
                        {
                            for (int i = 0; i < response.length(); i++)
                            {
                                JSONObject object=(JSONObject)response.get(i);
                            /*    adapter.add(object.getString("id"));
                                adapter.add(object.getString("name"));
                                adapter.add(object.getString("contact"));
                                adapter.notifyDataSetChanged();

                                */
                            if(id.getText().toString().equals(object.getString("email"))&&pass.getText().toString().equals(object.getString("pass")))
                            {
                                match=1;
                                int unique_id=object.getInt("id");
                                user_email=object.getString("email");
                                user_name=object.getString("name");
                                user_pass=object.getString("pass");
                                user_contact=object.getString("contact");
                                Intent home=new Intent(login_admin.this,admin_home.class);
                                home.putExtra("unique_id",unique_id);
                                startActivity(home);
                                finish();
                            }
                            if (match==0&& i==response.length()-1)
                                {
                                    tv.setText("Invalid Login credentials");
                                }




                            }
                        }
                        catch (Exception ex){
                            Toast.makeText(login_admin.this,""+ex.getMessage(),Toast.LENGTH_SHORT).show();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(login_admin.this,""+error.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }

                ){



                };

                requestQueue.add(request);

            }
        });



    }
}
