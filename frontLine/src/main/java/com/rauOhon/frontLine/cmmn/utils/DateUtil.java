package com.rauOhon.frontLine.cmmn.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * 1. 클래스	: DateUtil.java
 * 2. 작성자	: 신태휘
 * 3. 작성시간	: 2018. 5. 6.
 *
 * <pre>
 * 설명			:
 * 		현재 년월 		: getYearMonth()							return "yyyyMM"
 * 		현재 년월일		: getDate()									return "yyyyMMdd"
 * 		현재 시간		: getTime()									return "HHmmSS"
 * 		+ 일			: getAddDay(String yyyyMMdd, int 추가일)	return "yyyyMMdd"
 * 		+ 월			: getAddMonth(String yyyyMMdd, int 추가월)	return "yyyyMMdd"
 * 		어제 날짜		: getYesterday()							return "yyyyMMdd"
 * 		내일 날짜		: getTomorrow()								return "yyyyMMdd"
 * 		날짜값 확인		: isDate("yyyyMMdd")						return boolean
 * 		시간값 확인		: isTime("HHmmss")							return boolean
 * 		달의 마지막날	: getLastDay("yyyyMMdd")					return "dd"
 * 		날짜포맷변환	: getThisDay("yyyyMMdd", "yyyy/mm/dd") 		retrun "yyyy/MM/dd"
 * 		일자의 요일		: getDateDay("yyyyMMdd")					return "월"
 * 		두 날짜의 차이	: getDaysDiff("yyyyMMdd", "yyyyMMdd")		return	int
 * </pre>
 */
public class DateUtil {

    /**
     * 현재 년월 - YYYYMM
     */
    public static String getYearMonth() {
        String month;

        Calendar cal = Calendar.getInstance(Locale.getDefault());

        StringBuilder buf = new StringBuilder();

        buf.append(Integer.toString(cal.get(Calendar.YEAR)));
        month = Integer.toString(cal.get(Calendar.MONTH) + 1);
        if (month.length() == 1)
            month = "0" + month;

        buf.append(month);

        return buf.toString();
    }

    /**
     * 현재 년월일 - YYYYMMDD
     */
    public static String getDate() {
        String month, day;

        Calendar cal = Calendar.getInstance(Locale.getDefault());

        StringBuilder buf = new StringBuilder();

        buf.append(Integer.toString(cal.get(Calendar.YEAR)));
        month = Integer.toString(cal.get(Calendar.MONTH) + 1);
        if (month.length() == 1)
            month = "0" + month;
        day = Integer.toString(cal.get(Calendar.DATE));
        if (day.length() == 1)
            day = "0" + day;

        buf.append(month);
        buf.append(day);

        return buf.toString();
    }

    /**
     * 현재 시간 - HHMISS
     */
    public static String getTime() {
        String hour, min, sec;

        Calendar cal = Calendar.getInstance(Locale.getDefault());

        StringBuilder buf = new StringBuilder();

        hour = Integer.toString(cal.get(Calendar.HOUR_OF_DAY));
        if (hour.length() == 1)
            hour = "0" + hour;

        min = Integer.toString(cal.get(Calendar.MINUTE));
        if (min.length() == 1)
            min = "0" + min;

        sec = Integer.toString(cal.get(Calendar.SECOND));
        if (sec.length() == 1)
            sec = "0" + sec;

        buf.append(hour);
        buf.append(min);
        buf.append(sec);

        return buf.toString();
    }

    /**
     * 특정날짜에 일자를 더한 값
     *
     * @param DateTime
     *            YYMMDDHHMMSS
     * @param plusDay
     *            더할 일자
     * @return 특정날짜에 일자를 더한 값
     */
    public static String getAddDay(String DateTime, int plusDay) {

        if (DateTime == null)
            return "";

        if (DateTime.length() == 8)
            DateTime += "000000";

        if (DateTime.equals("99991231")) {
            return "99991231000000";
        }

        if (DateTime.equals("99991231235959")) {
            return "99991231235959";
        }

        int y = Integer.parseInt(DateTime.substring(0, 4));
        int m = Integer.parseInt(DateTime.substring(4, 6));
        int d = Integer.parseInt(DateTime.substring(6, 8));

        java.util.GregorianCalendar sToday = new java.util.GregorianCalendar();
        sToday.set(y, m - 1, d);
        sToday.add(GregorianCalendar.DAY_OF_MONTH, plusDay);

        int day = sToday.get(GregorianCalendar.DAY_OF_MONTH);
        int month = sToday.get(GregorianCalendar.MONTH) + 1;
        int year = sToday.get(GregorianCalendar.YEAR);

        String sNowyear = String.valueOf(year);
        String sNowmonth = "";
        String sNowday = "";

        if (month < 10)
            sNowmonth = "0" + String.valueOf(month);
        else
            sNowmonth = String.valueOf(month);

        if (day < 10)
            sNowday = "0" + String.valueOf(day);
        else
            sNowday = String.valueOf(day);

        return sNowyear + sNowmonth + sNowday;

    }

