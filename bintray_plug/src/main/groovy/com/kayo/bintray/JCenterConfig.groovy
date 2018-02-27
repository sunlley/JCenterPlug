package com.kayo.bintray

import org.gradle.api.Project

class JCenterConfig {

    PublishExtension extension

    JCenterConfig(PublishExtension extension) {
        this.extension = extension
    }

    void configure(Project project) {
        initDefaults()
        deriveDefaultsFromProject(project)

        PropertyFinder propertyFinder = new PropertyFinder(project, extension)

        project.bintray {
            user = propertyFinder.bintrayUser
            key = propertyFinder.bintrayKey
            publish = extension.autoPublish
            dryRun = propertyFinder.dryRun
            override = propertyFinder.override

            publications = extension.publications ?:
                    project.plugins.hasPlugin('com.android.library') ? ['release'] : [ 'maven' ]

            pkg {
                repo = extension.repoName
                userOrg = extension.userOrg
                name = extension.uploadName
                desc = extension.desc
                websiteUrl = extension.website
                issueTrackerUrl = extension.issueTracker
                vcsUrl = extension.repository

                licenses = extension.licences
                version {
                    name = propertyFinder.publishVersion
                    attributes = extension.versionAttributes
                }
            }
        }
        //最后的发布信息
        println('+-------------------------------------------')
        println('|        The Publish Information')
        println('|        -----------------------')
        println('| bintrayUser   :'+propertyFinder.bintrayUser)
        println('| bintrayKey    :'+propertyFinder.bintrayKey)
        println('| userOrg       :'+extension.userOrg)
        println('| publish       :'+extension.autoPublish)
        println('| dryRun        :'+propertyFinder.dryRun)
        println('| override      :'+propertyFinder.override)
        println('| repo          :'+extension.repoName)
        println('| groupId       :'+extension.groupId)
        println('| name          :'+extension.uploadName)
        println('| version       :'+propertyFinder.publishVersion)
        println('| desc          :'+extension.desc)
        println('| licences      :'+extension.licences)
        println('| website       :'+extension.website)
        println('| issueTracker  :'+extension.issueTracker)
        println('| repository    :'+extension.repository)
        println('+-------------------------------------------')
        println('| running uploadArchives...')
        println('| manual execute like following ')
        println('|     \'./gradlew clean build bintrayUpload\'')
        println('+-------------------------------------------')
//        project.tasks.bintrayUpload.mustRunAfter(project.tasks.uploadArchives)
    }

    private void initDefaults() {
        if (extension.uploadName.isEmpty()) {
            extension.uploadName = extension.artifactId
        }

        if (extension.website.contains('github.com')) {
            if (extension.issueTracker.isEmpty()) {
                extension.issueTracker = "${extension.website}/issues"
            }
            if (extension.repository.isEmpty()) {
                extension.repository = "${extension.website}.git"
            }
        }
    }

    private void deriveDefaultsFromProject(Project project) {
        if (extension.versionAttributes.isEmpty()) {
            def gradlePluginPropertyFinder = new ResourcesFinder(project)
            String bestPluginId = gradlePluginPropertyFinder.findBestGradlePluginId()
            if (bestPluginId != null) {
                extension.versionAttributes << ['gradle-plugin': "$bestPluginId:$extension.groupId:$extension.artifactId"]
                project.logger.info "Using plugin identifier '" + extension.versionAttributes.get('gradle-plugins') + "' for gradle portal."
            }
        }
    }
}