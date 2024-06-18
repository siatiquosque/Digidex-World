package br.com.siatiquosque.digidex_shared.data.db

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.SQLiteDriver
import androidx.sqlite.driver.NativeSQLiteConnection
import androidx.sqlite.driver.NativeSQLiteDriver
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import androidx.sqlite.throwSQLiteException
import br.com.siatiquosque.digidex_shared.data.db.DigimonWorld1Database
import cnames.structs.sqlite3
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.allocPointerTo
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.value
import platform.Foundation.NSHomeDirectory
import sqlite3.SQLITE_OK
import sqlite3.SQLITE_OPEN_CREATE
import sqlite3.SQLITE_OPEN_READWRITE
import sqlite3.sqlite3_open_v2


actual fun getDatabaseBuilder(): RoomDatabase.Builder<DigimonWorld1Database> {
    val dbFilePath = NSHomeDirectory() + "/my_room.db"
    return Room.databaseBuilder<DigimonWorld1Database>(
        name = dbFilePath,
        factory = { DigimonWorld1Database::class.instantiateImpl() }
    )
        .setDriver(BundledSQLiteDriver())
}


class WMTesNativeSQLiteDriver : SQLiteDriver{
    @OptIn(ExperimentalForeignApi::class)
    override fun open(fileName: String): SQLiteConnection = memScoped {
        val dbPointer = allocPointerTo<sqlite3>()
        val resultCode = sqlite3_open_v2(
            filename = fileName,
            ppDb = dbPointer.ptr,
            flags = SQLITE_OPEN_READWRITE or SQLITE_OPEN_CREATE,
            zVfs = null
        )
        if (resultCode != SQLITE_OK) {
            throwSQLiteException(resultCode, null)
        }
        NativeSQLiteConnection(dbPointer.value!!)
    }

}