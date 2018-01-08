package utsavbansal.teacher_evaluation_app;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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


public class fragment_register_admin extends Fragment {
    Button register;
    EditText name, id, password, contact;
    TextView tv;
    RequestQueue requestQueue,requestQueue1;      // get queue variable of volley Request Queue ,only can use after adding dependency in module_app gradle
    String url="https://teacherevaluation.000webhostapp.com/register_admin.php";          //url of api of server
    String url_admin="https://teacherevaluation.000webhostapp.com/login_admin.php";
    Input_checker input;
    boolean register_status=true;
    JSONArray  admin_response;
    int respone_length;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment_register_admin, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        // helper = new Helper(this);
        name = (EditText) getActivity().findViewById(R.id.newname_f);
        id = (EditText)  getActivity().findViewById(R.id.newid_f);
        password = (EditText)  getActivity().findViewById(R.id.newpass_f);
        contact = (EditText) getActivity(). findViewById(R.id.newcontact_f);
        tv = (TextView)  getActivity().findViewById(R.id.status_f);
        register = (Button)  getActivity().findViewById(R.id.newregister_f);
        requestQueue= Volley.newRequestQueue( getActivity());         // instantiate request queue variable
        requestQueue1= Volley.newRequestQueue( getActivity());
        input=new Input_checker();


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //       String msg = helper.saveData(name.getText().toString(), contact.getText().toString(), id.getText().toString(), password.getText().toString());
                //     tv.setText(msg);

                JsonArrayRequest request1=new JsonArrayRequest(url_admin, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        admin_response=response;
                        respone_length=response.length();

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(),""+error.getMessage(),Toast.LENGTH_SHORT).show();
                        register_status=false;
                        Toast.makeText(getActivity(),"Cannot connect to server,Please Try again later",Toast.LENGTH_SHORT).show();
                    }
                }

                ){
                };
                requestQueue1.add(request1);
                if(input.check_name(name.getText().toString()) && input.check_email_id(id.getText().toString())
                        && input.check_contact(contact.getText().toString()) && input.check_pass(password.getText().toString())
                       )
                {
                    if(register_status && input.check_user_existence_admin(admin_response,respone_length,name.getText().toString(),
                            id.getText().toString(),password.getText().toString()))
                    {
                        Toast.makeText(getActivity(),"Admin Already Exist",Toast.LENGTH_SHORT).show();
                    }
                    else if(register_status && !input.check_user_existence_admin(admin_response,respone_length,name.getText().toString(),
                            id.getText().toString(),password.getText().toString()))
                    {
                        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {     // override response listener by typing new listener
                            @Override
                            public void onResponse(String response) {
                                tv.setText(response);

                            }
                        }, new Response.ErrorListener() {                   // Fourth paramater
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                tv.setText(error.getMessage());
                            }
                        }

                                //Add curly brace to add new method here {    }
                        )/*here   Add {}   and override getParams Map type   */ {


                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> param = new HashMap<String, String>();
                                param.put("name", name.getText().toString());
                                    param.put("contact", contact.getText().toString());
                                    param.put("pass", password.getText().toString());
                                    param.put("email", id.getText().toString());

                                return param;
                            }
                        };

                        requestQueue.add(request);      // Add request in qu
                    }

                }
                else
                {
                    Toast.makeText(getActivity(),input.error,Toast.LENGTH_SHORT).show();
                }
            }
        });







    }
}
