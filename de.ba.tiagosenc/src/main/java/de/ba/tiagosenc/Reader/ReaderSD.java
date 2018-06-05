package de.ba.tiagosenc.Reader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.uima.UimaContext;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.fit.component.JCasCollectionReader_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Progress;
import org.apache.uima.util.ProgressImpl;
import org.dkpro.tc.api.io.TCReaderSingleLabel;
import org.dkpro.tc.api.type.TextClassificationOutcome;

import de.tudarmstadt.ukp.dkpro.core.api.metadata.type.DocumentMetaData;

public class ReaderSD 
	extends JCasCollectionReader_ImplBase 
	implements TCReaderSingleLabel
{
	
	
	public static final String PARAM_TWEET_FILE = "tweets";
    @ConfigurationParameter(name = PARAM_TWEET_FILE, mandatory = true)
    File tweets;
    
	public static final String PARAM_GOLD_FILE = "goldlabel";
    @ConfigurationParameter(name = PARAM_GOLD_FILE, mandatory = false)
    File goldlabels;
    
    List<String> text = new ArrayList<String>();
	List<String> gold = new ArrayList<String>();
	int totalines = 0;
    
	@Override
    public void initialize(UimaContext context)
        throws ResourceInitializationException
        
    {
        super.initialize(context);

        try {
           
           for (String tweet : FileUtils.readLines(tweets, "UTF-8")) {

               text.add(tweet);
           }
           
           
            for (String line : FileUtils.readLines(goldlabels, "UTF-8")) {
            	
                String[] parts = line.split(":::");
              
                gold.add(parts[1]);
                
            }
            
/*            if (text.size() != gold.size()) {
                throw new ResourceInitializationException(new Throwable(
                        "Size of text list does not match size of gold list."));
            }*/
            
        }
        catch (IOException e) {
            throw new ResourceInitializationException(e);
        }
        
        totalines = 0;
    }
	

	public boolean hasNext() throws IOException, CollectionException {
		
		return totalines < text.size();

	}

	public Progress[] getProgress() {

		return new Progress[] {new ProgressImpl(totalines, text.size(), "tweets")};
	}

	
	@Override
	public void getNext(JCas jCas) throws IOException, CollectionException {

		jCas.setDocumentText(text.get(totalines));
				
		DocumentMetaData dmd = DocumentMetaData.create(jCas);
		dmd.setDocumentTitle("Tweet" + totalines);
		dmd.setDocumentId(String.valueOf(totalines));
		
		 // setting the outcome / label for this document
       TextClassificationOutcome outcome = new TextClassificationOutcome(jCas);
       outcome.setOutcome(getTextClassificationOutcome(jCas));
       outcome.addToIndexes();	
       
       totalines ++;
	}

	
	public String getTextClassificationOutcome(JCas jcas) throws CollectionException {
		
		return gold.get(totalines);
	}

}
