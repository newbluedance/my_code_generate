package com.lcfsxg.generater.utils;

import com.lcfsxg.generater.java.FullyQualifiedJavaType;

/**
 * @author lichunfeng
 * @since 2019/8/1 19:09
 */
public interface JavaTypeConstant {
    FullyQualifiedJavaType LOMBOK_DATA=new FullyQualifiedJavaType("lombok.Data");
    FullyQualifiedJavaType COLUMN=new FullyQualifiedJavaType("javax.persistence.Column");
    FullyQualifiedJavaType GENERATEDVALUE=new FullyQualifiedJavaType("javax.persistence.GeneratedValue");
    FullyQualifiedJavaType ID=new FullyQualifiedJavaType("javax.persistence.Id");
    FullyQualifiedJavaType TABLE=new FullyQualifiedJavaType("javax.persistence.Table");

    FullyQualifiedJavaType VALID_LENGTH=new FullyQualifiedJavaType("org.hibernate.validator.constraints.Length");

    FullyQualifiedJavaType VALID_ADD=new FullyQualifiedJavaType("com.hdtd.ibasebuild.common.validation.group.Add");
    FullyQualifiedJavaType VALID_UPDATE=new FullyQualifiedJavaType("com.hdtd.ibasebuild.common.validation.group.Update");

    String COMMON_BASE_ENTITY_STR="com.hdtd.ibasebuild.common.base.BaseEntity";
    String COMMON_BASE_PARAM_STR="com.hdtd.ibasebuild.common.base.BaseParam";
    String COMMON_BASE_RESULT_STR="com.hdtd.ibasebuild.common.base.BaseResult";
    String ANNOTATIONS_MAPPER_STR="org.apache.ibatis.annotations.Mapper";
    String ANNOTATIONS_SERVICE_STR="org.springframework.stereotype.Service";
    String BASE_MAPPER_STR="com.hdtd.ibasebuild.common.base.BaseMapper";
    String BASE_SERVICE_STR="com.hdtd.ibasebuild.common.base.BaseService";
    String BASE_CONVERTER_STR="com.hdtd.ibasebuild.common.base.BaseConverter";
    String BASE_SERVICE_IMPL_STR="com.hdtd.ibasebuild.common.base.BaseServiceImpl";

}
