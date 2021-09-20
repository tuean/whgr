package org.tuean.util;

import org.apache.maven.model.Resource;
import org.apache.maven.project.MavenProject;
import org.tuean.consts.Consts;
import org.tuean.consts.Env;
import org.tuean.entity.CodeGenerateConfig;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.File;
import java.io.FileInputStream;

import static org.tuean.consts.Consts.SETTING_FILE_NAME;

public class YamlUtil {

    public static CodeGenerateConfig parseSetting(MavenProject project) {
        try {
            Log.getLog().info(project.toString());
            String configDirPath = PathUtil.getResourcePath(project, Consts.SETTING_DIR);
            String configFilePath = configDirPath + File.separator + SETTING_FILE_NAME;
            Yaml yaml = new Yaml(new Constructor(CodeGenerateConfig.class));
            CodeGenerateConfig config = yaml.load(new FileInputStream(configFilePath));
            Log.getLog().info(config.toString());
            return config;
        } catch (Exception var) {
            Log.getLog().error(SETTING_FILE_NAME + " load error");
            Log.getLog().error(var);
            throw new RuntimeException(SETTING_FILE_NAME + " load error");
        }

    }

}
