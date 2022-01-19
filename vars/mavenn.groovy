def call(body) {
     def pipelineParams= [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = pipelineParams
    body()
   def mavenGoals=pipelineParams.mavenGoals ?: 'clean package'
   def M3_HOME= tool 'maven'
