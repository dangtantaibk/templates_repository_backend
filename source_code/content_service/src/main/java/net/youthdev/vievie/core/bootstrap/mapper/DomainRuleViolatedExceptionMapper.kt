package net.youthdev.vievie.core.bootstrap.mapper

import net.youthdev.vievie.core.exception.DomainRuleViolatedException
import net.youthdev.vievie.core.exception.RestErrorModel
import net.youthdev.vievie.core.framework.rest.ResponseTemplate
import org.apache.commons.lang3.StringUtils
import org.apache.commons.lang3.exception.ExceptionUtils
import org.apache.commons.logging.LogFactory
import javax.ws.rs.core.Response
import javax.ws.rs.ext.ExceptionMapper
import javax.ws.rs.ext.Provider

@Provider
class DomainRuleViolatedExceptionMapper: ExceptionMapper<DomainRuleViolatedException> {
  override fun toResponse(ex: DomainRuleViolatedException): Response {
    LOG.error("Error: ${ex.message}, stack trace:%n${ExceptionUtils.getStackTrace(ex).replace("`", StringUtils.EMPTY)}")
    return ResponseTemplate.badRequest(RestErrorModel("DOMAIN_RULE_VIOLATED", ex.message, StringUtils.EMPTY, StringUtils.EMPTY))
  }

  companion object {
    private val LOG = LogFactory.getLog(DomainRuleViolatedExceptionMapper::class.java)
  }
}