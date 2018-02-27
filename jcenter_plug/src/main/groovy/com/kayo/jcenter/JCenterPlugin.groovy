package com.kayo.jcenter

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.publish.maven.MavenPublication

class JCenterPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {

        def moduleName = 'Module:' + project.name
        def line = "============================================"
        def plugName = "Hello JCenterPlugin !"
        println(line)
        def dy = (line.length() - plugName.length()) / 2
        println(" " * dy + plugName)
        if (moduleName.length() >= line.length()) {
            println(" " + moduleName)
        } else {
            def dy2 = (line.length() - moduleName.length()) / 2
            println(" " * dy2 + moduleName)
        }
        println(line)

        PublishExtension extension = project.extensions.create('publish', PublishExtension)
        project.afterEvaluate {
            extension.validate()
            project.apply([plugin: 'maven-publish'])
            attachArtifacts(extension, project)
            new com.jfrog.bintray.gradle.BintrayPlugin().apply(project)
            new JCenterConfig(extension).configure(project)
        }
    }

    void attachArtifacts(PublishExtension extension, Project project) {
        if (project.plugins.hasPlugin('com.android.library')) {
            project.android.libraryVariants.all { variant ->
                def artifactId = extension.artifactId;
                addArtifact(project, variant.name, artifactId, new AndroidArtifacts(variant))
            }
        } else {
            addArtifact(project, 'maven', project.publish.artifactId, new JavaArtifacts())
        }
    }

    void addArtifact(Project project, String name, String artifact, Artifacts artifacts) {
        PropertyFinder propertyFinder = new PropertyFinder(project, project.publish)
        project.publishing.publications.create(name, MavenPublication) {
            groupId project.publish.groupId
            artifactId artifact
            version = propertyFinder.publishVersion

            artifacts.all(it.name, project).each {
                delegate.artifact it
            }
            from artifacts.from(project)
        }
    }
}