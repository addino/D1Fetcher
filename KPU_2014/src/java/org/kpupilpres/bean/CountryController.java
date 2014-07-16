package org.kpupilpres.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import org.kpupilpres.bean.util.JsfUtil;
import org.kpupilpres.bean.util.PaginationHelper;
import org.kpupilpres.ejb.CityFacade;
import org.kpupilpres.ejb.CountryFacade;
import org.kpupilpres.ejb.DistrictFacade;
import org.kpupilpres.ejb.ProvinceFacade;
import org.kpupilpres.ejb.TpsFacade;
import org.kpupilpres.entity.Country;

@ManagedBean(name = "countryController")
@SessionScoped
public class CountryController implements Serializable {

    private Country current;
    private DataModel items = null;
    @EJB
    private CountryFacade ejbFacade;
    @EJB
    private ProvinceFacade provFacade;
    @EJB
    private CityFacade cityFacade;
    @EJB
    private DistrictFacade distFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public CountryController() {
    }

    public Country getSelected() {
        if (current == null) {
            current = new Country();
            selectedItemIndex = -1;
        }
        return current;
    }

    private CountryFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (Country) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Country();
        selectedItemIndex = -1;
        return "Create";
    }

    public String generateProvince() {
        try {
            getFacade().generateProvince();
        } catch (IOException ex) {
            Logger.getLogger(CountryController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Create";
    }

    public String generateCity() {
        try {
            provFacade.generateCity();
        } catch (IOException ex) {
            Logger.getLogger(CountryController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Create";
    }

    public String generateDistrict() {
        try {
            cityFacade.generateDistrict();
        } catch (IOException ex) {
            Logger.getLogger(CountryController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Create";
    }

    public String generateTps() {
        try {
            distFacade.generateTPSData();
        } catch (Exception ex) {
            Logger.getLogger(CountryController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CountryCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Country) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CountryUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Country) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CountryDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    @FacesConverter(forClass = Country.class)
    public static class CountryControllerConverter implements Converter {

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            CountryController controller = (CountryController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "countryController");
            return controller.ejbFacade.find(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuffer sb = new StringBuffer();
            sb.append(value);
            return sb.toString();
        }

        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Country) {
                Country o = (Country) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + CountryController.class.getName());
            }
        }
    }
}
