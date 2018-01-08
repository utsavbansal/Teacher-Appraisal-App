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

import static utsavbansal.teacher_evaluation_app.Login.student_id;
import static utsavbansal.teacher_evaluation_app.Login.student_password;

public class Edit_student_profile extends AppCompatActivity {
    Button update,camera,select;
    Image_Parser image_parser;
    ImageView imageView;
    Bitmap globalbitmap;
    TextView student_clg;
    EditText student_name,student_contact,student_roll,student_pass;
    Input_checker input;
    RequestQueue requestQueue;
    String url="http://teacherevaluation.000webhostapp.com/update_student.php";
    File selfiepic;
    boolean onclickimage=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_student_profile);
        update=(Button)findViewById(R.id.stdprofileupdate);
        camera=(Button)findViewById(R.id.edit_camera);
        select=(Button)findViewById(R.id.edit_select);

        image_parser =new Image_Parser();
        imageView=(ImageView)findViewById(R.id.edit_student_pic);
        student_name=(EditText)findViewById(R.id.stdprofilename_edit);
        student_contact=(EditText)findViewById(R.id.stdprofilecontact_edit);
        student_clg=(TextView)findViewById(R.id.stdprofilecollege_edit);
        student_roll=(EditText)findViewById(R.id.stdprofileroll_edit);
        student_pass=(EditText)findViewById(R.id.stdprofilepass_edit);
        input=new Input_checker();

        Bundle bd=getIntent().getExtras();
        String stdname=bd.getString("name");
        String stdroll=bd.getString("roll");
        String stdcon=bd.getString("contact");
        String stdclg=bd.getString("coll");

        student_name.setText(stdname);
        student_roll.setText(stdroll);
        student_clg.setText(stdclg);
        student_contact.setText(stdcon);
        student_pass.setText(student_password);
        imageView.setImageBitmap(image_parser.String_to_Bitmap(bd.getString("student_image")));
        requestQueue= Volley.newRequestQueue(this);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(input.check_image(onclickimage,globalbitmap)&&input.check_name(student_name.getText().toString())&&
                        input.check_roll(student_roll.getText().toString())
                        &&input.check_univ(student_clg.getText().toString())
                        &&input.check_contact(student_contact.getText().toString())
                        &&input.check_pass(student_pass.getText().toString())
                        )
                {
                    StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {//creating object of String class 1st para method,2nd para url,3rd para response (use listener for response)
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(Edit_student_profile.this, " " + response, Toast.LENGTH_LONG).show();
                        }
                    }, new Response.ErrorListener() {  //4th parameter to generate error msg if server dosent response
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(Edit_student_profile.this, " " + error.getMessage(), Toast.LENGTH_LONG).show();
                            //   textupdate.setText(error.getMessage());
                        }
                    }
                    ) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> param = new HashMap<String, String>(); //key+value
                            param.put("id", student_id);
                            param.put("rollno", student_roll.getText().toString());
                            param.put("name", student_name.getText().toString());
                            param.put("clg", student_clg.getText().toString());
                            param.put("contact", student_contact.getText().toString());
                            param.put("pass", student_pass.getText().toString());
                            param.put("image", image_parser.bitmap_to_string(globalbitmap));

                            return param;

                        }
                    };
                    requestQueue.add(request); //to add the request in queue



                }
                else
                    Toast.makeText(Edit_student_profile.this,input.error,Toast.LENGTH_SHORT).show();
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

        select.setOnClickListener(new View.OnClickListener() {
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
