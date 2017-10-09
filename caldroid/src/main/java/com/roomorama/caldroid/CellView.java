package com.roomorama.caldroid;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.caldroid.R;

/**
 * Created by Mads on 16/02/2015.
 */
public class CellView extends FrameLayout {

  private TextView textView;
  private ImageView imageView;

  public CellView(Context context) {
    super(context);
    inflate(context, R.layout.square_date_cell, this);
    textView = (TextView) findViewById(R.id.calendar_tv);
    imageView = (ImageView) findViewById(R.id.image_selected);
  }

  public void setSelected(boolean selected) {
    if (selected) {
      imageView.setVisibility(View.VISIBLE);
    } else {
      imageView.setVisibility(View.INVISIBLE);
    }
  }

  public void setFadedImage(boolean isFaded) {
    imageView.setAlpha(0.5f);
  }

  public TextView getTextView() {
    return textView;
  }

  public ImageView getImageView() {
    return imageView;
  }
}
