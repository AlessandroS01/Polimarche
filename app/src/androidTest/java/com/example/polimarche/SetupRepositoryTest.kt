import androidx.lifecycle.LiveData
import androidx.test.runner.AndroidJUnit4
import com.example.polimarche.data_container.setup.DataSetup
import com.example.polimarche.data_container.setup.SetupRepository
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class SetupRepositoryTest {

    @Test
    fun testFetch() = runBlocking {
        // Creare un'istanza di SetupRepository
        val repository = SetupRepository()

        // Chiama la funzione suspend per recuperare i dati dei setup
        repository.fetchSetupFromFirestore()

        // Delay per consentire il processamento dei dati
        delay(5000)

        // Verificare che l'elenco dei setup recuperato non è nullo e non è vuoto
        val fetchedSetupList = repository.listSetup.value
        assertNotNull(fetchedSetupList)
        assertTrue(fetchedSetupList!!.isNotEmpty())
    }
}

