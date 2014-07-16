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
@Table(name = "PROVINCE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Province.findAll", query = "SELECT p FROM Province p"),
    @NamedQuery(name = "Province.findById", query = "SELECT p FROM Province p WHERE p.id = :id"),
    @NamedQuery(name = "Province.findByCode", query = "SELECT p FROM Province p WHERE p.code = :code"),
    @NamedQuery(name = "Province.findByChildUrl", query = "SELECT p FROM Province p WHERE p.childUrl = :childUrl"),
    @NamedQuery(name = "Province.findByCreateDate", query = "SELECT p FROM Province p WHERE p.createDate = :createDate")})
public class Province implements Serializable {
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
    @JoinColumn(name = "FID_NATIONAL", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Country fidNational;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fidProvince")
    private Collection<City> cityCollection;

    public Province() {
    }

    public Province(Long id) {
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

    public Country getFidNational() {
        return fidNational;
    }

    public void setFidNational(Country fidNational) {
        this.fidNational = fidNational;
    }

    @XmlTransient
    public Collection<City> getCityCollection() {
        return cityCollection;
    }

    public void setCityCollection(Collection<City> cityCollection) {
        this.cityCollection = cityCollection;
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
        if (!(object instanceof Province)) {
            return false;
        }
        Province other = (Province) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.kpupilpres.entity.Province[ id=" + id + " ]";
    }
    
}
