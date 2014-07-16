/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kpupilpres.entity;

import java.io.Serializable;
import java.math.BigInteger;
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
@Table(name = "DISTRICT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "District.findAll", query = "SELECT d FROM District d"),
    @NamedQuery(name = "District.findById", query = "SELECT d FROM District d WHERE d.id = :id"),
    @NamedQuery(name = "District.findByCode", query = "SELECT d FROM District d WHERE d.code = :code"),
    @NamedQuery(name = "District.findByChildUrl", query = "SELECT d FROM District d WHERE d.childUrl = :childUrl"),
    @NamedQuery(name = "District.findByCreateDate", query = "SELECT d FROM District d WHERE d.createDate = :createDate")})
public class District implements Serializable {
    @Column(name = "CREATE_DATE")
    @Temporal(TemporalType.DATE)
    private Date createDate;
    @Column(name = "PRAHA")
    private BigInteger praha;
    @Column(name = "JOJK")
    private BigInteger jojk;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Column(name = "CODE")
    private String code;
    @Column(name = "CHILD_URL")
    private String childUrl;
    @JoinColumn(name = "FID_CITY", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private City fidCity;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fidDistrict")
    private Collection<Tps> tpsCollection;

    public District() {
    }

    public District(Long id) {
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

    public City getFidCity() {
        return fidCity;
    }

    public void setFidCity(City fidCity) {
        this.fidCity = fidCity;
    }

    @XmlTransient
    public Collection<Tps> getTpsCollection() {
        return tpsCollection;
    }

    public void setTpsCollection(Collection<Tps> tpsCollection) {
        this.tpsCollection = tpsCollection;
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
        if (!(object instanceof District)) {
            return false;
        }
        District other = (District) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.kpupilpres.entity.District[ id=" + id + " ]";
    }


    public BigInteger getPraha() {
        return praha;
    }

    public void setPraha(BigInteger praha) {
        this.praha = praha;
    }

    public BigInteger getJojk() {
        return jojk;
    }

    public void setJojk(BigInteger jojk) {
        this.jojk = jojk;
    }
    
}
