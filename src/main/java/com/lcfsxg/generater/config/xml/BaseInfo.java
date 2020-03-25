package com.lcfsxg.generater.config.xml;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lichunfeng
 * @since 2019/7/25 11:10
 */
@Data
public class BaseInfo implements Serializable {

    private static final long serialVersionUID = 54831826420959452L;

    private String author;
    private String projectPath;
    private String modulePath;
}
