package fetch.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Brightness4
import androidx.compose.material.icons.filled.Brightness5
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
            val curTheme = isSystemInDarkTheme()
            var darkTheme: Boolean by remember { mutableStateOf(curTheme) }
            FetchTheme (
                darkTheme = darkTheme
            ) {
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
                            Column {
                                Row (
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End, // Aligns item to the end (right side)
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    IconButton(
                                        onClick = { darkTheme = !darkTheme },
                                        colors = IconButtonDefaults.iconButtonColors(contentColor = MaterialTheme.colorScheme.primary)
                                    ) {
                                        Icon(
                                            imageVector = if (darkTheme) Icons.Default.Brightness5 else Icons.Default.Brightness4,
                                            contentDescription = if (darkTheme) "Switch to Light Theme" else "Switch to Dark Theme"
                                        )
                                    }
                                }
                                Inventory()
                            }
                        }
                    }
                }
            }
        }
    }
}
