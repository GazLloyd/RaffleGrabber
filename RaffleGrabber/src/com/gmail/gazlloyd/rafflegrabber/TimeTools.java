package com.gmail.gazlloyd.rafflegrabber;

import com.gmail.gazlloyd.rafflegrabber.gui.Main;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Gaz
 * Date: 20/11/13
 * Time: 22:24
 */
public class TimeTools {
    /*
        TODO fix all deprecated function uses
     */

    private static final SimpleTimeZone utc = new SimpleTimeZone(0, "Etc/UTC");
    private static Date citadel_start;
    private static final long ms_in_week = 1000*60*60*24*7;
    private static SimpleDateFormat sdf = new SimpleDateFormat("HH-mm d MMM Y");
    private static final String[] months = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};

    private static int curr_week;
    private static Date start_cal;
    private static Date end_cal;
    private static int start_day;
    private static String start_month;
    private static int end_day;
    private static String end_month;

    public static void initialise() {
        Main.logger.info("Initialising TimeTools");
        citadel_start = new Date(1311685200L*1000L);    // 26 July 2011, 13:00 UTC, in milliseconds - see http://www.unixtimestamp.com/
        Date curr = new Date();
        sdf.setTimeZone(utc);

        curr_week = (int)((curr.getTime() - citadel_start.getTime())/ms_in_week);

        start_cal = new Date(citadel_start.getTime() + curr_week*ms_in_week);
        end_cal = new Date(citadel_start.getTime() + (curr_week+1)*ms_in_week);

        start_day = start_cal.getDate();
        start_month = months[start_cal.getMonth()];
        end_day = end_cal.getDate();
        end_month = months[end_cal.getMonth()];

    }


    public static String folderName() {
        initialise();
        if (start_month.compareToIgnoreCase(end_month) == 0) {
            return "Week " + curr_week + " (" + start_day + "-" + end_day + " " + start_month + ")";
        }
        else {
            return "Week " + curr_week + " (" + start_day + " " + start_month +  " - " + end_day + " " + end_month + ")";
        }
    }



    public static String now() {
        return sdf.format(new Date());
    }

    public static long nowL() {
        return (new Date()).getTime();
    }



/*
    public static void main(String[] args) {
        TimeTools.initialise();

        System.out.println("citadel start: " + sdf.format(citadel_start.getTime()));
        System.out.println("current week: " + curr_week);
        System.out.println("dates: " + start_cal.toGMTString() + " to " + end_cal.toGMTString());   //deprecated but i'm lazy

    }
*/




}
