package com.weitongming.util;


import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.ArrayUtils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ConvertUtils {
    public static String convertToTime(byte[] bytes) {
        String bits = bytesToBit(bytes);
        if(bits.length()<32){
            String prefix = "";
            for(int i=0;i<32-bits.length();i++){
                prefix+="0";
            }
            bits = prefix+bits;
        }
        int year = Integer.parseInt(bits.substring(0,6),2);
        int month = Integer.parseInt(bits.substring(6,10),2);
        int date = Integer.parseInt(bits.substring(10,15),2);
        int hour = Integer.parseInt(bits.substring(15,20),2);
        int min = Integer.parseInt(bits.substring(20,26),2);
        int sec = Integer.parseInt(bits.substring(26,32),2);
        return "20"+(year<10?"0"+year:year) + "-" + (month < 10 ? "0" + month : month) + "-" + (date < 10 ? "0" + date : date) + " " + (hour < 10 ? "0" + hour : hour) + ":" + (min < 10 ? "0" + min : min) + ":" + (sec < 10 ? "0" + sec : sec);
    }
    public static String readString(byte[] array, int index, int length) throws Exception {
        byte[] temp = ArrayUtils.subarray(array, index, index + length);
        String result = new String(temp, "UTF-8");
        return result;
    }
    public static Integer readUnsignedInteger(byte[] bytes,int offset,int length){
        byte[] temp = ArrayUtils.subarray(bytes,offset,offset+length);
        String bits = bytesToBit(temp);
        Integer result = Integer.parseInt(bits,2);
        return result;
    }
    public static Short readUnsignedByte(byte b){
        String bits = ByteUtil.byteToBit(b);
        Short result = Short.parseShort(bits,2);
        return result;
    }
    public static Long readUnsignedLong(byte[] bytes,int offset){
        byte[] temp = ArrayUtils.subarray(bytes,offset,offset+4);
        String bits = bytesToBit(temp);
        Long result = Long.parseLong(bits,2);
        return result;
    }
    public static int cacluateDecimal(BigDecimal factor){
        String[] ss = factor.toString().split("\\.");
        String decimal = ss[1];
        StringBuilder sb = new StringBuilder(decimal);
        for(int i=decimal.length()-1;i>=0;i--){//去掉小数位结尾多余的0
            if(decimal.charAt(i)=='0'){
                sb = sb.deleteCharAt(i);
            }else{
                break;
            }
        }
        int l = ss.length <= 1 ? 0 : sb.length();

        return l;
    }
    public static String bytesToBit(byte[] bytes){
        StringBuilder sb = new StringBuilder();

        for(int i=0;i<bytes.length;i++){
            sb.append(ByteUtil.byteToBit(bytes[i]));
        }
        return sb.toString();
    }
    public static String toJson(Object obj) throws Exception{
        return JSONObject.toJSONString(obj);
    }
    public static Object parseJson(String json,Class cls) throws Exception{
        return JSONObject.parseObject(json ,cls);
    }
    //16进制转ascii
    public static String convertHexToAscii(String hex) {
        StringBuilder sb = new StringBuilder();
        StringBuilder temp = new StringBuilder();
        //49204c6f7665204a617661 split into two characters 49, 20, 4c...
        for (int i = 0; i < hex.length() - 1; i += 2) {
            //grab the hex in pairs
            String output = hex.substring(i, (i + 2));
            //convert hex to decimal
            int decimal = Integer.parseInt(output, 16);
            //convert the decimal to character
            sb.append((char) decimal);

            temp.append(decimal);
        }
        return sb.toString();
    }
    public static String convertDateToDayStr(Date time){
        Calendar c = Calendar.getInstance();
        c.setTime(time);
        int year = c.get(Calendar.YEAR);
        int month=c.get(Calendar.MONTH)+1;
        int date =  c.get(Calendar.DATE);
        String day = year+"-"+(month<10?"0"+month:month)+"-"+(date<10?"0"+date:date);
        return day;
    }


    public static List<String> byteToList(byte[] bytes){
        return Arrays.asList("11");

    }
}
