package example.android.roomwordsample

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.Main
import kotlin.coroutines.experimental.CoroutineContext

class WordViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: WordRepository
    private val allWords: LiveData<List<Word>>

    init {
        val wordsDao: WordDao = WordRoomDatabase.getDatabase(application).wordDao()
        repository = WordRepository(wordsDao)
        allWords = repository.allWords
    }

    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    /**
     * onCleared is called when the ViewModel is no longer used and will be destroyed so,
     * now is the time to cancel any long running jobs done by the parentJob.
     */
    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }

    fun insert(word: Word) = scope.launch(Dispatchers.IO) {
        repository.insert(word)
    }
}
