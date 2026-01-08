package org.example.project

import platform.MapKit.MKMapView
import platform.MapKit.MKMapViewDelegateProtocol
import platform.MapKit.MKOverlayProtocol
import platform.MapKit.MKOverlayRenderer
import platform.MapKit.MKTileOverlay
import platform.MapKit.MKTileOverlayRenderer
import platform.darwin.NSObject


class MapViewDelegate(): NSObject(), MKMapViewDelegateProtocol {

    override fun mapView(mapView: MKMapView, rendererForOverlay: MKOverlayProtocol): MKOverlayRenderer {
        return when (rendererForOverlay) {
            is MKTileOverlay -> MKTileOverlayRenderer(rendererForOverlay).apply { setAlpha(1.0) }
            else -> MKOverlayRenderer(rendererForOverlay).apply { setAlpha(0.0) }
        }
    }
}