import org.junit.Test;
import org.tuean.parser.java.JavaInterfaceFileParser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class JavaInterfaceParserTest {

    @Test
    public void testParser() {
        String file = "C:\\Users\\user\\Documents\\GitHub\\whgr\\whgr-backend\\src\\main\\java\\com\\tuean\\whgr\\dao\\MenusMapper.java";
        JavaInterfaceFileParser parser = new JavaInterfaceFileParser();
        try {
            parser.parser(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
