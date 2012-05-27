/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtworld.facade;

import com.gtworld.entity.Poi;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Antonio
 */
@Stateless
public class PoiFacade extends AbstractFacade<Poi> {
    @PersistenceContext(unitName = "GTWorldPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PoiFacade() {
        super(Poi.class);
    }
    
}
