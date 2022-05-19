/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.devCaotics.annotationTrackerEngine;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author devCaotics
 */
public class MyTree {
    
    Node root = new Node();
    MyTree(){
        root.info.folder = "";
        root.info.classFileContent = false;
    }
    
    void printClassFolders(){
        _printClassFolders(root);
    }
    
    private void _printClassFolders(Node n){
        if(n.info.classFileContent){
            for(String s : n.info.classNames)
                System.out.println(n.buildStringPathUntilMe()+"/"+s+".class");
        }
        for(Node nAux : n.sons){
            _printClassFolders(nAux);
        }
    }
    
    public String[] getProjectClasses(){
        
        List<String> classNames = _getProjectClasses(root);
        
        String names[] = new String[classNames.size()];
        
        classNames.toArray(names);
        
        return names;
        
    }
    
    private List<String> _getProjectClasses(Node n){
        List<String> classNames = new ArrayList<>();
       
        
         if(n.info.classFileContent){
            for(String s : n.info.classNames)
                classNames.add(n.buildStringPathUntilMe()+"/"+s);
         }
        
        for(Node nAux : n.sons){
           classNames.addAll(_getProjectClasses(nAux));
        }
        
        return classNames;
    }
    
}
