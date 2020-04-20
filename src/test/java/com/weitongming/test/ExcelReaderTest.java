package com.weitongming.test;

import com.weitongming.configuration.MybatisConfiguration;
import com.weitongming.configuration.Singleton;
import com.weitongming.dao.entity.*;
import com.weitongming.dao.mapper.SysFieldMapper;
import com.weitongming.dao.mapper.SysRegionFieldMapper;
import com.weitongming.dao.mapper.SysRegionMapper;
import com.weitongming.dao.mapper.VinAndUserIdRelationMapper;
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

        List<RegionField> regionFieldList = ExcelReader.readExcel("信号项分组0401.xls",
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
                    fount.setCnName(regionField.getCnName());

                    Singleton.INST.get(SysFieldMapper.class).updateByPrimaryKeySelective(fount);

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
    @Test
    public void readVinUserIdRelation(){

        List<VinUserIdRelation> vinUserIdRelations = ExcelReader.readExcel("C:\\Users\\weitongming\\Desktop\\临时文件夹\\dataBind-2020-04-08.xlsx",
                Arrays.asList("vin" ,"userId"),
                VinUserIdRelation.class);


        MybatisConfiguration.init();
        VinAndUserIdRelationMapper vinAndUserIdRelationMapper =  Singleton.INST.get(VinAndUserIdRelationMapper.class);

        vinUserIdRelations.forEach(vinAndUserIdRelationMapper::insertSelective);

    }

    @Test
    public void readPhev(){

        List<SysField> sysFields = ExcelReader.readExcel("C:\\Users\\weitongming\\Desktop\\临时文件夹\\phev数据项及中文说明.xlsx",
                Arrays.asList("enName" ,"cnName"),
                SysField.class);

        MybatisConfiguration.init();
        SysFieldMapper sysFieldMapper =  Singleton.INST.get(SysFieldMapper.class);

        sysFields.forEach(current ->{
            current.setModuleName("CN210S PHEV");
            sysFieldMapper.insertSelective(current);
        });

    }

    @Test
    public void read2(){
        List<CarNetWorkingFunction> carNetWorkingFunctions = ExcelReader.readExcel("C:\\Users\\weitongming\\Desktop\\车联网.xlsx",
                Arrays.asList("behaviorId" ,"behaviorDescription"),
                CarNetWorkingFunction.class);
        carNetWorkingFunctions.forEach(current ->{
            System.out.println("update car_networking_function set behavior_description = '" + current.getBehaviorDescription() + "' where behavior_id = " + current.getBehaviorId() + ";");
        });
    }
}
