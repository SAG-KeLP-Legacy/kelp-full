kelp-full
=========

 **KeLP** is the Kernel-based Learning Platform developed in the [Semantic Analytics Group][sag-site] of
the [University of Roma Tor Vergata][uniroma2-site]. 

This is a complete package of **KeLP**. You can include this dependency in your [Maven][maven-site] project to obtain all the 
funcionalities of the learning platform.
To use **KeLP** within your [Maven][maven-site] project, please add the following repositories to your pom file.

```
<repositories>
	<repository>
			<id>kelp_repo_snap</id>
			<name>KeLP Snapshots repository</name>
			<releases>
				<enabled>false</enabled>
				<updatePolicy>always</updatePolicy>
				<checksumPolicy>warn</checksumPolicy>
			</releases>
			<snapshots>
				<enabled>true</enabled>
				<updatePolicy>always</updatePolicy>
				<checksumPolicy>fail</checksumPolicy>
			</snapshots>
			<url>http://sag.art.uniroma2.it:8081/artifactory/kelp-snapshot/</url>
		</repository>
		<repository>
			<id>kelp_repo_release</id>
			<name>KeLP Stable repository</name>
			<releases>
				<enabled>true</enabled>
				<updatePolicy>always</updatePolicy>
				<checksumPolicy>warn</checksumPolicy>
			</releases>
			<snapshots>
				<enabled>false</enabled>
				<updatePolicy>always</updatePolicy>
				<checksumPolicy>fail</checksumPolicy>
			</snapshots>
			<url>http://sag.art.uniroma2.it:8081/artifactory/kelp-release/</url>
		</repository>
	</repositories>
```

Then, the [Maven][maven-site] dependency for the whole **KeLP** package:

```
<dependency>
    <groupId>it.uniroma2.sag.kelp</groupId>
    <artifactId>kelp-full</artifactId>
    <version>1.1.1</version>
</dependency>
```



[sag-site]: http://sag.art.uniroma2.it "SAG site"
[uniroma2-site]: http://www.uniroma2.it "University of Roma Tor Vergata"
[maven-site]: http://maven.apache.org "Apache Maven"
