package de.ba.tiagosenc;

import static org.junit.Assert.assertEquals;

import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.factory.CollectionReaderFactory;
import org.apache.uima.fit.pipeline.JCasIterable;
import org.apache.uima.jcas.JCas;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import de.tudarmstadt.ukp.dkpro.core.api.metadata.type.DocumentMetaData;

public class ReaderSDTest {
	    @Before
    public void setupLogging()
    {
        System.setProperty("org.apache.uima.logger.class", "org.apache.uima.util.impl.Log4jLogger_impl");
    }
	
    @Test
    public void stsReaderTest()
            throws Exception
    {
        CollectionReaderDescription reader = CollectionReaderFactory.createReaderDescription(
                ReaderSD.class,
                ReaderSD.PARAM_TWEET_FILE, "src/test/resources/training_tweets_es_cut.txt",                	
                ReaderSD.PARAM_GOLD_FILE, "src/test/resources/training_truth_es_cut.txt"
        );
        
        int i=0;
        for (JCas jcas : new JCasIterable(reader)) {
            i++;
        }
        
        assertEquals(3, i);
        
    } 
}