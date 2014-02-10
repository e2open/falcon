falcon
======

Selenium MVC test framework for Java

This is in progress. Working on test cases, examples and docs. It's essentially a Java version of [Watirmark](https://github.com/watirmark/watirmark), though lighter-weight. I'm currently using cucumber-jvm as the main driver for this and examples will be in that format.  


Developers:

When running the deploy target, you'll need to add  this to your settings.xml for gitHub authentication:

<servers>
  <server>
    <id>github</id>
    <username>GitHubLogin</username>
    <password>GitHubPassw0rd</password>
  </server>
</servers>

Be sure you check the setting in your POM for the target branch:
 <release.branch.name>mvn-repo-0.1.0.SNAPSHOT</release.branch.name>
