def call(Map pipelineParams) {

    pipeline {
        agent {
            //docker{ image 'gaddamnarendra/maven:latest'
            image 'gaddamnarendra/myprivaterepo:latest'
            registryUrl 'https://registry.hub.docker.com'
            registryCredentialsId 'DOCKER_CRDS'
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
            stage ('test') {
                steps {
                    parallel (
                        "unit tests": { sh 'mvn test' },
                        "integration tests": { sh 'mvn integration-test' }
                    )
                }
            }
         }
    }
}
