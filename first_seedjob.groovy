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

	def repo = 'https://github.com/Pradeepaero07/mvndemo.git' 
  	def sshRepo = 'git@github.com:Pradeepaero07/mvndemo.git' 

	  description("Your App Pipeline") 
	  keepDependencies(false) 

	  properties{ 
	    githubProjectUrl (repo) 
	    rebuild { 
	      autoRebuild(false) 
	    } 
	  } 

	  definition { 

	    cpsScm { 
	      scm { 
		git { 
		  remote { url(sshRepo) } 
		  branches('master') 
		  scriptPath('Jenkinsfile')
		} 
	      } 
    } 
}
