pipeline {
	agent any 
	
	stages {

	stage('PULL') {
		steps{
			git 'https://github.com/HarshCraft/studentapp-project.git'
		}
	}
	stage('BULD') {
		steps{
		dir('backend') {
			sh'''
			mvn clean package -DskipTests
			'''
			}
		}
	}
	 stage('TEST') {
            steps {
                withSonarQubeEnv('sonar-token1') {
                    dir('backend') {
                        sh '''
                            mvn org.sonarsource.scanner.maven:sonar-maven-plugin:sonar \
                              -Dsonar.projectKey=sonarp \
                              -Dsonar.projectName=sonarp
                        '''
                    }
                }
            }
        }

stage('QUALITY-GATES') {
            steps {
                timeout(time: 5, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }

        stage('DELIVERY TO S3') {
            steps {
                dir('backend')
                     {
                        sh '''
                        aws s3 cp target/student-registration-backend-0.0.1-SNAPSHOT.jar s3://diamond-head/student-artifact.jar
                        '''
                    }
                }
            }
	stage('INFRA - EKS Creation') {
            steps {
                script {
                    // Load your infra pipeline file
                    def infra = load 'infra-pipeline.groovy'
                    infra.runInfra()
                }
            }
        }
	
	 stage('DOCKER BUILD & PUSH') {	
	steps {
    dir('backend') {
     sh '''
     # Login to AWS ECR
     aws ecr get-login-password --region eu-west-1 | docker login --username AWS --password-stdin 585019521142.dkr.ecr.eu-west-1.amazonaws.com

     # Build the Docker image
     docker build -t student-app .

     # Tag the image for ECR
     docker tag student-app:latest 585019521142.dkr.ecr.eu-west-1.amazonaws.com/student-app:latest

     # Push the image to ECR
     docker push 585019521142.dkr.ecr.eu-west-1.amazonaws.com/student-app:latest
     '''
  }
}
}

stage('DEPLOY TO EKS') {
  steps {
    sh '''
    aws eks update-kubeconfig --region eu-west-1 --name my-cluster-19

    sed -i "s|<ECR-IMAGE-URL>|585019521142.dkr.ecr.eu-west-1.amazonaws.com/student-app:latest|g" backend/k8s/deployment.yml

    kubectl apply -f backend/k8s/deployment.yml
    kubectl get pods
    kubectl get svc
    '''
  }
}



	}
} 
	
