# kolich-common

My core common Java classes packaged up into a nice lil artifact.

## Latest Version

See the <a href="https://github.com/markkolich/kolich-common/releases">Releases</a> page for the latest version.

## Resolvers

If you wish to use this artifact, you can easily add it to your existing Maven project using <a href="https://github.com/markkolich/markkolich.github.com#marks-maven2-repository">my GitHub hosted Maven2 repository</a>.

### Maven

```xml
<repository>
  <id>Kolichrepo</id>
  <name>Kolich repo</name>
  <url>http://markkolich.github.com/repo/</url>
  <layout>default</layout>
</repository>

<dependency>
  <groupId>com.kolich</groupId>
  <artifactId>kolich-common</artifactId>
  <version>0.4</version>
  <scope>compile</scope>
</dependency>
```

### Gradle

```groovy
compile 'com.kolich:kolich-common:0.4'
```

## Licensing

Copyright (c) 2015 <a href="http://mark.koli.ch">Mark S. Kolich</a>

All code in this artifact is freely available for use and redistribution under the <a href="http://opensource.org/comment/991">MIT License</a>.

See <a href="https://github.com/markkolich/kolich-common/blob/master/LICENSE">LICENSE</a> for details.
