def runInfra() {

    stage('TF INIT') {
        dir('terraform') {
            sh 'terraform init'
        }
    }

    stage('TF PLAN') {
        dir('terraform') {
            sh 'terraform plan'
        }
    }

    stage('TF APPLY') {
       dir('terraform') {
            sh 'terraform apply -auto-approve'
        }
    }

}

return this
