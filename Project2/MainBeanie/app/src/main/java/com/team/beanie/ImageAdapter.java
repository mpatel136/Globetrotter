package com.team.beanie;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.team.beanie.database.controller.PicturesController;

/**
 * Class for the adapter of the images
 */
public class ImageAdapter extends BaseAdapter
{
    PicturesController pc;
    //Instantiates the context
    private Context mContext;

    /**
     * Constructor for the image adapter
     * @param c The context of the adpater
     */
    public ImageAdapter(Context c)
    {
        mContext = c;
        pc = new PicturesController(c);
    }

    /**
     * Gets the number of images
     * @return The number of images
     */
    public int getCount()
    {
        return 7;
//        return thumbnails.length;
    }

    /**
     * Get the image at the position
     * @param position The position of the image
     * @return The image
     */
    public Object getItem(int position)
    {
        return null;
    }

    /**
     * Gets the id of the image
     * @param position The position of the image
     * @return The id of the image
     */
    public long getItemId(int position)
    {
        return 0;
    }

    /**
     * Gets the view of the images
     * @param position The position of the images
     * @param convertView The view of the images
     * @param parent The parent view of the images
     * @return The view of the images
     */
    public View getView(int position, View convertView, ViewGroup parent)
    {
        //Set the image view with the context
        ImageView imageView = new ImageView(mContext);

        //Set the dimensions of the images
        imageView.setLayoutParams(new GridView.LayoutParams(400,400));

        //Sets the image scale
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        //Sets the padding of the images
        imageView.setPadding(8,8,8,8);

        //Set the id of the image at the position
        imageView.setId(thumbnails[position]);

        //Set the Pictures to the images in the database
        imageView.setImageBitmap(pc.getAllPictures(position+1));

        //Return the images
        return imageView;
    }

    //Instantiates a variable with the images
    private Integer[] thumbnails = {
            R.drawable.colosseum,
            R.drawable.eiffel_tower,
            R.drawable.pyramid_of_giza,
            R.drawable.stonehenge,
            R.drawable.taj_mahal,
            R.drawable.great_wall_of_china,
            R.drawable.chichen_itza
    };
}
