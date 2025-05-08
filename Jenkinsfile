pipeline {
    agent any

    environment {
        IMAGE = "aahanaasharrma/order-service"
        EC2_HOST = "13.61.145.165"
        EC2_USER = "ubuntu"
    }

    stages {
        stage('Docker Build & Push') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockerhub-creds', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    script {
                        echo "Logging into Docker Hub..."
                    }
                    sh '''
                        echo "$DOCKER_PASS" | docker login -u "$DOCKER_USER" --password-stdin
                        docker build -t $IMAGE .
                        docker push $IMAGE
                    '''
                }
            }
        }

       stage('Deploy on EC2') {
    steps {
        sshagent(['ec2-ssh']) {
            sh '''
                echo "Starting deployment to EC2..."
                ssh -o StrictHostKeyChecking=no $EC2_USER@$EC2_HOST "
                    echo 'Pulling latest Docker image...'
                    docker pull $IMAGE &&
                    echo 'Stopping existing container...' &&
                    docker stop order-service || true &&
                    docker rm order-service || true &&
                    echo 'Starting new container...' &&
                    docker run -d --name order-service -p 80:80 $IMAGE
                "
            '''
        }
    }
}
