import hudson.model.*


Build build = Executor.currentExecutor().currentExecutable
/*ParametersAction parametersAction = build.getAction(ParametersAction)
parametersAction.parameters.each { ParameterValue v ->
    println v
}*/

def prj_param_name = "Project_Name"
def app_param_name = "Application_Name"
def git_url_param_name = "Git_URL"
def branch_param_name = "Branch"
def jenkins_file_path_param_name = "Jenkins_file_path"


def resolver = build.buildVariableResolver
def project_name = resolver.resolve(prj_param_name)
def app_name = resolver.resolve(app_param_name)
def folderPath = app_name+"/dev-ci-cd/"+project_name
def gitURL = resolver.resolve(git_url_param_name)
def branchName = resolver.resolve(branch_param_name)
def jenkins_file_path = resolver.resolve(jenkins_file_path_param_name)

println folderPath

folder(app_name) {
    displayName(app_name)
    
    description('Folder for dev environment continuous integration and continuous deployment jobs')
}
folder(app_name+'/dev-ci-cd') {
    displayName('dev-ci-cd')
    
    description('Folder for dev environment continuous integration and continuous deployment jobs')
}

pipelineJob(folderPath) {

	  description("Your App Pipeline") 
	  keepDependencies(false) 

	  properties{ 
	    githubProjectUrl (gitURL) 
	    rebuild { 
	      autoRebuild(false) 
	    } 
	  } 
	

	  definition { 

	    cpsScm { 
	      	scm { 
			git { 
			  remote { url(gitURL) } 
			  branches(branchName) 
			  scriptPath(jenkins_file_path)
			} 
	     	} 
	} 
}
}
