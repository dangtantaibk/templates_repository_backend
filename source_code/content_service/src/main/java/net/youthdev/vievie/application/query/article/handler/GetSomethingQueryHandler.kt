package net.youthdev.vievie.application.query.article.handler

import com.github.javafaker.Faker
import net.youthdev.vievie.application.query.article.GetSomethingQuery
import net.youthdev.vievie.core.framework.bus.query.QueryHandler
import net.youthdev.vievie.core.infrastructure.persistence.UnitOfWork
import net.youthdev.vievie.model.repsonse.article.SomethingDTO
import org.jvnet.hk2.annotations.Service
import javax.inject.Inject
import javax.inject.Provider

@Service
class GetSomethingQueryHandler @Inject constructor(
    private val unitOfWorkFactory: Provider<UnitOfWork>
): QueryHandler<SomethingDTO, GetSomethingQuery> {

  override fun handle(query: GetSomethingQuery): SomethingDTO {
    unitOfWorkFactory.get().use { uow ->
      return SomethingDTO().apply {
        id = query.somethingId
        data = Faker.instance().code().isbn10()
      }
    }
  }
}