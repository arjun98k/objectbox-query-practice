package com.example.practiceobjectbox.data.local.entity

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class Note(
    @Id var id: Long = 0,
    var title: String,
    var content: String,

    var contentLength: Int = 0
)

