/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.devCaotics.TagHanddlers;

import br.devCaotics.annotationTrackerEngine.TaggedAnnotationList;
import br.devCaotics.annotations.CRUDRepository;
import br.devCaotics.annotations.CurrentInstanceMethod;
import br.devCaotics.annotations.ReadOneMethod;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 *
 * @author ALUNO
 */
public class LoadOneRepository extends SimpleTagSupport {

    private String className;
    private String var;
    private String id;

    @Override
    public void doTag() throws JspException, IOException {
        super.doTag(); //To change body of generated methods, choose Tools | Templates.

        for (Class c : TaggedAnnotationList.AnnotatedClasses) {

            CRUDRepository crudAnnot = (CRUDRepository) c.getAnnotation(CRUDRepository.class);

            if (crudAnnot.tagEntity().equalsIgnoreCase(this.className)) {

                Object o = null;

                Method[] rMethods = c.getDeclaredMethods();

                for (Method m : rMethods) {

                    if (m.isAnnotationPresent(CurrentInstanceMethod.class)) {
                        try {
                            o = m.invoke(null);
                        } catch (Exception e) {
                            
                            System.out.println("devCaotics info: Problem on "+this.className+" creating Repository Instance");
                            getJspContext().setAttribute(this.var, null, PageContext.PAGE_SCOPE);
                            getJspContext().setAttribute("devCaotics-error", "Problem on creating Repository Instance");
                            return;
                        }
                    }

                }

                if (o == null) {
                    System.out.println("devCaotics info: Problem on creating "+this.className+" Repository Instance");
                    getJspContext().setAttribute(this.var, new ArrayList(), PageContext.PAGE_SCOPE);
                    getJspContext().setAttribute("devCaotics-error", "Problem on creating Repository Instance on readOne tag");
                    return;
                }

                for (Method m : rMethods) {

                    if (m.isAnnotationPresent(ReadOneMethod.class)) {
                        try {
                            Object entity =  m.invoke(o, Integer.parseInt(this.id));

                            getJspContext().setAttribute(this.var, entity, PageContext.PAGE_SCOPE);
                            return;

                        } catch (Exception ex) {
                            
                            System.out.println("devCaotics info: Problem on calling readOne method");
                            getJspContext().setAttribute(this.var, new ArrayList(), PageContext.PAGE_SCOPE);
                            getJspContext().setAttribute("devCaotics-error", "Problem on calling readOne method on repository "+this.className);
                            return;
                        }
                    }

                }

                System.out.println("devCaotics info: Problem there is on readOne method on repository");
                getJspContext().setAttribute(this.var, null, PageContext.PAGE_SCOPE);
                getJspContext().setAttribute("devCaotics-error", "Problem on calling readOne method on Repository "+this.className);
                return;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
