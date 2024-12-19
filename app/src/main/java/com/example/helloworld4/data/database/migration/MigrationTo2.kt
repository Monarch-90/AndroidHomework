package com.example.helloworld4.data.database.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            """
    CREATE TABLE IF NOT EXISTS `notes` (
    `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    `userId` INTEGER NOT NULL,
    `title` TEXT NOT NULL,
    `text` TEXT NOT NULL,
    `date` TEXT NOT NULL,
    `imageUri` TEXT
    )
""".trimIndent()
        )
    }
}