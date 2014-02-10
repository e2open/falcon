package com.e2open.falcon.controller;

import com.e2open.falcon.elements.ElementType;
import com.e2open.falcon.annotations.AutoPopulateOff;
import com.e2open.falcon.browser.BrowserManager;
import com.e2open.falcon.model.Model;
import com.e2open.falcon.view.PageImpl;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public abstract class BrowserControllerImpl implements Controller {
    private static final Logger log = Logger.getLogger(BrowserController.class);
    private WebDriver browser;
    public final PageImpl pageReference;
    protected List<? extends Model> models;
    private String errorMessage;

    public BrowserControllerImpl() {
        pageReference = getView();
    }

    public void setModels(List<? extends Model> models) {
        this.models = models;
    }

    public void setModel(Model model) {
        List<Model> modelList = new ArrayList<Model>();
        modelList.add(model);
        this.models = modelList;
    }

    public WebDriver browser() {
        if (browser == null) browser = BrowserManager.INSTANCE.getDriver();
        return browser;
    }

    protected void assertModelsExist() {
        if (models == null) throw new RuntimeException("Controller has no models defined!");
    }

    protected void setValue(WebElement element, Object value) {
        log.debug(String.format("setting element '%s' to '%s'", element.toString(), value.toString()));
        String tag = element.getTagName();
        if (tag.equals("input")) {
            String type = element.getAttribute("type").toUpperCase();
            ElementType.valueOf(type.toUpperCase()).setValue(element, value);
        } else {
            ElementType.valueOf(tag.toUpperCase()).setValue(element, value);
        }
    }

    // writes values to the page
    public void populateValues(Model model) throws Exception {
        for (Field field : getFields(model)) {
            String name = field.getName();
            if (field.getAnnotation(AutoPopulateOff.class) == null) {
                Object value = getValue(model, name);
                setElementValue(name, value);
            }
        }
    }

    protected List<Field> getFields(Model model) {
        List<Field> fields = new ArrayList<Field>();
        for (Field field : model.getClass().getDeclaredFields()) {
            if (field.getAnnotation(AutoPopulateOff.class) == null) {
                fields.add(field);
            }
        }
        return fields;
    }

    // if the model has a method #get[FieldName] then call it
    // otherwise, just return the value of the field
    private Object getValue(Model model, String name) throws IllegalAccessException, InvocationTargetException {
        try {
            Method method = model.getClass().getMethod("get" + StringUtils.capitalize(name));
            return method.invoke(model, (Object[]) null);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(String.format("Model %s does not have @Getter annotation", model.getClass().toString()));
        }
    }

    private void setElementValue(String name, Object value) throws InvocationTargetException, IllegalAccessException {
        if (value == null || (value instanceof String && StringUtils.isBlank((String) value))) return;
        try {
            callOverrideSetMethod(name, value);
        } catch (NoSuchMethodException e) {
            callDefaultSetMethod(name, value);
        }
    }

    // Call the default method using the webelement from the page class
    private void callDefaultSetMethod(String name, Object value) throws IllegalAccessException, InvocationTargetException {
        try {
            Method method = pageReference.getClass().getDeclaredMethod(name);
            WebElement element = (WebElement) method.invoke(pageReference, (Object[]) null);
            setValue(element, value);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(String.format("Unable to locate method %s in page object %s", name, pageReference.toString()));
        }
    }

    // call the override method if it exists in the controller class
    // the method name is "set" + field name, eg: setName()
    private void callOverrideSetMethod(String name, Object value) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Method method;
        Class classRef;
        if (value instanceof String) {
            classRef = String.class;
        } else if (value instanceof Model) {
            classRef = Model.class;
        } else if (value instanceof List) {
            classRef = List.class;
        } else {
            throw new RuntimeException("Unsure how to detect override method using value: " + value);
        }
        method = getClass().getDeclaredMethod("overridePopulate" + StringUtils.capitalize(name), classRef);
        Object[] parameters = new Object[1];
        parameters[0] = value;
        method.invoke(this, parameters);
    }

    // click and check for POST errors on page
    protected void clickAndCheckForPOSTError(WebElement element) {
        element.click();
        handlePostErrors();
    }

    public void handlePostErrors() {
        try {
            errorMessage = getErrorMessage();
            if (errorMessage != null) throw new RuntimeException("POST Error: " + errorMessage);
        } catch (RuntimeException e) {
            if (e.getMessage().equals("Unable to locate popup window")) {
                // popup closed so can't check errors here
            } else {
                throw e;
            }
        }
    }

    public abstract String getErrorMessage();
}
