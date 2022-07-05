import org.junit.Test;
import org.tuean.parser.java.JavaInterfaceFileParser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class JavaInterfaceParserTest {

//    @Test
    public void testParser() {
//        String file = "C:\\Users\\user\\Documents\\GitHub\\whgr\\whgr-backend\\src\\main\\java\\com\\tuean\\whgr\\dao\\MenusMapper.java";
//        String file = "D:\\IdeaProjects\\whgr\\whgr-backend\\src\\main\\java\\com\\tuean\\whgr\\dao\\AdminAccountMapper.java";
//        String file = "/Users/tuean/IdeaProjects/whgr/whgr-backend/src/main/java/com/tuean/whgr/dao/MenusMapper.java";
        String file = "";
        JavaInterfaceFileParser parser = new JavaInterfaceFileParser();
        try {
            parser.parser(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
