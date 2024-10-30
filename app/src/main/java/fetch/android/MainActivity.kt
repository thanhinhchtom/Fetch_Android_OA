package fetch.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fetch.android.data.Item
import fetch.android.service.DataObject
import fetch.android.ui.theme.FetchTheme
import retrofit2.Callback
import retrofit2.Call
import retrofit2.Response

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                ItemList()
            }
        }
    }
}

@Composable
fun ItemList() {
    var items by remember { mutableStateOf(listOf<Item>()) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        DataObject.data.fetchItems().enqueue(object : Callback<List<Item>> {
            override fun onResponse(call: Call<List<Item>>, response: Response<List<Item>>) {
                if (response.isSuccessful) {
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
        Text(text = errorMessage ?: "Unknown error", modifier = Modifier.padding(16.dp))
    } else {
        ItemList(items)
    }
}

@Composable
fun ItemList(items: List<Item>) {
    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        val groupedItems = items.groupBy { it.listId }
        groupedItems.forEach { (listId, items) ->
            item {
                Text(
                    text = "List ID: $listId",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
            items(items) { item ->
                ItemRow(item = item)
            }
        }
    }
}

@Composable
fun ItemRow(item: Item) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = item.name ?: "Unnamed",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}