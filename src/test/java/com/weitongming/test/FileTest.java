package com.weitongming.test;

import com.alibaba.fastjson.JSONObject;
import com.weitongming.dao.entity.CarNetworkingData;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class FileTest {




    @Test
    public void testReadFile(){

        AtomicInteger valid = new AtomicInteger(0);
        AtomicInteger invalid = new AtomicInteger(0);

        Set<String> vinSet = new HashSet<>(2000);

        try {
            FileReader fr = new FileReader("C:\\Users\\weitongming\\Desktop\\车机数据\\wlapp_result_20200306.txt");
            BufferedReader bf = new BufferedReader(fr);
            String str;
            // 按行读取字符串
            while ((str = bf.readLine()) != null) {
                JSONObject jsonObject = JSONObject.parseObject(str);
                JSONObject dataJSON = jsonObject.getJSONObject("data").getJSONObject("commondata");
                if (dataJSON == null){
                    continue;
                }
                String dataString = dataJSON.toJSONString();
                CarNetworkingData carNetworkingData = JSONObject.parseObject(dataString, CarNetworkingData.class);


                    vinSet.add(carNetworkingData.getVin());
                    valid.addAndGet(1);

            }
            bf.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(valid.intValue());

        System.out.println(invalid.intValue());

        System.out.println(vinSet.size());

    }


    @Test
    public  void testReadFile2( ){
        List<CarNetworkingData> test =  testReadFile("LZWADAGA7KB737861");
        System.out.println(test);
    }


    public  List<CarNetworkingData> testReadFile(String vin){

        List<CarNetworkingData> list = new ArrayList<>(10);

        try {
            FileReader fr = new FileReader("C:\\Users\\weitongming\\Desktop\\车机数据\\wlapp_result_20191230.txt");
            BufferedReader bf = new BufferedReader(fr);
            String str;
            // 按行读取字符串
            while ((str = bf.readLine()) != null) {
                JSONObject jsonObject = JSONObject.parseObject(str);
                JSONObject dataJSON = jsonObject.getJSONObject("data").getJSONObject("commondata");
                if (dataJSON == null){
                    continue;
                }
                String dataString = dataJSON.toJSONString();
                CarNetworkingData carNetworkingData = JSONObject.parseObject(dataString, CarNetworkingData.class);

                if (carNetworkingData.getBehaviorId() == null || carNetworkingData.getBehaviorId().length() == 0 || "2001720".equalsIgnoreCase(carNetworkingData.getBehaviorId())) {
                    continue;
                }

                if (vin.equalsIgnoreCase(carNetworkingData.getVin())){
                    list.add(carNetworkingData);
                }

            }
            bf.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list ;

    }


}
