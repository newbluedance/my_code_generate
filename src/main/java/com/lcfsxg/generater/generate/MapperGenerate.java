package com.lcfsxg.generater.generate;

import com.lcfsxg.generater.config.xml.Model;
import com.lcfsxg.generater.java.*;
import com.lcfsxg.generater.utils.JavaTypeConstant;

import java.util.*;

/**
 * @author lichunfeng
 * @since 2019/8/1 16:24
 */
public class MapperGenerate extends BaseGenerate {

    /**
     * 开始生成,主要业务入口
     *
     * @return
     */
    @Override
    String generateOne(Model model) {
        //定义类编写单元
        Interface anInterface = new Interface(model.getMapperFullName());
        //类相关
        anInterface.setVisibility(JavaVisibility.PUBLIC);
        FullyQualifiedJavaType superInterFace = new FullyQualifiedJavaType(JavaTypeConstant.BASE_MAPPER_STR);

        superInterFace.addTypeArgument(new FullyQualifiedJavaType(model.getEntityFullName()));
        anInterface.addSuperInterface(superInterFace);
        //类注释和类注解
        anInterface.addJavaDocLine(getDefaultJavaDocLine(baseConfig.getBaseInfo().getAuthor()));
        anInterface.addAnnotation("@Mapper");
        //导包
        anInterface.addImportedTypes(getBaseImportedTypes());

        outFile(anInterface.getFormattedContent(),baseConfig.getBaseInfo().getProjectPath(),model.getMapperFullName());
        return anInterface.getFormattedContent();
    }

    /**
     * 获取基本需要导入的包
     *
     * @return
     */
    Set<FullyQualifiedJavaType> getBaseImportedTypes() {
        Set<FullyQualifiedJavaType> javaTypes = new HashSet<>();
        javaTypes.add(new FullyQualifiedJavaType(JavaTypeConstant.ANNOTATIONS_MAPPER_STR));
        return javaTypes;
    }


}
