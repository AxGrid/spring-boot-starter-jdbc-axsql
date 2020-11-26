package com.axgrid.plugins.axsql;

import com.axgrid.plugins.axsql.dto.AxSQLFileDescription;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.codehaus.plexus.util.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static java.lang.String.format;

@Mojo(
        name = "generate",
        defaultPhase = LifecyclePhase.GENERATE_SOURCES,
        threadSafe = true
)
public class AxSQLPlugin extends AbstractMojo {
    private static final String DEFAULT_INCLUDES = "**/*.axsql";

    @Parameter(
            required = true,
            property = "javaOutputDirectory",
            defaultValue = "${project.build.directory}/generated-sources/slots/java"
    )
    private File outputDirectory;

    @Parameter(
            required = true,
            defaultValue = "${basedir}/src/main/sql"
    )
    private File sourceRoot;



    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        if (sourceRoot != null && sourceRoot.exists()) {
            getLog().info(String.format("source: %s", sourceRoot));
            getLog().info(String.format("target: %s", outputDirectory));
            List<File> sources = findSources(sourceRoot);
            try {
                for(var file : sources) {
                    process(file);
                }
            }catch (IOException e) {
                getLog().error(format("error %s", e.getMessage()), e);
                throw new MojoFailureException(format("error %s", e.getMessage()));
            }
        }
    }

    private void process(File file) throws IOException {
        var content = FileUtils.fileRead(file);
        var sqlFileDescription = AxSQLFileDescription.parse(content);

        if (!outputDirectory.exists()) {
            FileUtils.mkdir(outputDirectory.getAbsolutePath());
        }

//        for(var slot : slots) {
//            //slot.setClassName(NameUtils.className(file.toPath(), ));
//            //String generated = creator.generate(slot);
//            var targetPath = NameUtils.javaOutputPath(file.toPath(), sourceRoot.toPath(), outputDirectory.toPath());
//            getLog().info(String.format("PATHS: %s, %s, %s", file.toPath(), sourceRoot.toPath(), outputDirectory.toPath()));
//            getLog().info(String.format("sql %s generated at %s", slot.getName(), targetPath));
//            FileUtils.forceMkdir(targetPath.toFile().getParentFile());
//            FileUtils.fileWrite(targetPath.toFile(), generated);
//        }
        var targetPath = NameUtils.javaOutputPath(file.toPath(), sourceRoot.toPath(), outputDirectory.toPath());
        String generated = "";
        FileUtils.forceMkdir(targetPath.toFile().getParentFile());
        FileUtils.fileWrite(targetPath.toFile(), generated);
    }


    /** Найти все файлы исходники **/
    private List<File> findSources(File directory) throws MojoExecutionException {
        if (!directory.isDirectory())
            throw new MojoExecutionException(format("'%s' is not a folder", directory));
        try {
            return FileUtils.getFiles(directory, DEFAULT_INCLUDES, "");
        } catch (IOException e) {
            throw new MojoExecutionException("SQL files reading", e);
        }
    }
}
