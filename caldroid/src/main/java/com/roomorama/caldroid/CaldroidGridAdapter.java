package com.roomorama.caldroid;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.caldroid.R;
import hirondelle.date4j.DateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * The CaldroidGridAdapter provides customized view for the dates gridview
 *
 * @author thomasdao
 */
public class CaldroidGridAdapter extends BaseAdapter {
  protected ArrayList<DateTime> datetimeList;
  protected int month;
  protected int year;
  protected Context context;
  protected List<DateTime> disableDates;
  protected Set<DateTime> selectedDates;

  // Use internally, to make the search for date faster instead of using
  // indexOf methods on ArrayList
  protected HashMap<DateTime, Integer> disableDatesMap = new HashMap<DateTime, Integer>();
  protected HashMap<DateTime, Integer> selectedDatesMap = new HashMap<DateTime, Integer>();

  protected DateTime minDateTime;
  protected DateTime maxDateTime;
  protected DateTime today;
  protected int startDayOfWeek;
  protected boolean sixWeeksInCalendar;
  protected boolean squareTextViewCell;
  protected Resources resources;

  /**
   * caldroidData belongs to Caldroid
   */
  protected HashMap<String, Object> caldroidData;
  /**
   * extraData belongs to client
   */
  protected HashMap<String, Object> extraData;

  public void setAdapterDateTime(DateTime dateTime) {
    this.month = dateTime.getMonth();
    this.year = dateTime.getYear();
    this.datetimeList =
        CalendarHelper.getFullWeeks(this.month, this.year, startDayOfWeek, sixWeeksInCalendar);
  }

  // GETTERS AND SETTERS
  public ArrayList<DateTime> getDatetimeList() {
    return datetimeList;
  }

  public DateTime getMinDateTime() {
    return minDateTime;
  }

  public void setMinDateTime(DateTime minDateTime) {
    this.minDateTime = minDateTime;
  }

  public DateTime getMaxDateTime() {
    return maxDateTime;
  }

  public void setMaxDateTime(DateTime maxDateTime) {
    this.maxDateTime = maxDateTime;
  }

  public List<DateTime> getDisableDates() {
    return disableDates;
  }

  public void setDisableDates(List<DateTime> disableDates) {
    this.disableDates = disableDates;
  }

  public Set<DateTime> getSelectedDates() {
    return selectedDates;
  }

  public void setSelectedDates(Set<DateTime> selectedDates) {
    this.selectedDates = selectedDates;
    if (selectedDates != null) {
      selectedDatesMap.clear();
      for (DateTime dateTime : selectedDates) {
        selectedDatesMap.put(dateTime, 1);
      }
    }
  }

  public HashMap<String, Object> getCaldroidData() {
    return caldroidData;
  }

  public void setCaldroidData(HashMap<String, Object> caldroidData) {
    this.caldroidData = caldroidData;

    // Reset parameters
    populateFromCaldroidData();
  }

  public HashMap<String, Object> getExtraData() {
    return extraData;
  }

  public void setExtraData(HashMap<String, Object> extraData) {
    this.extraData = extraData;
  }

  /**
   * Constructor
   */
  public CaldroidGridAdapter(Context context, int month, int year,
      HashMap<String, Object> caldroidData, HashMap<String, Object> extraData) {
    super();
    this.month = month;
    this.year = year;
    this.context = context;
    this.caldroidData = caldroidData;
    this.extraData = extraData;
    this.resources = context.getResources();

    // Get data from caldroidData
    populateFromCaldroidData();
  }

  /**
   * Retrieve internal parameters from caldroid data
   */
  @SuppressWarnings("unchecked") private void populateFromCaldroidData() {
    disableDates = (List<DateTime>) caldroidData.get(CaldroidFragment.DISABLE_DATES);
    if (disableDates != null) {
      disableDatesMap.clear();
      for (DateTime dateTime : disableDates) {
        disableDatesMap.put(dateTime, 1);
      }
    }

    selectedDates = (Set<DateTime>) caldroidData.get(CaldroidFragment.SELECTED_DATES);
    if (selectedDates != null) {
      selectedDatesMap.clear();
      for (DateTime dateTime : selectedDates) {
        selectedDatesMap.put(dateTime, 1);
      }
    }

    minDateTime = (DateTime) caldroidData.get(CaldroidFragment._MIN_DATE_TIME);
    maxDateTime = (DateTime) caldroidData.get(CaldroidFragment._MAX_DATE_TIME);
    startDayOfWeek = (Integer) caldroidData.get(CaldroidFragment.START_DAY_OF_WEEK);
    sixWeeksInCalendar = (Boolean) caldroidData.get(CaldroidFragment.SIX_WEEKS_IN_CALENDAR);
    squareTextViewCell = (Boolean) caldroidData.get(CaldroidFragment.SQUARE_TEXT_VIEW_CELL);

    this.datetimeList =
        CalendarHelper.getFullWeeks(this.month, this.year, startDayOfWeek, sixWeeksInCalendar);
  }

  public void updateToday() {
    today = CalendarHelper.convertDateToDateTime(Calendar.getInstance());
  }

  protected DateTime getToday() {
    if (today == null) {
      today = CalendarHelper.convertDateToDateTime(Calendar.getInstance());
    }
    return today;
  }

