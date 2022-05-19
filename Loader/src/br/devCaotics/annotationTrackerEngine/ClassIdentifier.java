package br.devCaotics.annotationTrackerEngine;

import br.devCaotics.annotations.CRUDRepository;
import java.io.File;
import java.util.ArrayList;
import java.util.List;





/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author devCaotics
 */
public class ClassIdentifier {

    private static boolean executed = false;
    
    public ClassIdentifier(){
        
    }

    public static void identify() {
        if (!executed) {
            
            System.out.println("devCaotics, solutions in infartomatic");
            
            executed = true;
            
            String _pathAux = new ClassIdentifier().getClass().getResource("ClassIdentifier.class").getPath().replace("%20", " ");
            
            String _pathAuxDecomp[] = _pathAux.split("/");
            
            String mainPath = "";
            
            for(String s: _pathAuxDecomp){
                
                if(s.equalsIgnoreCase("file:")){
                    continue;
                }
                
                if(s.equalsIgnoreCase("lib")){
                    mainPath += "classes";
                    break;
                }
                mainPath += s+"/";
                
            }

            MyTree tree = new MyTree();

            buildTree(tree.root, mainPath, "");

            String[] classNames = tree.getProjectClasses();
            List<Class> classes = new ArrayList();

            for (String s : classNames) {

                try {
                    
                    Class cAux = Class.forName(s.replace("/", ".").substring(1));
                    
                    if (cAux.isAnnotationPresent(CRUDRepository.class)) {
                        classes.add(cAux);
                    }

                } catch (ClassNotFoundException ex) {
                    System.out.println("devCaotics info: Error on load repository Classes, your system may not work properly");
                }

            }

            Class[] cls = new Class[classes.size()];

            classes.toArray(cls);

            TaggedAnnotationList.AnnotatedClasses = cls;
        }
    }

    public static void buildTree(Node tree, String mainpath, String path) {

        File f = new File(mainpath + path);
        
        for (String s : f.list()) {

            File fAux = new File(mainpath + path + "/" + s);

            if (fAux.isDirectory()) {
                Node n = new Node();
                n.info.folder = "/" + s;
                n.parent = tree;
                tree.sons.add(n);
                buildTree(n, mainpath, n.buildStringPathUntilMe());
            }
            if (s.contains(".class")) {
                tree.info.classFileContent = true;
                tree.info.classNames.add(s.replace(".class", ""));
            }

        }

    }

}
