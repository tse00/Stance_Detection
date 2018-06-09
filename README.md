# Stance_Detection

## Setup:

   - Java 1.8.0_172 (Java 10 is not supported!)

   - dkpro-tc-0.9.0 Framework


## Programm Structure:

/src/main/java/de.ba.tiagosenc.Pipeline/SdPipeline.java:
pipeline to execute the program

	
SdPipeline line 58:
to set your own DKPRO_HOME path before run the program. After execute the program all the information is gathered on the folder DKPRO_HOME.


SdPipeline line 63 
to evaluate the system on cross validation


SdPipeline line 64: 
to evaluate the system on train/test


SdPipeline between Line 95 and 112:
to select ontology-features and n-gram feature


SdPipeline line 92:
to select classifier Support Vector Machine


/src/main/java/de.ba.tiagosenc.Features/: 
classes for the Ontology-features


/src/main/java/de.ba.tiagosenc.WikiParser/: 
classes for Jsoup Wikipedia anchor text extraction


/de.ba.tiagosenc/src/main/resources/Data/:
Training/Test/Label tweets-data plus Spanish stopwords file.


/de.ba.tiagosenc/src/main/resources/Expanded Lists/:
Information is directly extracted here, when using the “WikiParser” classes.


/de.ba.tiagosenc/src/main/resources/Lists/:
Information is directly extracted here, when using the ontology-features.


/de.ba.tiagosenc/src/main/resources/Lists+Wiki/:
contain the lists from “Expanded Lists” and “Lists”.


/de.ba.tiagosenc/src/main/resources/Lists+Wiki_Intro/: 
contain the lists from “Expanded Lists” and “Lists”, where the “Expanded Lists” contain just the anchor texts from the introduction of each Wikipedia article.


/src/test/java/:
contain Junit-tests 


/src/test/resources/:
contain small content from the data files to test the reader
