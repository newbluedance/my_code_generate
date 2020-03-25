package com.lcfsxg.generater.generate;

import com.lcfsxg.generater.config.xml.GeneratorConfiguration;
import com.lcfsxg.generater.config.xml.Model;
import com.lcfsxg.generater.generate.beanConfig.BeanConfig;
import com.lcfsxg.generater.java.FullyQualifiedJavaType;
import com.lcfsxg.generater.java.Interface;
import com.lcfsxg.generater.java.JavaVisibility;
import com.lcfsxg.generater.utils.JavaTypeConstant;
import com.lcfsxg.generater.utils.MyStringUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author lichunfeng
 * @since 2019/8/1 16:24
 */
public class MapperXmlGenerate extends BaseGenerate {

    /**
     * 开始生成,主要业务入口
     *
     * @return
     */
    @Override
    String generateOne(Model model) {
        String xmlStr = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                + "<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\n"
                + "<mapper namespace=\"" + model.getMapperFullName() + "\">\n"
                + "</mapper>";
        String mapperPath = baseConfig.getBaseInfo().getProjectPath().replace("java","resources").concat("mappers\\")
                .concat(model.getEntityName()).concat("Mapper.xml");
        MyStringUtils.outFile(xmlStr, mapperPath);

        return xmlStr;
    }




}
