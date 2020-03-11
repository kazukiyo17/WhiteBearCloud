package util;

import java.security.MessageDigest;

/**
 * 
 *@class_name：MyMD5Util
 *@comments:
 *@param:
 *@return: 
 *@author:冯书逸
 *@createtime:2019年9月5日
 */

public class MyMD5Util {
    private static final String slat = "&%5123**GFGVHJBKIUGYFGHC*Y&^YU^*&%$&*^%$^#$%ETyfgfgchvjgUFGHGY^&*%^$rtytuy74563retdgfhGJ*&&%%$$#@";
    public static String encrypt(String dataStr) {
        try {
            dataStr = dataStr + slat;
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(dataStr.getBytes("UTF8"));
            byte s[] = m.digest();
            String result = "";
            for (int i = 0; i < s.length; i++) {
                result += Integer.toHexString((0x000000FF & s[i]) | 0xFFFFFF00).substring(6);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }
}
