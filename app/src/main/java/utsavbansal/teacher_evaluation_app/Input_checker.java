package utsavbansal.teacher_evaluation_app;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



/**
 * Created by Utsav Bansal on 18-08-2017.
 */

public class Input_checker {
    String error="None";

    public boolean check_name(String s)
    {
        boolean status=false;


        if(s.length()>0) {
            status = true;
            if(s.startsWith(" ")||s.endsWith(" ")||(s.startsWith(" ")&&s.endsWith(" ")))
            {
                status=false;
                error="please remove whitespace in name at begining or end";
            }
            for(int i=0;i<s.length();i++)
                if(s.charAt(i)=='0' || s.charAt(i)=='1' || s.charAt(i)=='2' || s.charAt(i)=='3' || s.charAt(i)=='4'
                        || s.charAt(i)=='5' || s.charAt(i)=='6' || s.charAt(i)=='7' || s.charAt(i)=='8' || s.charAt(i)=='9')
                {
                    status=false;
                    error="Name cannot contain numeric values";
                    break;
                }
        }
        else
            error="Please enter valid name";


        return status;
    }


    public boolean check_univ(String s)
    {
        boolean status=false;

        if(s.length()>0) {
            status = true;
            if(s.startsWith(" ")||s.endsWith(" "))
            {
                status=false;
                error="please remove whitespace in university at begining or end";
            }
        }
        else
            error="Please enter valid university";


        return status;
    }
    public boolean check_email_id(String s)
    {
        boolean status=false;

        if(s.length()>0) {
            status = true;
            if(s.contains(" "))
            {
                status=false;
                error="please remove whitespace in Email id";
            }
        }
        else
            error="Please enter valid email id";


        return status;
    }
    public boolean check_pass(String s)
    {
        boolean status=false;

        if(s.length()>0) {
            status = true;
            if(s.contains(" "))
            {
                status=false;
                error="please remove whitespace in password";
            }
        }
        else
            error="Please enter valid password";


        return status;
    }

    public boolean check_age(String s)
    {
        boolean status=false;
        int i;
        if(s.length()>0) {
            status = true;
            if(s.contains(" "))
            {
                status=false;
                error="please remove whitespace in age";
            }
            else if(Integer.parseInt(s)>=18){
                status=true;
                error="Age is not valid";
            }
            else if(Integer.parseInt(s)<18)
            {
                status=false;
                error="Age is not valid";
            }
        }
        else
            error="Please enter valid age";


        return status;
    }

    public boolean check_roll(String s)
    {
        boolean status=false;
        int i;
        if(s.length()>0) {
            status = true;
            if(s.contains(" "))
            {
                status=false;
                error="please remove whitespace in Roll number";
            }
            else if(Integer.parseInt(s)>18){
                status=true;
                error="Roll number is not valid";
            }

        }
        else
            error="Please enter valid roll number";


        return status;
    }


    public boolean check_contact(String s)
    {
        boolean status=false;
        int i;
        if(s.length()>=6&& s.length()<=10) {
            status = true;
            if(s.contains(" "))
            {
                status=false;
                error="please remove whitespace in Contact";
            }


        }
        else
            error="Please enter valid Contact number atleast 6 digits";


        return status;
    }



    public boolean check_comment(String s)
    {
        boolean status=false;
        int count=0;
        if(s.length()>0)
        {
            status=true;
            if(s.startsWith(" ")||s.endsWith(" ")||(s.startsWith(" ")&&s.endsWith(" ")))
            {
                status=false;
                error="please remove whitespace in comment at begining or end";
            }



        }
        else
            error="Please enter valid comment";


        return status;
    }

    public  boolean check_qual(String s)
    {
        boolean status=false;
        if(s.length()>0)
        {
            status=true;
            if(s.startsWith(" ")||s.endsWith(" ")||(s.startsWith(" ")&&s.endsWith(" ")))
            {
                status=false;
                error="please remove whitespace in Qualification at begining or end";
            }

        }

        else
            error="Please enter valid Qualification";
        return status;
    }
    public  boolean check_gen(String s)
    {
        boolean status=false;
        if(s.length()>0)
        {
            status=true;

        }

        else
            error="Please select Age";
        return status;
    }

