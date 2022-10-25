package xyz.artrinix.twine

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import xyz.artrinix.twine.auth.Authenticate
import xyz.artrinix.twine.ui.theme.AppTheme
import java.awt.Dimension
import kotlin.system.exitProcess

@Composable
fun InternalNavigationRail(
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.surface,
    contentColor: Color = contentColorFor(containerColor),
    header: @Composable (ColumnScope.() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    Surface(
        color = containerColor,
        contentColor = contentColor,
        modifier = modifier,
    ) {
        Column(
            Modifier.fillMaxHeight()
                .padding(vertical = 4.dp)
                .selectableGroup(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            if (header != null) {
                header()
                Spacer(Modifier.height(8.dp))
            }
            Column(
                Modifier.fillMaxHeight()
                    .width(80.0.dp)
                    .padding(vertical = 4.dp)
                    .selectableGroup(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                content()
            }
        }
    }
}

@Composable
@Preview
fun App() {
    AppTheme {
        var selectedGroup by remember { mutableStateOf(0) }
        var selectedItem by remember { mutableStateOf(0) }

        val itemsPrimary = listOf(
            "Home" to Icons.Filled.Home,
            "Search" to Icons.Filled.Search,
            "Instances" to Icons.Filled.AllInbox,
        )
        val itemsSecondary = listOf(
            "Changelog" to Icons.Filled.Article,
            "Settings" to Icons.Filled.Settings
        )

        InternalNavigationRail(
            header = {
                FloatingActionButton(onClick = {
                    val authToken = Authenticate.authenticateToMinecraft()
                    Authenticate.funni(authToken)
                }) {
                    Icon(Icons.Filled.Add, "Create instance")
                }
            },
        ) {
            itemsPrimary.forEachIndexed { index, item ->
                NavigationRailItem(
                    alwaysShowLabel = false,
                    icon = { Icon(item.second, contentDescription = item.first) },
                    label = { Text(item.first) },
                    selected = selectedItem == index && selectedGroup == 0,
                    onClick = { selectedItem = index; selectedGroup = 0 }
                )
            }

            Divider(
                color = MaterialTheme.colorScheme.outline,
                thickness = 1.dp,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp),
            )

            itemsSecondary.forEachIndexed { index, item ->
                NavigationRailItem(
                    alwaysShowLabel = false,
                    icon = { Icon(item.second, contentDescription = item.first) },
                    label = { Text(item.first) },
                    selected = selectedItem == index && selectedGroup == 1,
                    onClick = { selectedItem = index; selectedGroup = 1 }
                )
            }
        }


    }
}

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        state = WindowState(size = DpSize(1280.dp, 768.dp))
    ) {
        window.minimumSize = Dimension(800, 600)
        App()
    }
}
