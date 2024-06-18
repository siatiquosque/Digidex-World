package br.com.siatiquosque.digidex_shared.data.db

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import org.koin.mp.KoinPlatform.getKoin


actual fun getDatabaseBuilder(): RoomDatabase.Builder<DigimonWorld1Database> {
    val context: Application = getKoin().get()
    val dbFile = context.getDatabasePath("dw1.db")
    return Room.databaseBuilder<DigimonWorld1Database>(
            context = context,
            name = dbFile.absolutePath,
        )
        .createFromAsset("Digimon_World_1.db")

}