def call(body) {
     def pipelineParams= [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = pipelineParams
    body()
     def mvnGoals = pipelineParams.mvnGoals ?: 'clean package'
     def mvnTest = pipelineParams.mvnTest ?: 'test'
     def mvnintTest = pipelineParams.mvnintTest ?: 'integration-test'

    pipeline {
         //environment{ rtMaven=''}
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
                    /* script {
                            //def server = Artifactory.server('artifactory')
                               rtMaven = Artifactory.newMavenBuild()
                               //rtMaven.resolver server: server, releaseRepo: 'libs-release', snapshotRepo: 'libs-snapshot'
                                //rtMaven.deployer server: server, releaseRepo: 'libs-release-local', snapshotRepo: 'libs-snapshot-local'
                                rtMaven.tool = 'maven'
                                 def buildInfo = rtMaven.run pom: 'pom.xml', goals: 'install'
                                  //server.publishBuildInfo buildInfo
                            }*/
                     script{
                     if("${env.BRANCH_NAME}" !='master'){
                          mavenn{
                               mavenGoals = "${mvnGoals}"
                          }
                     } 
                    }
                }
            }
            stage ('test') {
                steps {
                     script{
                    parallel (
                         mavenn{
                              "unit tests:" { mavenGoals = "${mvnTest}"},
                               "integration tests:" {mavenGoals = "${mvnintTest}"}
                          }
                        //"unit tests": { rtMaven.run pom: 'pom.xml', goals: 'test' },
                        //"integration tests": { rtMaven.run pom: 'pom.xml', goals: 'integration-test' }
                    )
                     }
                }
            }
         }
    }
}
