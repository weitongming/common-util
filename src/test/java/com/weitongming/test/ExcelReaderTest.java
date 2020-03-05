package com.weitongming.test;

import com.weitongming.configuration.MybatisConfiguration;
import com.weitongming.configuration.Singleton;
import com.weitongming.dao.entity.RegionField;
import com.weitongming.dao.entity.SysField;
import com.weitongming.dao.entity.SysRegion;
import com.weitongming.dao.entity.SysRegionField;
import com.weitongming.dao.mapper.SysFieldMapper;
import com.weitongming.dao.mapper.SysRegionFieldMapper;
import com.weitongming.dao.mapper.SysRegionMapper;
import com.weitongming.util.ExcelReader;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ExcelReaderTest {

    @Test
    public void readExcel(){

        List<RegionField> regionFieldList = ExcelReader.readExcel("C:\\Users\\weitongming\\Desktop\\Book1.xlsx",
                Arrays.asList("regionName" ,"cnName","enName"),
                RegionField.class);
        System.out.println(regionFieldList);
        Map<String ,List<RegionField>> map = regionFieldList.stream().collect(
                Collectors.groupingBy(
                        RegionField::getRegionName,
                        Collectors.mapping(Function.identity(), Collectors.toList())
                ));
        System.out.println(map);

        MybatisConfiguration.init();
        map.entrySet().forEach(current ->{
            SysRegion sysRegion  = new SysRegion()
                    .setRegionName(current.getKey())
                    .setRegionStatus(1);
            Singleton.INST.get(SysRegionMapper.class).insertSelective(sysRegion);
            Long regionId = sysRegion.getId();
            List<RegionField> regionFields = current.getValue() ;

            regionFields.forEach(regionField ->{
                SysField search = new SysField().setEnName(regionField.getEnName());
                SysField fount = Singleton.INST.get(SysFieldMapper.class).selectOne(search);
                if (fount != null){
                    SysRegionField sysRegionField = new SysRegionField()
                            .setFieldId(Long.valueOf(fount.getId()))
                            .setRegionId(Long.valueOf(regionId));
                    Singleton.INST.get(SysRegionFieldMapper.class).insertSelective(sysRegionField);
                }
                else{
                    System.out.println("unknown:" + regionField.getEnName());
                }
            });
        });
    }
}
