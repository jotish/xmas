package com.christagram.app.ui.maps;

import android.content.Context;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.util.AttributeSet;

import com.christagram.app.R;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

public class PulseOverlayLayout extends MapOverlayLayout {
    private static final int ANIMATION_DELAY_FACTOR = 100;

    private PulseMarkerView startMarker, finishMarker;
    private int scaleAnimationDelay = 100;

    public PulseOverlayLayout(final Context context) {
        this(context, null);
    }

    public PulseOverlayLayout(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.pulse_wrapper_layout, this);
    }

    public void setupMarkers(final Point point, final LatLng latLng) {
        startMarker = new PulseMarkerView(getContext(), latLng, point);
        finishMarker = new PulseMarkerView(getContext(), latLng, point);
    }

    public void removeStartMarker() {
        removeMarker(startMarker);
    }

    public void removeFinishMarker() {
        removeMarker(finishMarker);
    }

    public void addStartMarker(final LatLng latLng) {
        startMarker = createPulseMarkerView(latLng);
        startMarker.updatePulseViewLayoutParams(googleMap.getProjection().toScreenLocation(latLng));
        addMarker(startMarker);
        startMarker.show();
    }

    public void addFinishMarker(final LatLng latLng) {
        finishMarker = createPulseMarkerView(latLng);
        finishMarker.updatePulseViewLayoutParams(googleMap.getProjection().toScreenLocation(latLng));
        addMarker(finishMarker);
        finishMarker.show();
    }

    @NonNull
    private PulseMarkerView createPulseMarkerView(final LatLng latLng) {
        return new PulseMarkerView(getContext(), latLng, googleMap.getProjection().toScreenLocation(latLng));
    }

    @NonNull
    private PulseMarkerView createPulseMarkerView(final int position, final Point point, final LatLng latLng) {
        PulseMarkerView pulseMarkerView = new PulseMarkerView(getContext(), latLng, point, position);
        addMarker(pulseMarkerView);
        return pulseMarkerView;
    }

    public void createAndShowMarker(final int position, final LatLng latLng) {
        PulseMarkerView marker = createPulseMarkerView(position, googleMap.getProjection().toScreenLocation(latLng), latLng);
        marker.showWithDelay(scaleAnimationDelay);
        scaleAnimationDelay += ANIMATION_DELAY_FACTOR;
    }

    public void showMarker(final int position) {
        ((PulseMarkerView)markersList.get(position)).pulse();
    }

    public void drawStartAndFinishMarker() {
        addStartMarker((LatLng)polylines.get(0));
        addFinishMarker((LatLng)polylines.get(polylines.size() - 1));
        setOnCameraIdleListener(null);
    }

    public void onBackPressed(final LatLngBounds latLngBounds) {
        moveCamera(latLngBounds);
        removeStartAndFinishMarkers();
        removeCurrentPolyline();
        showAllMarkers();
        refresh();
    }

    private void removeStartAndFinishMarkers() {
        removeStartMarker();
        removeFinishMarker();
    }
}
