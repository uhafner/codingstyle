node('java-agent') {
    stage ('Checkout') {
        checkout scm
    }

    stage ('Git mining') {
        discoverGitReferenceBuild()
        mineRepository()
        gitDiffStat()
    }

    stage ('Build, Test, and Static Analysis') {
        withMaven(mavenLocalRepo: '/var/data/m2repository', mavenOpts: '-Xmx768m -Xms512m') {
            sh 'mvn -V -e clean verify -Dmaven.test.failure.ignore -Dgpg.skip -Drevapi.skip'
        }

        recordIssues tools: [java(), javaDoc()], aggregatingResults: 'true', id: 'java', name: 'Java'
        recordIssues tool: errorProne(), healthy: 1, unhealthy: 20

        junit testResults: '**/target/*-reports/TEST-*.xml'

        recordCoverage(tools: [[parser: 'JACOCO']],
                id: 'jacoco', name: 'JaCoCo Coverage',
                sourceCodeRetention: 'EVERY_BUILD',
                qualityGates: [
                    [threshold: 60.0, metric: 'LINE', baseline: 'PROJECT', unstable: true],
                    [threshold: 60.0, metric: 'BRANCH', baseline: 'PROJECT', unstable: true]])

        recordIssues tools: [checkStyle(pattern: 'target/checkstyle-result.xml'),
            spotBugs(pattern: 'target/spotbugsXml.xml'),
            pmdParser(pattern: 'target/pmd.xml'),
            cpd(pattern: 'target/cpd.xml'),
            taskScanner(highTags:'FIXME', normalTags:'TODO', includePattern: '**/*.java', excludePattern: 'target/**/*')],
            qualityGates: [[threshold: 1, type: 'TOTAL', unstable: true]]
    }

    stage ('Mutation Coverage') {
        withMaven(mavenLocalRepo: '/var/data/m2repository', mavenOpts: '-Xmx768m -Xms512m') {
            sh "mvn org.pitest:pitest-maven:mutationCoverage"
        }
        recordCoverage(tools: [[parser: 'PIT']],
                id: 'pit', name: 'Mutation Coverage',
                sourceCodeRetention: 'EVERY_BUILD',
                qualityGates: [
                    [threshold: 60.0, metric: 'MUTATION', baseline: 'PROJECT', unstable: true]])
    }

    stage ('Collect Maven Warnings') {
        recordIssues tool: mavenConsole()
    }
}
