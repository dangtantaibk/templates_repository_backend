package net.youthdev.vievie.core.infrastructure.slack.impl

import com.squareup.okhttp.MediaType
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import com.squareup.okhttp.RequestBody
import net.youthdev.vievie.core.infrastructure.slack.SlackChannel
import net.youthdev.vievie.core.utils.JsonUtils
import org.apache.commons.lang3.StringUtils
import org.apache.commons.logging.LogFactory
import java.util.ArrayList
import java.util.concurrent.Executors

class SlackChannelImpl: SlackChannel {
  private val executorService = Executors.newFixedThreadPool(1)
  private val httpClient = ThreadLocal<OkHttpClient>()

  override fun pushToChannelAsync(channelWebhookUrl: String, content: String) {
    this.pushToChannelAsync(channelWebhookUrl, null, content)
  }

  override fun pushToChannelAsync(channelWebhookUrl: String, title: String?, content: String) {
    if (StringUtils.isEmpty(channelWebhookUrl)) return
    executorService.submit {
      try {
        if (httpClient.get() == null) {
          httpClient.set(OkHttpClient())
        }
        val requestMediaType = MediaType.parse("application/json; charset=utf-8")
        val body = RequestBody.create(requestMediaType, JsonUtils.toJson(SlackWebHookBody(title, content)))
        val request = Request.Builder().url(channelWebhookUrl)
            .post(body)
            .build()

        val response = httpClient.get().newCall(request).execute()
        response.body().close()
      } catch (ex: Exception) {
        LOG.error("Error when pushing to slack", ex)
      }
    }
  }

  class SlackWebHookBody {
    private var attachments: MutableList<SlackWebHookAttachment>? = null

    constructor(title: String, pretext: String, text: String) {
      this.attachments = ArrayList()
      this.attachments!!.add(SlackWebHookAttachment(title, pretext, text))
    }

    constructor(title: String?, text: String) {
      this.attachments = ArrayList()
      this.attachments!!.add(SlackWebHookAttachment(title, text))
    }

    constructor(text: String) {
      this.attachments = ArrayList()
      this.attachments!!.add(SlackWebHookAttachment(text))
    }

    private inner class SlackWebHookAttachment {
      var title: String? = null
      var pretext: String? = null
      var text: String? = null

      constructor(title: String, pretext: String, text: String) {
        this.title = title
        this.pretext = pretext
        this.text = text
      }

      constructor(title: String?, text: String) {
        this.title = title
        this.text = text
      }

      constructor(text: String) {
        this.text = text
      }
    }
  }

  companion object {
    private val LOG = LogFactory.getLog(SlackChannelImpl::class.java)
  }
}