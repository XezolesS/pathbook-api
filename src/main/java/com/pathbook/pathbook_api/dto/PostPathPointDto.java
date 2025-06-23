package com.pathbook.pathbook_api.dto;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;

import java.util.ArrayList;
import java.util.List;

public class PostPathPointDto {
    public Double latitude;
    public Double longitude;

    public PostPathPointDto() {}

    public PostPathPointDto(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public static LineString toLineString(PostPathDto path) {
        return toLineString(path.getPathPoints());
    }

    public static LineString toLineString(List<PostPathPointDto> points) {
        GeometryFactory geometryFactory = new GeometryFactory();
        Coordinate[] coordinates =
                points.stream()
                        .map(p -> new Coordinate(p.getLongitude(), p.getLatitude()))
                        .toArray(Coordinate[]::new);

        return geometryFactory.createLineString(coordinates);
    }

    public static List<PostPathPointDto> fromLineString(LineString lineString) {
        List<PostPathPointDto> points = new ArrayList<>();

        for (Coordinate coord : lineString.getCoordinates()) {
            // latitude = Y, longitude = X
            points.add(new PostPathPointDto(coord.getY(), coord.getX()));
        }

        return points;
    }
}
