package br.com.notaroberto.util;

import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

/**
 * Disponibiliza o view scope para managed beans gerenciados pelo spring
 * 
 */
public class ViewScope implements Scope {

    public final String VIEW_SCOPE_KEY = "CRANK_VIEW_SCOPE";

    @Override
    public Object get(String name, ObjectFactory objectFactory) {

        if (FacesContext.getCurrentInstance().getViewRoot() != null) {
            Map<String, Object> viewScope = extractViewScope();

            if (viewScope.get(name) == null) {
                Object object = objectFactory.getObject();
                viewScope.put(name, object);
                return object;
            } else {
                return viewScope.get(name);
            }
        } else {
            System.out.println("################ : GET VIEW ROOT NOT FOUND");
            return null;
        }

    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> extractViewScope() {
        Map<String, Object> attributes = FacesContext.getCurrentInstance().getViewRoot().getAttributes();

        Map<String, Object> viewScope = null;

        if (attributes.get(VIEW_SCOPE_KEY) == null) {
            viewScope = new HashMap<String, Object>();
            attributes.put(VIEW_SCOPE_KEY, viewScope);
        } else {
            viewScope = (Map<String, Object>) attributes.get(VIEW_SCOPE_KEY);
        }
        return viewScope;
    }

    @Override
    public String getConversationId() {
        return null;
    }

    @Override
    public void registerDestructionCallback(String name, Runnable callback) {
        System.out.println("#####################: We don't support destruction callbacks at this time");
    }

    @Override
    public Object remove(String name) {
        if (FacesContext.getCurrentInstance().getViewRoot() != null) {
            Map<String, Object> viewScope = extractViewScope();
            return viewScope.remove(name);
        } else {
            System.out.println("################ : REMOVE VIEW ROOT NOT FOUND");
            return null;
        }
    }

    @Override
    public Object resolveContextualObject(String key) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
