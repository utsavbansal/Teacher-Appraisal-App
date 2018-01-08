package utsavbansal.teacher_evaluation_app;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

public class registerModule extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Button Rregister,camera,select_image;
    Image_Parser image_parser;
    ImageView imageregister;
    RequestQueue requestQueue,requestQueue_teacher;  // variable added after adding dependencies used for volley ,get queue variable of volley typerequest queue
    String url="http://teacherevaluation.000webhostapp.com/register_teacher.php";  //url of  api of (server) created by sir on 000webhost site
    EditText edtname,edtgender,edtage,edtcontact,edtqual,edtemail,edtpassword;

    String url_teacher="https://teacherevaluation.000webhostapp.com/login_teacher.php";
    int response_length;
    JSONArray response_teacher;
    File selfiepic;
    Bitmap bmp_select,globalbitmap;
    boolean onclickimage=false;
    TextView tv;
    Input_checker input;
    RadioButton male,female;
    String gender,University="";
    Spinner univ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_module);

        input=new Input_checker();

        male=(RadioButton)findViewById(R.id.gen_male);
        female=(RadioButton)findViewById(R.id.gen_female);
        univ=(Spinner)findViewById(R.id.ntedtuni_spin);
        edtname=(EditText)findViewById(R.id.ntedtname);
        edtcontact=(EditText)findViewById(R.id.ntedtcontact);
        edtage=(EditText)findViewById(R.id.ntedtage);

        edtqual=(EditText)findViewById(R.id.ntedtqual);
        edtemail=(EditText)findViewById(R.id.ntedtemail);
        Rregister=(Button)findViewById(R.id.ntbtnregister);
        edtpassword=(EditText)findViewById(R.id.ntedtpassword);

        requestQueue= Volley.newRequestQueue(this);
        requestQueue_teacher= Volley.newRequestQueue(this);

        imageregister=(ImageView)findViewById(R.id.img);

        camera=(Button)findViewById(R.id.teacher_camera);
        select_image=(Button)findViewById(R.id.teacher_select);

        ArrayAdapter univ_adap=new ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line);
        univ_adap.add("Select University");
        univ_adap.add("AKTU(Abdul Kalam Technical University)");
        univ_adap.add("CCSU(Choudhary Charan Singh University)");
        univ_adap.notifyDataSetChanged();
        univ.setAdapter(univ_adap);
        univ.setOnItemSelectedListener(this);

        image_parser=new Image_Parser();

        Rregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                JsonArrayRequest request1=new JsonArrayRequest(url_teacher, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try
                        {
                            response_length=response.length();
                            response_teacher=response;

                        }
                        catch (Exception ex){
                            Toast.makeText(registerModule.this,""+ex.getMessage(),Toast.LENGTH_SHORT).show();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(registerModule.this,""+error.getMessage(),Toast.LENGTH_SHORT).show();
                        Toast.makeText(registerModule.this,"Failed to connect to server Please try again later",Toast.LENGTH_LONG).show();
                        finish();
                    }
                }

                ){



                };


                if(input.check_image(onclickimage,globalbitmap)&&input.check_name(edtname.getText().toString())&&  input.check_age(edtage.getText().toString()) &&  input.check_contact(edtcontact.getText().toString())
                        && input.check_email_id(edtemail.getText().toString()) &&input.check_pass(edtpassword.getText().toString())
                        &&input.check_qual(edtqual.getText().toString())&&input.check_univ(University)
                        &&((male.isChecked()&& input.check_gen(male.getText().toString()) ) || (female.isChecked()&& input.check_gen(male.getText().toString())))

                        )
                {
                    if(input.check_user_existence_teacher(response_teacher,response_length,edtname.getText().toString(),
                            edtemail.getText().toString(),edtpassword.getText().toString()))
                    {
                        Toast.makeText(registerModule.this,"User Already Exist",Toast.LENGTH_SHORT).show();
                    }
                    else if(!input.check_user_existence_teacher(response_teacher,response_length,edtname.getText().toString(),
                            edtemail.getText().toString(),edtpassword.getText().toString()))
                    {

                        if(female.isChecked())
                            gender="Female";
                        else
                            gender="Male";
                        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {//creating object of String class 1st para method,2nd para url,3rd para response (use listener for response)
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(registerModule.this, " "+response, Toast.LENGTH_LONG).show();
                                finish();
                            }
                        }, new Response.ErrorListener() {  //4th parameter to generate error msg if server dosent response
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                tv.setText(error.getMessage());
                            }
                        }
                        ){
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String,String>param=new HashMap<String, String>(); //key+value



                                param.put("name",edtname.getText().toString()); //to save the id edittext value to id attribute of table
                                param.put("age",edtage.getText().toString());

                                param.put("gender",gender);
                                param.put("contact",edtcontact.getText().toString());
                                param.put("email",edtemail.getText().toString());
                                param.put("qualification",edtqual.getText().toString());
                                param.put("university",University);
                                param.put("password",edtpassword.getText().toString());
                                param.put("image",image_parser.bitmap_to_string(globalbitmap));
                                return param;

                            }
                        };
                        requestQueue.add(request); //to add the request in queue
                    }


            }
            else {
                    if(!male.isChecked()&&!female.isChecked())
                        Toast.makeText(registerModule.this, "Please select gender", Toast.LENGTH_SHORT).show();
                    Toast.makeText(registerModule.this, input.error, Toast.LENGTH_SHORT).show();
                }
            }
        });

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                File path = Environment.getExternalStorageDirectory();
                File folder = new File(path.getAbsolutePath(), "/profilepic");
                folder.mkdirs();
                selfiepic = new File(folder.getAbsolutePath(), "pic.jpg");
                Uri imguri = Uri.fromFile(selfiepic);

                Intent callcamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                callcamera.putExtra(MediaStore.EXTRA_OUTPUT, imguri);

                startActivityForResult(callcamera, 123);



            }
        });



        select_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent opengallery=new Intent(Intent.ACTION_PICK);
                opengallery.setType("image/*");
                startActivityForResult(Intent.createChooser(opengallery,"Choose App"),505);
            }
        });

    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(adapterView.getItemAtPosition(i).toString()!="Select University") {
            University = adapterView.getItemAtPosition(i).toString();
            Toast.makeText(this, "Please note university will not be updated after registration", Toast.LENGTH_LONG).show();
        }else
            University="";
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==505&&resultCode==RESULT_OK)
        {
            ContentResolver resolver=getContentResolver();

            try {
                InputStream inputStream=resolver.openInputStream(data.getData());
                bmp_select= BitmapFactory.decodeStream(inputStream);
                globalbitmap=bmp_select;
                imageregister.setImageBitmap(bmp_select);
                onclickimage=true;
            }
            catch (Exception e) {
                Log.e("Reading_file_gallery",e.getMessage());
            }
        }

        if (requestCode == 123 && resultCode == RESULT_OK) {
            // Toast.makeText(this, "Image save successfully", Toast.LENGTH_SHORT).show();

            try {
                FileInputStream fis = new FileInputStream(selfiepic.getAbsolutePath());
                Bitmap bmp = BitmapFactory.decodeStream(fis);
                globalbitmap=bmp;
                imageregister.setImageBitmap(bmp);
                onclickimage=true;
            } catch (Exception ex) {
                Log.e("error_reading_file", ex.getMessage());
            }
        }
    }
}
