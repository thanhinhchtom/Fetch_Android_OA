package fetch.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.core.view.WindowInsetsCompat
import fetch.android.ui.component.Inventory
import fetch.android.ui.theme.FetchTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FetchTheme {
                val view = LocalView.current;
                val insets = WindowInsetsCompat.toWindowInsetsCompat(view.rootWindowInsets)
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background),
                ) {
                    BoxWithConstraints(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 20.dp)
                    ) {
                        val density = LocalDensity.current
                        // Convert the maxWidth from pixels to dp
                        val totalWidth = with(density) { constraints.maxWidth.toDp() }

                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .padding(
                                    top = with(LocalDensity.current) {
                                        insets.getInsets(
                                            WindowInsetsCompat.Type.statusBars()
                                        ).top.toDp()
                                    },
                                    bottom = with(LocalDensity.current) {
                                        insets.getInsets(
                                            WindowInsetsCompat.Type.navigationBars()
                                        ).bottom.toDp()
                                    },
                                    start = 0.03 * totalWidth,
                                    end = 0.03 * totalWidth
                                )
                        ) {
                            Inventory()
                        }
                    }
                }
            }
        }
    }
}
