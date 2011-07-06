package thredds.catalog;

/**
 *
 * @author Jordan Walker <jiwalker@usgs.gov>
 */
public class DatasetHelper {
    
    private DatasetHelper() {}
    
    public static InvDataset syncWithDatabase(int id, InvCatalog parent) {
        InvDataset editMe = parent.topDataset;
        // search database for datasets with id=id
        // set all the attributes and leaf nodes
        // search for children
        // recurse!
        return editMe;
    }
}
