@Library('aikido-jenkins-scripts') _

def builderPodLabel = getBuilderLabel()
def appName = "aikido-atemi-service"

podTemplate(label: builderPodLabel, yaml: getBuilderTemplate()) {
    node (builderPodLabel) {
        container('docker-helm') {
            stage('Helm Deploying') {
                echo "${params.Target}"
                runHelmDeployment("aikido-app-charts", "default", "alpha-production", appName, "${params.imageVersion}")
            }
        }
    }
}