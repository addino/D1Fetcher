/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kpupilpres.ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.kpupilpres.entity.Tps;

/**
 *
 * @author addin_000
 */
@Stateless
public class TpsFacade extends AbstractFacade<Tps> {
    @PersistenceContext(unitName = "KPU_2014PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TpsFacade() {
        super(Tps.class);
    }
    
}
