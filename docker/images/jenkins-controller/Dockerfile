# Prepare a Debian-based Docker image with several utilities installed to automatically generate SSH keys
FROM jenkins/jenkins:lts-jdk21

# We switch back to the Jenkins user for the remaining operations.
USER jenkins

# We copy the jobs directory from our current directory to the Jenkins home directory in the image.
COPY preconfigured-jobs /usr/share/jenkins/ref/jobs

# We write the Jenkins version to the UpgradeWizard state file.
# This prevents the Upgrade Wizard from showing up when Jenkins starts.
RUN echo "${JENKINS_VERSION}" > /usr/share/jenkins/ref/jenkins.install.UpgradeWizard.state

# We copy a list of plugins to install to the Jenkins ref directory in the image.
COPY plugins.txt /usr/share/jenkins/ref/plugins.txt

# We use the Jenkins plugin CLI to install the plugins listed in the plugins.txt file.
RUN jenkins-plugin-cli --verbose -f /usr/share/jenkins/ref/plugins.txt

# We copy a pre-configured Jenkins configuration file to the Jenkins ref directory in the image.
# This allows us to pre-configure Jenkins with our desired settings.
COPY jenkins.yaml /usr/share/jenkins/ref/jenkins.yaml

# Create an admin user and don't start the wizard
RUN echo 2.x > /usr/share/jenkins/ref/jenkins.install.UpgradeWizard.state
ENV JENKINS_OPTS -Djenkins.install.runSetupWizard=false
COPY security.groovy /usr/share/jenkins/ref/init.groovy.d/basic-security.groovy

# Configure Jenkins
COPY jenkins.yaml /usr/share/jenkins/ref/jenkins.yaml
