/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kpupilpres.ejb;

import java.math.BigInteger;
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
import org.kpupilpres.entity.District;
import org.kpupilpres.entity.Tps;

/**
 *
 * @author addin_000
 */
@Stateless
public class DistrictFacade extends AbstractFacade<District> {

    @PersistenceContext(unitName = "KPU_2014PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DistrictFacade() {
        super(District.class);
    }
    @EJB
    TpsFacade tpsFacade;

    public void generateTPSData() throws Exception {

        List<District> allDistrict = findAll();
        for (District d : allDistrict) {
            Logger.getLogger("Process District for : " + d.getCode() + "url: " + d.getChildUrl());
            try {
                Document doc = Jsoup.connect(d.getChildUrl()).
                        timeout(0).
                        userAgent("Mozilla").
                        get();
                Element tableElement = doc.getElementsByTag("table").last();

                Element tableBody = tableElement.getElementsByTag("tbody").first();
                Elements tableRows = tableBody.getElementsByTag("tr");
                Element districtElement = tableRows.get(2);
                Elements tpsCell = districtElement.getElementsByTag("th");
                Element prahaElement = tableRows.get(3);
                Elements prahaCell = prahaElement.getElementsByTag("td");
                Element jkElement = tableRows.get(4);
                Elements jkCell = jkElement.getElementsByTag("td");
                for (int i = 0; i < tpsCell.size() - 2; i++) {
                    Tps tps = new Tps();
                    tps.setFidDistrict(d);
                    tps.setCreateDate(new Date());
                    tps.setCode(tpsCell.get(i + 2).text());

                    if (prahaCell.get(i + 2).text() != null
                            && !prahaCell.get(i + 2).text().equals("")) {
                        tps.setPraha(new BigInteger((prahaCell.get(i + 2).text().trim())));
                    } else {
                        tps.setPraha(new BigInteger("0"));
                    }

                    if (jkCell.get(i + 2).text() != null
                            && !jkCell.get(i + 2).text().equals("")) {
                        tps.setJojk(new BigInteger((jkCell.get(i + 2).text().trim())));
                    } else {
                        tps.setJojk(new BigInteger("0"));
                    }

                    tpsFacade.edit(tps);

                }
            } catch (Exception ex) {
                Logger.getLogger(CityFacade.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
