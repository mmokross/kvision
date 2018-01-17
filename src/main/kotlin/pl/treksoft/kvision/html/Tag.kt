package pl.treksoft.kvision.html

import com.github.snabbdom.VNode
import pl.treksoft.kvision.core.KVManager
import pl.treksoft.kvision.panel.SimplePanel
import pl.treksoft.kvision.snabbdom.StringBoolPair

@Suppress("EnumNaming")
enum class TAG(val tagName: String) {
    H1("h1"),
    H2("h2"),
    H3("h3"),
    H4("h4"),
    H5("h5"),
    H6("h6"),
    P("p"),
    ABBR("abbr"),
    ADDRESS("address"),
    BLOCKQUOTE("blockquote"),
    FOOTER("footer"),
    PRE("pre"),
    UL("ul"),
    OL("ol"),
    DIV("div"),
    LABEL("label"),

    MARK("mark"),
    DEL("del"),
    S("s"),
    INS("ins"),
    U("u"),
    SMALL("small"),
    STRONG("strong"),
    EM("em"),
    CITE("cite"),
    CODE("code"),
    KBD("kbd"),
    VAR("var"),
    SAMP("samp"),
    SPAN("span"),
    LI("li")
}

enum class ALIGN(val className: String) {
    LEFT("text-left"),
    CENTER("text-center"),
    RIGHT("text-right"),
    JUSTIFY("text-justify"),
    NOWRAP("text-nowrap")
}

open class Tag(
    type: TAG, text: String? = null, rich: Boolean = false, align: ALIGN? = null,
    classes: Set<String> = setOf()
) : SimplePanel(classes) {
    var type = type
        set(value) {
            field = value
            refresh()
        }
    var text = text
        set(value) {
            field = value
            refresh()
        }
    var rich = rich
        set(value) {
            field = value
            refresh()
        }
    var align = align
        set(value) {
            field = value
            refresh()
        }

    override fun render(): VNode {
        return if (text != null) {
            if (rich) {
                kvh(type.tagName, arrayOf(KVManager.virtualize("<span>$text</span>")) + childrenVNodes())
            } else {
                kvh(type.tagName, arrayOf(text) + childrenVNodes())
            }
        } else {
            kvh(type.tagName, childrenVNodes())
        }
    }

    override fun getSnClass(): List<StringBoolPair> {
        val cl = super.getSnClass().toMutableList()
        align?.let {
            cl.add(it.className to true)
        }
        return cl
    }
}
