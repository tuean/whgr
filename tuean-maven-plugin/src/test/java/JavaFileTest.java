import org.junit.Test;
import org.tuean.entity.DBColumnInfo;
import org.tuean.entity.define.JavaClass;
import org.tuean.entity.define.JavaField;
import org.tuean.entity.define.JavaMethod;
import org.tuean.entity.define.JavaVisible;
import org.tuean.enums.JdbcTypeEnum;
import org.tuean.generator.JavaGenerator;
import org.tuean.parser.java.JavaInterfaceFileParser;
import org.tuean.util.InitUtil;
import org.tuean.util.Util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JavaFileTest {

//    @Test
    public void testMakeJavaFile() throws IOException {
        List<DBColumnInfo> list = DBTest.testDBMap();
        String tableName = "menus";
        String packageInfo = "com.tuean";
        List<JavaField> fieldList = new ArrayList<>();
        List<JavaMethod> methodList = new ArrayList<>();
        for (DBColumnInfo column : list) {
            String fieldName = Util.dbColumn2JavaField(column.getName());
            String fieldJdbcType = column.getType();
            Class fieldJavaType = JdbcTypeEnum.getByDBType(fieldJdbcType);

            JavaField field = new JavaField(JavaVisible.visiblePrivate(), false, false, fieldJavaType, fieldName);
            fieldList.add(field);
            JavaMethod fieldGetMethod = InitUtil.getMethod(field);
            JavaMethod fieldSetMethod = InitUtil.setMethod(field);
            methodList.add(fieldGetMethod);
            methodList.add(fieldSetMethod);
        }

        JavaClass javaClazz = new JavaClass();
        javaClazz.setFieldList(fieldList);
        javaClazz.setClassName(Util.uppercaseFirst(tableName));
        javaClazz.setMethodList(methodList);
        javaClazz.setPackageInfo(packageInfo);
        javaClazz.setImportList(null);
        System.out.println(javaClazz);

        String outPath = "D:\\IdeaProjects\\whgr\\";
        javaClazz.setLocationPath(outPath);
        JavaGenerator.createJavaFile(javaClazz);
    }


    @Test
    public void testParserInterface() throws FileNotFoundException {
        JavaInterfaceFileParser parser = new JavaInterfaceFileParser();
//        JavaClass javaClass = parser.parser(new FileInputStream("C:\\Users\\user\\Documents\\GitHub\\whgr\\whgr-backend\\src\\main\\java\\com\\tuean\\whgr\\dao\\TodoInfoMapper.java"));
        JavaClass javaClass = parser.parser(new FileInputStream("D:\\IdeaProjects\\whgr\\whgr-backend\\src\\main\\java\\com\\tuean\\whgr\\dao\\TodoInfoMapper.java"));
    }
}
