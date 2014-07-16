/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kpupilpres.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author addin_000
 */
@Entity
@Table(name = "CITY")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "City.findAll", query = "SELECT c FROM City c"),
    @NamedQuery(name = "City.findById", query = "SELECT c FROM City c WHERE c.id = :id"),
    @NamedQuery(name = "City.findByCode", query = "SELECT c FROM City c WHERE c.code = :code"),
    @NamedQuery(name = "City.findByChildUrl", query = "SELECT c FROM City c WHERE c.childUrl = :childUrl"),
    @NamedQuery(name = "City.findByCreateDate", query = "SELECT c FROM City c WHERE c.createDate = :createDate")})
public class City implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Column(name = "CODE")
    private String code;
    @Column(name = "CHILD_URL")
    private String childUrl;
    @Column(name = "CREATE_DATE")
    @Temporal(TemporalType.DATE)
    private Date createDate;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fidCity")
    private Collection<District> districtCollection;
    @JoinColumn(name = "FID_PROVINCE", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Province fidProvince;

    public City() {
    }

    public City(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
    public Collection<District> getDistrictCollection() {
        return districtCollection;
    }

    public void setDistrictCollection(Collection<District> districtCollection) {
        this.districtCollection = districtCollection;
    }

    public Province getFidProvince() {
        return fidProvince;
    }

    public void setFidProvince(Province fidProvince) {
        this.fidProvince = fidProvince;
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
        if (!(object instanceof City)) {
            return false;
        }
        City other = (City) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.kpupilpres.entity.City[ id=" + id + " ]";
    }
    
}
