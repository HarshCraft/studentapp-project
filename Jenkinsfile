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
			echo "Test success"
		}
	}
	stage('Deploy') {
		steps {
			echo "Deploy success"
		}
	}
	}
} 
	
