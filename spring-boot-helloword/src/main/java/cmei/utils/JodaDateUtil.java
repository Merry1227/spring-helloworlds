package cmei.utils;


import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * JodaDateUtil
 *
 * @author meicanhua
 * @create 2017-05-18 下午5:33
 **/
public class JodaDateUtil {

    public static final String MONTH_FORMAT = "yyyyMM";

    public static final String DAY_FORMAT = "yyyyMMdd";

    public static final String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final String YEAR_FORMAT = "yyyy";

    public static LocalDate parseDate(String date, String format) {
        LocalDate localDate = LocalDate.parse(date, DateTimeFormat.forPattern(format) );
        return localDate;
    }

    /***
     * 获取连续m个月数组
     * @param m
     * @param includeCurrent
     * @return
     */
    public static List<LocalDate> getNearMonths(int m, boolean includeCurrent) {

        if ( m <= 0 ) return null;
        List<LocalDate> months = new ArrayList<>();
        LocalDate currentDate = new LocalDate();

        int nNear = m;
        if (includeCurrent) {
            months.add(currentDate);
            nNear = nNear-1;
        }
        while (nNear > 0) {
            currentDate = currentDate.minusMonths(1);
            months.add(currentDate);
            nNear = nNear-1;
        }
        return months;

    }

    /**
     * 以String方式返回最近的m个月
     * @param m
     * @return
     */
    public static List<String> getNearMonths(int m, String format, boolean includeCurrent) {
        return getNearMonths(m, format, includeCurrent, new LocalDate());
    }

    public static List<String> getNearMonths(int m, String format, boolean includeCurrent, LocalDate sinceDate) {
        if ( m <= 0 ) return null;
        List<String> months = new ArrayList<>();
        LocalDate currentDate = sinceDate;
        int nNear = m;
        if (includeCurrent) {
            months.add(currentDate.toString(format));
            nNear = nNear-1;
        }
        while (nNear > 0) {
            currentDate = currentDate.minusMonths(1);
            months.add(currentDate.toString(format));
            nNear = nNear-1;
        }
        return months;
    }

    public static String afterMonth(int m, String format, boolean includeCurrent, LocalDate localDate) {
        if ( m <= 0) return null;

        LocalDate afterMonth = null;

        if (!includeCurrent) {
            afterMonth = localDate.minusMonths(m);
        } else {
            afterMonth = localDate.minusMonths(m-1);
        }

        return afterMonth.toString(format);

    }

    public static List<LocalDate> getNearWeekends(int m, boolean includeCurrent) {

        if ( m <= 0 ) return null;
        List<LocalDate> months = new ArrayList<>();
        LocalDate currentDate = new LocalDate().withDayOfWeek(DateTimeConstants.SUNDAY);

        int nNear = m;
        if (includeCurrent) {
            months.add(currentDate);
            nNear = nNear-1;
        }
        while (nNear > 0) {
            currentDate = currentDate.minusWeeks(1);
            months.add(currentDate);
            nNear = nNear-1;
        }
        return months;

    }

