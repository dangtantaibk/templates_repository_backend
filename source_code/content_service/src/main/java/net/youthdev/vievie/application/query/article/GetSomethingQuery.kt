package net.youthdev.vievie.application.query.article

import net.youthdev.vievie.core.framework.bus.query.BaseQuery
import net.youthdev.vievie.model.repsonse.article.SomethingDTO

class GetSomethingQuery(
    val somethingId: Int
): BaseQuery<SomethingDTO>()