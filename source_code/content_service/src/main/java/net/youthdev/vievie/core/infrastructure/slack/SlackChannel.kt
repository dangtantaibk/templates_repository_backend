package net.youthdev.vievie.core.infrastructure.slack

import org.jvnet.hk2.annotations.Contract

@Contract
interface SlackChannel {
  fun pushToChannelAsync(channelWebhookUrl: String, content: String)

  fun pushToChannelAsync(channelWebhookUrl: String, title: String?, content: String)
}