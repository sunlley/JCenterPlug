package com.kayo.jcenter

import org.gradle.api.Project

interface Artifacts {

    def all(String publicationName, Project project)

}