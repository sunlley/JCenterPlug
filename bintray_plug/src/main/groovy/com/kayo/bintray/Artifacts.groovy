package com.kayo.bintray

import org.gradle.api.Project

interface Artifacts {

    def all(String publicationName, Project project)

}