package com.e2open.falcon.framework.elements;

import com.e2open.falcon.framework.waiters.Waiter;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public enum ElementType {
    TEXT {
        @Override
        public void setValue(WebElement element, Object value) {
            element.clear();
            element.sendKeys(value.toString());
        }

        @Override
        public String getValue(WebElement element) {
            return element.getAttribute("value");
        }
    }, CHECKBOX {
        @Override
        public void setValue(WebElement element, Object value) {
            if (value instanceof Boolean) {
                if ((Boolean) value) {
                    if (!element.isSelected()) element.click();
                } else {
                    if (element.isSelected()) element.click();
                }
            } else if (value instanceof String) {
                if (((String) value).toLowerCase().equals("true")) {
                    if (!element.isSelected()) element.click();
                } else if (((String) value).toLowerCase().equals("false")) {
                    if (element.isSelected()) element.click();
                } else {
                    throw new RuntimeException("Not sure how to handle setting radio using: " + value.toString());

                }
            } else {
                throw new RuntimeException("Not sure how to handle setting radio using: " + value.toString());
            }
        }

        @Override
        public String getValue(WebElement element) {
            return element.isSelected() ? "true" : "false";
        }
    }, RADIO {
        @Override
        public void setValue(WebElement element, Object value) {
            element.click();
        }

        @Override
        public String getValue(WebElement element) {
            return element.isSelected() ? "true" : "false";
        }
    }, SUBMIT {
        @Override
        public void setValue(WebElement element, Object value) {
            element.click();
        }

        @Override
        public String getValue(WebElement element) {
            return null;
        }
    }, PASSWORD {
        @Override
        public void setValue(WebElement element, Object value) {
            element.clear();
            element.sendKeys(value.toString());
        }

        @Override
        public String getValue(WebElement element) {
            return element.getAttribute("value");
        }
    }, LINK {
        @Override
        public void setValue(WebElement element, Object value) {
            element.click();
        }

        @Override
        public String getValue(WebElement element) {
            return null;
        }
    }, DIV {
        @Override
        public void setValue(WebElement element, Object value) {
            element.click();
        }

        @Override
        public String getValue(WebElement element) {
            return null;
        }
    }, SELECT {
        @Override
        public void setValue(WebElement element, Object value) {
            Select selectList = new Select(element);
            Waiter waiter = new Waiter();
            waiter.waitUntilSelectOptionPresent(selectList, (String) value);
            selectList.selectByVisibleText(value.toString());
        }

        @Override
        public String getValue(WebElement element) {
            Select selectList = new Select(element);
            return selectList.getFirstSelectedOption().getText();
        }

        public Object getAllValues(WebElement element) {
            Select selectList = new Select(element);
            Function<WebElement, String> getTextFunctor = new Function<WebElement, String>() {
                @Override
                public String apply(WebElement input) {
                    return input.getText();
                }
            };
            return Lists.transform(selectList.getAllSelectedOptions(), getTextFunctor);
        }
    };

    public abstract void setValue(WebElement element, Object value);

    public abstract String getValue(WebElement element);
}