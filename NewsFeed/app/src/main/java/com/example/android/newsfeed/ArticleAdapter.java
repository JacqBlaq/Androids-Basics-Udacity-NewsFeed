package com.example.android.newsfeed;

import android.app.Activity;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

/**
 * Created by Jacquelyn Gboyor on 11/13/2017.
 */

public class ArticleAdapter extends ArrayAdapter<ArticleDetails>{

    public ArticleAdapter(Activity context, ArrayList<ArticleDetails> articles){
       super(context, 0, articles);
    }

    private String font = "fonts/Uptown Elegance Bold.ttf";

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_items, parent, false);
        }
        ArticleDetails currentGuide = getItem(position);

        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), font);
        /**
         * Set Article image
         */
        ImageView bookpic = (ImageView) listItemView.findViewById(R.id.article_picture);
        Picasso.with(getContext()).load(currentGuide.getPicture()).placeholder(R.drawable.noimage).error(R.drawable.noimage).fit().into(bookpic);
        /**
         * Set Title
         */
        TextView title = (TextView) listItemView.findViewById(R.id.title_of_article);
        title.setText(currentGuide.getTitle());
        title.setTypeface(typeface);
        /**
         * Set Section name
         */
        TextView sectionName = (TextView) listItemView.findViewById(R.id.sectionName);
        sectionName.setText(currentGuide.getSectionName());
        /**
         * Set Rating background color & parse to int
         */
        TextView rating = (TextView) listItemView.findViewById(R.id.rating);
        GradientDrawable circle = (GradientDrawable) rating.getBackground();
        int rateColor = getRatingColor(currentGuide.getRating());//Call method that parses rating from string to int then sets background color
        circle.setColor(rateColor);
        rating.setText(currentGuide.getRating());
        /**
         * Set Author name
         */
        TextView author = (TextView) listItemView.findViewById(R.id.author);
        author.setText("Author: " + currentGuide.getAuthor());
        /**
         * Set Format for date by frist getting substring of date then changing format
         */
        TextView date = (TextView) listItemView.findViewById(R.id.date);
        SimpleDateFormat newDateFormat = new SimpleDateFormat("EEEE, MMM dd, yyyy");
        SimpleDateFormat oldDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String getDate = currentGuide.getDate().toString().substring(0, 10);
        try {
            Date newDate = oldDateFormat.parse(getDate);
            date.setText(newDateFormat.format(newDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return listItemView;
    }//End

    /***********************************************************/
    private int getRatingColor(String rateInt){
        int colorChoice = 0;
        int colorFloor = Integer.parseInt(rateInt);

        switch (colorFloor){
                case 1: colorChoice = R.color.star1;
            break;
                case 2: colorChoice = R.color.star2;
            break;
                case 3: colorChoice = R.color.star3;
            break;
                case 4: colorChoice = R.color.star4;
            break;
                case 5: colorChoice = R.color.star5;
        }
        return ContextCompat.getColor(getContext(), colorChoice);
    }
}//End class
