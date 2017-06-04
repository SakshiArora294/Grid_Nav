package com.example.sheliza.grid_nav.Alarm;

/**
 * Created by Sheliza on 14-05-2017.
 */

public class DateTimeSorter {
        public int mIndex;
        public String mDateTime;


        public DateTimeSorter(int index, String DateTime){
            mIndex = index;
            mDateTime = DateTime;
        }

        public DateTimeSorter(){}


        public int getIndex() {
            return mIndex;
        }

        public void setIndex(int index) {
            mIndex = index;
        }

        public String getDateTime() {
            return mDateTime;
        }

        public void setDateTime(String dateTime) {
            mDateTime = dateTime;
        }
    }


