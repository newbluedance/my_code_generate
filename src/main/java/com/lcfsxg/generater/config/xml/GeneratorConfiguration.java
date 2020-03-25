package com.lcfsxg.generater.config.xml;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lichunfeng
 * @since 2019/7/25 11:17
 */
@Data
@AllArgsConstructor
public class GeneratorConfiguration implements Serializable {
    private static final long serialVersionUID = 79831826420959010L;

    private JdbcInfo jdbc;

    private BaseInfo baseInfo;

//    private List<ModelContent> modelContentList;

    private List<Model> modelList;

    public GeneratorConfiguration() {
//        modelContentList = new ArrayList<>();
        modelList = new ArrayList<Model>();
    }
    /**
     * 添加集合属性元素的方法，add后的单词必须决定了xml中元素的名字
     *
     * @param model
     */
    public void addModel(Model model) {
        String modulePackage = StringUtils.replace(getBaseInfo().getModulePath(), "\\", ".");
        model.setEntityFullName( modulePackage.concat(".entity.").concat(model.getEntityName()));
        model.setParamFullName( modulePackage.concat(".entity.dto.").concat(model.getEntityName()).concat("Param"));
        model.setResultFullName( modulePackage.concat(".entity.dto.").concat(model.getEntityName()).concat("Result"));

        model.setMapperFullName( modulePackage.concat(".mapper.").concat(model.getEntityName()).concat("Mapper"));
        model.setServiceFullName( modulePackage.concat(".service.").concat(model.getEntityName()).concat("Service"));
        model.setServiceImplFullName( modulePackage.concat(".service.impl.").concat(model.getEntityName()).concat("ServiceImpl"));
        model.setConverterFullName( modulePackage.concat(".mapperstruct.").concat(model.getEntityName()).concat("Converter"));

        modelList.add(model);
    }


}