    public static List<String> getNearWeekends(int m, String format, boolean includeCurrent, LocalDate sinceDate, int weekday) {
        if ( m <= 0 ) return null;
        List<String> months = new ArrayList<>();
        LocalDate currentDate = sinceDate.withDayOfWeek(weekday);
        int nNear = m;
        if (includeCurrent) {
            months.add(currentDate.toString(format));
            nNear = nNear-1;
        }
        while (nNear > 0) {
            currentDate = currentDate.minusWeeks(1);
            months.add(currentDate.toString(format));
            nNear = nNear-1;
        }
        return months;
    }
    //获得从年初至今的周信息，也包含了去年最后一个周末的信息
    public static List<String> getWeekendInfo(String format, LocalDate sinceDate, boolean includeCurrWeek) {
        List<String> weeks = new ArrayList<>();
        LocalDate currentDateSunday = sinceDate.withDayOfWeek(DateTimeConstants.SUNDAY);
        LocalDate currentDateMonday = sinceDate.withDayOfWeek(DateTimeConstants.MONDAY);
        String currYear = currentDateSunday.toString(YEAR_FORMAT);
        int currNum = 52;

        if(includeCurrWeek){
            String tmp = "";
            tmp += currentDateMonday.toString(format) + "-";
            tmp += currentDateSunday.toString(format);
            weeks.add(tmp);
        }

        while (true && currNum > 0) {

            currentDateSunday = currentDateSunday.minusWeeks(1);
            currentDateMonday = currentDateMonday.minusWeeks(1);
            //判断目前的日期和当天日期是否是同一年
            if(!currentDateSunday.toString(YEAR_FORMAT).equals(currYear))
                break;


            String tmp = "";
            tmp += currentDateMonday.toString(format) + "-";
            tmp += currentDateSunday.toString(format);
            weeks.add(tmp);
            currNum--;

        }
        //获得去年的最后的一个周
        String tmp = "";
        tmp += currentDateMonday.toString(format) + "-";
        tmp += currentDateSunday.toString(format);
        weeks.add(tmp);
        /*
        if(weeks.isEmpty()){
            String tmp = "";
            tmp += currentDateMonday.toString(format) + "-";
            tmp += currentDateMonday.toString(format);
            weeks.add(tmp);
        }*/
        return weeks;
    }

    public static List<String> getYTDWeekends(String format, LocalDate sinceDate, boolean includeCurrWeek, boolean includePreYearLastWeek){
        List<String> weeks = getWeekendInfo(format, sinceDate, includeCurrWeek);
        int size = weeks.size();
        if(includePreYearLastWeek)
            return weeks;
        else
            return weeks.subList(0, size - 1);
    }




    public static List<String> getNearSeasons(int m, LocalDate sinceDate){
        if(m <=0)
            return null;
        List<String> seasons = new ArrayList<>();
        LocalDate currDate = sinceDate;
        String currMonth = sinceDate.toString(MONTH_FORMAT);
        int currNum = Integer.valueOf(currMonth.substring(4, currMonth.length()));
        int seasonNum = (currNum + 2) / 3;
        seasons.add(currMonth.substring(0, 4) + "Q" + seasonNum);
        while(m > 1){
             currDate = currDate.minusMonths(3);
             currMonth = currDate.toString(MONTH_FORMAT);
             currNum =  Integer.valueOf(currMonth.substring(4, currMonth.length()));
             seasonNum = (currNum + 2) / 3;
             seasons.add(currMonth.substring(0, 4) + "Q" + seasonNum);
             m--;
        }
        return seasons;
    }

    public static List<String> getMonthsBySeasons(List<String> seasons){
        if(seasons.isEmpty())
            return null;
        List<String> months = new ArrayList<>();
        for(String season : seasons){
            int seasonNum = Integer.valueOf(season.substring(5,season.length()));
            String year = season.substring(0, 4);
            if(seasonNum == 4){
                months.add(year + seasonNum * 3);
                months.add(year + (seasonNum * 3 - 1));
                months.add(year + (seasonNum * 3 - 2));
            }else{
                months.add(year + "0" + seasonNum * 3);
                months.add(year + "0" + (seasonNum * 3 - 1));
                months.add(year + "0" + (seasonNum * 3 - 2));
            }
        }
        return months;
    }

    public static String getSeasonByMonth(String month){
        int currNum = Integer.valueOf(month.substring(4, month.length()));
        int seasonNum = (currNum + 2) / 3;
        String season = month.substring(0, 4) + "Q" + seasonNum;
        return season;
    }

    public static List<String> getMonthsBySeason(String season){
        if(StringUtils.isEmpty(season))
            return null;
        List<String> months = new ArrayList<>();
            int seasonNum = Integer.valueOf(season.substring(5,season.length()));
            String year = season.substring(0, 4);
            if(seasonNum == 4){
                months.add(year + seasonNum * 3);
                months.add(year + (seasonNum * 3 - 1));
                months.add(year + (seasonNum * 3 - 2));
            }else{
                months.add(year + "0" + seasonNum * 3);
                months.add(year + "0" + (seasonNum * 3 - 1));
                months.add(year + "0" + (seasonNum * 3 - 2));
            }

        return months;
    }
}