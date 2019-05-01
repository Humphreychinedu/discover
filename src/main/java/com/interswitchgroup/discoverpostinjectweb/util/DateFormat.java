package com.interswitchgroup.discoverpostinjectweb.util;

import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class DateFormat {

        public static String newDate() {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            Date now = new Date();
            String strDate = sdf.format(now);
            return strDate;
        }

}
