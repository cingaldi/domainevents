# Domain Events: A clean approach

## Prequisites

* JDK 11
* Docker v. 19+
* Docker-Compose v. 1.26+
* make

## Why Domain Events

### Managing side-effects

### Flexible Consistency Semantics
 Sometimes we are led to think that domain events === async stuff that flies back and forth into our process and eventually consistent distributed transactions.
 Actually this is not took for granted: it really depends on the undelying technology. Therefore, domain events give developers the freedom to choose how to deal with consistency. With Spring Boot we can implement
 
 * Synchronous, in-thread local events
 * Asynchronous, multithreaded local events
 * Asychronous, multiprocess remote events
 
Other ecoystems offer different tools and implementations: think about Akka or Vertx and their Actor Model. Nodejs, Python's asyncio based on event-loops. Goroutines/channels
and so on...

### Adding Logging/Tracing capabilities
In this example is shown how it's possible -in some extent- to decorate event listeners in Spring Boot in order to address cross-cutting concerns. if the latter statements
makes you think about AOP, you should know that it's very tricky to develop an Aspect that joins Spring's ApplicationEventPublisher, since this is also the ApplicationContext.

An even more trvial approach would just be to wrap ApplicationEventPublisher into a custom decorator and define the wrapper as a Primary Bean: this works indeed, but there is no way to inject it into AbstractAggregateRoot inheriting objects

So I came out with another approach: define a "catch-all" event listener that listens to all Events that implement the DomainEvent interface. This means that devs declare
that certain events are also domain events, and domain events should be treated in a specific way; for instance, logging each occourrence and generating a new span

### How to publish remote (cross-domain) events
When you split an event-driven, monolithic app into multiple microservices, one of the operation that should be done is to replace local domain events with "remote events"
sent over the wire (in this example, the "wire" is RabbitMQ) it would be nice if we are able to do it without any modification to the core logic. That's why this example defines a specific EventAdapter that listens for CrossDomainEvents, a specialization of DomainEvents. Every CrossDomainEvent is serialized an associated to a specific topic (routing key) declared from the dev. Thus, to publish an event as a RabbitMQ message, is sufficent to make your event POJO to implement the CrossDomainEvent interface and implementing declared methods

### Best Practices for beautiful domains

## How to run it

You need to start the local "dev environment" in order to execute tests
`sudo make run-infra`

Then you run tests from you favouire IDE or from command line with
`mvn clean test`

Finally, shut down the infrastructure with
`sudo make stop-infra`
