package fetch.android.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fetch.android.data.Item
import kotlin.math.roundToInt

@Composable
fun ItemList(listId: Int, allItemsInList: List<Item>) {
    BoxWithConstraints (
        modifier = Modifier.fillMaxWidth()
    ) {
        //Get maximum width for responsive
        val density = LocalDensity.current
        val totalWidth = with(density) { constraints.maxWidth.toDp() }
        val numCol: Int = (totalWidth / (130.dp)).roundToInt()
        var show by remember { mutableStateOf(false) }

        //Grid for each list id
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    RoundedCornerShape(16.dp)
                )

        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                ),
                onClick = { show = !show }) {
                Text(
                    text = "List $listId",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    ))
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = if (show) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = if (show) "Hide List" else "Show List",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }

            if (show) {
                allItemsInList.chunked(numCol).forEach { rowItems ->
                    Row (
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                    ){
                        rowItems.forEach { item ->
                            Column (
                                modifier = Modifier
                                    .width(totalWidth / numCol)
                                    .padding(horizontal = 4.dp)
                            ) {
                                ItemCard(item = item)
                            }
                        }
                    }
                }
            }
        }
    }


    //all items will be displayed as a grid
//    LazyVerticalGrid(
//        modifier = Modifier.fillMaxWidth(),
//        contentPadding = PaddingValues(8.dp),
//        horizontalArrangement = Arrangement.spacedBy(8.dp),
//        verticalArrangement = Arrangement.spacedBy(8.dp),
//        columns = GridCells.Adaptive(minSize = 100.dp)
//    ) {
//        items(allItemsInList) { item ->
//            Column {
//                ItemCard(item = item)
//            }
//        }
//    }
//    Spacer(modifier = Modifier.height(16.dp)) // Spacer below each grid
}