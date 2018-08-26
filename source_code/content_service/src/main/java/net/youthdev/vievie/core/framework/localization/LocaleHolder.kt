package net.youthdev.vievie.core.framework.localization

import java.util.Locale

object LocaleHolder {
  private const val DEFAULT_VIEVIE_LANGUAGE_TAG = "en-US"

  private val LOCALE_HOLDER = ThreadLocal<Locale>()

  fun set(locale: Locale) {
    LOCALE_HOLDER.set(locale)
  }

  fun get(): Locale {
    var locale: Locale? = LOCALE_HOLDER.get()
    if (locale == null) {
      locale = Locale(DEFAULT_VIEVIE_LANGUAGE_TAG)
    }
    return locale
  }

  fun remove() {
    LOCALE_HOLDER.remove()
  }

}