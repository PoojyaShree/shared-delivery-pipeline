def call(body={}) {
    def pipelineParams = [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = pipelineParams
    body()
    pipeline {
        agent any;
        stages {
            stage('build') {
                steps {
                    echo "BUILD"
                }
            }
            stage('unitest') {
                when {
                    anyOf {
                        equals expected: true, actual: pipelineParams.isEmpty();
                        equals expected: false, actual: pipelineParams.skipUnitest
                    }  
                }
                steps {
                    echo "UNITEST"
                }
            }
        }
    }
}
========jenkins file====
  //its skip the unit test
  @Library('my-shared-library')_
stageskip(){
    skipUnitest = true
}
//below both scenario will run the unitest stage
@Library('my-shared-library')_
stageskip(){
    skipUnitest = false
  }
or
@Library('my-shared-library')_
stageskip(){
}
