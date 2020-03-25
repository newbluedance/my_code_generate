package com.lcfsxg.generater.config.xml;

import lombok.Data;

import java.io.Serializable;
import java.security.PrivateKey;

/**
 * @author lichunfeng
 * @since 2019/7/25 11:10
 */
@Data
public class JdbcInfo implements Serializable {
    private static final long serialVersionUID = 79831828210959011L;
    private String url;
    private String userName;
    private String passWord;

    public String getTableSchema(){
        String[] split = url.split("\\?");
        String[] split1 = split[0].split("/");
        return split1[split1.length-1];
    }

}
