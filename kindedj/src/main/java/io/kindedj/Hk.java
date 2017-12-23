/*
 * Copyright (c) KindedJ 2017
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.kindedj;

/**
 * Hk, or higher kind, represents a parametrized type of a class/interface. It would be equivalent to F&lt;A&gt; if Java had support for higher kinds.
 * <br><br>
 * To construct higher kinds of arity 2+ you have to nest Hk into its witness type parameter.
 * For example, to represent F&lt;G, H, A&gt; you have to implement an interface or type alias Hk&lt;Hk&lt;Hk&lt;F, G&gt;, H&gt;, A&gt;
 *
 * @param <F> The witness type of the parametrized class/interface to be lifted as a type constructor
 * @param <A> The type parameter applied to the type constructor witnessed by &lt;F&gt;
 */
public interface Hk<F, A> {
}
