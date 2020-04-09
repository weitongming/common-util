package com.weitongming.test;

import com.weitongming.configuration.MybatisConfiguration;
import com.weitongming.configuration.Singleton;
import com.weitongming.dao.entity.SysField;
import com.weitongming.dao.mapper.SysFieldMapper;
import com.weitongming.util.FileUtil;
import com.weitongming.util.ParseUtil;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class ParseTest {



    @Test
    public void testParse() {

        List<String> content = FileUtil.readFileAndReturnList("2020-03-03-5-temp.txt");
        MybatisConfiguration.init();
        SysFieldMapper sysFieldMapper = Singleton.INST.get(SysFieldMapper.class);


        List<SysField> sysFields = sysFieldMapper.selectAll();

        Map<String, List<SysField>> groupByMark = sysFields
                .stream()
                .collect(
                        Collectors.groupingBy(SysField::getMark,
                                Collectors.mapping(Function.identity(), toList())
                        ));
        content.forEach(current -> {
            try {
                Map parsed = ParseUtil.receive(current, groupByMark);
                System.out.println(parsed);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }
}
