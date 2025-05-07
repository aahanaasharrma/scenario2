pipeline {
    agent any

    tools {
        gradle 'Gradle 7+'
    }

    environment {
        ARTIFACTORY_CREDENTIALS = credentials('jfrog-creds')  // your Jenkins credential ID
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'develop', url: 'https://github.com/aahanaasharrma/jenkins2.git'
            }
        }

        stage('Build Services') {
            steps {
                sh './user-service/gradlew -p user-service clean build'
                sh './order-service/gradlew -p order-service clean build'
            }
        }

        stage('Publish to Artifactory') {
            steps {
                sh '''
                ./user-service/gradlew -p user-service publish \
                -Partifactory_user="$ARTIFACTORY_CREDENTIALS_USR" \
                -Partifactory_password="$ARTIFACTORY_CREDENTIALS_PSW"
                '''
                sh '''
                ./order-service/gradlew -p order-service publish \
                -Partifactory_user="$ARTIFACTORY_CREDENTIALS_USR" \
                -Partifactory_password="$ARTIFACTORY_CREDENTIALS_PSW"
                '''
            }
        }

    }
}
