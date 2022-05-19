/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.devCaotics.annotationTrackerEngine;

/**
 *
 * @author devCaotics
 */
public class TaggedAnnotationList {
    
    public static Class[] AnnotatedClasses = null;
    
    
    static{
        br.devCaotics.annotationTrackerEngine.ClassIdentifier.identify();
    }
}
