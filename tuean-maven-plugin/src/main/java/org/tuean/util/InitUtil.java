package org.tuean.util;

import com.google.inject.internal.util.Lists;
import org.tuean.entity.DBColumnInfo;
import org.tuean.entity.XmlNode;
import org.tuean.entity.define.*;

import java.util.*;
import java.util.stream.Collectors;

import static org.tuean.consts.Consts.JAVA_END;
import static org.tuean.util.Util.lowercaseFirst;
import static org.tuean.util.Util.uppercaseFirst;

public class InitUtil {


    public static JavaMethod getMethod(JavaField field) {
        JavaMethod method = new JavaMethod();
        method.setArgs(null);
        method.setJavaVisible(JavaVisible.visiblePublic());
        method.setStatic(false);
        method.setMethodName("get" + uppercaseFirst(field.getFieldName()));
        method.setMethodBody(Lists.newArrayList("return this." + field.getFieldName() + JAVA_END));
        method.setReturnClass(field.getFieldClazz());
        method.setVoidFlag(false);
        return method;
    }

    public static JavaMethod setMethod(JavaField field) {
        JavaMethod method = new JavaMethod();
//        method.setArgClazzs(new Class[]{field.getFieldClazz()});
//        method.setArgNames(new String[]{field.getFieldName()});
        JavaMethodArgs methodArg = new JavaMethodArgs(0, field.getFieldName(), field.getFieldClazz(), null, null);
        method.setArgs(Lists.newArrayList(methodArg));
        method.setJavaVisible(JavaVisible.visiblePublic());
        method.setStatic(false);
        method.setMethodName("set" + uppercaseFirst(field.getFieldName()));
        method.setMethodBody(Lists.newArrayList("this." + field.getFieldName() + " = " + field.getFieldName() + JAVA_END));
        method.setVoidFlag(true);
        return method;
    }


    public static JavaMethod insertMethod(JavaClass clazz) {
        JavaMethod method = new JavaMethod();
        method.setJavaVisible(JavaVisible.visibleEmpty());
        method.setFinal(false);
        method.setVoidFlag(false);
        method.setStatic(false);
        method.setReturnClass(int.class);
        method.setMethodName("insert");
//        method.setArgClazzs(null);
//        method.setArgClassStrs(new String[]{clazz.getClassName()});
//        method.setArgNames(new String[]{lowercaseFirst(clazz.getClassName())});
        JavaMethodArgs arg = new JavaMethodArgs(0, lowercaseFirst(clazz.getClassName()), null, clazz.getClassName(), null);
        method.setArgs(Lists.newArrayList(arg));
        method.setInterfaceMethod(true);
        return method;
    }

    public static JavaMethod updateMethod(JavaClass clazz) {
        JavaMethod method = new JavaMethod();
        method.setJavaVisible(JavaVisible.visibleEmpty());
        method.setFinal(false);
        method.setVoidFlag(false);
        method.setStatic(false);
        method.setReturnClass(int.class);
        method.setMethodName("update");
//        method.setArgClazzs(null);
//        method.setArgClassStrs(new String[]{clazz.getClassName()});
//        method.setArgNames(new String[]{lowercaseFirst(clazz.getClassName())});
        JavaMethodArgs arg = new JavaMethodArgs(0, lowercaseFirst(clazz.getClassName()), null, clazz.getClassName(), null);
        method.setArgs(Lists.newArrayList(arg));
        method.setInterfaceMethod(true);
        return method;
    }


    public static JavaClass initDaoClass(String className, String packageInfo, String outFile, JavaClass entityClass) {
        JavaClass mapperClass = new JavaClass();
        mapperClass.setClassName(className);
        mapperClass.setClassType("interface");
        mapperClass.setPackageInfo(packageInfo);
        mapperClass.setLocationPath(outFile);
        mapperClass.setImportList(Lists.newArrayList(entityClass.getPackageInfo() + "." + entityClass.getClassName() + JAVA_END));
        List<JavaMethod> methods = new ArrayList<>();
        methods.add(insertMethod(entityClass));
        methods.add(updateMethod(entityClass));
        mapperClass.setMethodList(methods);
        return mapperClass;
    }


    public static XmlNode initMapperXml(List<DBColumnInfo> dbColumnInfos, String tableName, JavaClass mapperClass, JavaClass entityClass) {
        XmlNode node = new XmlNode();
        node.setTag("mapper");
        Map<String, Object> tagAttrs = new HashMap<>();
        tagAttrs.put("namespace", mapperClass.getPackageInfo());
        node.setTagAttrs(tagAttrs);

        List<XmlNode> nodes = new LinkedList<>();

        // resultMap
        XmlNode resultMap = initResultMap(dbColumnInfos, entityClass);
        nodes.add(resultMap);

        // sql
        XmlNode sql = initSql(dbColumnInfos);
        nodes.add(sql);

        node.setNodes(nodes);
        return node;
    }


    public static XmlNode initResultMap(List<DBColumnInfo> list, JavaClass entityClass) {
        XmlNode node = new XmlNode();
        node.setTag("resultMap");
        node.setId("BaseResultMap");

        Map<String, Object> tagAttrs = new HashMap<>();
        tagAttrs.put("type", entityClass.getPackageInfo() + "." + entityClass.getClassName());
        node.setTagAttrs(tagAttrs);

        List<XmlNode> nodes = new LinkedList<>();
        for (DBColumnInfo dbColumnInfo : list) {
            XmlNode r = new XmlNode();
            String tag = "PRI".equals(dbColumnInfo.getKey().toUpperCase(Locale.ROOT)) ? "id" : "result";
            r.setTag(tag);
            Map<String, Object> rAttrs = new HashMap<>();
            rAttrs.put("column", dbColumnInfo.getName());
            rAttrs.put("property", Util.dbColumn2JavaField(dbColumnInfo.getName()));
            rAttrs.put("jdbcType", dbColumnInfo.getType());
            r.setTagAttrs(rAttrs);
            nodes.add(r);
        }
        node.setNodes(nodes);
        return node;
    }

    public static XmlNode initSql(List<DBColumnInfo> list) {
        XmlNode node = new XmlNode();
        node.setTag("sql");
        node.setId("Base_Column_List");
        node.setContent(list.stream().map(DBColumnInfo::getName).collect(Collectors.joining(", ")));
        return node;
    }

}
