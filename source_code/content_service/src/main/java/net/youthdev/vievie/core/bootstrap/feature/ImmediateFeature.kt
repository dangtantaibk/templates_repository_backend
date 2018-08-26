package net.youthdev.vievie.core.bootstrap.feature

import org.glassfish.hk2.api.ServiceLocator
import org.glassfish.hk2.utilities.ServiceLocatorUtilities
import javax.inject.Inject
import javax.ws.rs.core.Feature
import javax.ws.rs.core.FeatureContext

class ImmediateFeature @Inject constructor(
    locator: ServiceLocator
) : Feature {

  init {
    ServiceLocatorUtilities.enableImmediateScope(locator)
  }

  override fun configure(featureContext: FeatureContext): Boolean {
    return true
  }
}