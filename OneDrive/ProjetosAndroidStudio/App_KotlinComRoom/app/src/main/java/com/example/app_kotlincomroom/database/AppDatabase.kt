package com.example.app_kotlincomroom.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.app_kotlincomroom.model.Produto
import com.example.app_kotlincomroom.database.dao.ProdutoDao
import com.example.app_kotlincomroom.database.dao.UsuarioDao
import com.example.app_kotlincomroom.database.migrations.MIGRATION_1_2
import com.example.app_kotlincomroom.database.migrations.MIGRATION_2_3
import com.example.app_kotlincomroom.model.Usuario

@Database(entities = [Produto::class, Usuario::class], version = 3, exportSchema = true)

@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun produtoDao(): ProdutoDao
    abstract fun usuarioDao(): UsuarioDao

    companion object {
        fun instacia(context: Context): AppDatabase {

            return Room.databaseBuilder(
                context, AppDatabase::class.java, "app_kotlin-com-room.db"
            ).allowMainThreadQueries().addMigrations(MIGRATION_1_2, MIGRATION_2_3 ).build()

        }
    }

}