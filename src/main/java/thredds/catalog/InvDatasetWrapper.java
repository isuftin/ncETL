package thredds.catalog;

import java.util.LinkedList;
import thredds.catalog.ThreddsMetadata.Contributor;
import thredds.catalog.ThreddsMetadata.Source;
import thredds.catalog.ThreddsMetadata.Vocab;
import ucar.nc2.constants.FeatureType;

/**
 * NOTE: There are cases in which I cast an InvDataset to InvDatasetImpl without checking
 * As far as I know, this is always legal, but if it presents a problem (further
 * subclassing, etc) then this should be fixed by doing something clever that
 * I didn't take the time to figure out at this point.
 * To sum up, there are smells here, but I didn't bother to remedy them.
 * @author Jordan Walker <jiwalker@usgs.gov>
 */
public class InvDatasetWrapper extends InvDatasetImpl{

    /** dataset to build up */
    private boolean built = false;

    /**
     * Copy constructor for existing dataset
     * @param from dataset to copy
     */
    public InvDatasetWrapper(InvDataset from) {
        super((InvDatasetImpl) from);
        //TDO - Make sure that all necessary elements are being set via parent's copy constructor
    }
    
    public InvDatasetWrapper(String name, String id) {
        super(null, name);
        this.id = id;
    }
    
    public InvDatasetWrapper authorityName(String authorityName) {
        this.authorityName = authorityName;
        return this;
    }

    public InvDatasetWrapper access(String serviceName, String urlPath,
                                    String dataFormat) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public InvDatasetWrapper collectionType(String ct) {
        this.collectionType = CollectionType.getType(ct);
        return this;
    }

    public InvDatasetWrapper contributor(String name, String role) {
        Contributor contrib = new Contributor(name, role);
        if (this.contributors == null) {
            this.contributors = new LinkedList<Contributor>();
        }
        this.contributors.add(contrib);
        return this;
    }

    /**
     *
     * http://www.unidata.ucar.edu/mailing_lists/archives/thredds/2004/msg00057.html
     * @param name creator name
     * @param url web address
     * @param email contact address
     * @param vocab (optional) controlledVocabulary
     * @return 
     */
    public InvDatasetWrapper creator(String name, String url, String email,
                                     String vocab) {
        Vocab vocabulary = new Vocab(name, vocab);
        Source source = new Source(vocabulary, url, email);
        if (this.creators == null) {
            this.creators = new LinkedList<Source>();
        }
        this.creators.add(source);
        return this;
    }

    public InvDatasetWrapper dataFormatType(String dataFormatType) {
        this.dataFormatType = DataFormatType.getType(dataFormatType);
        return this;
    }

    public InvDatasetWrapper dataType(String dataType) {
        this.dataType = FeatureType.getType(dataType);
        return this;
    }

    public InvDatasetWrapper documentation(String type, String text) {
        InvDocumentation doc = new InvDocumentation(null, null, null, type, text);
        if (this.docs == null) {
            this.docs = new LinkedList<InvDocumentation>();
        }
        this.docs.add(doc);
        return this;
    }

    public InvDatasetWrapper xlinkDocumentation(String href, String title) {
        InvDocumentation doc = new InvDocumentation(href, null, title, null,
                                                    null);
        if (this.docs == null) {
            this.docs = new LinkedList<InvDocumentation>();
        }
        this.docs.add(doc);
        return this;
    }

    public InvDatasetWrapper keyword(String keyword, String vocab) {
        Vocab vocabulary = new Vocab(keyword, vocab);
        if (this.keywords == null) {
            this.keywords = new LinkedList<Vocab>();
        }
        this.keywords.add(vocabulary);
        return this;
    }

    public InvDatasetWrapper metadata() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public InvDatasetWrapper project(String project, String vocab) {
        Vocab vocabulary = new Vocab(project, vocab);
        if (this.projects == null) {
            this.projects = new LinkedList<Vocab>();
        }
        this.projects.add(vocabulary);
        return this;
    }

    public InvDatasetWrapper property(String key, String value) {
        InvProperty prop = new InvProperty(key, value);
        if (this.properties == null) {
            this.properties = new LinkedList<InvProperty>();
        }
        this.properties.add(prop);
        return this;
    }

    public InvDatasetWrapper publisher(String name, String url, String email,
                                       String vocab) {
        Vocab vocabulary = new Vocab(name, vocab);
        Source source = new Source(vocabulary, url, email);
        if (this.publishers == null) {
            this.publishers = new LinkedList<Source>();
        }
        this.publishers.add(source);
        return this;
    }

    public InvDatasetWrapper restrictAccess() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * May need to do something more complicated here, such as a serviceBuilder
     * @param name serviceName
     * @return 
     */
    public InvDatasetWrapper service(String name) {
        this.setServiceName(name);
        return this;
    }

    public InvDatasetWrapper geospatialCoverage() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public InvDatasetWrapper timeCoverage() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
    public InvDatasetWrapper variable() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Returns the underlying dataset, can only be run once
     * @return underlying dataset
     */
    public InvDataset build() {
        if (!built) {
            built = true;
            return this;
        }
        else {
            throw new UnsupportedOperationException("build() can only be called once");
        }
    }
}
