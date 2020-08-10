//package com.weitongming.util;
//
//import com.alibaba.fastjson.JSONObject;
//
///**
// * @author weitongming
// * @date 2020年07月01日 17:35
// */
//
//
//public class HttpUtils {
//
//    /**
//     * 根据经纬度从百度获取城市信息
//     * 报错的时候默认返回柳州
//     * @param lon 经度
//     * @param lat 纬度
//     * @return 地理信息
//     */
//    private Geocoder getRegionFromBaidu(String lon ,String lat){
//        try {
//            String targetUrl = String.format(BAIDU_API_URL ,lat,lon);
//            String resp = HttpUtil.get(targetUrl);
//            //可能空指针
//            JSONObject jsonObject = JSONObject.parseObject(resp).getJSONObject("result").getJSONObject("addressComponent");
//            return new Geocoder().setCity(jsonObject.getString("city")).setProvince(jsonObject.getString("province"));
//        }catch (Exception e){
//            return new Geocoder().setCity("柳州市").setProvince("广西壮族自治区");
//        }
//    }
//}
