# KindedJ
A collection of shared interfaces for evidence-based Higher Kinded Types in the JVM

[![Download](https://api.bintray.com/packages/kindedj/maven/kindedj/images/download.svg)](https://bintray.com/kindedj/maven/kindedj/_latestVersion)
[![Gitter chat](https://badges.gitter.im/KindedJ/KindedJ.png)](https://gitter.im/KindedJ/Lobby)

## Projects that support KindedJ

*Add yourself here alphabetically by [sending a Pull Request](https://github.com/KindedJ/KindedJ/compare)*

| | Name & Organisation | Language | Since version | Description |
|:---:| --- |:---:| ---:| --- |
<img src="https://raw.githubusercontent.com/kategory/kategory-art/master/kategory-brand-light.png" width="48">|[Kategory](https://github.com/kategory/kategory)|Kotlin|0.3.6|Functional Data Types & Abstractions for Kotlin|

## Purpose

The objective of KindedJ is to provide a common set of interfaces that enable cross-library and cross-language support for all projects using evidence-based Higher Kinded Types emulation in the JVM.

## Rationale

Higher Kinded Types is feature of a type system which main purpose is to allow type constructor polymorphism, that is, abstracting over type constructors.
A type constructor is a function at the type level that take type(s) as argument(s) and returns a type as a result. The arity of a type constructor is represented by the number of generic parameters in its signature.
We can say that List is a type constructor as it contains a type parameter: it needs 1 type argument to produce a concrete type. `List<T>`  applied with the type argument `String` in the position `T` gives you the concrete type `List<String>`. A type that can contain either a value of type A or a value of type B two type parameters, and requires a type constructor expressed as `Either<A, B>`.

The group of type constructors with exactly one parameter is usually represented as `* -> *`, and every increment in arity adds a new * parameter. This way, `Either<A, B>` has a type constructor that belongs to the group `* -> * -> * `.

Filling a generic parameter with a concrete value lowers the arity of the constructor by one *, so an `Either<Int, B>` belongs to `* -> *` and `List<String>` is `*`. All arities can be represented as arity 1 by grouping them on what's called a type function `(* -> * -> *) -> *`. This grouping is a key construct to support Higher Kinded Type emulation in the JVM.
 
This representation of type parameters allows library writers to create abstractions that are generic on the type constructor. The archetype of such a method is the `map` method. `map` receives a type parameter `B` and a parameter whose type constructor has arity of at least 1 and `A` as the last type parameter. Map's return has the same type constructor as the input with `B` as its last type parameter. In the JDK a version of this method exists in `java.util.Optional`, `java.util.stream.Stream` and `java.util.concurrent.CompletableFuture`, and could be declared for many others like `java.util.Iterable`. If you want to write a reusable piece of code that can work for every type that implements `map`, without HKTs you actually need to write boilerplate code, and potential errors, for every type you wish to support. With HKTs, you only need to write it once!

You can see some examples of reusable pieces of code using HKTs in the projects supporting KindedJ listed above. You can read more about kind and type theory in [Wikipedia](https://en.wikipedia.org/wiki/Kind_(type_theory)) or [StackOverflow](https://softwareengineering.stackexchange.com/a/276861/72626).

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
public class ExampleMonad<G, H> implements Monad<Hk<Hk<ExampleWitness, G>, H>> {
    public <A, B> Hk<Hk<Hk<ExampleWitness, G>, H>, B> flatMap(Function<A, Hk<Hk<Hk<ExampleWitness, G>, H>, B>> partialHk) {
        /* ... */
    }
}
```

It is not uncommon to create inheritance schemes or typealiases to represent HKTs of different arities. This allows avoiding conflicting function signatures due to generic type erasure in Java and improves readability, all at the expense of dealing with up/downcasting troubles.

### Aliases via subtyping

Library authors are not bound to use the `Hk` interface directly everywhere, they may use there own interfaces, as long as the root interface extends `io.kindedj.Hk`.

Convenient support for multiple type parameters is one motivation for such aliases, eg. instead of:
```java
final class Tuple3<A, B, C> extends Hk<Hk<Hk<w, A>, B>, C> {
  enum w{};
  ...
}
```
One may introduce the following aliases:
```java
interface Higher<F, A> extends Hk<F, A> {}

interface Higher2<F, A, B> extends Higher<Higher<F, A>, B> {}

interface Higher3<F, A, B, C> extends Higher2<Higher<F, A>, B, C> {}
```
and then have `Tuple3` extends `Higher3`:
```java
final class Tuple3<A, B, C> extends Higher3<w, A, B, C> {
  enum w{};
  ...
}
```

However the introduction of such aliases may require a normalization step to recover the standard KindedJ encoding for the purpose of interoperability with the wider KindedJ ecosystem.
KindedJ ship a small utility class providing those normalization methods: `io.kindedj.HCov`:
```java
  Higher3<w, A, B, C> t3 = ...;

  Hk<Hk<Hk<w, A>, B>, C> normalizedT3 = HCov.covary3(t3)

```


## Distribution

Add as a dependency to your `build.gradle`

```groovy
dependencies {
    compile 'io.kindedj:kindedj:1.0.1'
}
```
or to your `pom.xml`

```xml
<dependency>
    <groupId>io.kindedj</groupId>
    <artifactId>kindedj</artifactId>
    <version>1.0.1</version>
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
