package org.tuean;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.tuean.consts.Env;
import org.tuean.entity.ConfigGenerator;
import org.tuean.runner.MineRunner;
import org.tuean.util.Log;
import org.tuean.util.PathUtil;
import org.tuean.util.YamlUtil;

import java.util.Map;


@Mojo(name = "codeGenerator", defaultPhase = LifecyclePhase.GENERATE_RESOURCES)
public class CodeGeneratorMojo extends AbstractMojo {







    @Parameter(defaultValue = "${project}", required = true, readonly = true)
    MavenProject project;

    @Parameter(property = "scope")
    String scope;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        // system init
        Log.getLog().info("work dir is : " + PathUtil.getWorkPath(project));
        Env.codeGenerateConfig = YamlUtil.parseSetting(project);
        Env.mavenProject = project;

        // get database and table config
        Map<String, ConfigGenerator> map = Env.codeGenerateConfig.getGenerator();
        if (map == null || map.size() < 1) {
            Log.getLog().warn("no table to generate exit");
            return;
        }

        for (String tableName : map.keySet()) {
            ConfigGenerator configGenerator = map.get(tableName);
            MineRunner.javaRun(configGenerator, tableName);
        }

    }


}