  @SuppressWarnings("unchecked")
  protected void setCustomResources(DateTime dateTime, View backgroundView, TextView textView) {
    // Set custom background resource
    HashMap<DateTime, Integer> backgroundForDateTimeMap =
        (HashMap<DateTime, Integer>) caldroidData.get(
            CaldroidFragment._BACKGROUND_FOR_DATETIME_MAP);
    if (backgroundForDateTimeMap != null) {
      // Get background resource for the dateTime
      Integer backgroundResource = backgroundForDateTimeMap.get(dateTime);

      // Set it
      if (backgroundResource != null) {
        backgroundView.setBackgroundResource(backgroundResource.intValue());
      }
    }

    if (textView != null) {
      HashMap<DateTime, Integer> textColorForDateTimeMap =
          (HashMap<DateTime, Integer>) caldroidData.get(
              CaldroidFragment._TEXT_COLOR_FOR_DATETIME_MAP);
      if (textColorForDateTimeMap != null) {
        // Get textColor for the dateTime
        Integer textColorResource = textColorForDateTimeMap.get(dateTime);

        // Set it
        if (textColorResource != null) {
          textView.setTextColor(resources.getColor(textColorResource.intValue()));
        }
      }
    }
    // Set custom text color
  }

  /**
   * Customize colors of text and background based on states of the cell
   * (disabled, active, selected, etc)
   *
   * To be used only in getView method
   */
  protected void customizeTextView(int position, CellView cellView) {
    cellView.setBackgroundResource(R.drawable.white_clickable);
    cellView.getTextView().setTextColor(resources.getColor(R.color.grey_text));

    // Get the padding of cell so that it can be restored later
    int topPadding = cellView.getPaddingTop();
    int leftPadding = cellView.getPaddingLeft();
    int bottomPadding = cellView.getPaddingBottom();
    int rightPadding = cellView.getPaddingRight();

    // Get dateTime of this cell
    DateTime dateTime = this.datetimeList.get(position);

    // Set color of the dates in previous / next month
    if (dateTime.getMonth() != month) {
      cellView.getTextView().setTextColor(resources.getColor(R.color.grey_text_light));
    } else if (dateTime.compareTo(getToday()) < 0) {
      cellView.getTextView().setTextColor(resources.getColor(R.color.grey_text_light));
    }

    boolean shouldResetDiabledView = false;
    boolean shouldResetSelectedView = false;

    // Customize for disabled dates and date outside min/max dates
    if ((minDateTime != null && dateTime.lt(minDateTime)) || (maxDateTime != null && dateTime.gt(
        maxDateTime)) || (disableDates != null && disableDatesMap.containsKey(dateTime))) {

      cellView.getTextView().setTextColor(resources.getColor(R.color.grey_text_light));
      if (CaldroidFragment.disabledBackgroundDrawable == -1) {
        //cellView.setBackgroundResource(R.drawable.disable_cell);
      } else {
        //cellView.setBackgroundResource(CaldroidFragment.disabledBackgroundDrawable);
      }

      if (dateTime.equals(getToday())) {
        cellView.getTextView().setTextColor(resources.getColor(R.color.blue_light));
      }
    } else {
      shouldResetDiabledView = true;
    }

    // Customize for selected dates
    if (selectedDates != null && selectedDatesMap.containsKey(dateTime)) {
      cellView.setSelected(true);
      cellView.getTextView().setTextColor(resources.getColor(R.color.white));
    } else {
      cellView.setSelected(false);
      shouldResetSelectedView = true;
    }

    if (shouldResetDiabledView && shouldResetSelectedView) {
      // Customize for today
      if (dateTime.equals(getToday())) {
        cellView.getTextView().setTextColor(resources.getColor(R.color.blue_light));
      }
    }

    // Set text
    cellView.getTextView().setText("" + dateTime.getDay());

    // Set custom color if required
    //setCustomResources(dateTime, cellView, cellView);

    // Somehow after setBackgroundResource, the padding collapse.
    // This is to recover the padding
    cellView.setPadding(leftPadding, topPadding, rightPadding, bottomPadding);

    HashMap<DateTime, Integer> backgroundForDateTimeMap =
        (HashMap<DateTime, Integer>) caldroidData.get(
            CaldroidFragment._BACKGROUND_FOR_DATETIME_MAP);
    if (backgroundForDateTimeMap != null) {

      Integer backgroundResource = backgroundForDateTimeMap.get(dateTime);
      // Set it
      if (backgroundResource != null) {
        cellView.getImageView().setImageResource(backgroundResource.intValue());
        cellView.getTextView().setTextColor(resources.getColor(R.color.white));
        cellView.setSelected(true);
      }
    }
  }

  @Override public int getCount() {
    return this.datetimeList.size();
  }

  @Override public Object getItem(int position) {
    return datetimeList.get(position);
  }

  @Override public long getItemId(int arg0) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override public View getView(int position, View convertView, ViewGroup parent) {
    CellView cellView;

    if (convertView == null) {
      LayoutInflater inflater =
          (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      if (squareTextViewCell) {
        cellView = new CellView(context);
      } else {
        cellView = new CellView(context);
      }
    } else {
      cellView = (CellView) convertView;
    }

    customizeTextView(position, cellView);

    return cellView;
  }
}
