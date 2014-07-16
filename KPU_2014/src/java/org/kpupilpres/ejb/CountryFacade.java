/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kpupilpres.ejb;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.kpupilpres.entity.Country;
import org.kpupilpres.entity.Province;

/**
 *
 * @author addin_000
 */
@Stateless
public class CountryFacade extends AbstractFacade<Country> {

    @PersistenceContext(unitName = "KPU_2014PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CountryFacade() {
        super(Country.class);
    }
    @EJB
    ProvinceFacade provFacade;

    public void generateProvince() throws IOException {

        List<Country> allCountry = findAll();
        for (Country c : allCountry) {
            Logger.getLogger("Process Country for : " + c.getCode() + " url: " + c.getChildUrl());
            try {
                Document doc = Jsoup.connect(c.getChildUrl()).
                        timeout(0).
                        get();
                Elements wilayahElements = doc.getElementsByAttributeValue("name", "wilayah_id");
                Element ele = wilayahElements.get(0);
                for (int i = 0; i < ele.childNodeSize(); i++) {
                    Element optionWil = ele.child(i);
                    if (optionWil.hasAttr("value") && optionWil.attr("value") != null
                            && !optionWil.attr("value").equals("")) {

                        Province prov = new Province();
                        prov.setFidNational(c);
                        prov.setCreateDate(new Date());
                        prov.setCode(optionWil.text());
                        prov.setId(Long.valueOf(optionWil.attr("value")));
                        prov.setChildUrl("http://pilpres2014.kpu.go.id/da1.php?cmd=select&grandparent=" + c.getId().toString() + "&parent=" + prov.getId());
                        provFacade.edit(prov);
                    }
                }
                Thread.sleep(200);
            } catch (Exception ex) {
                Logger.getLogger(CityFacade.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
