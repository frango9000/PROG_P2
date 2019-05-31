/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author NarF
 */
public class DateTimeFormat {
    
    static final String USER_FORMAT = "HH:mm:ss dd-MM-uuuu";
    static final String DB_FORMAT = "uuuu-MM-dd HH:mm:ss.SSS";

    public static String toDbString(LocalDateTime datetime) {
        return datetime.format(DateTimeFormatter.ofPattern(DB_FORMAT));
    }

    public static String toDbString(String f_HHmmssddMMuuuu) {
        LocalDateTime ldt = userStringToLocalDateTime(f_HHmmssddMMuuuu);
        return ldt.format(DateTimeFormatter.ofPattern(DB_FORMAT));
    }
    
    public static LocalDateTime userStringToLocalDateTime(String f_HHmmssddMMuuuu){
        return LocalDateTime.parse(f_HHmmssddMMuuuu, DateTimeFormatter.ofPattern(USER_FORMAT));
    }
    
    public static String toUserString(LocalDateTime datetime) {
        return datetime.format(DateTimeFormatter.ofPattern(USER_FORMAT));
    }
    
    public static String toUserString(String f_uuuuMMddHHmmssSSS) {
        LocalDateTime ldt = dbStringToLocalDateTime(f_uuuuMMddHHmmssSSS);
        return ldt.format(DateTimeFormatter.ofPattern(USER_FORMAT));
    }
    
    public static LocalDateTime dbStringToLocalDateTime(String f_uuuuMMddHHmmssSSS){
        return LocalDateTime.parse(f_uuuuMMddHHmmssSSS, DateTimeFormatter.ofPattern(DB_FORMAT));
    }
}
