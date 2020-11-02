# Domain Events: A clean approach

##Prequisites

* JDK 11
* Docker v. 19+
* Docker-Compose v. 1.26+
* make

##Why Domain Events

### Managing side-effects

###Flexible Consistency Semantics

###Adding Logging/Tracing capabilities

###How to publish remote (cross-domain) events

###Best Practices for beautiful domains

##How to run it

You need to start the local "dev environment" in order to execute tests
`sudo make run-infra`

Then you run tests from you favouire IDE or from command line with
`mvn clean test`

Finally, shut down the infrastructure with
`sudo make stop-infra`