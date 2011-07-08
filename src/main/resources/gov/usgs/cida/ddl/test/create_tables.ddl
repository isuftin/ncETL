-- This is just a test file. However, if the contents of the file are changed in any way, the test that depends
-- on this file will no longer run.

-- Also, always end comments with ;

CREATE TABLE collection_type 
    (id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), type varchar(32), inserted boolean, updated boolean, PRIMARY KEY (id));

CREATE TABLE data_type 
    (id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), type varchar(32), inserted boolean, updated boolean, PRIMARY KEY (id));

CREATE TABLE data_format 
    (id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), type varchar(32), inserted boolean, updated boolean, PRIMARY KEY (id));

CREATE TABLE documentation_type 
    (id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), type varchar(32), inserted boolean, updated boolean, PRIMARY KEY (id));

CREATE TABLE service_type 
    (id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), type varchar(32), inserted boolean, updated boolean, PRIMARY KEY (id));
        
CREATE TABLE controlled_vocabulary 
    (id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), vocab varchar(32), inserted boolean, updated boolean, PRIMARY KEY (id));
        
CREATE TABLE global_config 
    (id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), base_dir varchar(512), thredds_dir varchar(512), PRIMARY KEY (id));
        
CREATE TABLE catalog 
    (id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), location varchar(512), name varchar(64), expires date, version varchar(8), inserted boolean, updated boolean, PRIMARY KEY (id));
        
CREATE TABLE ingest 
    (id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), catalog_id INT CONSTRAINT CATALOG2_FK REFERENCES catalog, name varchar(128), ftpLocation varchar(512), rescanEvery bigint, fileRegex varchar(64), successDate date, successTime time, username varchar(64), password varchar(64), active boolean, inserted boolean, updated boolean, PRIMARY KEY (id));

CREATE TABLE dataset 
    (id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), catalog_id INT CONSTRAINT CATALOG3_FK REFERENCES catalog, collection_type_id INT CONSTRAINT COLLECTION1_FK REFERENCES collection_type, data_type_id INT CONSTRAINT DATATYPE_FK REFERENCES data_type, name varchar(64), ncid varchar(128), authority varchar(64), inserted boolean, updated boolean, PRIMARY KEY (id));

CREATE TABLE service 
    (id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), catalog_id INT CONSTRAINT CATALOG4_FK REFERENCES catalog, service_id INT CONSTRAINT SERVICE1_FK REFERENCES service,  service_type_id INT CONSTRAINT SERVICETYPE_FK REFERENCES service_type, name varchar(64), base varchar(32),  description varchar(512), suffix varchar(32), inserted boolean, updated boolean, PRIMARY KEY (id));

CREATE TABLE access 
    (id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), dataset_id INT CONSTRAINT DATASET1_FK REFERENCES dataset, service_id INT CONSTRAINT SERVICE2_FK REFERENCES service, dataformat_id INT CONSTRAINT DATAFORMAT_FK REFERENCES data_format, url_path varchar(512), inserted boolean, updated boolean, PRIMARY KEY (id));
        
CREATE TABLE documentation 
    (id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), dataset_id INT CONSTRAINT DATASET2_FK REFERENCES dataset, documentation_type_id INT CONSTRAINT DOCTYPE_FK REFERENCES documentation_type, xlink_href varchar(256), xlink_title varchar(256), text varchar(1024), inserted boolean, updated boolean, PRIMARY KEY (id));
        
CREATE TABLE property 
    (id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), dataset_id INT CONSTRAINT DATASET3_FK REFERENCES dataset, name varchar(128), value varchar(512), inserted boolean, updated boolean, PRIMARY KEY (id));

CREATE TABLE keyword 
    (id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), value varchar(64), controlled_vocabulary_id INT CONSTRAINT VOCAB1_FK REFERENCES controlled_vocabulary, inserted boolean, updated boolean, PRIMARY KEY (id));

CREATE TABLE contributor 
    (id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), role varchar(64), text varchar(256), inserted boolean, updated boolean, PRIMARY KEY (id));

CREATE TABLE creator 
    (id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), name varchar(256), controlled_vocabulary_id INT CONSTRAINT VOCAB2_FK REFERENCES controlled_vocabulary, contact_url varchar(512), contact_email varchar(256), inserted boolean, updated boolean, PRIMARY KEY (id));
        
CREATE TABLE keyword_join 
    (dataset_id INT CONSTRAINT DATASET4_FK REFERENCES dataset, keyword_id INT CONSTRAINT KEYWORD_FK REFERENCES keyword, inserted boolean, updated boolean);

CREATE TABLE contributor_join 
    (dataset_id INT CONSTRAINT DATASET5_FK REFERENCES dataset, contributor_id INT CONSTRAINT CONTRIB_FK REFERENCES contributor, inserted boolean, updated boolean);
        
CREATE TABLE creator_join 
    (dataset_id INT CONSTRAINT DATASET6_FK REFERENCES dataset, creator_id INT CONSTRAINT CREATOR_FK REFERENCES creator, inserted boolean, updated boolean);        