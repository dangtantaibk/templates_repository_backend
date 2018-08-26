package net.youthdev.vievie.core.bootstrap

class Main {
  companion object {
    private const val APP_NAME = "Vievie Content Service"
    private const val BASE_URI = "http://0.0.0.0:10000/"

    @JvmStatic
    fun main(vararg args: String) {
      val app = App(APP_NAME)
      app.run(BASE_URI)
    }
  }
}