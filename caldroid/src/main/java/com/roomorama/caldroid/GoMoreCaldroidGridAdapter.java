package com.roomorama.caldroid;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import hirondelle.date4j.DateTime;
import java.util.HashMap;

//
// GoMoreCaldroidGridAdapter
// GoMore
//
// Created by Ben on 08/09/2015.
// Copyright (c) 2014 SHAPE A/S. All rights reserved.
//
public class GoMoreCaldroidGridAdapter extends CaldroidGridAdapter {

  public GoMoreCaldroidGridAdapter(Context context, int month, int year,
      HashMap<String, Object> caldroidData, HashMap<String, Object> extraData) {
    super(context, month, year, caldroidData, extraData);
  }

  @Override public View getView(int position, View convertView, ViewGroup parent) {
    GoMoreCellView cellView;

    if (convertView == null) {
      if (squareTextViewCell) {
        cellView = new GoMoreCellView(context);
      } else {
        cellView = new GoMoreCellView(context);
      }
    } else {
      cellView = (GoMoreCellView) convertView;
    }

    setCellContent(position, cellView);

    return cellView;
  }

  protected void setCellContent(int position, GoMoreCellView cellView) {

    // Reset to default in case of recycled view
    cellView.reset();

    // Get dateTime of this cell
    DateTime dateTime = this.datetimeList.get(position);

    // Get the padding of cell so that it can be restored later
    int topPadding = cellView.getPaddingTop();
    int leftPadding = cellView.getPaddingLeft();
    int bottomPadding = cellView.getPaddingBottom();
    int rightPadding = cellView.getPaddingRight();

    // Specific background
    HashMap<DateTime, Integer> backgroundForDateTimeMap =
        (HashMap<DateTime, Integer>) caldroidData.get(
            CaldroidFragment._BACKGROUND_FOR_DATETIME_MAP);
    if (backgroundForDateTimeMap != null) {
      Integer backgroundResource = backgroundForDateTimeMap.get(dateTime);
      // Set it
      if (backgroundResource != null) {
        cellView.setBackground(backgroundResource);
      }
    }

    // Specific selected background
    HashMap<DateTime, Integer> selectedBackgroundForDateTimeMap =
        (HashMap<DateTime, Integer>) caldroidData.get(
            CaldroidFragment._SELECTED_BACKGROUND_FOR_DATETIME_MAP);
    if (selectedBackgroundForDateTimeMap != null) {
      Integer selectedBackgroundResource = selectedBackgroundForDateTimeMap.get(dateTime);

      // Set the selected background
      if (selectedBackgroundResource != null) {
        cellView.setSelectedBackground(selectedBackgroundResource);
      }
    }

    cellView.setText("" + dateTime.getDay());

    setCellState(cellView, dateTime);

    // Somehow after setBackgroundResource, the padding collapses.
    // This is to recover the padding
    cellView.setPadding(leftPadding, topPadding, rightPadding, bottomPadding);
  }

  private void setCellState(GoMoreCellView cellView, DateTime dateTime) {

    // Today
    if (dateTime.equals(getToday())) {
      cellView.setIsToday();
    }

    // Next/Prev month - Disabled
    if (dateTime.getMonth() != month || dateTime.compareTo(getToday()) < 0) {
      cellView.setDisabled();
    }

    // OutsideMin/Max - Disabled
    if ((minDateTime != null && dateTime.lt(minDateTime)) || (maxDateTime != null && dateTime.gt(
        maxDateTime))) {
      cellView.setDisabled();
    }

    // Selected
    if (selectedDates != null && selectedDatesMap.containsKey(dateTime)) {
      cellView.setSelected(true);
    } else {
      cellView.setSelected(false);
    }
  }
}
