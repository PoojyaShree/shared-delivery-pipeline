@Library('my-shared-library') _
mypipeline(
    branch = 'master'
    scmUrl = 'https://github.com/PoojyaShree/maven-web-application')
    
==========================
@Library('my-shared-library') _
myDeliveryPipeline {
    branch = 'master'
    scmUrl = 'https://github.com/PoojyaShree/maven-web-application'
    test = false
}
===========================
  library identifier: 'custom-lib@main', retriever: modernSCM(
  [$class: 'GitSCMSource',
   remote: 'https://github.com/PoojyaShree/shared-delivery-pipeline.git',
   credentialsId: 'GitCredentials'])
   myDeliveryPipeline {
    branch = 'master'
    scmUrl = 'https://github.com/PoojyaShree/maven-web-application'
    test = false
}
