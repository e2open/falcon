package com.e2open.falcon.controller;

import com.e2open.falcon.model.Model;

public abstract class BrowserCRUDControllerImpl extends BrowserControllerImpl implements BrowserController, BrowserCRUDController {

    public void create() throws Exception {
        super.assertModelsExist();
        for (Model model : models) {
            gotoAddPage(model);
            populateValues(model);
            save(model);
            handlePostErrors();
        }
    }

    // todo implement in interface
    public void edit() throws Exception {
        super.assertModelsExist();
        for (Model model : models) {
            gotoEditPage(model);
            populateValues(model);
            save(model);
            handlePostErrors();
        }
    }

    protected abstract void gotoEditPage(Model model);
}
