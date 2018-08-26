package net.youthdev.vievie.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import org.joda.time.DateTime
import javax.persistence.PrePersist
import javax.persistence.PreUpdate

class DataRecord {
  private var created: Long = 0

  private var lastModified: Long = 0

  private var deletedOn: Long? = null

  @JsonIgnore
  private var version: Int = 0

  @PrePersist
  private fun onPrePersist() {
    val currentTimestamp = DateTime.now().millis
    created = currentTimestamp
    lastModified = currentTimestamp
    this.version = 0
  }

  @PreUpdate
  private fun onPreUpdate() {
    lastModified = DateTime.now().millis
  }
}