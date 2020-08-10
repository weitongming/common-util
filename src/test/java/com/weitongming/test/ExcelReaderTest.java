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
            Integer regionId = sysRegion.getId();
            List<RegionField> regionFields = current.getValue() ;

            regionFields.forEach(regionField ->{
                SysField search = new SysField().setEnName(regionField.getEnName());
                SysField fount = Singleton.INST.get(SysFieldMapper.class).selectOne(search);
                if (fount != null){
                    fount.setCnName(regionField.getCnName());

                    Singleton.INST.get(SysFieldMapper.class).updateByPrimaryKeySelective(fount);

                    SysRegionField sysRegionField = new SysRegionField()
                            .setFieldId(fount.getId())
                            .setRegionId(Integer.valueOf(regionId));
                    Singleton.INST.get(SysRegionFieldMapper.class).insertSelective(sysRegionField);
                }
                else{
                    System.out.println("unknown:" + regionField.getEnName());
                }
            });
        });
    }


    @Test
    public void readExcelV2(){

        List<RegionField> regionFieldList = ExcelReader.readExcel("信号项分组0401.xls",
                Arrays.asList("regionName" ,"cnName","enName"),
                RegionField.class);
        Map<String ,List<RegionField>> map = regionFieldList.stream().collect(
                Collectors.groupingBy(
                        RegionField::getRegionName,
                        Collectors.mapping(Function.identity(), Collectors.toList())
                ));
        System.out.println(map);

        MybatisConfiguration.init();

        map.forEach((key, regionFields) -> {

            List<String> modelList = Arrays.asList("CN180S MCE","CN202M","CN210S","CN220C");

            modelList.forEach(model->{
                SysRegion sysRegion = Singleton.INST.get(SysRegionMapper.class).selectOne(new SysRegion()
                        .setRegionName(key)
                        .setModuleName(model)
                        .setRegionStatus(1));

                if (sysRegion == null){
                    return;
                }
                regionFields.forEach(regionField -> {
                    SysField search = new SysField().setEnName(regionField.getEnName()).setModuleName(model);
                    List<SysField> fountList = Singleton.INST.get(SysFieldMapper.class).select(search);

                    if (fountList == null){
                        return;
                    }
                    fountList.forEach(fount->{
                        SysRegionField sysRegionField = new SysRegionField().setRegionId(sysRegion.getId()).setFieldId(fount.getId());
                        Singleton.INST.get(SysRegionFieldMapper.class).insertSelective(sysRegionField);
                    });
                });

            });

        });
    }


    @Test
    public void readExcel3(){

        List<RegionField> regionFieldList = ExcelReader.readExcel("phev信号分组0518.xlsx",
                Arrays.asList("enName","cnName","mark","regionName" ),
                RegionField.class);
        System.out.println(regionFieldList);
        Map<String ,List<RegionField>> map = regionFieldList.stream().collect(
                Collectors.groupingBy(
                        RegionField::getRegionName,
                        Collectors.mapping(Function.identity(), Collectors.toList())
                ));
        System.out.println(map);
        String moduleName = "CN210S PHEV";
        MybatisConfiguration.init();
        map.entrySet().forEach(current ->{
            SysRegion sysRegion  = new SysRegion()
                    .setModuleName(moduleName)
                    .setRegionName(current.getKey())
                    .setRegionStatus(1);
            Singleton.INST.get(SysRegionMapper.class).insertSelective(sysRegion);
            Integer regionId = sysRegion.getId();
            List<RegionField> regionFields = current.getValue() ;

            regionFields.forEach(regionField ->{
                SysField insert = new SysField()
                        .setEnName(regionField.getEnName())
                        .setCnName(regionField.getCnName())
                        .setMark(regionField.getMark())
                        .setModuleName(moduleName);
                Singleton.INST.get(SysFieldMapper.class).insertSelective(insert);

                SysRegionField sysRegionField = new SysRegionField()
                        .setFieldId(insert.getId())
                        .setRegionId(regionId);
                Singleton.INST.get(SysRegionFieldMapper.class).insertSelective(sysRegionField);

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

        List<SysField> sysFields = ExcelReader.readExcel("C:\\Users\\weitongming\\Desktop\\临时文件夹\\phev数据项-0421.xlsx",
                Arrays.asList("enName" ,"cnName","mark"),
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

    @Test
    public void updateField(){
        List<SysField> sysFieldList = ExcelReader.readExcel("字段说明0615.xlsx",
                Arrays.asList("enName" ,"dataRange","dataUnit"),
                SysField.class);

        MybatisConfiguration.init();
        SysFieldMapper sysFieldMapper =  Singleton.INST.get(SysFieldMapper.class);



        sysFieldList.forEach(current ->{

            List<SysField> foundList = sysFieldMapper.select(new SysField().setEnName(current.getEnName()));
            foundList.forEach(sysField -> {

                sysField.setDataRange(current.getDataRange());
                sysField.setDataUnit(current.getDataUnit());

                sysFieldMapper.updateByPrimaryKeySelective(sysField);
                System.out.println(sysField.getId());
            });

        });
    }


}
