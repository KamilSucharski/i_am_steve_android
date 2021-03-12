package com.iamsteve.data.mapper

import com.iamsteve.data.dto.ComicDTO
import com.iamsteve.domain.model.Comic
import com.iamsteve.domain.util.Mapper

class ComicMapper : Mapper<ComicDTO, Comic> {

    override fun ComicDTO.map() = Comic(
        number = number,
        title = title,
        date = date
    )
}