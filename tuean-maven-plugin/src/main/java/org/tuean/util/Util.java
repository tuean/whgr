package org.tuean.util;

import org.apache.commons.collections.CollectionUtils;
import org.apache.maven.project.MavenProject;
import org.apache.maven.model.Resource;

import java.io.File;
import java.util.List;

public class Util {

    public static String getWorkPath(MavenProject project) {
        return project.getFile().getAbsolutePath();
    }

    public static String getResourcePath(MavenProject project, String dictionaryName) {
        List<Resource> resources = project.getResources();
        if (CollectionUtils.isEmpty(resources)) return null;
        String path = resources.get(0).getDirectory();
        return path + File.separator + dictionaryName;
    }

}
