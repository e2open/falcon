package com.e2open.falcon.framework.elements;

import com.e2open.falcon.framework.waiters.Waiter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public enum ElementType {
    TEXT {
        @Override
        public void setValue(WebElement element, Object value) {
            element.clear();
            element.sendKeys(value.toString());
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
    }, RADIO {
        @Override
        public void setValue(WebElement element, Object value) {
            element.click();
        }
    }, SUBMIT {
        @Override
        public void setValue(WebElement element, Object value) {
            element.click();
        }
    }, PASSWORD {
        @Override
        public void setValue(WebElement element, Object value) {
            element.clear();
            element.sendKeys(value.toString());
        }
    }, LINK {
        @Override
        public void setValue(WebElement element, Object value) {
            element.click();
        }
    }, DIV {
        @Override
        public void setValue(WebElement element, Object value) {
            element.click();
        }
    }, SELECT {
        @Override
        public void setValue(WebElement element, Object value) {
            Select selectList = new Select(element);
            Waiter waiter = new Waiter();
            waiter.waitUntilSelectOptionPresent(selectList, (String) value);
            selectList.selectByVisibleText(value.toString());
        }
    };

    public abstract void setValue(WebElement element, Object value);
}