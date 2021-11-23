import org.junit.Test;
import org.tuean.entity.define.JavaField;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JavaFileTest {

    @Test
    public void testMakeJavaFile() {
        Map<String, String> dbParamMap = DBTest.testDBMap();
        List<JavaField> fieldList = new ArrayList<>();
        for (String column : dbParamMap.keySet()) {

        }
    }
}
