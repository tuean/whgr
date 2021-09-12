package org.tuean.util;

import com.google.gson.JsonObject;
import org.apache.maven.model.Resource;
import org.apache.maven.plugin.logging.SystemStreamLog;
import org.apache.maven.project.MavenProject;
import org.tuean.entity.CodeGenerateConfig;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import static org.tuean.consts.Consts.SETTING_DIR;
import static org.tuean.consts.Consts.SETTING_FILE_NAME;

public class YamlUtil {

    public static CodeGenerateConfig codeGenerateConfig;

    public static void parseSetting(MavenProject project) {
        try {
            Log.getLog().info(project.toString());
            List<Resource> resources = project.getResources();
            Resource configResource = resources.stream().filter(n -> SETTING_DIR.equals(n.getDirectory())).findFirst().get();
            String configFilePath = configResource.getDirectory() + File.separator + SETTING_FILE_NAME;
            Yaml yaml = new Yaml();
            CodeGenerateConfig config = yaml.loadAs(new FileInputStream(configFilePath), CodeGenerateConfig.class);
            Log.getLog().info(config.toString());
//        CodeGenerateConfig config = new CodeGenerateConfig();
            codeGenerateConfig = config;
        } catch (Exception var) {
            Log.getLog().info(SETTING_FILE_NAME + " load error");
        }
    }

}
