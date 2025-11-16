pipeline {
    agent any

    environment {
        MINIKUBE_IP = '13.235.19.72' // PUBLIC IP
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

        stage('Docker Build & Push') {
            steps {
                script {
                    docker.withRegistry('https://index.docker.io/v1/', 'dockerhub-creds') {
                        def customImage = docker.build("honey120ar/java-cicd-demo:latest")
                        customImage.push()
                    }
                }
            }
        }

        stage('Deploying on EC2 Minikube Cluster') {
            steps {
                withCredentials([sshUserPrivateKey(credentialsId: 'ec2-minikube-key', keyFileVariable: 'SSH_KEY')]) {
                    sh '''
                        echo "Copying deployment file..."
                        scp -i $SSH_KEY -o StrictHostKeyChecking=no deployment.yaml ubuntu@$MINIKUBE_IP:/home/ubuntu/

                        echo "Deleting old deployment..."
                        ssh -i $SSH_KEY -o StrictHostKeyChecking=no ubuntu@$MINIKUBE_IP "kubectl delete -f /home/ubuntu/deployment.yaml --ignore-not-found=true"

                        echo "Applying new deployment..."
                        ssh -i $SSH_KEY -o StrictHostKeyChecking=no ubuntu@$MINIKUBE_IP "kubectl apply -f /home/ubuntu/deployment.yaml"
                    '''
                }
            }
        }

    } // stages
} // pipeline
