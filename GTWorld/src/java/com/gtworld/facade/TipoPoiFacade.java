/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtworld.facade;

import com.gtworld.entity.TipoPoi;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Antonio
 */
@Stateless
public class TipoPoiFacade extends AbstractFacade<TipoPoi> {
    @PersistenceContext(unitName = "GTWorldPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TipoPoiFacade() {
        super(TipoPoi.class);
    }
    
}
