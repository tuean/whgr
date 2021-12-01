import org.apache.maven.Maven;
import org.apache.maven.project.MavenProject;
import org.tuean.consts.Env;
import org.tuean.entity.CodeGenerateConfig;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;

import static org.tuean.consts.Consts.SETTING_FILE_NAME;

public class Prepare {

    public static void init() {
        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(SETTING_FILE_NAME);
        Yaml yaml = new Yaml(new Constructor(CodeGenerateConfig.class));
        CodeGenerateConfig config = yaml.load(in);
        Env.codeGenerateConfig = config;
        Env.mavenProject = new MavenProject();
    }

}
