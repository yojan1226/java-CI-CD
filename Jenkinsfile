pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/your-repo/java-cicd-demo.git'
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

        stage('Code Quality - SonarQube') {
            environment {
                SONARQUBE_URL = 'SonarQubeServer'
            }
            steps {
                withSonarQubeEnv('SonarQubeServer') {
                    sh 'mvn sonar:sonar -Dsonar.projectKey=java-cicd-demo'
                }
            }
        }

        stage('Docker Build & Push') {
            steps {
                sh '''
                docker build -t your-dockerhub-username/java-cicd-demo:latest .
                docker push your-dockerhub-username/java-cicd-demo:latest
                '''
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                sh 'kubectl apply -f deployment.yaml'
            }
        }
    }
}
