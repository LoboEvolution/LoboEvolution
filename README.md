<p align="center">
	<img src="https://avatars3.githubusercontent.com/u/6113075?s=460&v=4" height="130">
</p>
<p align="center">	
	<a href="https://app.codacy.com/gh/LoboEvolution/LoboEvolution/dashboard?utm_content" alt="Codacy"><img src="https://app.codacy.com/project/badge/Grade/899f68bba4a5463d8a7699821d840c5c" /></a>
	<a href="https://codebeat.co/projects/github-com-loboevolution-loboevolution-master"><img alt="codebeat badge" src="https://codebeat.co/badges/74e4393e-77b9-44a9-ad98-0b33fb839754" /></a>
	<a href="https://codeclimate.com/github/LoboEvolution/LoboEvolution/maintainability"><img src="https://api.codeclimate.com/v1/badges/eaeed65cfc69b72b4701/maintainability" /></a>
	<a href="https://github.com/LoboEvolution/LoboEvolution/actions/workflows/codeql.yml"><img src="https://github.com/LoboEvolution/LoboEvolution/actions/workflows/codeql.yml/badge.svg?branch=master" alt="Docs"></a>
</p>
<p align="center">
	<a href="http://sourceforge.net/projects/loboevolution/"><img src="https://img.shields.io/website-up-down-green-red/http/shields.io.svg" alt="Website"/></a>
        <a href="https://github.com/LoboEvolution/LoboEvolution/graphs/contributors" alt="Contributors"><img src="https://img.shields.io/github/contributors/LoboEvolution/LoboEvolution"/></a>
	<a href="https://github.com/LoboEvolution/LoboEvolution/pulse" alt="Activity"><img src="https://img.shields.io/github/commit-activity/m/LoboEvolution/LoboEvolution"/></a>
	<a href="" alt="PRs"><img src="https://img.shields.io/badge/PRs-welcome-brightgreen.svg" /></a>
	<a href="https://loboevolution.github.io/LoboEvolution/"><img src="https://inch-ci.org/github/oswetto/LoboEvolution.svg" alt="Docs"></a>
</p>
<p align="center">
	<a href="https://github.com/LoboEvolution/LoboEvolution/blob/master/LICENSE"><img src="https://img.shields.io/badge/License-MIT-yellow.svg" alt="MIT"></a>		
	<img src="https://img.shields.io/badge/version-4.0-blue" alt="Stable Version"/>
        <a href="https://github.com/LoboEvolution/LoboEvolution/releases/download/4.0/loboevolution-4.0.jar"><img src="https://img.shields.io/github/downloads/LoboEvolution/LoboEvolution/total.svg" alt="Download"></a>
	<a href="https://github.com/LoboEvolution/LoboEvolution/packages/1550365"><img src="https://github.com/LoboEvolution/LoboEvolution/actions/workflows/publish-github.yml/badge.svg" alt="Github Package"></a>
	<a href="https://github.com/oswetto"><img src="https://img.shields.io/badge/Ask%20me-anything-1abc9c.svg" alt="Ask Me"></a>
</p>

Lobo Evolution began as a fork of the now-defunct project called the LoboBrowser [credits](https://sourceforge.net/projects/xamj/).

### Supports
Lobo Evolution is an extensible all-Java web browser and RIA platform. <br/>

### CobraEvolution
Loboevolution use [CobraEvolution](https://github.com/LoboEvolution/CobraEvolution) for render page and and parse html, css and js.
[CobraEvolution](https://github.com/LoboEvolution/CobraEvolution) is a browserless java project

See CobraEvolution in action
* Html Parser => [CobraParser](https://github.com/LoboEvolution/CobraEvolution/blob/main/LoboUnitTest/src/test/java/org/loboevolution/driver/CobraParser.java)
* Html Render and Parser => [CobraHtmlPanel](https://github.com/LoboEvolution/CobraEvolution/blob/main/LoboUnitTest/src/test/java/org/loboevolution/driver/CobraHtmlPanel.java)

After <a href="https://github.com/LoboEvolution/LoboEvolution/releases/download/4.0/loboevolution-4.0.jar">download<a/> to run
```
java -jar loboevolution-4.0.jar
```

### Local Building
Run 
[PlatformInit.java](https://github.com/oswetto/LoboEvolution/blob/master/LoboEvo/src/main/java/org/loboevolution/init/PlatformInit.java)

### Maven Users
```
mvn install -Dmaven.test.skip=true
```

### Maven Unit Test & report
```
mvn surefire-report:report
```

| Unit Tests | Passed | Errors | Failures | Skipped | Rate Success |                        Result                         |
|:----------:|:------:|:------:|:--------:|:-------:|:------------:|:-----------------------------------------------------:|
|    5574    |  3218  |   23   |   2243   |   1     |    59,39%    | [see](https://loboevolution.github.io/LoboEvolution/surefire-report.html) |

### How To Do
[TODO](https://github.com/oswetto/LoboEvolution/wiki/How-TODO)

### Pre-History
The history of all commits that transform LoboBrowser in LoboEvoluiton is available in [a separate repository](https://github.com/oswetto/LoboEvolutionPreHistory).

## Contributors
![Your Repository's Stats](https://contrib.rocks/image?repo=LoboEvolution/LoboEvolution)
