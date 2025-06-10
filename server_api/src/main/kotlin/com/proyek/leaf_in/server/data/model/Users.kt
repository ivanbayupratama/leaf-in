package com.proyek.leaf_in.server.data.model

import org.jetbrains.exposed.sql.Table

object Users : Table() {
    val id = varchar("id", 50)
    val fullName = varchar("full_name", 255)
    val email = varchar("email", 255)
    val password = varchar("password", 255)

    override val primaryKey = PrimaryKey(id)
}