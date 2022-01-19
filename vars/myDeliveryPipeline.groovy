def call(body) {
     def pipelineParams= [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = pipelineParams
    body()

    pipeline {
       
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
                             //def server = Artifactory.server('artifactory')
                              def rtMaven = Artifactory.newMavenBuild()
                               //rtMaven.resolver server: server, releaseRepo: 'libs-release', snapshotRepo: 'libs-snapshot'
                                //rtMaven.deployer server: server, releaseRepo: 'libs-release-local', snapshotRepo: 'libs-snapshot-local'
                                rtMaven.tool = 'maven'
                                 def buildInfo = rtMaven.run pom: 'pom.xml', goals: 'install'
                                  //server.publishBuildInfo buildInfo
                            }
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
