import org.junit.Test;
import org.tuean.entity.define.JavaClass;
import org.tuean.entity.define.JavaField;
import org.tuean.entity.define.JavaMethod;
import org.tuean.entity.define.JavaVisible;
import org.tuean.enums.JdbcTypeEnum;
import org.tuean.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JavaFileTest {

    @Test
    public void testMakeJavaFile() {
        Map<String, String> dbParamMap = DBTest.testDBMap();
        String tableName = "menus";
        String packageInfo = "com.tuean";
        List<JavaField> fieldList = new ArrayList<>();
        List<JavaMethod> methodList = new ArrayList<>();
        for (String column : dbParamMap.keySet()) {
            String fieldName = Util.dbColumn2JavaField(column);
            String fieldJdbcType = dbParamMap.get(column);
            Class fieldJavaType = JdbcTypeEnum.getByDBType(fieldJdbcType);

            JavaField field = new JavaField(JavaVisible.visiblePrivate(), false, false, fieldJavaType, fieldName);
            fieldList.add(field);
            JavaMethod fieldGetMethod = Util.getMethod(field);
            JavaMethod fieldSetMethod = Util.setMethod(field);
            methodList.add(fieldGetMethod);
            methodList.add(fieldSetMethod);
        }

        JavaClass javaClazz = new JavaClass();
        javaClazz.setFieldList(fieldList);
        javaClazz.setClassName(tableName);
        javaClazz.setMethodList(methodList);
        javaClazz.setPackageInfo(packageInfo);
        javaClazz.setImportList(null);
        System.out.println(javaClazz);
    }
}
