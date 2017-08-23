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
 * Hk, or higher kind, represents a value that wraps another value. It would be equivalent to F&lt;A&gt; if Java supported higher kinds.
 * <br/><br/>
 * To construct higher kinds of arity 2+ you have to nest Hk into its witness type parameter.
 * For example, to represent F&lt;G&lt;H&lt;A&gt;&gt;&gt; you have to create an interface or type alias HK&lt;HK&lt;HK&lt;F, G&gt;, H&gt;, A&gt;
 *
 * @param <F> The witness type marking the wrapper type
 * @param <A> The value contained inside &lt;F&gt;
 */
public interface Hk<F, A> {
}
