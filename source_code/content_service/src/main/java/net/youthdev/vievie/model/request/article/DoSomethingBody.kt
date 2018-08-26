package net.youthdev.vievie.model.request.article

import javax.validation.constraints.NotNull

class DoSomethingBody {
  @NotNull(message = "Message cannot be null or empty")
  var message: String? = null
}