package thredds.catalog;

import org.junit.Test;
import ucar.nc2.constants.FeatureType;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
/**
 *
 * @author Ivan Suftin <isuftin@usgs.gov>
 */
public class InvDatasetBuilderTest {
    
    public InvDatasetBuilderTest() {
    }

    @Test
    public void testInvDatasetBuilder() {
        InvDatasetBuilder testBuilder = new InvDatasetBuilder("testName", "testID");
        InvDataset test = testBuilder.authorityName("testAuthorityName")
                .collectionType("testCollectionType")
                .contributor("testContributorName", "testContributorRole")
                .creator("testCreatorName", "testCreatorURL", "testCreatorEmail", "testCreatorVocab")
                .dataFormatType("testDataFormatType") 
                .dataType(FeatureType.ANY.name()) 
                .documentation("testDocumentationType", "testDocumentationText")
                .xlinkDocumentation("testXlinkDocumentationHref", "testXlinkDocumentationTitle") 
                .keyword("testKeyword", "testVocab") 
                .project("testProject", "testVocab")
                .property("testKey", "testValue")
                .publisher("testPublisherName", "testPublisherURL", "testPublisherEmail", "testPublisherVocab") 
                .service("testServiceName")
                .build();
        
        assertThat(test.authorityName, is(equalTo("testAuthorityName")));
        
        assertThat(test.collectionType.toString(), is(equalTo("testCollectionType")));
        
        assertThat(test.contributors, is(notNullValue()));
        assertThat(test.contributors.size(), is(equalTo(1)));
        assertThat(test.contributors.get(0).getName(), is(equalTo("testContributorName")));
        assertThat(test.contributors.get(0).getRole(), is(equalTo("testContributorRole")));
        
        assertThat(test.getCreators(), is(notNullValue()));
        assertThat(test.getCreators().size(), is(equalTo(1)));
        assertThat(test.getCreators().get(0).getName(), is(equalTo("testCreatorName")));
        assertThat(test.getCreators().get(0).getUrl(), is(equalTo("testCreatorURL")));
        assertThat(test.getCreators().get(0).getEmail(), is(equalTo("testCreatorEmail")));
        assertThat(test.getCreators().get(0).getVocabulary(), is(equalTo("testCreatorVocab")));
        
        assertThat(test.getDataType().name(), is(equalTo("ANY")));
        
        assertThat(test.getDocumentation(), is(notNullValue()));
        assertThat(test.getDocumentation().size(), is(equalTo(2)));
        assertThat(test.getDocumentation().get(0).getType(), is(equalTo("testDocumentationType")));
        assertThat(test.getDocumentation().get(0).getInlineContent(), is(equalTo("testDocumentationText")));
        assertThat(test.getDocumentation().get(1).getXlinkHref(), is(equalTo("testXlinkDocumentationHref")));
        assertThat(test.getDocumentation().get(1).getXlinkTitle(), is(equalTo("testXlinkDocumentationTitle")));
        
        assertThat(test.getKeywords().size(), is(equalTo(1)));
        assertThat(test.getKeywords().get(0).getText(), is(equalTo("testKeyword")));
        assertThat(test.getKeywords().get(0).getVocabulary(), is(equalTo("testVocab")));
        
        assertThat(test.getProjects(), is(notNullValue()));
        assertThat(test.getProjects().size(), is(equalTo(1)));
        assertThat(test.getProjects().get(0).getText(), is(equalTo("testProject")));
        assertThat(test.getProjects().get(0).getVocabulary(), is(equalTo("testVocab")));
        
        assertThat(test.getProperties(), is(notNullValue()));
        assertThat(test.getProperties().size(), is(equalTo(1)));
        assertThat(test.getProperties().get(0).getName(), is(equalTo("testKey")));
        assertThat(test.getProperties().get(0).getValue(), is(equalTo("testValue")));
        
        assertThat(test.getPublishers(), is(notNullValue()));
        assertThat(test.getPublishers().size(), is(equalTo(1)));
        assertThat(test.getPublishers().get(0).getName(), is(equalTo("testPublisherName")));
        assertThat(test.getPublishers().get(0).getUrl(), is(equalTo("testPublisherURL")));
        assertThat(test.getPublishers().get(0).getEmail(), is(equalTo("testPublisherEmail")));
        assertThat(test.getPublishers().get(0).getVocabulary(), is(equalTo("testPublisherVocab")));
        
        //assertThat(test.services.get(0).getName(), is(equalTo("testServiceName"))); 
    }
    
    @Test
    public void testInvDatasetBuilderWithAlreadyBuiltFlag() {
        InvDatasetBuilder testBuilder = new InvDatasetBuilder("testName", "testID");
        InvDataset test = testBuilder.build();
        try {
            test = testBuilder.build();
        } catch (UnsupportedOperationException e) {
            assertThat(e.getMessage(), is(equalTo("build() can only be called once")));
        }
    }
    
//    @Test 
//    public void testInvDatasetBuilderCopierConstructor() {
//        InvDatasetBuilder testBuilderOriginal = new InvDatasetBuilder("testName", "testID");
//        InvDataset original = testBuilderOriginal
//                .authorityName("testAuthorityName")
//                .build();
//        
//        InvDatasetBuilder testBuilderCopier = new InvDatasetBuilder(original);
//        InvDataset copy = testBuilderCopier.build();
//        assertThat(copy.authorityName, is(equalTo("testAuthorityName")));
//    }
    
    @Test
    public void testNotYetImplementedFunctions() {
        InvDatasetBuilder testBuilder = new InvDatasetBuilder("testName", "testID");
        try {
            testBuilder.access("", "", "");
        } catch (Exception e) {
            assertThat(e,  instanceOf(UnsupportedOperationException.class));
        }
        
        try {
            testBuilder.metadata();
        } catch (Exception e) {
            assertThat(e,  instanceOf(UnsupportedOperationException.class));
        }
        
        try {
            testBuilder.restrictAccess();
        } catch (Exception e) {
            assertThat(e,  instanceOf(UnsupportedOperationException.class));
        }
        
        try {
            testBuilder.geospatialCoverage();
        } catch (Exception e) {
            assertThat(e,  instanceOf(UnsupportedOperationException.class));
        }
        
        try {
            testBuilder.timeCoverage();
        } catch (Exception e) {
            assertThat(e,  instanceOf(UnsupportedOperationException.class));
        }
        
        try {
            testBuilder.variable();
        } catch (Exception e) {
            assertThat(e,  instanceOf(UnsupportedOperationException.class));
        }
        
    }
}
