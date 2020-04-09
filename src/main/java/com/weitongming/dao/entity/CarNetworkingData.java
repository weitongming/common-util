package com.weitongming.dao.entity;


import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CarNetworkingData implements Comparable<CarNetworkingData>{

    @JSONField(defaultValue = "clienttype")
    private String clientType;

    @JSONField(defaultValue = "phone_series")
    private String phoneSeries;

    @JSONField(defaultValue = "app_version")
    private String appVersion;

    @JSONField(defaultValue = "clientversion")
    private String clientVersion;

    @JSONField(defaultValue = "frombrand")
    private String fromBrand;

    @JSONField(defaultValue = "behaviorid")
    private String behaviorId;

    @JSONField(defaultValue = "phone_os")
    private String phoneOs;

    @JSONField(defaultValue = "phone_brand")
    private String phoneBrand;

    @JSONField(defaultValue = "interactiveway")
    private String interActiveWay;

    @JSONField(defaultValue = "happentime")
    private String happenTime;

    @JSONField(defaultValue = "latitude")
    private String latitude;

    @JSONField(defaultValue = "longitude")
    private String longitude;

    @JSONField(defaultValue = "cp_source")
    private String cpSource;

    @JSONField(defaultValue = "phone_carrier")
    private String phoneCarrier;

    @JSONField(defaultValue = "phone_num")
    private String phoneNum;

    @JSONField(defaultValue = "vin")
    private String vin;

    @JSONField(defaultValue = "userid_deviceid")
    private String userIdDeviceId;

    private Integer timeStamp ;

    @Override
    public int compareTo(CarNetworkingData carNetworkingData) {
        return this.getHappenTime().compareTo(carNetworkingData.getHappenTime());
    }
}
