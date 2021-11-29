package org.tuean.generator;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.tuean.consts.Consts;
import org.tuean.consts.Env;
import org.tuean.entity.ConfigGenerator;
import org.tuean.entity.define.JavaClass;
import org.tuean.entity.define.JavaField;
import org.tuean.entity.define.JavaMethod;
import org.tuean.entity.define.JavaVisible;
import org.tuean.enums.JdbcTypeEnum;
import org.tuean.util.Log;
import org.tuean.util.PathUtil;
import org.tuean.util.Util;

import javax.lang.model.element.Modifier;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import static org.tuean.consts.Consts.EMPTY_STR;
import static org.tuean.consts.Consts.JAVA_END;

public class JavaGenerator {

    public static String getEntityFileName(ConfigGenerator configGenerator) {
        String entityPath = PathUtil.getEntityPath();
        Util.initDir(entityPath);
        return entityPath + File.separator + configGenerator.getEntity() + ".java";
    }

    public static String getMapperFileName(ConfigGenerator configGenerator) {
        String entityPath = PathUtil.getEntityPath();
        Util.initDir(entityPath);
        return entityPath + File.separator + configGenerator.getEntity() + ".java";
    }

    public static boolean checkMapperFileExist(ConfigGenerator configGenerator, String tableName) {
        String javaFileName = getMapperFileName(configGenerator);
        File file = new File(javaFileName);
        return file.exists();
    }

    public static boolean checkEntityFileExist(ConfigGenerator configGenerator, String tableName) {
        String javaFileName = getEntityFileName(configGenerator);
        File file = new File(javaFileName);
        return file.exists();
    }



    public static void createJavaFile(String filePath, JavaClass javaClass) throws IOException {
        FileOutputStream out = new FileOutputStream(filePath, false);

        // package info
        writePackage(out, javaClass.getPackageInfo());

        // imports
        writeImport(out, javaClass.getImportList());

        // fields
        writeFields(out, javaClass.getFieldList());

        // methods

    }

    private static void writePackage(OutputStream out, String packageInfo) throws IOException {
        if (StringUtils.isBlank(packageInfo)) return;
        out.write((packageInfo + JAVA_END).getBytes(StandardCharsets.UTF_8));
        Util.nextLine(out);
    }

    private static void writeImport(OutputStream out, List<String> importList) throws IOException {
        if (importList == null) return;
        for (String line : importList) {
            out.write(line.getBytes(StandardCharsets.UTF_8));
            Util.nextLine(out);
        }
    }

    private static void writeFields(OutputStream out, List<JavaField> fields) throws IOException {
        if (fields == null) return;
        StringBuffer sb;
        for (JavaField field : fields) {
            sb = new StringBuffer();
            sb.append(JavaVisible.getVisibleString(field.getJavaVisible()));
            if (field.isFinal()) sb.append(Consts.FINAL);
            if (field.isStatic()) sb.append(Consts.STATIC);
            sb.append(field.getFieldClazz().toString()); sb.append(EMPTY_STR);
            sb.append(field.getFieldName());
            sb.append(JAVA_END);
            out.write(sb.toString().getBytes(StandardCharsets.UTF_8));
            Util.nextLine(out);
        }
    }

    private static void writeMethods(OutputStream out, List<JavaMethod> methods) {
        if (methods == null) return;
        StringBuffer sb;
        for (JavaMethod method : methods) {
            sb = new StringBuffer();
            sb.append(JavaVisible.getVisibleString(method.getJavaVisible()));
            if (method.isFinal()) sb.append(Consts.FINAL);
            if (method.isStatic()) sb.append(Consts.STATIC);
            sb.append(method.getMethodName());
            sb.append("() {");
            if (!CollectionUtils.isEmpty(method.getMethodBody())) {
                method.getMethodBody().forEach(n -> {
                    try {
                        out.write(n.getBytes(StandardCharsets.UTF_8));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
            sb.append("}");
        }
    }

    @Deprecated
    public static void createJavaFile(ConfigGenerator configGenerator,
                                      Map<String, String> dbColumnMap) throws IOException {
        String javaFileName = getEntityFileName(configGenerator);
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



    public static void updateJavaFile(ConfigGenerator configGenerator,
                                      Map<String, String> dbColumnMap,
                                      JavaClass javaClass) {

    }



}
