package utsavbansal.teacher_evaluation_app;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

/**
 * Created by Utsav Bansal on 14-10-2017.
 */

public class Image_Parser {


    public  String bitmap_to_string(Bitmap bitmap){
        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
        byte[] imagebytes=outputStream.toByteArray();
        String encodedImage= Base64.encodeToString(imagebytes,Base64.DEFAULT);

        return encodedImage;
    }

    public Bitmap String_to_Bitmap(String image){

        byte[] decodestring=Base64.decode(image,Base64.DEFAULT);
        Bitmap bitmap= BitmapFactory.decodeByteArray(decodestring,0,decodestring.length);
        return  bitmap;

    }






}
