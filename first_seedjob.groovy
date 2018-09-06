import hudson.model.*


Build build = Executor.currentExecutor().currentExecutable
ParametersAction parametersAction = build.getAction(ParametersAction)
parametersAction.parameters.each { ParameterValue v ->
    println v
}

def hardcoded_param = "Project_Name"
def resolver = build.buildVariableResolver
def hardcoded_param_value = resolver.resolve(hardcoded_param)


pipelineJob(hardcoded_param_value) {

	definition {
        cps {
            sandbox()
            script("""
                node {
                    stage 'Hello world'
                    echo 'Hello World 1'
                    stage "invoke another pipeline"
                    build 'pipeline-being-called'
                    stage 'Goodbye world'
                    echo "Goodbye world"
					stage('Checkout'){
					checkout([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[url: 'https://github.com/Pradeepaero07/mvndemo.git']]])
					}
							
					stage('Testing Stage'){
						withMaven(maven: 'maven3.5.4'){
							bat 'mvn test'
						}
					}
                }
            """.stripIndent())
        }
    }
}
