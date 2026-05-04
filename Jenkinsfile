pipeline {
    agent any

    parameters {
        choice(name: 'SUITE', choices: ['testng-smoke.xml', 'testng-regression.xml'])
    }

    stages {

        stage('Clone') {
            steps {
                git 'https://github.com/kalpeshchavhan1234/1.git'
            }
        }

        stage('Run Tests') {
            steps {
                bat "mvn clean test -DsuiteXmlFile=${params.SUITE}"
            }
        }

    }

    post {
        success {
            echo "Tests Passed"
        }
        failure {
            echo "Tests Failed"
        }
        always {
            echo "Pipeline Finished"
        }
    }
}