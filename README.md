# KindedJ
A collection of shared interfaces for evidence-based Higher Kinded Types in the JVM

[![Gitter chat](https://badges.gitter.im/KindedJ/KindedJ.png)](https://gitter.im/KindedJ/Lobby)

## Projects that support KindedJ

*Add yourself here alphabetically by [sending a Pull Request](https://github.com/KindedJ/KindedJ/compare)*

| | Name & Organisation | Language | Since version | Description |
|:---:| --- |:---:| ---:| --- |
<img src="https://raw.githubusercontent.com/kategory/kategory-art/master/kategory-brand-light.png" width="48">|[Kategory](https://github.com/kategory/kategory)|Kotlin|0.3.6|Functional Data Types & Abstractions for Kotlin|

## Purpose

A Higher Kinded Type represents a value that wraps another value. They can be reasoned about as type constructors, where arity represents the number of generic parameters. For example, a List can contain only one parameter, and is expressed as `List<T>`. A type that can contain either a value of type A or a value of type B has arity 2, and requires a type constructor with the same number of parameters, expressed as `Either<A, B>`. The group of type constructors with exactly one parameter is usually represented as `* -> *`, and every increment in arity adds a new * parameter. This way, `Either<A, B>` has a type constructor that belongs to the group `* -> * -> * `. Filling a generic parameter with a concrete value lowers the arity of the constructor by one *, so an `Either<Int, B>` belongs to `* -> *` and List<String> is `*`. You can read more about kinds in [Wikipedia](https://en.wikipedia.org/wiki/Kind_(type_theory)) or [StackOverflow](https://softwareengineering.stackexchange.com/a/276861/72626).
 
This representation of generic polymorphism for any arity allows library writers to create abstractions that work on any wrapper type in a completely generic fashion. For library design this means the equivalent of supporting parameters of type F\<A>, which would take any `* -> *`. You can see some examples of such generic functions in the projects supporting KindedJ listed above.

The objective of KindedJ is to provide a common set of interfaces that enable cross-library and cross-language support for all opensource maintainers using evidence-based higher kind emulation in the JVM.

## Usage

Evidence-based Higher Kinded Types (HKTs) is a way of emulating HKTs even without explicit language support. It is described in the paper [Lightweight higher-kinded Polymorphism](https://www.cl.cam.ac.uk/~jdy22/papers/lightweight-higher-kinded-polymorphism.pdf) by Jeremy Yallop and Leo White.

A *datatype* is any class who we want to make into an HKT. Examples include List, Future, Optional, Stream, Observable... To consider a class a datatype it has to implement the interface `Hk<F, A>`, or provide a wrapper class that does. In this interface, the generic parameter `F` is considered the *witness* and the generic parameter `A` the *value*.

A *witness* type is an empty type without constructors. This type is unique globally and exclusively used to identify a single datatype when used as a generic parameter. This type has to be different than the datatype, a datatype should not be a witness of itself. Generic parameters requiring a witness are usually represented by the characters F, G, H, or datatype specific characters like L for the left side of an Either.

A *value* is any data type that doesn't affect the operations on the container. For example, you would not expect a `List` of `Strings` to behave differently than a `List` of `UserDto` for operations like `size()` or `map()`. Generic parameters requiring a value are usually represented by the characters A, B, C, D...

Evidence-based HKTs allow transformation from their higher kind form into their datatype form, and viceversa, in what's called an isomorphism. This isomorphism allows transformation in both directions without information loss.

### Spec

An HKT built with KindedJ follows the following guidelines:

* A witness has to be co-located with its datatype and be easily discoverable. Witness types must be available publicly outside the library. In the cases where the witness is dynamically generated, like with annotation processing or compiler plugins, it's expected that the witness belongs at least to the same package or similar physical unit. Witnesses should not be inheritable or used for multiple datatypes.

* By subtyping we obtain a way of transforming datatypes into higher kinds. A datatype must implement the `Hk` interface for its unique witness. That way any operation that'd require a generic HKT is capable of receiving a datatype.

* The reverse operation from HKT to datatype cannot be completed using subtyping. An *evidence function* that allows transforming from HKT to datatype must be provided. Evidence functions have to be available publicly and globally to be accessed by any other KindedJ user.

### Higher Kinded Type Arities

Generic functions and constructs that consume evidence-based HKTs are only capable of understanding kinds of arity 1. To construct HKTs of arity 2+ it is required to nest Hk into its witness type parameter. When trying to represent `F<G, H, A>` it is required to create an instance in the form of `Hk<Hk<Hk<F, G>, H>, A>`.

In the opposite case, it may happen that HKTs of arity higher than 1 require a version with fewer arguments for some generic constructs. These cases are solved by creating a new interface or typealias of that arity. 

One snippet example containing both cases, where `F<G, H, A>` is constructed in a function where values `A` and `B` are injected on a per call basis:

```java
public class ExampleMonad<F, G, H> implements Monad<Hk<Hk<F, G>, H>> {
    public <A, B> Hk<Hk<Hk<F, G>, H>, B> flatMap(Function<A, Hk<Hk<Hk<F, G>, H>, B>> partialHk) {
        /* ... */
    }
}
```

It is not uncommon to create inheritance schemes or typealiases to represent HKTs of different arities. This allows avoiding conflicting function signatures due to generic type erasure in Java and improves readability, all at the expense of dealing with up/downcasting troubles.

## Distribution

Add as a dependency to your `build.gradle`

```groovy
dependencies {

    compile 'io.kindedj:KindedJ:1.0.0'

}
```
or to your `pom.xml`

```xml
<dependency>
    <groupId>io.kindedj</groupId>
    <artifactId>KindedJ</artifactId>
    <version>1.0.0</version>
</dependency>
```

## License

Copyright (c) KindedJ 2017

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
