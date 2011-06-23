package thredds.catalog;

import thredds.catalog.ThreddsMetadata.Contributor;

/**
 *
 * @author Jordan Walker <jiwalker@usgs.gov>
 */
public class InvDatasetBuilder {
    
    private InvDataset dataset; //= new InvDatasetImpl(parent, name);
    
    public InvDatasetBuilder(String name) {
        dataset = new InvDatasetImpl(null, name);
    }
    
    public InvDatasetBuilder id(String id) {
        dataset.id = id;
        return this;
    }
    
    public InvDatasetBuilder collectionType(String ct) {
        dataset.collectionType = CollectionType.getType(ct);
        return this;
    }
    
    public InvDatasetBuilder contributor(String name, String role) {
        Contributor contrib = new Contributor(name, role);
        dataset.contributors.add(contrib);
        return this;
    }
    
    protected InvDataset getInternalInvDataset() {
        return this.dataset;
    }
}