    /**
     * 특정날짜에 달을 더한 값
     *
     * @param DateTime
     *            YYMMDDHHMMSS
     * @param plusMonth
     *            더할 일자
     * @return 특정날짜에 일자를 더한 값
     */
    public static String getAddMonth(String DateTime, int plusMonth) {

        if (DateTime == null)
            return "";

        if (DateTime.length() == 8)
            DateTime += "000000";

        if (DateTime.equals("99991231")) {
            return "99991231000000";
        }

        if (DateTime.equals("99991231235959")) {
            return "99991231235959";
        }

        int y = Integer.parseInt(DateTime.substring(0, 4));
        int m = Integer.parseInt(DateTime.substring(4, 6));
        int d = Integer.parseInt(DateTime.substring(6, 8));

        java.util.GregorianCalendar sToday = new java.util.GregorianCalendar();
        sToday.set(y, m - 1, d);
        sToday.add(GregorianCalendar.MONTH, plusMonth);

        int day = sToday.get(GregorianCalendar.DAY_OF_MONTH);
        int month = sToday.get(GregorianCalendar.MONTH) + 1;
        int year = sToday.get(GregorianCalendar.YEAR);

        String sNowyear = String.valueOf(year);
        String sNowmonth = "";
        String sNowday = "";

        if (month < 10)
            sNowmonth = "0" + String.valueOf(month);
        else
            sNowmonth = String.valueOf(month);

        if (day < 10)
            sNowday = "0" + String.valueOf(day);
        else
            sNowday = String.valueOf(day);

        return sNowyear + sNowmonth + sNowday;

    }

    /**
     * 어제 날짜 - YYYYMMDD
     */
    public static String getYesterday() {
        java.util.GregorianCalendar sToday = new java.util.GregorianCalendar();
        sToday.add(GregorianCalendar.DAY_OF_MONTH, -1);

        int day = sToday.get(GregorianCalendar.DAY_OF_MONTH);
        int month = sToday.get(GregorianCalendar.MONTH) + 1;
        int year = sToday.get(GregorianCalendar.YEAR);

        String sNowyear = String.valueOf(year);
        String sNowmonth = "";
        String sNowday = "";

        if (month < 10)
            sNowmonth = "0" + String.valueOf(month);
        else
            sNowmonth = String.valueOf(month);

        if (day < 10)
            sNowday = "0" + String.valueOf(day);
        else
            sNowday = String.valueOf(day);

        return sNowyear + sNowmonth + sNowday;
    }

    /**
     * 내일 날짜 - YYYYMMDD
     */
    public static String getTomorrow() {
        java.util.GregorianCalendar sToday = new java.util.GregorianCalendar();
        sToday.add(GregorianCalendar.DAY_OF_MONTH, 1);

        int day = sToday.get(GregorianCalendar.DAY_OF_MONTH);
        int month = sToday.get(GregorianCalendar.MONTH) + 1;
        int year = sToday.get(GregorianCalendar.YEAR);

        String sNowyear = String.valueOf(year);
        String sNowmonth = "";
        String sNowday = "";

        if (month < 10)
            sNowmonth = "0" + String.valueOf(month);
        else
            sNowmonth = String.valueOf(month);

        if (day < 10)
            sNowday = "0" + String.valueOf(day);
        else
            sNowday = String.valueOf(day);

        return sNowyear + sNowmonth + sNowday;
    }

