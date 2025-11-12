pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/yojan1226/java-CI-CD.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }

       //  stage('Code Quality - SonarQube') {
       //     environment {
       //         SONARQUBE_URL = 'SonarQubeServer'
       //     }
       //     steps {
       //         withSonarQubeEnv('SonarQubeServer') {
       //            sh 'mvn sonar:sonar -Dsonar.projectKey=java-cicd-demo'
       //         }
       //     }
       // } 

        stage('Docker Build & Push') {
            steps {
                script {
                    // Log in to Docker using Jenkins credentials
                    docker.withRegistry('https://index.docker.io/v1/', 'dockerhub-creds') {
                        def customImage = docker.build("honey120ar/java-cicd-demo:latest")
                        customImage.push()
                    }
                }
            }
        }
    }
}
