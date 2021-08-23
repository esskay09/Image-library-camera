package com.terranullius.task.framework.datasource.network.mappers

import com.terranullius.task.business.domain.model.Image
import com.terranullius.task.business.domain.util.EntityMapper
import com.terranullius.task.framework.datasource.network.model.ImageDto

/**
 * Class to map ImageDto to Image domain model and vice-versa
 * */

class NetworkMapper: EntityMapper<ImageDto, Image> {

    override fun mapFromEntity(entity: ImageDto): Image {

        return Image(
            id = entity.id,
            description = entity.description,
            imageUrl = entity.imageUrl,
            publishedDate = entity.publishedDate,
            title = entity.title
        )

    }

    override fun mapToEntity(domainModel: Image): ImageDto {
            return ImageDto(
                id = domainModel.id,
                description = domainModel.description,
                imageUrl = domainModel.imageUrl,
                publishedDate = domainModel.publishedDate,
                title = domainModel.title
            )
    }
}