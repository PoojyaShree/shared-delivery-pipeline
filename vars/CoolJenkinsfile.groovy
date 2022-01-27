def call(body) {
// evaluate the body block, and collect configuration into the object
//def config = [:]
//body.resolveStrategy = Closure.DELEGATE_FIRST
//body.delegate = config
body()
	pipeline{
		agent any
		stages{
			
stage( "legacy" ) {
	steps{
	echo( "hello world" )
	}//steps
}//stage
 }//stages
}//pipeline
}//call
