def call(Map pipelineParams) {

    pipeline {
        agent {
            docker{ image 'gaddamnarendra/maven:latest'
                  }
        }
              
        stages {
            stage('checkout git') {
                steps {
                    git branch: pipelineParams.branch, url: pipelineParams.scmUrl
                }
            }

            stage('build') {
                steps {
                    sh 'mvn clean package -DskipTests=true'
                }
            }
         }
    }
}
