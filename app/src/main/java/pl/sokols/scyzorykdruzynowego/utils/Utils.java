package pl.sokols.scyzorykdruzynowego.utils;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    @SuppressLint("SimpleDateFormat")
    public static Date getDateFromString(String date) throws ParseException {
        return new SimpleDateFormat("dd-MM-yyyy").parse(date);
    }
}
