import hudson.model.*


Build build = Executor.currentExecutor().currentExecutable
/*ParametersAction parametersAction = build.getAction(ParametersAction)
parametersAction.parameters.each { ParameterValue v ->
    println v
}*/

def prj_param_name = "Project_Name"
def app_param_name = "Application_Name"
def resolver = build.buildVariableResolver
def project_name = resolver.resolve(prj_param_name)
def app_name = resolver.resolve(app_param_name)

folder(app_name) {
    displayName(app_name)
    description('Folder for dev environment continuous integration and continuous deployment jobs')
}

pipelineJob(app_name+'/dev-ci-cd/'+project_name) {

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
			  remote { url(repo) } 
			  branches('master') 
			  scriptPath('Jenkinsfile')
			} 
	     	} 
	} 
}
}
