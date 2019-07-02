FROM jenkins/jenkins:lts-alpine

USER root
RUN addgroup -g 102 docker
RUN adduser jenkins docker
RUN apk add libltdl
USER jenkins

# Install plugins
COPY plugins.txt /usr/share/jenkins/ref/plugins.txt
RUN /usr/local/bin/install-plugins.sh < /usr/share/jenkins/ref/plugins.txt

# Create admin user and don't start the wizard
RUN echo 2.0 > /usr/share/jenkins/ref/jenkins.install.UpgradeWizard.state
ENV JENKINS_OPTS -Djenkins.install.runSetupWizard=false
COPY security.groovy /usr/share/jenkins/ref/init.groovy.d/basic-security.groovy

# Copy material theme to user content so it can be referenced using http
COPY material-blue-grey.css /usr/share/jenkins/ref/userContent/material-blue-grey.css

# Configure Jenkins
COPY jenkins.yaml /usr/share/jenkins/ref/jenkins.yaml
