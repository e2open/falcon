package com.e2open.falcon.framework.controller;

import com.e2open.falcon.framework.model.Model;

public interface BrowserCRUDController {
    public void gotoAddPage(Model model);
    public void gotoHomePage(Model model);

    public void create() throws Exception;
    public void save(Model model);
}
