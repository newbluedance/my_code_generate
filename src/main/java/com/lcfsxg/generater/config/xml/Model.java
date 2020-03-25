package com.lcfsxg.generater.config.xml;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author lichunfeng
 * @since 2019/7/25 16:52
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Model implements Serializable {

    private String tableName;
    private String entityName;

    private String entityFullName;
    private String paramFullName;
    private String resultFullName;
    private String mapperFullName;
    private String serviceFullName;
    private String converterFullName;
    private String serviceImplFullName;

}