    public boolean check_user_existence_student(JSONArray response_teacher,int object_length, String name,  String rollno, String password) {
        boolean status = false;

        for (int i = 0; i < object_length; i++)
        {
            try {
                JSONObject object=(JSONObject)response_teacher.get(i);
                if (    ((object.getString("name").startsWith(name)
                        ||object.getString("name").toLowerCase().startsWith(name.toLowerCase())
                         ||object.getString("name").toUpperCase().startsWith(name.toUpperCase())
                        )
                                    &&
                         (object.getString("rollno").startsWith(rollno)
                         ||object.getString("rollno").toUpperCase().startsWith(rollno.toUpperCase())
                         ||object.getString("rollno").toLowerCase().startsWith(rollno.toLowerCase())
                         )
                                     &&
                         (object.getString("pass").startsWith(password)
                         ||object.getString("pass").toLowerCase().startsWith(password.toLowerCase())
                         ||object.getString("pass").toUpperCase().startsWith(password.toUpperCase()))
                        )
                            ||
                        ((object.getString("rollno").startsWith(rollno)
                                ||object.getString("rollno").toUpperCase().startsWith(rollno.toUpperCase())
                                ||object.getString("rollno").toLowerCase().startsWith(rollno.toLowerCase())
                            )
                                &&
                                (object.getString("pass").startsWith(password)
                                        ||object.getString("pass").toLowerCase().startsWith(password.toLowerCase())
                                        ||object.getString("pass").toUpperCase().startsWith(password.toUpperCase()))
                                )
                )
                {
                    status=true;
                    break;
                }

            } catch (JSONException e) {
                error=e.getMessage();
            }
        }
        return status;
    }


    public boolean check_user_existence_teacher( JSONArray response_student,int object_length, String name,  String email, String password) {
        boolean status = false;

        for (int i = 0; i < object_length; i++)
        {
            try {
                JSONObject object=(JSONObject)response_student.get(i);
                if ((   (object.getString("name").startsWith(name)
                            ||object.getString("name").toLowerCase().startsWith(name.toLowerCase())
                            ||object.getString("name").toUpperCase().startsWith(name.toUpperCase())
                        )
                         &&
                         (object.getString("email").startsWith(email)
                             ||object.getString("email").toLowerCase().startsWith(email.toLowerCase())
                             ||object.getString("email").toUpperCase().startsWith(email.toUpperCase())
                         )
                         &&
                         (object.getString("password").startsWith(password)
                         ||object.getString("password").toLowerCase().startsWith(password.toLowerCase())
                         ||object.getString("password").toUpperCase().startsWith(password.toUpperCase()))
                    )
                        ||
                        ((object.getString("email").startsWith(email)
                                ||object.getString("email").toLowerCase().startsWith(email.toLowerCase())
                                ||object.getString("email").toUpperCase().startsWith(email.toUpperCase())
                                )
                                &&
                                (object.getString("password").startsWith(password)
                                        ||object.getString("password").toLowerCase().startsWith(password.toLowerCase())
                                        ||object.getString("password").toUpperCase().startsWith(password.toUpperCase()))
                                )
                )
                {
                    status=true;
                    break;
                }

            } catch (JSONException e) {
                error=e.getMessage();
            }
        }
        return status;
    }

    public boolean check_user_existence_admin( JSONArray response_admin,int object_length, String name,  String email, String password) {
        boolean status = false;

        for (int i = 0; i < object_length; i++)
        {
            try {
                JSONObject object=(JSONObject)response_admin.get(i);
                if (  (  (object.getString("name").startsWith(name)
                            ||object.getString("name").toLowerCase().startsWith(name.toLowerCase())
                            ||object.getString("name").toUpperCase().startsWith(name.toUpperCase())
                        )
                            &&
                            (object.getString("email").startsWith(email)
                                 ||object.getString("email").toLowerCase().startsWith(email.toLowerCase())
                                 ||object.getString("email").toUpperCase().startsWith(email.toUpperCase())
                            )
                            &&
                            (object.getString("pass").startsWith(password)
                                    ||object.getString("pass").toLowerCase().startsWith(password.toLowerCase())
                                    ||object.getString("pass").toUpperCase().startsWith(password.toUpperCase()))
                    )
                     ||
                        ((object.getString("email").startsWith(email)
                                ||object.getString("email").toLowerCase().startsWith(email.toLowerCase())
                                ||object.getString("email").toUpperCase().startsWith(email.toUpperCase())
                                )
                                &&
                                (object.getString("pass").startsWith(password)
                                        ||object.getString("pass").toLowerCase().startsWith(password.toLowerCase())
                                        ||object.getString("pass").toUpperCase().startsWith(password.toUpperCase()))
                                )
                        )
                {
                    status=true;
                    break;
                }

            } catch (JSONException e) {
                error=e.getMessage();
            }
        }
        return status;
    }

    public boolean check_image(boolean img,Bitmap bmp){
       if(!img) {
           error = "Please select a valid image";
           return false;
       }
        else {
            Image_Parser image_parser=new Image_Parser();
           if(image_parser.bitmap_to_string(bmp).length()<48000)
               return true;
            else
                error="Image size cannot be more than 8 kb";
                return false;
       }

    }


}
