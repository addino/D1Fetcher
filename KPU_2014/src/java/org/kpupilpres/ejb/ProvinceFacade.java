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
import org.kpupilpres.entity.City;
import org.kpupilpres.entity.Country;
import org.kpupilpres.entity.Province;

/**
 *
 * @author addin_000
 */
@Stateless
public class ProvinceFacade extends AbstractFacade<Province> {

    @PersistenceContext(unitName = "KPU_2014PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProvinceFacade() {
        super(Province.class);
    }
    @EJB
    CityFacade cityFacade;

    public void generateCity() throws IOException {

        List<Province> allProvince = findAll();
        for (Province p : allProvince) {
            Logger.getLogger("Process Province for : " + p.getCode() + " url: " + p.getChildUrl());
            try {
                Document doc = Jsoup.connect(p.getChildUrl()).
                        timeout(0).
                        get();
                Elements wilayahElements = doc.getElementsByAttributeValue("name", "wilayah_id");
                Element ele = wilayahElements.get(0);
                for (int i = 0; i < ele.childNodeSize(); i++) {
                    Element optionWil = ele.child(i);
                    if (optionWil.hasAttr("value") && optionWil.attr("value") != null
                            && !optionWil.attr("value").equals("")) {

                        City city = new City();
                        city.setFidProvince(p);
                        city.setCreateDate(new Date());
                        city.setCode(optionWil.text());
                        city.setId(Long.valueOf(optionWil.attr("value")));
                        city.setChildUrl("http://pilpres2014.kpu.go.id/da1.php?cmd=select&grandparent=" + p.getId().toString() + "&parent=" + city.getId());
                        cityFacade.edit(city);
                    }
                }
                Thread.sleep(200);
            } catch (Exception ex) {
                Logger.getLogger(CityFacade.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
