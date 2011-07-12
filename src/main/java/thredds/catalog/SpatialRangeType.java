package thredds.catalog;

/**
 *
 * @author Jordan Walker <jiwalker@usgs.gov>
 */
public enum SpatialRangeType {
    NORTH_SOUTH("northsouth"),
    EAST_WEST("eastwest"),
    UP_DOWN("updown"),
    UNKNOWN("");
    
    String type;
    
    SpatialRangeType(String type) {
        this.type = type;
    }
    
    public static SpatialRangeType getType(String type) {
        if (NORTH_SOUTH.type.equalsIgnoreCase(type)) {
            return NORTH_SOUTH;
        }
        else if (EAST_WEST.type.equalsIgnoreCase(type)) {
            return EAST_WEST;
        }
        else if (UP_DOWN.type.equalsIgnoreCase(type)) {
            return UP_DOWN;
        }
        else {
            return UNKNOWN;
        }
    }
}
