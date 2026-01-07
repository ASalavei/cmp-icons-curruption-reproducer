@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalForeignApi::class,
    ExperimentalComposeUiApi::class
)

package org.example.project

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.UIKitView
import androidx.compose.ui.window.ComposeUIViewController
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.bell
import kotlinproject.composeapp.generated.resources.check
import kotlinproject.composeapp.generated.resources.heart
import kotlinproject.composeapp.generated.resources.mail
import kotlinproject.composeapp.generated.resources.star
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.readValue
import org.jetbrains.compose.resources.painterResource
import platform.CoreGraphics.CGRectZero
import platform.CoreLocation.CLLocationCoordinate2DMake
import platform.Foundation.NSNotificationCenter
import platform.Foundation.NSOperationQueue
import platform.MapKit.MKMapView
import platform.MapKit.MKPointAnnotation
import platform.MapKit.MKUserLocation
import platform.MapKit.overlays
import platform.MapKit.removeOverlays
import platform.UIKit.UIApplicationDidEnterBackgroundNotification
import kotlin.random.Random

@Composable
fun Content() {
    var mapView: MKMapView? by remember { mutableStateOf(null) }
    var isNewLaunch by remember { mutableStateOf(true) }

    DisposableEffect(mapView) {
        val observer = NSNotificationCenter.defaultCenter.addObserverForName(
            name = UIApplicationDidEnterBackgroundNotification,
            `object` = null,
            queue = NSOperationQueue.mainQueue
        ) { _ ->
            mapView?.clearAllContent()
        }
        onDispose {
            NSNotificationCenter.defaultCenter.removeObserver(observer)
        }
    }

    Column(Modifier.fillMaxSize().statusBarsPadding()) {
        if (isNewLaunch) {
            Text("Application just started!")
        }
        Text("Material Icons", modifier = Modifier.padding(horizontal = 8.dp), fontSize = 12.sp)
        LazyRow(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val icons = listOf(
                Icons.Filled.Home,
                Icons.Filled.Settings,
                Icons.Filled.Favorite,
                Icons.Filled.Add,
                Icons.Filled.Delete,
                Icons.Filled.Edit,
                Icons.Filled.Search,
                Icons.Filled.Menu,
                Icons.Filled.Info,
                Icons.Filled.Check,
                Icons.Filled.Close,
                Icons.Filled.Share,
                Icons.Filled.Star
            )
            items(icons) { icon ->
                Icon(imageVector = icon, contentDescription = null, tint = Color.Gray)
            }
        }
        
        Text("Custom SVG Icons", modifier = Modifier.padding(horizontal = 8.dp), fontSize = 12.sp)
        LazyRow(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val svgIcons = listOf(
                Res.drawable.heart,
                Res.drawable.bell,
                Res.drawable.check,
                Res.drawable.star,
                Res.drawable.mail
            )
            items(svgIcons) { iconRes ->
                Image(
                    painter = painterResource(iconRes),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(Color.Blue)
                )
            }
        }

        Row(Modifier.fillMaxWidth()) {
            Button(onClick = {
                isNewLaunch = false
                val lat = 48.1351 + (Random.nextDouble() - 0.5) * 0.1
                val lon = 11.5820 + (Random.nextDouble() - 0.5) * 0.1
                val annotation = MKPointAnnotation().apply {
                    setCoordinate(CLLocationCoordinate2DMake(lat, lon))
                    setTitle("Munich Pin")
                }
                mapView?.addAnnotation(annotation)
            }, modifier = Modifier.weight(1f).padding(4.dp)) {
                Text("Add annotation")
            }
            Button(onClick = {
                isNewLaunch = false
                mapView?.clearAllContent()
            }, modifier = Modifier.weight(1f).padding(4.dp)) {
                Text("Clear all annotations")
            }
        }
        UIKitView(
            factory = {
                MKMapView(CGRectZero.readValue()).also {
                    mapView = it
                }
            },
            modifier = Modifier.weight(1f).fillMaxWidth(),
            update = {}
        )
    }
}

private fun MKMapView.clearAllContent() {
    removeAnnotations(annotations().filter { it !is MKUserLocation })
    removeOverlays(overlays())
}

fun MainViewController() = ComposeUIViewController {
    Content()
}
