package utsavbansal.teacher_evaluation_app;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Utsav Bansal on 04-08-2017.
 */

public class myrecyleradpater extends RecyclerView.Adapter<myrecyleradpater.MyContentHolder> {
    ArrayList<String> Student_Name=new ArrayList<>();
    ArrayList<String> Student_clg=new ArrayList<>();
    ArrayList<Integer> profile_pic=new ArrayList<>();
    ArrayList<Integer> Id=new ArrayList<>();
    ArrayList<String> image=new ArrayList<>();
    Bitmap bmp;
    Image_Parser image_parser=new Image_Parser();




    public void addContents(String name,String clg,int pic,int id,String img)             // Added by user to add content in array list
    {
        Id.add(id);
        Student_Name.add(name);
        Student_clg.add(clg);
        profile_pic.add(pic);
        image.add(img);
    }

    @Override
    public MyContentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_layout_student,null);
        MyContentHolder holder=new MyContentHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyContentHolder holder, final int position) {

        holder.img.setImageResource(profile_pic.get(position));
        holder.stud_name.setText(Student_Name.get(position));
        holder.stud_clg.setText(Student_clg.get(position));
        holder.img.setImageBitmap(image_parser.String_to_Bitmap(image.get(position)));
        holder.stud_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int indentity=Id.get(position);
                Intent validate=new Intent(view.getContext(),eject_student_admin.class);
                validate.putExtra("u_id",indentity);
                view.getContext().startActivity(validate);

            }
        });

    }

    @Override
    public int getItemCount() {
        return Student_Name.size();
    }


    public class MyContentHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView stud_name,stud_clg;


        public MyContentHolder(View itemView) {
            super(itemView);
            img=(ImageView)itemView.findViewById(R.id.imgmain_stud);
            stud_name=(TextView)itemView.findViewById(R.id.namemain_stud);
            stud_clg=(TextView)itemView.findViewById(R.id.clgmain_stud);




        }
    }



}
