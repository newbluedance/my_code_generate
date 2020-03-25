package com.lcfsxg.generater.generate;

import com.lcfsxg.generater.MainTest;
import com.lcfsxg.generater.config.xml.GeneratorConfiguration;
import com.lcfsxg.generater.config.xml.Model;
import com.lcfsxg.generater.generate.beanConfig.BeanConfig;
import com.lcfsxg.generater.utils.JdbcUtil;
import com.lcfsxg.generater.utils.MyStringUtils;
import org.apache.commons.betwixt.io.BeanReader;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.xml.sax.SAXException;

import java.beans.IntrospectionException;
import java.io.*;
import java.util.Date;
import java.util.List;

/**
 * @author lichunfeng
 * @since 2019/8/1 16:24
 */
public abstract class BaseGenerate {

    protected static GeneratorConfiguration baseConfig;

    protected List<BeanConfig> beanConfigList;

    String sql = "SELECT COLUMN_COMMENT comment, column_name jdbcName, DATA_TYPE jdbcTypeName,CHARACTER_MAXIMUM_LENGTH dataLength FROM information_schema.COLUMNS co WHERE table_name = ? and TABLE_SCHEMA=?  ORDER BY ORDINAL_POSITION";


    static {
       try {
           InputStream resourceAsStream = MainTest.class.getClassLoader().getResourceAsStream("generate.xml");

           //创建一个BeanReader实例，相当于转换器
           BeanReader beanReader = new BeanReader();
           //配置BeanReader实例
           beanReader.getXMLIntrospector().getConfiguration().setAttributesForPrimitives(false);
           beanReader.registerBeanClass(GeneratorConfiguration.class);
           baseConfig = (GeneratorConfiguration) beanReader.parse(resourceAsStream);


       } catch (IntrospectionException e) {
           e.printStackTrace();
       } catch (IOException e) {
           e.printStackTrace();
       } catch (SAXException e) {
           e.printStackTrace();
       }
   }

    /**
     * 开始生成,主要业务入口
     *
     * @return
     */
    public void generate() {
        List<Model> modelList = baseConfig.getModelList();
        for (Model model : modelList) {
            //取得数据config
            JdbcUtil jdbcUtil = new JdbcUtil(baseConfig.getJdbc());
            String tableSchema = baseConfig.getJdbc().getTableSchema();
            beanConfigList = jdbcUtil.executeQuery(sql, new BeanConfig(), model.getTableName(),tableSchema);

            generateOne(model);
        }
    }

    /**
     * 开始生成,主要业务入口
     *
     * @return
     */
    abstract String generateOne(Model model);

    /**
     * 类注释
     *
     * @return
     */
    protected String getDefaultJavaDocLine(String author) {
        return "/**\n"
                + " * \n"
                + " * @author " + author + "\n"
                + " * @date " + DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss") + "\n"
                + " */";
    }

    /**
     *
     * @param s
     * @param path
     */
    protected void outFile(String s, String path,String javaFullName) {
        javaFullName=StringUtils.replace(javaFullName,".","\\");
        String replace = path.concat(javaFullName).concat(".java");
        MyStringUtils.outFile(s,replace);
    }

}
