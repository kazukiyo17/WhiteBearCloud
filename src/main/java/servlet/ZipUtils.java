package servlet;

import java.security.MessageDigest;


public class ZipUtils {
	public static String string2MD5(String inStr){
        MessageDigest md5 = null;
        try{
            md5 = MessageDigest.getInstance("MD5");
        }catch (Exception e){
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }
        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];
 
        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++){
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
 
    }
 
    /**
     * ���ܽ����㷨 ִ��һ�μ��ܣ����ν���
     */
    public static String convertMD5(String inStr){
 
        char[] a = inStr.toCharArray();
        for (int i = 0; i < a.length; i++){
            a[i] = (char) (a[i] ^ 't');
        }
        String s = new String(a);
        return s;
 
    }


    public static void main(String[] args) {
    	
    	String str="http://123456-1259063860.cos.ap-shanghai.myqcloud.com/a2391c5a-f403-4df2-9106-0a72dec421bb?sign=q-sign-algorithm%3Dsha1%26q-ak%3DAKIDd4FBrtMdfMvIUZCev491hX9WfopaJThg%26q-sign-time%3D1567771576%3B1567951576%26q-key-time%3D1567771576%3B1567951576%26q-header-list%3D%26q-url-param-list%3D%26q-signature%3Ddb2f4a25f0922a24d49d5e8f691d7f9b19c53877";
    	String test=ZipUtils.convertMD5(str);
    	System.out.println(test);
    	System.out.println(ZipUtils.convertMD5(test));
    
    }
    
}
