package org.tuean.generator;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import org.tuean.consts.Consts;
import org.tuean.consts.Env;
import org.tuean.entity.ConfigGenerator;
import org.tuean.enums.JdbcTypeEnum;
import org.tuean.util.Log;
import org.tuean.util.PathUtil;
import org.tuean.util.Util;

import javax.lang.model.element.Modifier;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

public class JavaGeneratorEntity {


    public static void createJavaFile(ConfigGenerator configGenerator, Map<String, String> dbColumnMap) throws IOException {
        String entityPath = PathUtil.getEntityPath();
        Util.initDir(entityPath);
        String javaFileName = entityPath + File.separator + configGenerator.getEntity() + ".java";

        String packageName = Env.codeGenerateConfig.getMapper().getEntity()
                + Consts.POINT
                + configGenerator.getEntity();

        TypeSpec.Builder entityBuilder = TypeSpec.classBuilder(packageName)
                .addModifiers(Modifier.PUBLIC);

        for (String column : dbColumnMap.keySet()) {
            Class columnClazz = JdbcTypeEnum.getByDBType(dbColumnMap.get(column));
            if (columnClazz == null) {
                Log.getLog().error(column + " cannot find java type");
                throw new RuntimeException(column + " cannot find java type");
            }
            entityBuilder.addField(columnClazz, column, Modifier.PRIVATE);
            MethodSpec setMethod = MethodSpec.methodBuilder(Util.setMethodName(column))
                    .addModifiers(Modifier.PUBLIC)
                    .addParameter(columnClazz, column)
                    .addStatement("this.$N = $N", column, column)
                    .build();
            entityBuilder.addMethod(setMethod);


            MethodSpec getMethod = MethodSpec.methodBuilder(Util.getMethodName(column))
                    .addModifiers(Modifier.PUBLIC)
                    .returns(columnClazz)
                    .addStatement("return this.$N", column)
                    .build();
            entityBuilder.addMethod(getMethod);
        }


        TypeSpec entity = entityBuilder.build();
        JavaFile javaFile = JavaFile.builder(packageName, entity).build();

        File file = new File(javaFileName);
        javaFile.writeTo(file);
    }



}
