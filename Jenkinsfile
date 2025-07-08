pipeline {
    agent { label 'agent2' }

    tools {
        git 'Default'
    }

    stages {
        stage('Prepare Environment') {
            steps {
                script {
                    sh 'chmod +x ./gradlew'
                }
            }
        }
        stage('Checkstyle Main') {
            steps {
                script {
                    sh './gradlew checkstyleMain -P"dotenv.filename"="/var/agent-jdk21/env/.env.develop'
                }
            }
        }
        stage('Checkstyle Test') {
            steps {
                script {
                    sh './gradlew checkstyleTest -P"dotenv.filename"="/var/agent-jdk21/env/.env.develop'
                }
            }
        }
        stage('Package') {
                    steps {
                        sh './gradlew build -P"dotenv.filename"="/var/agent-jdk21/env/.env.develop'
                    }
                }
        stage('Update DB') {
            steps {
                script {
                    sh './gradlew update -P"dotenv.filename"="/var/agent-jdk21/env/.env.develop"'
                }
            }
        }
        stage('JaCoCo Report') {
            steps {
                script {
                    sh './gradlew jacocoTestReport -P"dotenv.filename"="/var/agent-jdk21/env/.env.develop'
                }
            }
        }
        stage('JaCoCo Verification') {
            steps {
                script {
                    sh './gradlew jacocoTestCoverageVerification -P"dotenv.filename"="/var/agent-jdk21/env/.env.develop'
                }
            }
        }
        stage('Docker Build') {
            steps {
                sh 'docker build -t job4j_devops .'
            }
        }
    }
    post {
        always {
            script {
                def buildInfo = "Build number: ${currentBuild.number}\n" +
                                "Build status: ${currentBuild.currentResult}\n" +
                                "Started at: ${new Date(currentBuild.startTimeInMillis)}\n" +
                                "Duration so far: ${currentBuild.durationString}"
                telegramSend(message: buildInfo)
            }
        }
    }   
}
