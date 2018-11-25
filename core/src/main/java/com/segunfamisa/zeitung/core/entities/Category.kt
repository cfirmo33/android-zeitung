package com.segunfamisa.zeitung.core.entities

sealed class Category(val category: String) {
    class Business : Category(category = "business")
    class Entertainment : Category(category = "entertainment")
    class General : Category(category = "general")
    class Health : Category(category = "health")
    class Science : Category(category = "science")
    class Sports : Category(category = "sports")
    class Technology : Category(category = "technology")
}
