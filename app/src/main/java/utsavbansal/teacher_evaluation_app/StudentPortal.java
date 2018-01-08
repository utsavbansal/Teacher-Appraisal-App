package utsavbansal.teacher_evaluation_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StudentPortal extends AppCompatActivity {
    Button login,sign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_portal);

        login=(Button)findViewById(R.id.btnlogin);
        sign=(Button)findViewById(R.id.btnsignup);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callogin=new Intent(StudentPortal.this,Login.class);
                startActivity(callogin);
                finish();

            }
        });
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callsign=new Intent(StudentPortal.this,Signup.class);
                startActivity(callsign);


            }
        });


    }
}
