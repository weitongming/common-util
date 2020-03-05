package com.weitongming.test;

import com.weitongming.util.FileUtil;
import org.junit.Test;

import java.util.List;

public class FileTest {




    @Test
    public void testReadFile(){
        List<String> content = FileUtil.readFileAndReturnList("2020-03-03-5-temp.txt");

        System.out.println(content);
    }
}
