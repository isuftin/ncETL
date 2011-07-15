package thredds.catalog2.simpleImpl;

/**
 *
 * @author isuftin
 */
public class PropertyImplBuilder {
    private boolean built = false;
    private PropertyImpl propertyImpl;
    
    public PropertyImplBuilder(final String name, final String value) {
        propertyImpl = new PropertyImpl(name, value);
    }
    
    
    
    public PropertyImpl build() {
        if (!built) {
            built = true;
            return propertyImpl;
        } else {
            throw new UnsupportedOperationException("build() can only be called once");
        }
    }
}
