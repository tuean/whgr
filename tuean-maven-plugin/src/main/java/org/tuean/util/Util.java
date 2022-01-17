package org.tuean.util;

import com.google.inject.internal.util.Lists;
import edu.emory.mathcs.backport.java.util.Arrays;
import org.apache.commons.collections.CollectionUtils;
import org.apache.http.util.Asserts;
import org.apache.maven.model.Resource;
import org.apache.maven.project.MavenProject;
import org.tuean.consts.Env;
import org.tuean.entity.ConfigMapper;
import org.tuean.entity.XmlNode;
import org.tuean.entity.define.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

import static org.tuean.consts.Consts.*;

public class Util {


    public static void initDir(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public static String dbColumn2JavaField(String column) {
        StringBuffer sb = new StringBuffer();
        boolean _flag = false;
        for (int x = 0; column.length() > x; x++) {
            char t = column.charAt(x);
            boolean flag = t == '_';
            if (flag) {
                _flag = true;
                continue;
            }
            if (_flag && x != 1) {
                sb.append(String.valueOf(column.charAt(x)).toUpperCase());
            } else {
                sb.append(column.charAt(x));
            }
            _flag = false;
        }
        return sb.toString();
    }


    public static String lowercaseFirst(String key) {
        String k1 = key.substring(0, 1);
        return k1.toLowerCase(Locale.ROOT) + key.substring(1);
    }

    public static String uppercaseFirst(String key) {
        String k1 = key.substring(0, 1);
        return k1.toUpperCase(Locale.ROOT) + key.substring(1);
    }

    public static String getMethodName(String key) {
        return "get" + uppercaseFirst(key);
    }

    public static String setMethodName(String key) {
        return "set" + uppercaseFirst(key);
    }

    public static void nextLine(OutputStream out) throws IOException {
        String newLine = System.getProperty(CONFIG_LINE_SEPARATOR);
        out.write(newLine.getBytes(StandardCharsets.UTF_8));
    }

    public static void nextLine(StringBuffer sb) {
        nextLine(sb, 1);
    }

    public static void nextLine(StringBuffer sb, int lines) {
        String newLine = System.getProperty(CONFIG_LINE_SEPARATOR);
        for (int i = 0; i < lines; i++) {
            sb.append(newLine);
        }
    }


    public static String blank(int length) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            sb.append(BLANK_SPACE);
        }
        return sb.toString();
    }

    public static byte[] string2bytes(String str) {
        return str == null ? new byte[0] : str.getBytes(StandardCharsets.UTF_8);
    }

    public static String className(Class clazz) {
        return clazz.getSimpleName();
    }

    public static String findEntityLocation(ConfigMapper configMapper, MavenProject mavenProject) {
        StringBuffer sb = new StringBuffer();
        String basePath = mavenProject.getBasedir().getPath();
        sb.append(basePath);
        String workdir = configMapper.getWorkdir();
        Asserts.notBlank(workdir, "workdir must not be null");
        List<String> workdirs = Arrays.asList(workdir.split("\\/"));
        workdirs.forEach(n -> {
            sb.append(File.separator);
            sb.append(n);
        });
        String entity = Env.codeGenerateConfig.getMapper().getEntity();
        Asserts.notBlank(entity, "entity must not be null");
        List<String> entitys = Arrays.asList(entity.split("\\."));
        entitys.forEach(n -> {
            sb.append(File.separator);
            sb.append(n);
        });
        String outPath = sb.toString();
        File file = new File(outPath);
        if (!file.exists()) { file.mkdirs(); }
//
//        return "D:\\IdeaProjects\\whgr\\";
        return outPath;
    }

    public static String findDaoLocation(ConfigMapper configMapper, MavenProject mavenProject) {
        StringBuffer sb = new StringBuffer();
        String basePath = mavenProject.getBasedir().getPath();
        sb.append(basePath);
        String workdir = configMapper.getWorkdir();
        Asserts.notBlank(workdir, "workdir must not be null");
        List<String> workdirs = Arrays.asList(workdir.split("\\/"));
        workdirs.forEach(n -> {
            sb.append(File.separator);
            sb.append(n);
        });
        String dao = Env.codeGenerateConfig.getMapper().getDao();
        Asserts.notBlank(dao, "entity must not be null");
        List<String> daos = Arrays.asList(dao.split("\\."));
        daos.forEach(n -> {
            sb.append(File.separator);
            sb.append(n);
        });
        String outPath = sb.toString();
        File file = new File(outPath);
        if (!file.exists()) { file.mkdirs(); }
//
//        return "D:\\IdeaProjects\\whgr\\";
        return outPath;
    }




    public static String removeByFirst(String str, String first) {
        if (str.startsWith(first)) {
            return str.substring(first.length(), str.length() - 1);
        }
        return str;
    }

    public static String removeByEnd(String str, String end) {
        if (str.endsWith(end)) {
            return str.substring(0, str.length() - end.length() - 1);
        }
        return str;
    }



    public static JavaClass unionDaoJavaClass(JavaClass initClass, JavaClass oldClass) {
        List<JavaMethod> oldMethods = oldClass.getMethodList();
        if (CollectionUtils.isEmpty(oldMethods)) return initClass;

        List<String> importList = initClass.getImportList();
        List<String> oldImportList = oldClass.getImportList();
        List<String> list = unionList(importList, oldImportList);

        List<JavaMethod> initMethods = initClass.getMethodList();
        List<String> mNames = oldMethods.stream().map(JavaMethod::getMethodName).map(String::trim).collect(Collectors.toList());
        for (JavaMethod m : initMethods) {
            if (mNames.contains(m.getMethodName())) continue;
            oldMethods.add(m);
        }

        initClass.setMethodList(oldMethods);
        initClass.setImportList(list);
        return initClass;
    }

    public static List<String> unionList(List<String> l1, List<String> l2) {
        Set<String> r = new HashSet<>();
        if (l1 != null) r.addAll(l1);
        if (l2 != null) r.addAll(l2);
        return new ArrayList<>(r);
    }

    public static String findResourceByName(String resourceFile, MavenProject mavenProject) {
        try {
            List<Resource> resources = mavenProject.getResources();

            return null;
        } catch (Exception var) {
            Log.getLog().error("load error: " + resourceFile, var);
            return null;
        }
    }


    public static XmlNode unionXml(XmlNode initNode, XmlNode oldNode) {
        if (oldNode == null) return initNode;
//        List<String> oldIds = oldNode.getNodes().stream().map(XmlNode::getId).collect(Collectors.toList());
        List<String> initIds = initNode.getNodes().stream().map(XmlNode::getId).collect(Collectors.toList());

        for (XmlNode node : oldNode.getNodes()) {
            if (initIds.contains(node.getId())) continue;
            initNode.getNodes().add(node);
        }
        return initNode;
    }


    public static String tableName2ClassName(String tableName) {
        return Util.uppercaseFirst(tableName) + "Mapper";
    }

    public static String getBaseWholeClass(String classStr) {
        if ("Long".equals(classStr)) {
            return "java.lang.Long";
        } else if ("int".equals(classStr)) {
            return "java.lang.Integer";
        } else if ("String".equals(classStr)) {
            return "java.lang.String";
        } else if ("Double".equals(classStr)) {
            return "java.lang.Double";
        } else {
            throw new RuntimeException("no key type");
        }
    }


    public static void main(String[] args) {
//        System.out.println(dbColumn2JavaField("_plan_status"));
    }
}
