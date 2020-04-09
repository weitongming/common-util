package com.weitongming.util;


import com.weitongming.dao.entity.SysField;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Bolt,转换
 */
public class ParseUtil {
    private final static Logger logger = LoggerFactory.getLogger(ParseUtil.class);

    private Map<String, List<SysField>> markToSysFields;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static List<String> signeds = Arrays.asList("rssi", "engineWaterTemperature", "spdLimWarmOfseSetSt", "trgDecelValForAebEba", "lfTyreTemperature", "rfTyreTemperature", "laTyreTemperature", "RaTyreTemperature");


    /**
     * 原始数据解析-解析成kv数据
     * @param msg 原始数据
     * @param markToSysFields 掩码以及字段定义（单个掩码可能对应多个字段）
     * @return kv数据
     */
    public static Map<String ,Object> timParse(String msg ,Map<String, List<SysField>> markToSysFields){

        if (msg == null || "".equals(msg.trim())) {
            return null;
        }

        Map<String, Object> resultMap = new LinkedHashMap<>();
        //添加协议版本号
        resultMap.put("protocolVersion", ConvertUtils.readUnsignedByte(HexUtils.hex2Bytes(msg.substring(2, 4))[0]));
        //循环解析掩码
        for (Map.Entry<String ,List<SysField>> current:markToSysFields.entrySet()) {
            //判断是否存在该掩码
            int start = msg.indexOf(current.getKey());
            int end  = 0;
            if (start == -1) {
                continue;
            }
            //一个掩码可能有多个内容，也要循环
            for(SysField sysField :current.getValue()){

            }
        }

        return null;
    }

