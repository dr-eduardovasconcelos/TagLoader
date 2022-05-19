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
public class Node {
    NodeInfo info = new  NodeInfo();
    List<Node> sons = new ArrayList<>();
    Node parent = null;
    
    public String buildStringPathUntilMe(){
        if(parent == null){
            return info.folder;
        }
        
        return parent.buildStringPathUntilMe()+info.folder;
    }
}
