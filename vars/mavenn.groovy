def call(body) {
     def pipelineParams= [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = pipelineParams
    body()
   def M3_HOME= tool 'maven'
   def mavenGoals=pipelineParams.mavenGoals 
}
