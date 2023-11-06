package id.anantyan.foodapps.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import id.anantyan.foodapps.data.local.dao.UsersDao
import id.anantyan.foodapps.data.local.entities.UserEntity

@Database(
    entities = [
        UserEntity::class
    ], version = 1, exportSchema = false
)
abstract class RoomDB: RoomDatabase() {
    companion object {
        @Volatile
        private var INSTANCE: RoomDB? = null

        fun database(context: Context): RoomDB {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val db = Room.databaseBuilder(
                    context.applicationContext,
                    RoomDB::class.java,
                    "db_app"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = db
                return db
            }
        }
    }

    abstract fun usersDao(): UsersDao
}