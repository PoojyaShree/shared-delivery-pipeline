def "Jenkinsfile"() {
	setup:
		def Jenkinsfile = loadPipelineScriptForTest("com/homeaway/CoolJenkinsfile.groovy")
	when:
		Jenkinsfile.run()
	then:
		1 * getPipelineMock("node")("legacy", _)
		1 * getPipelineMock("echo")("hello world")
}
