package com.technikon.final_project_ed.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * DateFormatterUtil class validates, parses and formats dates
 *
 * @author Kostas Vamvakousis
 */
public class DateFormatterUtil {

    //date format for the date output format
    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    //regex pattern of the correct date format
    private static final Pattern DATE_PATTERN = Pattern.compile("^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$");

    /**
     * getParsedDate parses a dateString as parameter and returns the
     * appropriate date according to the date format that it uses. If the date
     * is unparsable it returns null.
     *
     * @param dateString
     * @return
     */
    public Date getParsedDate(String dateString) {
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    /**
     * isDateValid method is responsible for checking if the given dateString
     * matches the correct DATE_PATTERN. If it matches isDateValid returns true.
     *
     * @param dateString
     * @return
     * @throws ParseException
     */
    public boolean isDateValid(String dateString) throws ParseException {
        return DATE_PATTERN.matcher(dateString).matches();
    }

    /**
     * getFormattedDate method gets a Date as a parameter and returns the
     * formatted string
     *
     * @param date
     * @return
     */
    public String getFormattedDate(Date date) {
        return dateFormat.format(date);
    }
}
