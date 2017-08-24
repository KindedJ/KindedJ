# KindedJ
A collection of shared interfaces for evidence-based Higher Kinded Types in the JVM

[![Gitter chat](https://badges.gitter.im/KindedJ/KindedJ.png)](https://gitter.im/KindedJ/Lobby)

## Projects that support KindedJ

#### Legend 
Name & Organisation - Language - Supported since version

*Add yourself here by [sending a Pull Request](https://github.com/KindedJ/KindedJ/compare)*

[Kategory](https://github.com/kategory/kategory) - Kotlin - 0.3.6

## Purpose

A Higher Kinded Type represents a value that wraps another value. It would be equivalent to F\<A> if Java supported higher kinds. This allows library writers to create abstractions that work on any wrapper type in a completely generic fashion. You can see some examples in the projects supporting KindedJ listed above.

The objective of KindedJ is to provide a common set of interfaces that enable cross-library and cross-language support for all opensource maintainers using evidence-based emulation in the JVM.

## Usage

Evidence-based Higher Kinded Types (HKTs) is a way of emulating HKTs even without explicit language support. 

A *datatype* is any class who we want to make into an HKT. Examples include List, Future, Optional, Stream, Observable... To consider a class a datatype it has to implement the interface `Hk<F, A>`, or provide a wrapper class that does. In this interface, the generic parameter `F` is considered the *witness* and the generic parameter `A` the *value*.

A *witness* type is an empty type without constructors. This type is unique globally and exclusively used to identify a single datatype when used as a generic parameter.

A *value* is any data type that doesn't affect the operations on the container. For example, you would not expect a `List` of `Strings` to behave differently than a `List` of `UserDto` for operations like `size()` or `map()`.

Evidence-based HKTs allow transformation from their higher kind form into their datatype form, and viceversa, in what's called an isomorphism. This isomorphism allows transformation in both directions without information loss.

### Spec

An HKT built with KindedJ follows the following guidelines:

* A witness has to be co-located with its datatype and be easily discoverable. Witness types must be available publicly outside the library. In the cases of code generation it's expected that the witness belongs at least to the same package or similar physical unit. Witnesses should not be inheritable or used for multiple datatypes.

* By subtyping we obtain a way of transforming datatypes into higher kinds. The datatype must implement the `Hk` interface for its unique witness. That way any operation that'd require a generic HKT is capable of receiving a datatype.

* The reverse operation from HKT to datatype cannot be completed using subtyping. An *evidence function* that allows transforming from HKT to datatype must be provided. Evidence functions have to be available publicly and globally to be accessed by any other KindedJ user.

### Higher arities

To construct HKTs of arity 2+ it is required to nest Hk into its witness type parameter.

For example, to represent F<G<H\<A>>> you have to create an interface or type alias in the form of Hk<Hk<Hk<F, G>, H>, A>

### Distribution

Add as a dependency to your `build.gradle`

```groovy
dependencies {
    ...
    compile 'io.kindedj:KindedJ:1.0.0'
    ...
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

### License

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
