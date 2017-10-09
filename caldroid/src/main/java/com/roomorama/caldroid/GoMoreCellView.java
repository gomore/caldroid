package com.roomorama.caldroid;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.caldroid.R;

//
// GoMoreCellView
// GoMore
//
// Created by Ben on 09/09/2015.
// Copyright (c) 2014 SHAPE A/S. All rights reserved.
//
public class GoMoreCellView extends FrameLayout {

  private TextView textView;
  private ImageView imageView;

  private @DrawableRes int backgroundResource = 0;
  private @DrawableRes int selectedBackgroundResource;

  public GoMoreCellView(Context context) {
    super(context);
    inflate(context, R.layout.square_date_cell, this);
    textView = (TextView) findViewById(R.id.calendar_tv);
    imageView = (ImageView) findViewById(R.id.image_selected);
  }

  public void reset() {
    backgroundResource = 0;
    selectedBackgroundResource = R.drawable.calendar_background_selected;

    imageView.setImageDrawable(null);
    textView.setTextColor(getContext().getResources().getColor(R.color.grey_text));
  }

  public void setText(String text) {
    textView.setText(text);
  }

  public void setSelected(boolean selected) {
    if (selected) textView.setTextColor(getContext().getResources().getColor(R.color.white));
    imageView.setImageResource(selected ? selectedBackgroundResource : backgroundResource);
  }

  public void setBackground(@DrawableRes int resource) {
    backgroundResource = resource;
  }

  public void setSelectedBackground(@DrawableRes int resource) {
    selectedBackgroundResource = resource;
  }

  public void setDisabled() {
    textView.setTextColor(getContext().getResources().getColor(R.color.grey_text_light));
  }

  public void setIsToday() {
    textView.setTextColor(getContext().getResources().getColor(R.color.blue_light));
  }

  public void setTextColor(int color) {
    textView.setTextColor(color);
  }
}
