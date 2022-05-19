/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.devCaotics.TagHanddlers;

import br.devCaotics.annotationTrackerEngine.TaggedAnnotationList;
import br.devCaotics.annotations.CRUDRepository;
import br.devCaotics.annotations.CurrentInstanceMethod;
import br.devCaotics.annotations.ReadAllMethod;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 *
 * @author devCaotics
 */
public class LoadListRepository extends SimpleTagSupport{

    private String className;
    private String var;
    
    @Override
    public void doTag() throws JspException, IOException {
        super.doTag(); //To change body of generated methods, choose Tools | Templates.
    
        br.devCaotics.annotationTrackerEngine.ClassIdentifier.identify();
        
        for(Class c: TaggedAnnotationList.AnnotatedClasses){
            
            CRUDRepository crudAnnot = (CRUDRepository)c.getAnnotation(CRUDRepository.class);
            
            if(crudAnnot.tagEntity().equalsIgnoreCase(this.className)){
                
                Object o = null;
                
                Method[] rMethods = c.getDeclaredMethods();
                
                for(Method m: rMethods){
                    
                    if(m.isAnnotationPresent(CurrentInstanceMethod.class)){
                        try {
                            o = m.invoke(null);
                        } catch(Exception e){
                            System.out.println("devCaotics info: Problem on creating Repository Instance");
                            getJspContext().setAttribute(this.var, new ArrayList(), PageContext.PAGE_SCOPE);
                            getJspContext().setAttribute("devCaotics-error", "Problem on creating Repository Instance");
                            return;
                        }
                    }
                    
                }
                
                if(o == null){
                            System.out.println("devCaotics info: Problem on creating Repository Instance");
                            getJspContext().setAttribute(this.var, new ArrayList(), PageContext.PAGE_SCOPE);
                            getJspContext().setAttribute("devCaotics-error", "Problem on creating Repository Instance");
                            return;
                }
                
                List listAux = null;
                
                for(Method m: rMethods){
                    
                    if(m.isAnnotationPresent(ReadAllMethod.class)){
                        try {
                           listAux = (List)m.invoke(o);
                            
                            getJspContext().setAttribute(this.var, listAux, PageContext.PAGE_SCOPE);
                            return;
                            
                        } catch (Exception ex) {
                            System.out.println("devCaotics info: Problem on creating Repository Instance");
                            getJspContext().setAttribute(this.var, new ArrayList(), PageContext.PAGE_SCOPE);
                            getJspContext().setAttribute("devCaotics-error", "Problem on creating Repository Instance");
                            return;
                        } 
                    }
                    
                }
                
                if(listAux == null){
                            System.out.println("devCaotics info: Problem on creating Repository Instance");
                            getJspContext().setAttribute(this.var, new ArrayList(), PageContext.PAGE_SCOPE);
                            getJspContext().setAttribute("devCaotics-error", "Problem on creating Repository Instance");
                            return;
                }
                
            }
            
        }
    
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getVar() {
        return var;
    }

    public void setVar(String var) {
        this.var = var;
    }
    
    
    
}
