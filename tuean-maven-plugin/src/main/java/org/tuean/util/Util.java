package org.tuean.util;

import org.apache.maven.project.MavenProject;

public class Util {

    public static String getWorkPath(MavenProject project) {
        return project.getFile().getAbsolutePath();
    }

}
