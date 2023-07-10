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
        // Create an instance of the repository
        val repository = SetupRepository()

        // Call the suspend function to fetch setup data
        repository.fetchSetupFromFirestore()

        // Delay to allow the data to be processed
        delay(5000) // Adjust the delay time if needed

        // Assert that the fetched setup list is not null and not empty
        val fetchedSetupList = repository.listSetup.value
        assertNotNull(fetchedSetupList)
        assertTrue(fetchedSetupList!!.isNotEmpty())
    }
}

