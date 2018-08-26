package net.youthdev.vievie.core.framework.localization

import org.apache.commons.lang3.ArrayUtils

import java.util.Arrays

class MessageHolder {
  private var type: Type? = null
  private lateinit var code: String
  private lateinit var args: Array<out Any>
  private var referenceMessageHolders: Array<out MessageHolder>? = null
  private var content: String? = null
  private var contentInVi: String? = null

  constructor(code: String, vararg args: Any) {
    this.type = Type.CODE
    this.code = code
    this.args = args
  }

  fun with(vararg referenceMessageHolders: MessageHolder): MessageHolder {
    this.referenceMessageHolders = referenceMessageHolders
    return this
  }

  constructor() {
    // nothing to do
  }

  fun asRaw(content: String, contentInVi: String): MessageHolder {
    this.type = Type.RAW
    this.content = content
    this.contentInVi = contentInVi
    return this
  }

  fun asRaw(content: String): MessageHolder {
    this.type = Type.RAW
    this.content = content
    this.contentInVi = content
    return this
  }

  fun toMessage(languageTag: String): String? {
    return when (type) {
      MessageHolder.Type.CODE -> if (referenceMessageHolders == null) {
        MessageResolver.getMessageWithLangTag(languageTag, this.code, this.args)
      } else {
        MessageResolver.getMessageWithLangTag(
            languageTag,
            this.code,
            ArrayUtils.addAll(
                this.args,
                Arrays.stream(this.referenceMessageHolders!!)
                    .map { it.toMessage(languageTag) }
                    .toArray()))
      }

      MessageHolder.Type.RAW -> if (languageTag.contains(LangCode.EN.code())) content else contentInVi

      else -> throw UnsupportedOperationException()
    }
  }

  private enum class Type {
    CODE, RAW
  }

}
