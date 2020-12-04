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

package pl.treksoft.kvision.onsenui.dialog

import org.w3c.dom.events.MouseEvent
import pl.treksoft.kvision.core.AttributeSetBuilder
import pl.treksoft.kvision.html.Align
import pl.treksoft.kvision.html.CustomTag
import pl.treksoft.kvision.utils.set

/**
 * An alert dialog button component.
 *
 * @constructor Creates an alert dialog button component.
 * @param content the content of the button.
 * @param rich whether [content] can contain HTML code
 * @param align text align
 * @param icon an icon placed on the button
 * @param classes a set of CSS class names
 * @param init an initializer extension function
 */
@Suppress("LeakingThis")
open class AlertDialogButton(
    content: String? = null,
    rich: Boolean = false,
    align: Align? = null,
    icon: String? = null,
    classes: Set<String> = setOf(),
    init: (AlertDialogButton.() -> Unit)? = null
) : CustomTag("ons-alert-dialog-button", content, rich, align, classes) {

    /**
     *  The icon placed on the button.
     */
    var icon: String? by refreshOnUpdate(icon)

    /**
     * A modifier attribute to specify custom styles.
     */
    var modifier: String? by refreshOnUpdate()

    init {
        init?.invoke(this)
    }

    override fun buildAttributesSet(attributeSetBuilder: AttributeSetBuilder) {
        super.buildAttributesSet(attributeSetBuilder)
        icon?.let {
            attributeSetBuilder.add("icon", it)
        }
        modifier?.let {
            attributeSetBuilder.add("modifier", it)
        }
    }

    /**
     * A convenient helper for easy setting onClick event handler.
     */
    open fun onClick(handler: AlertDialogButton.(MouseEvent) -> Unit): AlertDialogButton {
        this.setEventListener<AlertDialogButton> {
            click = { e ->
                self.handler(e)
            }
        }
        return this
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun AlertDialog.alertDialogButton(
    content: String? = null,
    rich: Boolean = false,
    align: Align? = null,
    icon: String? = null,
    classes: Set<String>? = null,
    className: String? = null,
    init: (AlertDialogButton.() -> Unit)? = null
): AlertDialogButton {
    val alertDialogButton = AlertDialogButton(content, rich, align, icon, classes ?: className.set, init)
    this.footerPanel.add(alertDialogButton)
    return alertDialogButton
}
