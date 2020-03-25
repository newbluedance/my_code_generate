package com.lcfsxg.generater.generate.beanConfig;

import com.lcfsxg.generater.java.FullyQualifiedJavaType;
import com.lcfsxg.generater.utils.MyStringUtils;
import lombok.Data;

/**
 * @author lichunfeng
 */
@Data
public class BeanConfig {

    private String jdbcName;

    private String comment;

    private String jdbcTypeName;

    private String dataLength;

    private String declareStr;

    private String comments;

    private String annotation;

    private String fieldType;

    private String fieldName;

    protected FullyQualifiedJavaType fullyQualifiedJavaType;


    public String getFieldName() {
        if (jdbcName.indexOf("is_") == 0) {
            return MyStringUtils.lineToHump(jdbcName.substring(3).concat("_flag"));
        }

        return MyStringUtils.lineToHump(jdbcName) ;
    }


    public String getComments() {
        if ("id".equals(jdbcName)) {
            return "/** ID主键自增 */";
        }
        return "/** " + comment + " */";
    }

    public String getAnnotation() {
        if ("id".equals(jdbcName)) {
            return "@Id\n"
                + "    @GeneratedValue(generator = \"JDBC\")";
        } else if (jdbcName.indexOf("is_") == 0) {
            return "@Column(name = \"" + jdbcName + "\")";
        }
        return "@Column";
    }



    public String getFieldType() {
        if ("int".equals(this.jdbcTypeName) || "tinyint".equals(this.jdbcTypeName) || "smallint".equals(this.jdbcTypeName)) {
            return "Integer";
        } else if ("bigint".equals(this.jdbcTypeName)) {
            return "Long";
        }
        else if ("double".equals(this.jdbcTypeName)) {
            return "Double";
        }
        else if ("float".equals(this.jdbcTypeName)) {
            return "Float";
        }
        else if ("decimal".equals(this.jdbcTypeName)) {
            return "BigDecimal";
        } else if ("varchar".equals(this.jdbcTypeName) || "text".equals(this.jdbcTypeName)||"enum".equals(this.jdbcTypeName)) {
            return "String";
        } else if ("date".equals(this.jdbcTypeName)) {
            return "java.time.LocalDate";
        } else if ("timestamp".equals(this.jdbcTypeName)) {
            return "java.time.LocalDateTime";
        } else if ("datetime".equals(this.jdbcTypeName)) {
            return "java.time.LocalDateTime";
        } else if ("mediumblob".equals(this.jdbcTypeName)) {
            return "byte[]";
        } else {
            return "undefine";
        }

    }

}
