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

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class Signup extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Button register,camera,select_pic;
    EditText roll,name,coll,cnt,pad;
    TextView tv;
    Input_checker input;
    Image_Parser image_parser;
    ImageView imageView;
    Bitmap global_bitmap;
    RequestQueue requestQueue,requestQueue_student;
    String url="http://teacherevaluation.000webhostapp.com/register.php";
    String url_student="http://teacherevaluation.000webhostapp.com/login_student.php";
    int response_length;
    JSONArray response_student;
    File selfiepic;
    boolean onclickimage=false;
    Spinner univ;
    String University;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        imageView=(ImageView)findViewById(R.id.image_student);
        image_parser=new Image_Parser();
        requestQueue= Volley.newRequestQueue(this);
        requestQueue_student=Volley.newRequestQueue(this);

        camera=(Button)findViewById(R.id.student_camera);
        select_pic=(Button)findViewById(R.id.student_select);

        tv=(TextView)findViewById(R.id.txt);
        roll = (EditText) findViewById(R.id.signid);
        name = (EditText) findViewById(R.id.signupname);
        univ = (Spinner) findViewById(R.id.signupcollege_spin);
        cnt = (EditText) findViewById(R.id.signcontact);
        pad = (EditText) findViewById(R.id.signpassword);
        register = (Button) findViewById(R.id.signregister);
        input=new Input_checker();
        ArrayAdapter univ_adap=new ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line);
        univ_adap.add("Select University");
        univ_adap.add("AKTU(Abdul Kalam Technical University)");
        univ_adap.add("CCSU(Choudhary Charan Singh University)");
        univ_adap.notifyDataSetChanged();
        univ.setAdapter(univ_adap);
        univ.setOnItemSelectedListener(this);




        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                JsonArrayRequest request1=new JsonArrayRequest(url_student, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try
                        {
                            response_length=response.length();
                            response_student=response;

                        }
                        catch (Exception ex){
                            Toast.makeText(Signup.this,""+ex.getMessage(),Toast.LENGTH_SHORT).show();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Signup.this,""+error.getMessage(),Toast.LENGTH_SHORT).show();
                        Toast.makeText(Signup.this,"Failed to connect to server Please try again later",Toast.LENGTH_LONG).show();
                        finish();
                    }
                }

                ){
                };




                if (input.check_image(onclickimage,global_bitmap)&&input.check_name(name.getText().toString()) && input.check_roll(roll.getText().toString())
                        && input.check_contact(cnt.getText().toString()) && input.check_pass(pad.getText().toString())
                        && input.check_univ(University))
                {
                    if(input.check_user_existence_student(response_student,response_length,name.getText().toString(),
                            roll.getText().toString(),pad.getText().toString()))
                    {
                        Toast.makeText(Signup.this,"User Already Exist",Toast.LENGTH_SHORT).show();
                    }
                    else if(!input.check_user_existence_student(response_student,response_length,name.getText().toString(),
                            roll.getText().toString(),pad.getText().toString()))
                    {
                        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String s) {
                                Toast.makeText(Signup.this,s,Toast.LENGTH_SHORT).show();
                                finish();

                            }
                        },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError volleyError) {
                                        tv.setText(volleyError.getMessage());

                                    }
                                }
                        ) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> param = new HashMap<String, String>();

                                param.put("rollno", roll.getText().toString());
                                param.put("name", name.getText().toString());
                                param.put("clg", University);
                                param.put("contact", cnt.getText().toString());
                                param.put("pass", pad.getText().toString());
                                param.put("image", image_parser.bitmap_to_string(global_bitmap));

                                return param;
                            }
                        };
                        requestQueue.add(request);
                    }
                }
                else
                {
                    Toast.makeText(Signup.this,input.error,Toast.LENGTH_SHORT).show();
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


        select_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent opengallery=new Intent(Intent.ACTION_PICK);
                opengallery.setType("image/*");
                startActivityForResult(Intent.createChooser(opengallery,"Choose App"),505);

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==505&&resultCode==RESULT_OK)
        {
            ContentResolver resolver=getContentResolver();

            try {
                InputStream inputStream=resolver.openInputStream(data.getData());
                Bitmap bmp_select= BitmapFactory.decodeStream(inputStream);
                global_bitmap=bmp_select;
                imageView.setImageBitmap(bmp_select);
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
                global_bitmap=bmp;
                imageView.setImageBitmap(bmp);
                onclickimage=true;
            } catch (Exception ex) {
                Log.e("error_reading_file", ex.getMessage());
            }
        }


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
}
