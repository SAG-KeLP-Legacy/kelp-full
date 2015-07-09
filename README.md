kelp-full
=========

 **KeLP** is the Kernel-based Learning Platform developed in the [Semantic Analytics Group][sag-site] of
the [University of Roma Tor Vergata][uniroma2-site]. 

This is a complete package of **KeLP**. 
It aggregates the following modules:

* [kelp-core](https://github.com/SAG-KeLP/kelp-core): it contains the core interfaces and classes; for instance it includes the interfaces and abstract classes needed to define novel representations, kernels or algorithms;

* [vector-representation](https://github.com/SAG-KeLP/vector-representation): it contains *SparseVector* and *DenseVector*;

* [discrete-representation](https://github.com/SAG-KeLP/discrete-representation): it contains *TreeRepresentation*, *SequenceRepresentation* and *StringRepresentation*;

* [graph-representation](https://github.com/SAG-KeLP/graph-representation): it contains *DirectedGraphRepresentation*;

* [standard-kernel](https://github.com/SAG-KeLP/standard-kernel): it contains common kernel functions, such as the *PolynomialKernel* and the *RbfKernel*;

* [tree-kernel](https://github.com/SAG-KeLP/tree-kernel): it contains several convolution kernels, such as *SubTreeKernel*, *SubSetTreeKernel*, *PartialTreeKernel*, *SmoothedPartialTreeKernel* and *SequenceKernel*;

* [graph-kernel](https://github.com/SAG-KeLP/graph-kernel): it contains graph kernels, such as *ShortestPathKernel* and the Weisfeiler-Lehman Subtree Kernel for Graphs;

* [batch-learning-margin](https://github.com/SAG-KeLP/batch-large-margin): it contains the implementation of several svm algorithms for classification and regression and the corresponding prediction function; for instance it includes *OneClassClassification*, *PegasosLearningAlgorithm*, *CSvmClassification* or *LibLinearRegression*;

* [online-large-margin](https://github.com/SAG-KeLP/online-large-margin): it contains the implementations of Online Learning algorithms in their linear and kernel-based versions, for classification and regression, e.g. *KernelizedPerceptron* or *LinearPassiveAggressive*. Furthermore some online learning algorithm over a budget are included, such as *BudgetedPassiveAggressive*;

* [kernel-clustering](https://github.com/SAG-KeLP/kernel-clustering): it contains the *KernelBasedKMean* algorithm.

**KeLP** can be easily included in your [Maven][maven-site] project adding the following repositories to your pom file:

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

Alternatively, thanks to the modularity of **KeLP**, you can include a fine grain selection of its modules adding to your POM files only the dependancies you need among the modules stated above.  

[sag-site]: http://sag.art.uniroma2.it "SAG site"
[uniroma2-site]: http://www.uniroma2.it "University of Roma Tor Vergata"
[maven-site]: http://maven.apache.org "Apache Maven"