    public static Map receive(String msg, Map<String, List<SysField>> markToSysFields) throws Exception {

        Map<String, Object> resultMap = new LinkedHashMap<>();
        List<String> marks = new ArrayList<>(markToSysFields.keySet());
        if (msg == null || "".equals(msg.trim())) {
            return null;
        }
        //协议版本号
        resultMap.put("protocolVersion", ConvertUtils.readUnsignedByte(HexUtils.hex2Bytes(msg.substring(2, 4))[0]));
        int index = 4;
        //有符号数列
        //TODO:假定分隔符不重复
        for (int i = 0; i < marks.size(); i++) {
            String mark = marks.get(i);
            int start = msg.indexOf(mark, index);
            if (start == -1) {
                continue;
            }
            //寻找每一个字段的结束位置
            int end = -1;
            List<SysField> sysFields = markToSysFields.get(mark);
            //先长度累加
            int len = 0;
            //只有头部不进行转义
            if (mark.indexOf("7e") == 0) {
                for (SysField sysField : sysFields) {
                    if (sysField.getLength() != null) {
                        len += sysField.getLength();
                    }
                }
            }
            //没有长度就找下一个标识符，这样要求找到下一个mark前,mark一定不能在内容中
            if (len == 0) {
                for (int j = i + 1; j < marks.size(); j++) {
                    int indx = msg.indexOf(marks.get(j), start);
                    if (indx != -1) {
                        end = indx;
                        break;
                    }
                }
                //结尾
                if (end == -1) {
                    end = msg.length();
                }
            } else {
                end = start + 4 + len * 2;
            }

            index = end;
            String content = msg.substring(start + 4, end);
            content = content.replaceAll("7ffe", "7e").replaceAll("7ffc", "7c").replaceAll("7ffd", "7d").
                    replaceAll("7ffa", "7a").replaceAll("7ffb", "7b").replaceAll("7fff", "7f");
            byte[] contentBytes = HexUtils.hex2Bytes(content);
            //蜂窝网络及相关代码
            if ("7c73".equals(mark)) {
                String s1 = content.substring(content.indexOf("7b01") + 4, content.indexOf("7b02"));
                if (StringUtils.isBlank(s1)) {
                    resultMap.put("cellularNetworkCountryCode", "");
                } else {
                    byte[] b1 = HexUtils.hex2Bytes(s1);
                    String r1 = ConvertUtils.readString(b1, 0, b1.length);
                    resultMap.put("cellularNetworkCountryCode", r1);
                }
                String s2 = content.substring(content.indexOf("7b02") + 4, content.indexOf("7b03"));
                if (StringUtils.isBlank(s2)) {
                    resultMap.put("cellularNetworkCode", "");
                } else {
                    byte[] b2 = HexUtils.hex2Bytes(s2);
                    String r2 = ConvertUtils.readString(b2, 0, b2.length);
                    resultMap.put("cellularNetworkCode", r2);
                }
                String s3 = content.substring(content.indexOf("7b03") + 4, content.indexOf("7b04"));
                byte[] b3 = HexUtils.hex2Bytes(s3);
                Integer r3 = ConvertUtils.readUnsignedInteger(b3, 0, b3.length);
                resultMap.put("regionalAreaCode", r3);
                String s4 = content.substring(content.indexOf("7b04") + 4);
                byte[] b4 = HexUtils.hex2Bytes(s4);
                Long r4 = ConvertUtils.readUnsignedLong(b4, 0);
                resultMap.put("baseStationCode", r4);
                continue;
            }
            int offset = 0;
            String tyreAirPressure = "";
            for (int j = 0; j < sysFields.size(); j++) {
                SysField sysField = sysFields.get(j);
                Object b = null;
                //VIN,TBOX PDSN号
                if ("7e01".equals(sysField.getMark()) || "7e02".equals(sysField.getMark())) {
                    b = ConvertUtils.convertHexToAscii(content);
                }
                //timeStamp,采集时间
                else if ("7e07".equals(sysField.getMark()) || "7d01".equals(sysField.getMark())) {
                    b = ConvertUtils.convertToTime(contentBytes);
                }
                //有三种可能长度
                else if ("7c96".equals(mark)) {
                    if (content.length() == 2) {
                        b = ConvertUtils.readUnsignedByte(contentBytes[0]);
                    } else {
                        b = ConvertUtils.readUnsignedInteger(contentBytes, 0, content.length() / 2);
                    }
                }
                //告警信息
                else if ("7c10".equals(mark)) {
                    if(ArrayUtils.isNotEmpty(contentBytes)){
                        List<String> strings = ConvertUtils.byteToList(contentBytes);
                        if(CollectionUtils.isNotEmpty(strings)){
                            b = String.join(",", strings);
                        }
                    }
                } else if ("7c72".equals(mark)) {//网络频段
                    if (contentBytes == null || contentBytes.length == 0) {
                        b = "";
                    } else {
                        b = ConvertUtils.readString(contentBytes, 0, contentBytes.length);
                    }
                } else if (sysField.getLength() != null) {
                    if (offset >= contentBytes.length) {
                        break;
                    }
                    if (sysField.getLength() == 1) {
                        //TODO:byte有可能第一位为1,这时候要看是有符号数还是无符号数，如果是无符号数这里要改
                        b = contentBytes[offset];
                        //  if0(!mark.equals("rssi")){//有符号
                        if (!signeds.contains(sysField.getEnName())) {
                            b = ConvertUtils.readUnsignedByte(contentBytes[offset]);
                        }
                        offset++;
                    } else if (sysField.getLength() == 2 || sysField.getLength() == 3) {
                        b = ConvertUtils.readUnsignedInteger(contentBytes, offset, sysField.getLength());
                        offset = offset + sysField.getLength();
                    } else if (sysField.getLength() == 4) {
                        if (sysField.getEnName().equals("gpsTime")) {
                            b = ConvertUtils.convertToTime(ArrayUtils.subarray(contentBytes, offset, contentBytes.length));
                            resultMap.put("gpsTime", b);
                            continue;
                        } else {
                            b = ConvertUtils.readUnsignedLong(contentBytes, offset);
                            offset = offset + 4;
                        }
                    }
                    //}
                    BigDecimal a = new BigDecimal(b.toString());
                    if (sysField.getFactor() != null) {
                        BigDecimal factor = new BigDecimal(sysField.getFactor().toString());
                        int l = ConvertUtils.cacluateDecimal(factor);
                        //electricValue是1位小数
                        if (sysField.getEnName().equals("electricValue") || mark.equals("7c84")) {
                            l = 1;
                        }
                        BigDecimal result = a.multiply(factor);
                        if (sysField.getOffset() != null) {
                            //TODO:暂时不考虑offset有小数时
                            result = result.add(new BigDecimal(sysField.getOffset()));
                        }
                        if (l > 0) {
                            b = result.setScale(l, BigDecimal.ROUND_HALF_UP).doubleValue();
                        } else {
                            if (b instanceof Long) {
                                b = result.longValue();
                            } else if (b instanceof Integer) {
                                b = result.intValue();
                            } else if (b instanceof Short) {
                                b = result.shortValue();
                            }
                        }
                    } else if (sysField.getOffset() != null) {
                        //TODO:暂时不考虑offset有小数时
                        BigDecimal result = a.add(BigDecimal.valueOf(sysField.getOffset()));
                        b = b instanceof Integer ? result.intValue() : result.longValue();
                    }
                } else {

                }

                if ("7c24".equals(mark)) {
                    //tbox取不到有效值，不发kafka。
                } else if ("7c84".equals(mark) && i == 0) {
                    resultMap.put("lfPressure", b);
                    //                   tyreAirPressure += b + ",";
                } else if ("7c84".equals(mark) && i == 1) {
                    resultMap.put("rfPressure", b);
//                   tyreAirPressure += b + ",";
                } else if ("7c84".equals(mark) && i == 2) {
                    resultMap.put("lrPressure", b);
//                   tyreAirPressure += b + ",";
                } else if ("7c84".equals(mark) && i == 3) {
                    resultMap.put("rrPressure", b);
                } else {

                    resultMap.put(sysField.getEnName(), b);
                }
            }

        }
        if (resultMap.get("avgFuelConsumed") != null) {
            resultMap.put("avgFuelConsumed", new BigDecimal(resultMap.get("avgFuelConsumed").toString()).divide(new BigDecimal("100000"), 2));
        }
        if (resultMap.get("temperatureUsable") != null) {
            //车外温度是无效时
            if ("1".equals(resultMap.get("temperatureUsable").toString())) {
                resultMap.put("ambientTemperature", 0);
            } else {
                int temp = Integer.parseInt(resultMap.get("ambientTemperature").toString());
                Integer tepr = temp / 10 - 40;
                resultMap.put("ambientTemperature", tepr);
            }
        }

        return resultMap;
    }

}
