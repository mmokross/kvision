/*
 * Copyright (c) 2017-present Robert Jaros
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package pl.treksoft.kvision.react

import kotlinx.browser.document
import org.w3c.dom.HTMLElement
import pl.treksoft.jquery.invoke
import pl.treksoft.jquery.jQuery
import pl.treksoft.kvision.core.Container
import pl.treksoft.kvision.panel.ContainerType
import pl.treksoft.kvision.panel.Root
import react.RBuilder
import react.RProps
import react.child
import react.createRef
import react.dom.div
import react.functionalComponent
import react.useEffectWithCleanup
import react.useState

/**
 * A helper functional component used by KVision React.
 */
fun <S> reactWrapper(builder: RBuilder.(refresh: (S) -> Unit) -> Unit) = functionalComponent<RProps> {
    @Suppress("UnsafeCastFromDynamic")
    val state = useState<S> { js("{}") }
    builder(state.component2())
}

/**
 * A helper functional component which allows to use KVision components as React children.
 */
fun kvisionWrapper(builder: Container.() -> Unit) = functionalComponent<RProps> {
    val elRef = createRef<HTMLElement>()
    useEffectWithCleanup {
        var root: Root? = null
        var el: HTMLElement? = null
        elRef.current?.let {
            el = document.createElement("div") as HTMLElement
            it.appendChild(el!!)
            root = Root(el!!, ContainerType.NONE, false, init = builder)
        };
        {
            root?.dispose()
            el?.let { jQuery(it).remove() }
        }
    }
    div {
        ref = elRef
    }
}

/**
 * An extension function to simplify kvisionWrapper usage.
 */
fun RBuilder.kv(builder: Container.() -> Unit) = child(kvisionWrapper(builder))
