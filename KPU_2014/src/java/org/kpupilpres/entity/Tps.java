/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kpupilpres.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author addin_000
 */
@Entity
@Table(name = "TPS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tps.findAll", query = "SELECT t FROM Tps t"),
    @NamedQuery(name = "Tps.findById", query = "SELECT t FROM Tps t WHERE t.id = :id"),
    @NamedQuery(name = "Tps.findByCode", query = "SELECT t FROM Tps t WHERE t.code = :code"),
    @NamedQuery(name = "Tps.findByChildUrl", query = "SELECT t FROM Tps t WHERE t.childUrl = :childUrl"),
    @NamedQuery(name = "Tps.findByCreateDate", query = "SELECT t FROM Tps t WHERE t.createDate = :createDate")})
public class Tps implements Serializable {

    @Size(max = 50)
    @Column(name = "CODE")
    private String code;
    @Column(name = "CREATE_DATE")
    @Temporal(TemporalType.DATE)
    private Date createDate;
    @Column(name = "PRAHA")
    private BigInteger praha;
    @Column(name = "JOJK")
    private BigInteger jojk;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="ID")
    private Long id;
    @Column(name = "CHILD_URL")
    private String childUrl;
    @JoinColumn(name = "FID_DISTRICT", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private District fidDistrict;

    public Tps() {
    }

    public Tps(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public District getFidDistrict() {
        return fidDistrict;
    }

    public void setFidDistrict(District fidDistrict) {
        this.fidDistrict = fidDistrict;
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
        if (!(object instanceof Tps)) {
            return false;
        }
        Tps other = (Tps) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.kpupilpres.entity.Tps[ id=" + id + " ]";
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
