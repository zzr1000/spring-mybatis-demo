package org.zzr1000.springTest.config;

import org.mybatis.spring.mapper.MapperScannerConfigurer;

/**
 * mybatis
 * xiaoxian20180402
 */
public class MyBatisMapperScannerConfig {

    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        mapperScannerConfigurer.setBasePackage(" org.zzr1000.springTest.dao");
        return mapperScannerConfigurer;
    }

}
