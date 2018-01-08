package utsavbansal.teacher_evaluation_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;


import static utsavbansal.teacher_evaluation_app.Login_teacher.teacher_age;
import static utsavbansal.teacher_evaluation_app.Login_teacher.teacher_contact;
import static utsavbansal.teacher_evaluation_app.Login_teacher.teacher_email;
import static utsavbansal.teacher_evaluation_app.Login_teacher.teacher_gender;
import static utsavbansal.teacher_evaluation_app.Login_teacher.teacher_image;
import static utsavbansal.teacher_evaluation_app.Login_teacher.teacher_name;
import static utsavbansal.teacher_evaluation_app.Login_teacher.teacher_password;
import static utsavbansal.teacher_evaluation_app.Login_teacher.teacher_qualification;
import static utsavbansal.teacher_evaluation_app.Login_teacher.teacher_university;

public class loginModule extends AppCompatActivity {
    TextView name,age,gender,contact,email,qual,password,university;
    RatingBar star;
    Button logout,lupdate;
    ImageView imagelogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_module);

        name=(TextView)findViewById(R.id.txtname);
        age=(TextView)findViewById(R.id.txtage);
        gender=(TextView)findViewById(R.id.txtgender);
        contact=(TextView)findViewById(R.id.txtcontact);
        email=(TextView)findViewById(R.id.txtemail);
        password=(TextView)findViewById(R.id.txtpassword);
        university=(TextView)findViewById(R.id.txtunversity);
        qual=(TextView)findViewById(R.id.txtqualification);
        name.setText("Name:  "+teacher_name);
        age.setText("Age:  "+teacher_age);
        gender.setText("Gender:  "+teacher_gender);
        contact.setText("Contact:  "+teacher_contact);
        email.setText("Email:  "+teacher_email);
        qual.setText("Qualification:  "+teacher_qualification);
        password.setText("Password:  "+teacher_password);
        university.setText("University:  "+teacher_university);
        Image_Parser image_parser=new Image_Parser();
        imagelogin=(ImageView)findViewById(R.id.img2);
        lupdate=(Button)findViewById(R.id.lbtnupdate);
        logout=(Button)findViewById(R.id.btnlogout);
        imagelogin.setImageBitmap(image_parser.String_to_Bitmap(teacher_image));
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callmain=new Intent(loginModule.this,Login_teacher.class);
                startActivity(callmain);
                finish();
            }
        });
        lupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callupdate=new Intent(loginModule.this,updateModule.class);
                startActivity(callupdate);
            }
        });


    }
}
