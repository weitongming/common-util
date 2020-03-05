package com.weitongming.configuration;


import com.weitongming.dao.mapper.CarNetWorkingFunctionMapper;
import com.weitongming.dao.mapper.SysFieldMapper;
import com.weitongming.dao.mapper.SysRegionFieldMapper;
import com.weitongming.dao.mapper.SysRegionMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import tk.mybatis.mapper.mapperhelper.MapperHelper;

import java.io.IOException;
import java.io.InputStream;

/**
 * mybatis 配置项
 */
public class MybatisConfiguration {

    private static SqlSession sqlSession = null;

    public static void init(){
        String resource = "mybatis.xml";
        InputStream inputStream;
        try {
            inputStream = Resources.getResourceAsStream(resource);
            // 构建sqlSession工厂
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            // 获取sqlSession
            //这里的openSession参数设为true，不设的话，每次提交之后，数据库都会回滚
            sqlSession = sqlSessionFactory.openSession(true);
            //将tk.mybatis集成到mybatis中
            MapperHelper mapperHelper = new MapperHelper();
            //mapperHelper.registerMapper(FaultMapper.class);
            mapperHelper.processConfiguration(sqlSession.getConfiguration());

            //构建tkMapper
            CarNetWorkingFunctionMapper carNetWorkingFunctionMapper = sqlSession.getMapper(CarNetWorkingFunctionMapper.class);
            SysFieldMapper sysFieldMapper = sqlSession.getMapper(SysFieldMapper.class);
            SysRegionFieldMapper sysRegionFieldMapper = sqlSession.getMapper(SysRegionFieldMapper.class);
            SysRegionMapper sysRegionMapper = sqlSession.getMapper(SysRegionMapper.class);


            //缓存mapper起来
            Singleton.INST.single(CarNetWorkingFunctionMapper.class, carNetWorkingFunctionMapper);
            Singleton.INST.single(SysFieldMapper.class, sysFieldMapper);
            Singleton.INST.single(SysRegionFieldMapper.class, sysRegionFieldMapper);
            Singleton.INST.single(SysRegionMapper.class, sysRegionMapper);



        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
    }


    /**
     * 销毁上下文
     */
    public static void destroy(){
        if (sqlSession != null) {
                sqlSession.close();
            }
    }
}
