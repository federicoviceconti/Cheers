package open.vice.cheers.repositories.model

import open.vice.cheers.core.extension.toDate
import open.vice.cheers.network.model.BeerResponseItem
import java.time.LocalDate

class Beer(
    val id: Int,
    val name: String,
    val tagLine: String,
    val url: String,
    val description: String,
    val firstBrewed: LocalDate
) {
    constructor(responseItem: BeerResponseItem) : this(
        responseItem.id,
        responseItem.name,
        responseItem.tagLine,
        responseItem.url,
        responseItem.description,
        responseItem.firstBrewed.toDate()
    )
}