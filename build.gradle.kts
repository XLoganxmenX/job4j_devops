import com.github.spotbugs.snom.Confidence

plugins {
	checkstyle
	java
	jacoco
	id("org.springframework.boot") version "3.4.0"
	id("io.spring.dependency-management") version "1.1.6"
    id("com.github.spotbugs") version "6.0.26"
    id("org.liquibase.gradle") version "3.0.1"
    id("co.uzzu.dotenv.gradle") version "4.0.0"
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
    implementation(libs.spring.boot.starter.data.jpa)
    implementation(libs.spring.boot.starter.actuator)
    implementation(libs.postgresql)
    implementation(libs.micrometer.registry.prometheus)
    liquibaseRuntime(libs.liquibase)
    liquibaseRuntime(libs.postgresql)
    liquibaseRuntime(libs.jaxb)
    liquibaseRuntime(libs.logback.core)
    liquibaseRuntime(libs.logback.classic)
    liquibaseRuntime(libs.picocli)
	testImplementation(libs.spring.boot.starter.test)
	testRuntimeOnly(libs.junit.platform.launcher)
	testImplementation(libs.junit.jupiter)
	testImplementation(libs.assertj.core)
    testImplementation(libs.liquibase)
}

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath(libs.liquibase)
    }
}

liquibase {
    activities.register("main") {
        val args = mutableMapOf(
            "logLevel" to "info",
            "url" to env.DB_URL.value,
            "username" to env.DB_USERNAME.value,
            "password" to env.DB_PASSWORD.value,
            "classpath"      to "src/main/resources",
            "changelogFile"  to "db/changelog/db.changelog-master.xml"
        )

        args.entries.removeIf { it.value.isNullOrBlank() }

        this.arguments = args
    }
    runList = "main"
}

tasks.bootJar {
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
        reportLevel = Confidence.HIGH
    }
}

tasks.register<Zip>("archiveResources") {
    group = "custom optimization"
    description = "Archives the resources folder into a ZIP file"

    val inputDir = file("src/main/resources")
    val outputDir = layout.buildDirectory.dir("archives")

    inputs.dir(inputDir) // Входные данные для инкрементальной сборки
    outputs.file(outputDir.map { it.file("resources.zip") }) // Выходной файл

    from(inputDir)
    destinationDirectory.set(outputDir)
    archiveFileName.set("resources.zip")

    doLast {
        println("Resources archived successfully at ${outputDir.get().asFile.absolutePath}")
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

tasks.register("profile") {
    doFirst {
        println(env.DB_URL.value)
    }
}

tasks.named<Test>("test") {
    systemProperty("spring.datasource.url", env.DB_URL.value)
    systemProperty("spring.datasource.username", env.DB_USERNAME.value)
    systemProperty("spring.datasource.password", env.DB_PASSWORD.value)
}