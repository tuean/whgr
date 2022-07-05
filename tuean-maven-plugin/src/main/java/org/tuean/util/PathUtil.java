package org.tuean.util;

import org.apache.commons.collections.CollectionUtils;
import org.apache.maven.model.Resource;
import org.apache.maven.project.MavenProject;
import org.tuean.consts.Consts;
import org.tuean.consts.Env;

import java.io.File;
import java.util.List;

public class PathUtil {

    public static String getWorkPath(MavenProject project) {
        return project.getFile().getAbsolutePath();
    }

    public static String getResourcePath(MavenProject project, String dictionaryName) {
        List<Resource> resources = project.getResources();
        if (CollectionUtils.isEmpty(resources)) return null;
        String path = resources.get(0).getDirectory();
        return path + File.separator + dictionaryName;
    }

    public static String getEntityPath() {
        String parentPath = Env.mavenProject.getBasedir().getPath();
        String workDirPath = parentPath + File.separator + Env.codeGenerateConfig.getMapper().getWorkdir();

        String entityConfig = Env.codeGenerateConfig.getMapper().getEntity();
        String entityConfigFilePath = workDirPath + File.separator + entityConfig.replace(Consts.POINT, File.separator);
        return entityConfigFilePath;
    }

    public static String configPath2sysPath(String path) {
        if (path.contains(".")) path = path.replace(".", File.separator);
        return path;
    }

}