    /**
     * 년도 1900 - 9999, 월 01 - 12, 일 01 - 31, 시 00 - 23, 분 00 - 59, 초 00 - 59
     *
     * @param param
     *            검사 문자열
     *
     * @return 검사결과
     */
    public static boolean isDate(String param) {
        if (param == null || param.length() != 8)
            return false;

        try {
            int year = Integer.parseInt(param.substring(0, 4));
            int month = Integer.parseInt(param.substring(4, 6));
            int day = Integer.parseInt(param.substring(6, 8));

            if (year < 1900 || year > 9999)
                return false;
            if (month < 1 || month > 12)
                return false;
            if (day < 1 || day > 31)
                return false;

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 년도 1900 - 9999, 월 01 - 12, 일 01 - 31, 시 00 - 23, 분 00 - 59, 초 00 - 59
     *
     * @param param
     *            검사 문자열
     *
     * @return 검사결과
     */
    public static boolean isTime(String param) {
        if (param == null || param.length() != 6)
            return false;

        try {
            int hour = Integer.parseInt(param.substring(0, 2));
            int min = Integer.parseInt(param.substring(2, 4));
            int sec = Integer.parseInt(param.substring(4, 6));

            if (hour < 0 || hour > 23)
                return false;
            if (min < 0 || min > 59)
                return false;
            if (sec < 0 || sec > 59)
                return false;

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * YYYYMMDD 포멧이나 YYYYDD 포멧의 달의 마지막 날짜
     * @param date
     * @return 해당 달의 마지막 날
     */
    public static String getLastDay(String date) {
        return getLastDay(Integer.parseInt(date.substring(0, 4)), Integer
                .parseInt(date.substring(4, 6)), false);
    }

    /**
     * @param yyyy
     *            년
     * @param mm
     *            월
     * @param isNowDate
     *            - 구하려는 달이 현재달일 경우 현재 날짜를 리턴할지
     * @return 해당 달의 마지막 날
     */
    public static String getLastDay(int yyyy, int mm, boolean isNowDate) {
        Calendar calendar = Calendar.getInstance();
        String str = "";
        if (isNowDate && mm == calendar.get(Calendar.MONTH) + 1) {
            str = calendar.get(Calendar.DATE) + "";
        } else {
            calendar.set(yyyy, mm - 1, 1);
            calendar.add(Calendar.MONTH, 1);
            calendar.add(Calendar.DATE, -1);
            int date = calendar.get(Calendar.DATE);
            str = date < 10 ? "0" + date : date + "";
        }
        return str;
    }

    /**
     * 입력된 일자를 입력된 포멧의 날짜로 반환
     *
     * @param type
     * @return String
     */
    public static String getFormatDay(String date, String type) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd") ;
        Date stringToDate = null;

        try {
            stringToDate = transformDate(date);
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        if (stringToDate == null) {
            return "[ ERROR ]: date must be 'yyyyMMdd'";
        }
        if (date == null || date.length() != 8)
            return "[ ERROR ]: date must be 'yyyyMMdd'";

        try {
            if (type.toLowerCase().equals("yyyy-mm-dd")) {
                sdf = new SimpleDateFormat("yyyy-MM-dd");
                return sdf.format(stringToDate);
            }
            if (type.toLowerCase().equals("yyyy/mm/dd")) {
                sdf = new SimpleDateFormat("yyyy/MM/dd");
                return sdf.format(stringToDate);
            }
            else {
                sdf = new SimpleDateFormat(type);
                return sdf.format(stringToDate);
            }
        } catch(Exception e) {
            return "[ ERROR ]: type must be 'YYYY-MM-DD', 'YYYY/MM/DD'";
        }
    }

    /**
     * @param date
     * @return Date
     * @throws ParseException
     */
    public static Date transformDate(String date) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd") ;
        SimpleDateFormat afterFormat = new SimpleDateFormat("yyyy-MM-dd");

        java.util.Date tempDate = null;

        tempDate = sdf.parse(date);

        String transDate = afterFormat.format(tempDate);

        Date d = afterFormat.parse(transDate);

        return d;
    }

    /**
     * 입력된 일자의 요일을 구한다.
     * @param date "yyyyMMdd"
     * @return String 월 ~ 일
     */
    public static String getDateDay(String date) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd") ;

        String day = "" ;
        Date nDate = null;

        try {
            nDate = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (nDate == null) {
            return "[ ERROR ]: date must be 'yyyyMMdd'";
        }

        Calendar cal = Calendar.getInstance() ;
        cal.setTime(nDate);

        int dayNum = cal.get(Calendar.DAY_OF_WEEK) ;

        switch(dayNum){
            case 1:
                day = "일";
                break ;
            case 2:
                day = "월";
                break ;
            case 3:
                day = "화";
                break ;
            case 4:
                day = "수";
                break ;
            case 5:
                day = "목";
                break ;
            case 6:
                day = "금";
                break ;
            case 7:
                day = "토";
                break ;
        }
        return day ;
    }
    
    /**
     * 입력된 두 날짜의 차이를 int 형으로 반환한다.
     * @param sDate1
     * @param sDate2
     * @return int
     * 예제
     * 		"20180512", "20180512" = 0
     * 		"20180512", "20180511" = -1
     * 		"20180412", "20180511" = 29
     * 		"20190512", "20180511" = -366
     * 		"20180512", "20190511" = 364
     */
    public static int getDaysDiff(String sDate1, String sDate2) {
        String dateStr1 = sDate1;
        String dateStr2 = sDate2;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());

        Date date1 = null;
        Date date2 = null;

        try {
            date1 = sdf.parse(dateStr1);
            date2 = sdf.parse(dateStr2);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format : args[0] = " + sDate1 + " args[1] = "+ sDate2);
        }

        int days1 = (int) ((date1.getTime() / 3600000) / 24);
        int days2 = (int) ((date2.getTime() / 3600000) / 24);

        return days2 - days1;
    }
}