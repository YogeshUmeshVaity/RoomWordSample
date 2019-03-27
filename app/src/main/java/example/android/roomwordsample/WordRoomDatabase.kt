package example.android.roomwordsample

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

@Database(entities = [Word::class], version = 1)
abstract class WordRoomDatabase : RoomDatabase() {
    abstract fun wordDao(): WordDao

    companion object {
        @Volatile
        private var INSTANCE: WordRoomDatabase? = null

        // synchronized() is used to ensure that a shared resource(WordRoomDatabase in this case) is not
        // used concurrently by multiple threads.
        fun getDatabase(context: Context): WordRoomDatabase { return INSTANCE ?: synchronized(this) {
                return Room.databaseBuilder(
                    context.applicationContext,
                    WordRoomDatabase::class.java,
                    "Word_Database").build()
            }
        }
    }
}
