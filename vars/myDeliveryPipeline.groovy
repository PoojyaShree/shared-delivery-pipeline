def call(body) {
     def pipelineParams= [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = pipelineParams
    body()
     
    pipeline {
         environment{ rtMaven=''}
        agent any /*{
            docker{ 
                //image 'gaddamnarendra/maven:latest'
            image 'gaddamnarendra/myprivaterepo:latest'
            registryUrl 'https://registry.hub.docker.com'
            registryCredentialsId 'DOCKER_CRDS'
                  }
              }*/
              
        stages {
            stage('checkout git') {
                steps {
                    git branch: pipelineParams.branch, url: pipelineParams.scmUrl
                }
            }

            stage('build') {
                steps {
                    //sh 'mvn clean package -DskipTests=true'
                    script {                            
                               rtMaven = Artifactory.newMavenBuild()
                                rtMaven.tool = 'maven'
                                 def buildInfo = rtMaven.run pom: 'pom.xml', goals: 'install'
                               }
                          }
                        }
                    
            stage ('test') {
                 when {
                    anyOf {
                        equals expected: false, actual: pipelineParams.isEmpty();
                        equals expected: true, actual: pipelineParams.test
                    }  
                }
                steps {
                     script{
                    parallel (
                         "unit tests": { rtMaven.run pom: 'pom.xml', goals: 'test' },
                        "integration tests": { rtMaven.run pom: 'pom.xml', goals: 'integration-test' }
                    )
                     }
                }
            }
         }
    }
}
