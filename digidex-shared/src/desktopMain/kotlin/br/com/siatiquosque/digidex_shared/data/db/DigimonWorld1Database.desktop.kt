package br.com.siatiquosque.digidex_shared.data.db

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import java.io.File

actual fun getDatabaseBuilder(): RoomDatabase.Builder<DigimonWorld1Database> {
    val dbFile = File(System.getProperty("java.io.tmpdir"), "my_room.db")
    return Room.databaseBuilder<DigimonWorld1Database>(
        name = dbFile.absolutePath,
    )
        .setDriver(BundledSQLiteDriver())
}