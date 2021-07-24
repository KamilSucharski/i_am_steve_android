package com.iamsteve.data.mapper

import com.iamsteve.data.dto.ComicDto
import com.iamsteve.domain.model.Comic
import com.iamsteve.domain.util.abstraction.Mapper

class ComicMapper : Mapper<ComicDto, Comic> {

    override fun ComicDto.map() = Comic(
        number = number,
        title = title,
        date = date
    )
}