package com.lcfsxg.generater.generate;

import com.lcfsxg.generater.config.xml.Model;
import com.lcfsxg.generater.java.FullyQualifiedJavaType;
import com.lcfsxg.generater.java.JavaVisibility;
import com.lcfsxg.generater.java.TopLevelClass;
import com.lcfsxg.generater.utils.JavaTypeConstant;

import java.util.HashSet;
import java.util.Set;

/**
 * @author lichunfeng
 * @since 2019/8/1 16:24
 */
public class ServiceImplGenerate  extends BaseGenerate{

    /**
     * 开始生成,主要业务入口
     *
     * @return
     */
    @Override
    String generateOne(Model model) {
        //定义类编写单元
        TopLevelClass topLevelClass = new TopLevelClass(model.getServiceImplFullName());
        //类相关
        topLevelClass.setVisibility(JavaVisibility.PUBLIC);
        FullyQualifiedJavaType superClass = new FullyQualifiedJavaType(JavaTypeConstant.BASE_SERVICE_IMPL_STR);
        FullyQualifiedJavaType superInterface = new FullyQualifiedJavaType(model.getServiceFullName());

        superClass.addTypeArgument(new FullyQualifiedJavaType(model.getEntityFullName()));
        superClass.addTypeArgument(new FullyQualifiedJavaType(model.getParamFullName()));
        superClass.addTypeArgument(new FullyQualifiedJavaType(model.getResultFullName()));
        topLevelClass.setSuperClass(superClass);
        topLevelClass.addSuperInterface(superInterface);

        //类注释和类注解
        topLevelClass.addJavaDocLine(getDefaultJavaDocLine(baseConfig.getBaseInfo().getAuthor()));
        topLevelClass.addAnnotation("@Service");
        //导包
        topLevelClass.addImportedTypes(getBaseImportedTypes());

        outFile(topLevelClass.getFormattedContent(),baseConfig.getBaseInfo().getProjectPath(),model.getServiceImplFullName());
        return topLevelClass.getFormattedContent();
    }

    /**
     * 获取基本需要导入的包
     * @return
     */
    static Set<FullyQualifiedJavaType> getBaseImportedTypes() {
        Set<FullyQualifiedJavaType> javaTypes = new HashSet<>();
        javaTypes.add(new FullyQualifiedJavaType(JavaTypeConstant.ANNOTATIONS_SERVICE_STR));
        return javaTypes;
    }


}
