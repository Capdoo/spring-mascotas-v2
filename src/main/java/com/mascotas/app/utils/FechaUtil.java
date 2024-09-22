package com.mascotas.app.utils;

import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class FechaUtil {

    public FechaUtil() {
        super();
    }

    //Convertir TimeStamp en fecha String
    public static Timestamp getTimestampFromStringDate(String fecha) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date parsedDate;
        try {
            parsedDate = dateFormat.parse(fecha);
            Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
            return timestamp;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    public static String getStrindDateFromTimestamp(Timestamp mytimestamp) {
        return new SimpleDateFormat("dd/MM/yyyy").format(mytimestamp);
    }
}