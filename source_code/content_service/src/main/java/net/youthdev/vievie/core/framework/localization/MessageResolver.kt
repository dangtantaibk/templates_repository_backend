package net.youthdev.vievie.core.framework.localization

import java.text.MessageFormat
import java.util.*

class MessageResolver {
  companion object {
    const val VIEVIE_LANGUAGE_TAG_VN = "vi-VN"
    const val VIEVIE_LANGUAGE_TAG_EN = "en-US"
    private const val MESSAGE_RESOURCE_BASE_NAME = "i18n.messages"

    fun getMessage(code: String, vararg args: Any): String {
      val locale = LocaleHolder.get()
      // it's cached by default
      val messageResource = ResourceBundle.getBundle(MESSAGE_RESOURCE_BASE_NAME, locale)
      val message = messageResource.getString(code)
      return if (args.isNotEmpty()) {
        MessageFormat.format(message, *args)
      } else message
    }

    fun getMessageWithLangTag(languageTag: String, code: String, vararg args: Any): String {
      val locale = Locale.forLanguageTag(languageTag)
      // it's cached by default
      val messageResource = ResourceBundle.getBundle(MESSAGE_RESOURCE_BASE_NAME, locale)
      val message = messageResource.getString(code)
      return if (args.isNotEmpty()) {
        MessageFormat.format(message, *args)
      } else message
    }

    fun getMessageInVn(code: String, vararg args: Any): String {
      return getMessageWithLangTag(VIEVIE_LANGUAGE_TAG_VN, code, *args)
    }

    fun getMessageInEn(code: String, vararg args: Any): String {
      return getMessageWithLangTag(VIEVIE_LANGUAGE_TAG_EN, code, *args)
    }
  }
}