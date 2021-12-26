package org.tuean.generator;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.tuean.consts.Consts;
import org.tuean.consts.Env;
import org.tuean.entity.ConfigGenerator;
import org.tuean.entity.define.*;
import org.tuean.enums.JdbcTypeEnum;
import org.tuean.util.Log;
import org.tuean.util.PathUtil;
import org.tuean.util.Util;

import javax.lang.model.element.Modifier;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import static org.tuean.consts.Consts.*;

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



    public static void createJavaFile(JavaClass javaClass) throws IOException {

        FileOutputStream out = new FileOutputStream(javaClass.getLocationPath(), false);

        // package info
        writePackage(out, javaClass.getPackageInfo());

        // imports
        writeImport(out, javaClass.getImportList());

        // class
        out.write(Util.string2bytes("public" + BLANK_SPACE + javaClass.getClassType() + BLANK_SPACE + javaClass.getClassName() + BLANK_SPACE + "{"));
        Util.nextLine(out);

        // fields
        writeFields(out, javaClass.getFieldList(), 4);

        // methods
        writeMethods(out, javaClass.getMethodList(), 4);

        out.write(Util.string2bytes("}"));
    }

    private static void writePackage(OutputStream out, String packageInfo) throws IOException {
        if (StringUtils.isBlank(packageInfo)) return;
        out.write(Util.string2bytes("package " + packageInfo + JAVA_END));
        Util.nextLine(out);
    }

    private static void writeImport(OutputStream out, List<String> importList) throws IOException {
        if (importList == null) return;
        for (String line : importList) {
            if (!StringUtils.isBlank(line)) {
                out.write(Util.string2bytes("import " + line));
                Util.nextLine(out);
            }
        }
        Util.nextLine(out);
    }

    private static void writeFields(OutputStream out, List<JavaField> fields, int blanks) throws IOException {
        if (fields == null) return;
        StringBuffer sb;
        for (JavaField field : fields) {
            sb = new StringBuffer();
            sb.append(Util.blank(blanks));
            sb.append(JavaVisible.getVisibleString(field.getJavaVisible()));
            if (field.isFinal()) sb.append(Consts.FINAL);
            if (field.isStatic()) sb.append(Consts.STATIC);
            sb.append(field.getFieldClazz().getSimpleName()); sb.append(BLANK_SPACE);
            sb.append(field.getFieldName());
            sb.append(JAVA_END);
            out.write(Util.string2bytes(sb.toString()));
            Util.nextLine(out);
        }
    }

    private static void writeMethods(OutputStream out, List<JavaMethod> methods, int blanks) throws IOException {
        if (methods == null) return;
        StringBuffer sb;
        for (JavaMethod method : methods) {
            sb = new StringBuffer();
            sb.append(Util.blank(blanks));
            sb.append(JavaVisible.getVisibleString(method.getJavaVisible()));
            if (method.isFinal()) sb.append(Consts.FINAL);
            if (method.isStatic()) sb.append(Consts.STATIC);
            if (method.isVoidFlag()) {
                sb.append("void ");
            } else {
                if (method.getReturnClass() != null) {
                    sb.append(method.getReturnClass().getSimpleName() + BLANK_SPACE);
                } else {
                    sb.append(method.getReturnClassStr() + BLANK_SPACE);
                }
            }
            sb.append(method.getMethodName());
            sb.append("(");
            boolean firstFlag = true;
            if (!CollectionUtils.isEmpty(method.getArgs())) {
                for (JavaMethodArgs arg : method.getArgs()) {
                    if (arg == null) continue;
                    String annotationStr = arg.getAnnotation() == null ? "" : arg.getAnnotation().toCodeStr();
                    if (!firstFlag) sb.append(COMMA).append(BLANK_SPACE);
                    if (!StringUtils.isBlank(annotationStr)) sb.append(annotationStr).append(BLANK_SPACE);
                    sb.append(arg.getArgClassStr()).append(BLANK_SPACE);
                    sb.append(arg.getArgName());
                    firstFlag = false;
                }
            }

            if (method.isInterfaceMethod()) { // interface condition
                sb.append(")");
                sb.append(JAVA_END);
                nextLine(sb);
                out.write(Util.string2bytes(sb.toString()));
                continue;
            }

            sb.append(") {");
            nextLine(sb);
            int innerBlanks = blanks + Consts.NEXT_BLANK;
            if (method.getMethodBody() != null) {
                for (String s : method.getMethodBody()) {
                    sb.append(Util.blank(innerBlanks));
                    sb.append(s);
                    nextLine(sb);
                }
            }
            sb.append(Util.blank(blanks));
            sb.append("}");
            nextLine(sb);
            out.write(Util.string2bytes(sb.toString()));
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


    private static void nextLine(StringBuffer sb) {
        sb.append("\r\n");
    }


}
