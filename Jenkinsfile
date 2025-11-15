pipeline {
    agent any

    environment {
        MINIKUBE_ip = '52.66.174.192' //PUBLIC IP
    }

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

        stage('Deploying on EC2 Minikube Cluster') {
            steps {
                    sh 'scp deployment.yaml ubuntu@$MINIKUBE_IP:/home/ubuntu/'
                    sh 'ssh ubuntu@$MINIKUBE_IP "kubectl delete -f /home/ubuntu/deployment.yaml --ignore-not-found=true"'
                    sh 'ssh ubuntu@$MINIKUBE_IP "kubectl apply -f /home/ubuntu/deployment.yaml"'
                }
            }
        }
    } // stages
} //pipeline
