package net.youthdev.vievie.core.framework.localization

enum class LangCode constructor(private val code: String) {
  VI("vi"),
  EN("en");

  fun code(): String {
    return code
  }
}
