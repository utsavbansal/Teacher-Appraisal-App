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
 * Created by Utsav Bansal on 05-08-2017.
 */

public class adpaterclass extends RecyclerView.Adapter<adpaterclass.MyContentHolder> {
    ArrayList<String> Name=new ArrayList<>();
    ArrayList<String> University=new ArrayList<>();
    ArrayList<Integer> profile_pic=new ArrayList<>();
    ArrayList<Integer> Id=new ArrayList<>();
    ArrayList<String> image=new ArrayList<>();
    Bitmap bmp;
    Image_Parser image_parser=new Image_Parser();
    public void addContents(String name,String univ,int pic,int id,String img)             // Added by user to add content in array list
    {
        Id.add(id);
        Name.add(name);
        University.add(univ);
        profile_pic.add(pic);
        image.add(img);
    }

    @Override
    public MyContentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_layout,null);
        MyContentHolder holder=new MyContentHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyContentHolder holder, final int position) {
        holder.img.setImageResource(profile_pic.get(position));
        holder.profess_name.setText(Name.get(position));
        holder.profess_univ.setText(University.get(position));
        holder.img.setImageBitmap(image_parser.String_to_Bitmap(image.get(position)));

        holder.profess_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int indentity=Id.get(position);
                Intent validate=new Intent(view.getContext(),Others.class);
                validate.putExtra("u_id",indentity);
                view.getContext().startActivity(validate);
            }
        });

    }

    @Override
    public int getItemCount() {
        return Name.size();
    }


    public class MyContentHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView profess_name,profess_univ;


        public MyContentHolder(View itemView) {
            super(itemView);
            img=(ImageView)itemView.findViewById(R.id.imgmain);
            profess_name=(TextView)itemView.findViewById(R.id.namemain);
            profess_univ=(TextView)itemView.findViewById(R.id.clgmain);




        }
    }


}
