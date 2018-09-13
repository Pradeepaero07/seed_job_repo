import hudson.model.*


Build build = Executor.currentExecutor().currentExecutable
/*ParametersAction parametersAction = build.getAction(ParametersAction)
parametersAction.parameters.each { ParameterValue v ->
    println v
}*/

def app_repo_param = "buildGitRepo"
def app_param = "app_name"
def pipeline_repo_param = "gitpipelinerepo"
def config_repo_param = "buildConfigRepo"
def gear_id_param = "gear_id"
def jenkins_file_path_param = "jenkins_file_path"
def gitURL_param = "gitURL"
def branch_param = "branch"


def resolver = build.buildVariableResolver
def app_repo = resolver.resolve(app_repo_param)
String app_name = resolver.resolve(app_param)
def pipeline_repo = resolver.resolve(pipeline_repo_param)
def config_repo = resolver.resolve(config_repo_param)
def gear_id = resolver.resolve(gear_id_param)
def jenkins_file_path = resolver.resolve(jenkins_file_path_param)
def gitURL = resolver.resolve(gitURL_param)
def branch = resolver.resolve(branch_param)

def folderPath = app_name+"-"+gear_id+"/dev-ci-cd/build_job"

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
	  parameters {
        	stringParam('buildGitRepo', app_repo, 'Application: application git repository URL')
            stringParam('buildConfigRepo', config_repo, 'Application: config git repository URL')
            stringParam('gitpipelinerepo', pipeline_repo, 'Pipeline utilities repository URL')
            stringParam('app_name', app_name, 'Application name')
            stringParam('gear_id', gear_id, 'Application gear ID')
            stringParam('app_name_lower_case', app_name.toLowerCase(), 'Application name in lower case')
    	
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
