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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static utsavbansal.teacher_evaluation_app.Login_teacher.teacher_age;
import static utsavbansal.teacher_evaluation_app.Login_teacher.teacher_contact;
import static utsavbansal.teacher_evaluation_app.Login_teacher.teacher_email;
import static utsavbansal.teacher_evaluation_app.Login_teacher.teacher_gender;
import static utsavbansal.teacher_evaluation_app.Login_teacher.teacher_id;
import static utsavbansal.teacher_evaluation_app.Login_teacher.teacher_image;
import static utsavbansal.teacher_evaluation_app.Login_teacher.teacher_name;
import static utsavbansal.teacher_evaluation_app.Login_teacher.teacher_password;
import static utsavbansal.teacher_evaluation_app.Login_teacher.teacher_qualification;
import static utsavbansal.teacher_evaluation_app.Login_teacher.teacher_university;

public class updateModule extends AppCompatActivity {
    TextView textupdate,utxtid,uuni,ugender;
    EditText uname,ucontact,uage,uemail,uqual,upassword;
    Button uupdate,camera,select_pic;
    int oid;
    RequestQueue requestQueue;
    String url="http://teacherevaluation.000webhostapp.com/update_teacher.php";
    Image_Parser image_parser;
    ImageView imageView;
    Bitmap bmp_select,globalbitmap;
    Input_checker input;
    File selfiepic;
    boolean onclickimage=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_module);
        input=new Input_checker();

        image_parser=new Image_Parser();
        imageView=(ImageView)findViewById(R.id.imgupdate);
        camera=(Button)findViewById(R.id.teacher_update_camera);
        select_pic=(Button)findViewById(R.id.teacher_update_select);

        uname=(EditText)findViewById(R.id.uedtname);
        uage=(EditText) findViewById(R.id.uedtage);
        textupdate=(TextView)findViewById(R.id.txtupdate);
        utxtid=(TextView)findViewById(R.id.utxtid);
        upassword=(EditText)findViewById(R.id.uedtpassword);
        ugender=(TextView)findViewById(R.id.uedtgender);
        uemail=(EditText)findViewById(R.id.uedtemail);
        uqual=(EditText)findViewById(R.id.uedtqual);
        ucontact=(EditText)findViewById(R.id.uedtcontact);
        uuni=(TextView) findViewById(R.id.uedtuni);

        requestQueue= Volley.newRequestQueue(this);



        uname.setText(teacher_name);
        uage.setText(teacher_age);
        ugender.setText(teacher_gender);
        ucontact.setText(teacher_contact);
        uemail.setText(teacher_email);
        uqual.setText(teacher_qualification);
        upassword.setText(teacher_password);
        uuni.setText(teacher_university);
        utxtid.setText(teacher_id);
        oid=Integer.parseInt(teacher_id);


        imageView.setImageBitmap(image_parser.String_to_Bitmap(teacher_image));
        globalbitmap=image_parser.String_to_Bitmap(teacher_image);
        uupdate=(Button)findViewById(R.id.ubtnupdate);
        uupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(input.check_image(onclickimage,globalbitmap)&&input.check_name(uname.getText().toString())&&  input.check_age(uage.getText().toString()) &&  input.check_contact(ucontact.getText().toString())
                        && input.check_email_id(uemail.getText().toString()) &&input.check_pass(upassword.getText().toString())
                        && input.check_univ(uuni.getText().toString()))
                {
                    StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {//creating object of String class 1st para method,2nd para url,3rd para response (use listener for response)
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(updateModule.this, " " + response, Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }, new Response.ErrorListener() {  //4th parameter to generate error msg if server dosent response
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(updateModule.this, " " + error.getMessage(), Toast.LENGTH_LONG).show();
                            //   textupdate.setText(error.getMessage());
                        }
                    }
                    ) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> param = new HashMap<String, String>(); //key+value
                            param.put("id", String.valueOf(oid));
                            param.put("age", uage.getText().toString()); //to save the id edittext value to id attribute of table
                            param.put("name", uname.getText().toString());
                            param.put("contact", ucontact.getText().toString());

                            param.put("gender", ugender.getText().toString());

                            param.put("email", uemail.getText().toString());
                            param.put("qualification", uqual.getText().toString());


                            param.put("university", uuni.getText().toString());
                            param.put("password", upassword.getText().toString());
                            param.put("image", image_parser.bitmap_to_string(globalbitmap));

                            return param;

                        }
                    };
                    requestQueue.add(request); //to add the request in queue
                }
                else
                    Toast.makeText(updateModule.this,input.error,Toast.LENGTH_SHORT).show();
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
                bmp_select= BitmapFactory.decodeStream(inputStream);
                globalbitmap=bmp_select;
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
                globalbitmap=bmp;
                imageView.setImageBitmap(bmp);
                onclickimage=true;
            } catch (Exception ex) {
                Log.e("error_reading_file", ex.getMessage());
            }
        }
    }
}
