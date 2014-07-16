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
import org.kpupilpres.entity.District;

/**
 *
 * @author addin_000
 */
@Stateless
public class CityFacade extends AbstractFacade<City> {

    @PersistenceContext(unitName = "KPU_2014PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CityFacade() {
        super(City.class);
    }
    @EJB
    DistrictFacade distFacade;

    public void generateDistrict() throws IOException {

        List<City> allCity = findAll();
        for (City c : allCity) {
            Logger.getLogger("Process City for : " + c.getCode() + " url: " + c.getChildUrl());
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

                        District district = new District();
                        district.setFidCity(c);
                        district.setCreateDate(new Date());
                        district.setCode(optionWil.text());
                        district.setId(Long.valueOf(optionWil.attr("value")));
                        district.setChildUrl("http://pilpres2014.kpu.go.id/da1.php?cmd=select&grandparent=" + c.getId().toString() + "&parent=" + district.getId());
                        distFacade.edit(district);
                    }
                }
                Thread.sleep(200);
            } catch (Exception ex) {
                Logger.getLogger(CityFacade.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
}
