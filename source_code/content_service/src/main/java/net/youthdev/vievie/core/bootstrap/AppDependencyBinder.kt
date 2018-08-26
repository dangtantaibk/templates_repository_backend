package net.youthdev.vievie.core.bootstrap

import org.apache.commons.logging.LogFactory
import org.glassfish.hk2.api.Immediate
import org.glassfish.hk2.utilities.binding.AbstractBinder
import org.glassfish.jersey.process.internal.RequestScoped
import org.jvnet.hk2.annotations.Contract
import org.jvnet.hk2.annotations.Service
import org.reflections.Reflections
import org.reflections.scanners.SubTypesScanner
import javax.inject.Named
import javax.inject.Singleton
import javax.persistence.EntityManagerFactory
import javax.persistence.Persistence

class AppDependencyBinder: AbstractBinder() {

  override fun configure() {
    try {
      bind(buildEntityManagerFactory()).to(EntityManagerFactory::class.java)

      scanAndBind(AppConfig.SCAN_PACKAGE)
    } catch (ex: Exception) {
      LOG.error("Error while setting up dependencies", ex)
      System.exit(1)
    }
  }

  private fun buildEntityManagerFactory(): EntityManagerFactory {
    val env = System.getenv()
    val overriddenConfigs = HashMap<String, String>()

    if (env.containsKey("VIEVIE_DATABASE_HOST") && env.containsKey("VIEVIE_DATABASE_PORT") && env.containsKey("VIEVIE_DATABASE_NAME")) {
      overriddenConfigs["hibernate.connection.url"] =
          "jdbc:mysql://${env["VIEVIE_DATABASE_HOST"]}:${env["VIEVIE_DATABASE_PORT"]}/${env["VIEVIE_DATABASE_NAME"]}" +
          "?useUnicode=true&charSet=utf8mb4&serverTimezone=UTC&autoReconnect=true&failOverReadOnly=false&maxReconnects=10"
    }
    if (env.containsKey("VIEVIE_DATABASE_USER")) {
      overriddenConfigs["hibernate.connection.username"] = env["VIEVIE_DATABASE_USER"]!!
    }
    if (env.containsKey("VIEVIE_DATABASE_PASSWORD")) {
      overriddenConfigs["hibernate.connection.password"] = env["VIEVIE_DATABASE_PASSWORD"]!!
    }
    if (env.containsKey("VIEVIE_DATABASE_GENERATE_STATISTICS")) {
      overriddenConfigs["hibernate.generate_statistics"] = env["VIEVIE_DATABASE_GENERATE_STATISTICS"]!!
    }

    return Persistence.createEntityManagerFactory("HibernatePersistenceUnit", overriddenConfigs)
  }

  private fun scanAndBind(packageName: String) {
    LOG.info("Scanning for binding injection...")
    val reflections = Reflections(packageName, SubTypesScanner(false))

    for (clazz in reflections.getSubTypesOf(Any::class.java)) {
      val serviceAnnotation = clazz.getAnnotation(Service::class.java)

      if (serviceAnnotation != null) {
        this.handleBinding(clazz, clazz, clazz.getAnnotation(Named::class.java))
      } else {
        val contractAnnotation = clazz.getAnnotation(Contract::class.java)
        if (contractAnnotation != null) {
          reflections.getSubTypesOf(clazz).forEach { subClazz -> this.handleBinding(subClazz, clazz, subClazz.getAnnotation(Named::class.java)) }
        }
      }
    }
  }

  private fun handleBinding(service: Class<out Any>, type: Class<*>, nameAnnotation: Named?) {
    val bindingBuilder = bind(service).to(type).named(nameAnnotation?.value)

    if (service.isAnnotationPresent(Singleton::class.java)) {
      bindingBuilder.`in`(Singleton::class.java)
    } else if (service.isAnnotationPresent(Immediate::class.java)) {
      bindingBuilder.`in`(Immediate::class.java)
    } else if (service.isAnnotationPresent(RequestScoped::class.java)) {
      bindingBuilder.`in`(RequestScoped::class.java)
    }
  }

  companion object {
    private val LOG = LogFactory.getLog(AppDependencyBinder::class.java)
  }
}