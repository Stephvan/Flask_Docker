import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.dockerCommand
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.dockerCompose
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.python
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.vcs
import jetbrains.buildServer.configs.kotlin.v2019_2.vcs.GitVcsRoot

/*
The settings script is an entry point for defining a TeamCity
project hierarchy. The script should contain a single call to the
project() function with a Project instance or an init function as
an argument.

VcsRoots, BuildTypes, Templates, and subprojects can be
registered inside the project using the vcsRoot(), buildType(),
template(), and subProject() methods respectively.

To debug settings scripts in command-line, run the

    mvnDebug org.jetbrains.teamcity:teamcity-configs-maven-plugin:generate

command and attach your debugger to the port 8000.

To debug in IntelliJ Idea, open the 'Maven Projects' tool window (View
-> Tool Windows -> Maven Projects), find the generate task node
(Plugins -> teamcity-configs -> teamcity-configs:generate), the
'Debug' option is available in the context menu for the task.
*/

version = "2021.1"

project {

    vcsRoot(HttpsGithubComStephvanFlaskDockerGitRefsHeadsMaster)

    buildType(Build)
}

object Build : BuildType({
    name = "Build"

    vcs {
        root(HttpsGithubComStephvanFlaskDockerGitRefsHeadsMaster)
    }

    steps {
        dockerCommand {
            commandType = build {
                source = file {
                    path = "app/Dockerfile"
                }
            }
        }
        dockerCommand {
            commandType = build {
                source = file {
                    path = "deployments/app/Dockerfile"
                }
            }
        }
        python {
            environment = venv {
            }
            command = file {
                filename = "app.py"
            }
            param("teamcity.build.workingDir", "app")
        }
        dockerCompose {
            file = "deployments/docker-compose.yml"
        }
    }

    triggers {
        vcs {
        }
    }
})

object HttpsGithubComStephvanFlaskDockerGitRefsHeadsMaster : GitVcsRoot({
    name = "https://github.com/Stephvan/Flask_Docker.git#refs/heads/master"
    url = "https://github.com/Stephvan/Flask_Docker.git"
    branch = "refs/heads/master"
    branchSpec = "refs/heads/*"
    authMethod = password {
        userName = "Stephvan"
        password = "zxxfd2043a7dd5a1ba9f10cf4f553e1956ce87796e4cdbf1986"
    }
})
