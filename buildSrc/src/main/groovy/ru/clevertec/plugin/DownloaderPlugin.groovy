package ru.clevertec.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import ru.clevertec.task.FileDownloader

class DownloaderPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        def map = [description: "Download check", group: "custom", type: FileDownloader]
        project.task(map, "customTask") {
            sourceUrl = 'https://i.ibb.co/X3VfJ0w/2022-03-09-00-42-30.png'
            target = new File('src/main/resources/imageCheck.png')
        }
    }
}
