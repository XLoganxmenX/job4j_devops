plugins {
	checkstyle
	java
	jacoco
	id("org.springframework.boot") version "3.4.0"
	id("io.spring.dependency-management") version "1.1.6"
    id("com.github.spotbugs") version "6.0.26"
}

group = "ru.job4j.devops"
version = "1.0.0"

tasks.jacocoTestCoverageVerification {
    violationRules {
        rule {
            limit {
                minimum = "0.8".toBigDecimal()
            }
        }

        rule {
            isEnabled = false
            element = "CLASS"
            includes = listOf("org.gradle.*")

            limit {
                counter = "LINE"
                value = "TOTALCOUNT"
                maximum = "0.3".toBigDecimal()
            }
        }
    }
}

repositories {
	mavenCentral()
}

dependencies {
	compileOnly(libs.lombok)
	annotationProcessor(libs.lombok)
	implementation(libs.spring.boot.starter.web)
	testImplementation(libs.spring.boot.starter.test)
	testRuntimeOnly(libs.junit.platform.launcher)
	testImplementation(libs.junit.jupiter)
	testImplementation(libs.assertj.core)
}

tasks.jar {
    archiveClassifier.set("")
}

tasks.withType<Test> {
	useJUnitPlatform()
    finalizedBy(tasks.spotbugsMain)
}

tasks.withType<Javadoc> {
    options.encoding = "UTF-8"
}

tasks.register<Zip>("zipJavaDoc") {
    group = "documentation" // Группа, в которой будет отображаться задача
    description = "Packs the generated Javadoc into a zip archive"

    dependsOn("javadoc") // Указываем, что задача зависит от выполнения javadoc

    from("build/docs/javadoc") // Исходная папка для упаковки
    archiveFileName.set("javadoc.zip") // Имя создаваемого архива
    destinationDirectory.set(layout.buildDirectory.dir("archives")) // Директория, куда будет сохранен архив
}

tasks.spotbugsMain {
    reports.create("html") {
        required = true
        outputLocation.set(layout.buildDirectory.file("reports/spotbugs/spotbugs.html"))
    }
}

tasks.test {
    finalizedBy(tasks.spotbugsMain)
}

tasks.register("checkJarSize") {
    group = "verification"
    description = "Check size of jar file and print info"
    dependsOn("jar")
    doLast {
        val fileSize = (file("build/libs/${project.name}-${project.version}.jar").length() / 1024F) / 1024F
        if (fileSize > 5) {
            println("JAR file exceeds the size limit of 5 MB")
        } else {
            println("JAR file is within the acceptable size limit")
        }
        println("Current size: $fileSize MB")
    }
}
