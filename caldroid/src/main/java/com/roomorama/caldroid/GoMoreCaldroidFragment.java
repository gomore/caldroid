package com.roomorama.caldroid;

import hirondelle.date4j.DateTime;
import java.util.Calendar;

//
// GoMoreCaldroidFragment
// GoMore
//
// Created by Ben on 08/09/2015.
// Copyright (c) 2014 SHAPE A/S. All rights reserved.
//
public class GoMoreCaldroidFragment extends CaldroidFragment {

  public void setSelectedBackgroundResourceForDate(int backgroundRes, Calendar date) {
    DateTime dateTime = CalendarHelper.convertDateToDateTime(date);
    selectedBackgroundForDateTimeMap.put(dateTime, backgroundRes);
  }

  public void selectDate(Calendar date) {

    // BookRentalCalendar only needs one date selected at a time
    clearSelectedDates();

    DateTime dateTime = CalendarHelper.convertDateToDateTime(date);
    selectedDates.add(dateTime);

    for (CaldroidGridAdapter adapter : datePagerAdapters) {
      adapter.setSelectedDates(selectedDates);
      adapter.notifyDataSetChanged();
    }
  }

  public CaldroidGridAdapter getNewDatesGridAdapter(int month, int year) {
    return new GoMoreCaldroidGridAdapter(getActivity(), month, year, getCaldroidData(), extraData);
  }
}
