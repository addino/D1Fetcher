/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kpupilpres.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.ejb.EJB;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author addin_000
 */
@Entity
@Table(name = "COUNTRY")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Country.findAll", query = "SELECT c FROM Country c"),
    @NamedQuery(name = "Country.findById", query = "SELECT c FROM Country c WHERE c.id = :id"),
    @NamedQuery(name = "Country.findByCode", query = "SELECT c FROM Country c WHERE c.code = :code"),
    @NamedQuery(name = "Country.findByChildUrl", query = "SELECT c FROM Country c WHERE c.childUrl = :childUrl"),
    @NamedQuery(name = "Country.findByCreateDate", query = "SELECT c FROM Country c WHERE c.createDate = :createDate")})
public class Country implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "CODE")
    private String code;
    @Column(name = "CHILD_URL")
    private String childUrl;
    @Column(name = "CREATE_DATE")
    @Temporal(TemporalType.DATE)
    private Date createDate;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fidNational")
    private Collection<Province> provinceCollection;
    

    public Country() {
    }

    public Country(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getChildUrl() {
        return childUrl;
    }

    public void setChildUrl(String childUrl) {
        this.childUrl = childUrl;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @XmlTransient
    public Collection<Province> getProvinceCollection() {
        return provinceCollection;
    }

    public void setProvinceCollection(Collection<Province> provinceCollection) {
        this.provinceCollection = provinceCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Country)) {
            return false;
        }
        Country other = (Country) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.kpupilpres.entity.Country[ id=" + id + " ]";
    }
    

    
}
