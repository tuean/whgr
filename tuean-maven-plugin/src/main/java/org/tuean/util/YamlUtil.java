package org.tuean.util;

import com.google.gson.JsonObject;
import org.apache.maven.plugin.logging.SystemStreamLog;
import org.apache.maven.project.MavenProject;

public class YamlUtil {

    public static JsonObject parseSetting(MavenProject project) {
        Log.getLog().info(project.toString());
        JsonObject result = new JsonObject();

        return result;
    }

}
