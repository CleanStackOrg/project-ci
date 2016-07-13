# Project CI
New Build Pipeline System for Continuous Integration

# Build
## Check last version
mvn versions:display-dependency-updates
## Updating POM Versions
mvn release:update-versions -DautoVersionSubmodules=true
see http://maven.apache.org/maven-release/maven-release-plugin/examples/update-versions.html

# Roadmap
Planned modules:
- pipeline (stages, dsl)
- plan (trello, burndown)
- specs (cucumber editor)
- code (git, svn, cvs)
- build (maven, gradle)
- doc (wiki, site)
- deliver (nexus, repo)
- deploy (ansible)
- run (docker)
- report (cucumber results, bui)
- qa (sonar)
