import hudson.model.*


Build build = Executor.currentExecutor().currentExecutable
/*ParametersAction parametersAction = build.getAction(ParametersAction)
parametersAction.parameters.each { ParameterValue v ->
    println v
}*/

def param_name = "Project_Name"
def resolver = build.buildVariableResolver
def hardcoded_param_value = resolver.resolve(param_name)


pipelineJob(hardcoded_param_value) {

	definition {
        cps {
            sandbox()
            script("""
                node{
			stage('Checkout'){
				git 'https://github.com/Pradeepaero07/mvndemo.git'
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
