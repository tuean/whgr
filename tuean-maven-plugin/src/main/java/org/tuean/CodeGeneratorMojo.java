package org.tuean;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.tuean.util.Util;
import org.tuean.util.YamlUtil;


@Mojo(name = "codeGenerator", defaultPhase = LifecyclePhase.GENERATE_RESOURCES)
public class CodeGeneratorMojo extends AbstractMojo {

    @Parameter(defaultValue = "${project}", required = true, readonly = true)
    MavenProject project;

    @Parameter(property = "scope")
    String scope;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info("work dir is : " + Util.getWorkPath(project));
        YamlUtil.parseSetting(project);
    }


}
