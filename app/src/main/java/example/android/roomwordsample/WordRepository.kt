package example.android.roomwordsample

import android.arch.lifecycle.LiveData
import android.support.annotation.WorkerThread

// The repository has reference to DAO object and it gets data using this DAO object
class WordRepository(private val wordDao: WordDao) {
    val allWords: LiveData<List<Word>> = wordDao.getAllWords()

    /**
     * You must call this on a non-UI thread or your app will crash.
     * Room ensures that you don't do any long-running operations on the main thread, blocking the UI.
     * Add the @WorkerThread annotation, to mark that this method needs to be called from a non-UI thread.
     * Add the suspend modifier to tell the compiler that this needs to be called from a coroutine
     * or another suspending function.
     */
    @WorkerThread
    suspend fun insert(word: Word) {
        wordDao.insert(word)
    }
}
