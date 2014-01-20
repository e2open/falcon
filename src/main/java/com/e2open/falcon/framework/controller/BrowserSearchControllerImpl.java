package com.e2open.falcon.framework.controller;


import com.e2open.falcon.framework.model.Model;

public abstract class BrowserSearchControllerImpl extends BrowserControllerImpl implements BrowserSearchController {
    public void search() {
        super.assertModelsExist();
        for (Model model : models) {
            try {
                populateValues(model);
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage(), e);
            }
            doSearch();
        }
    }

    public void doSearch() {
        clickAndCheckForPOSTError(pageReference.searchButton());
    }
}
