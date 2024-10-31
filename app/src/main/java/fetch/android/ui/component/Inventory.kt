package fetch.android.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fetch.android.data.Item
import fetch.android.service.DataObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun Inventory () {
    var items by remember { mutableStateOf(listOf<Item>()) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    //Getting data from api
    LaunchedEffect(Unit) {
        DataObject.data.fetchItems().enqueue(object : Callback<List<Item>> {
            override fun onResponse(call: Call<List<Item>>, response: Response<List<Item>>) {
                if (response.isSuccessful) {

                    //filter out items that are null or blank and sort all items
                    items = response.body()
                        ?.filter { !it.name.isNullOrBlank() }
                        ?.sortedWith(compareBy({ it.listId }, { it.name })) ?: emptyList()
                } else {
                    errorMessage = "Error fetching data"
                }
            }

            override fun onFailure(call: Call<List<Item>>, t: Throwable) {
                errorMessage = "Network Error: ${t.message}"
            }
        })
    }

    if (errorMessage != null) {
        Text(
            text = errorMessage ?: "Unknown error",
            modifier = Modifier.padding(16.dp),
            color = MaterialTheme.colorScheme.onBackground)
    } else {
        val groupedItems = items.groupBy { it.listId }
        Column (
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            groupedItems.forEach { (listId, items) ->
                ItemList(listId, items)
            }
        }

    }
}