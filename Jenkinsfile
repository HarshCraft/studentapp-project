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
	 

	}
} 
	
