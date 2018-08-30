package com.company;

import edu.stanford.nlp.ie.machinereading.structure.MachineReadingAnnotations;
import edu.stanford.nlp.ie.machinereading.structure.RelationMention;
import edu.stanford.nlp.io.IOUtils;
import edu.stanford.nlp.ling.*;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.trees.*;
import edu.stanford.nlp.util.*;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * User: Brijesh
 * Date: 6/17/17
 * Time: 6:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class AnnotateSample {
    public static void main(String[] args) {
        try{
            Properties props = StringUtils.argsToProperties(args);
            props.setProperty("annotators", "tokenize,ssplit,lemma,pos,parse,ner");
            StanfordCoreNLP pipeline = new StanfordCoreNLP();
            String sentence = "Barack Obama lives in America. Obama works for the Federal Goverment.";
            Annotation doc = new Annotation(sentence);
            pipeline.annotate(doc);
            RelationExtractorAnnotator r = new RelationExtractorAnnotator(props);
            r.annotate(doc);
            for(CoreMap s: doc.get(CoreAnnotations.SentencesAnnotation.class)){
                System.out.println("For sentence " + s.get(CoreAnnotations.TextAnnotation.class));
                List<RelationMention> rls  = s.get(MachineReadingAnnotations.RelationMentionsAnnotation.class);
                for(RelationMention rl: rls){
                    System.out.println(rl.toString());
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}

