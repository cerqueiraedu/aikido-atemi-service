@Library('aikido-jenkins-scripts') _

def builderPodLabel = getBuilderLabel()
def appName = "aikido-atemi-service"
def image

podTemplate(label: builderPodLabel, yaml: getBuilderTemplate()) {
    node (builderPodLabel) {
        container('docker-helm') {
            stage('Fetching Code') { 
                checkout scm
            }
            stage('Docker Build') {
                image = docker.build("ecerqueira/${appName}", ".")
            }
            stage('Running Tests') {
                def testResultsPath = "${env.WORKSPACE}/build"
                runNodeTests(appName, testResultsPath)
            }
            stage('Collect Test Results') {
                junit "**/build/*.xml"
            }
            stage('Triggering Promotion Process') {
                build job: 'Aikido Atemi Service MultiConfig', 
                parameters: [[$class: 'StringParameterValue', name: 'build', value: 'success'], 
                            [$class: 'StringParameterValue', name: 'branchName', value: "${BRANCH_NAME}"]]  
            }
        }
    }
}