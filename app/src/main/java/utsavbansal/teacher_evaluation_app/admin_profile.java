package utsavbansal.teacher_evaluation_app;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import static utsavbansal.teacher_evaluation_app.admin_home.user_id;
import static utsavbansal.teacher_evaluation_app.login_admin.user_contact;
import static utsavbansal.teacher_evaluation_app.login_admin.user_email;
import static utsavbansal.teacher_evaluation_app.login_admin.user_name;
import static utsavbansal.teacher_evaluation_app.login_admin.user_pass;


public class admin_profile extends Fragment {
     TextView name,id,contact,password;
    Button logout;
    //Helper helper;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_admin_profile, container, false);


    }

    @Override
    public void onStart() {
        super.onStart();
        name=(TextView) getActivity().findViewById(R.id.name_profile);
        id=(TextView)getActivity().findViewById(R.id.email_id_profile);
        contact=(TextView)getActivity().findViewById(R.id.contact_profile);
        password=(TextView)getActivity().findViewById(R.id.pwd_profile);
        logout=(Button)getActivity().findViewById(R.id.admin_logout);
      //  helper=new Helper(getActivity());
       // Cursor cv=helper.getObject(user_id);


        name.setText("Name :"+user_name);
        contact.setText("Contact :"+user_contact);
        id.setText("Email-ID :"+user_email);
        password.setText("Password :"+user_pass);


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getActivity().finish();
            }
        });

    }
}
