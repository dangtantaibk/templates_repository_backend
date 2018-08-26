package net.youthdev.vievie.core.bootstrap

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider
import io.swagger.jaxrs.config.BeanConfig
import io.swagger.jaxrs.listing.ApiListingResource
import io.swagger.jaxrs.listing.SwaggerSerializers
import net.youthdev.vievie.core.bootstrap.feature.ImmediateFeature
import net.youthdev.vievie.core.bootstrap.filter.RequestFilter
import net.youthdev.vievie.core.bootstrap.filter.ResponseFilter
import net.youthdev.vievie.core.bootstrap.mapper.ValidationExceptionMapper
import org.apache.commons.logging.LogFactory
import org.glassfish.jersey.message.GZipEncoder
import org.glassfish.jersey.server.ResourceConfig
import org.glassfish.jersey.server.filter.EncodingFilter

class AppConfig: ResourceConfig() {

  init {
    packages(true, SCAN_PACKAGE)
    register(ImmediateFeature::class.java)
    register(AppDependencyBinder())
    register(ValidationExceptionMapper::class.java)
    register(RequestFilter::class.java)
    register(ResponseFilter::class.java)

    // support gzip encoding
    EncodingFilter.enableFor(this, GZipEncoder::class.java)

    // custom object json mapper
    val jsonProvider = JacksonJaxbJsonProvider()
    val objectMapper = ObjectMapper()
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    jsonProvider.setMapper(objectMapper)

    register(jsonProvider)

    initSwagger()
  }

  private fun initSwagger() {
    try {
      register(ApiListingResource::class.java)
      register(SwaggerSerializers::class.java)

      val beanConfig = BeanConfig()
      beanConfig.version = "1.0.2"
      beanConfig.schemes = arrayOf("http")
      beanConfig.host = "localhost:10000"
      beanConfig.basePath = "/"
      beanConfig.resourcePackage = "net.youthdev.vievie.presentation"
      beanConfig.scan = true
    } catch (e: Throwable) {
      LOG.error("Error when initialize Swagger", e)
    }

  }

  companion object {
    private val LOG = LogFactory.getLog(AppConfig::class.java)
    const val SCAN_PACKAGE = "net.youthdev.vievie"
  }
}