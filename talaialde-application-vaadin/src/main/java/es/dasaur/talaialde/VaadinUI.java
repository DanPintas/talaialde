package es.dasaur.talaialde;

import static es.dasaur.talaialde.constants.Messages.APP_TITLE;

import com.vaadin.annotations.Widgetset;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.communication.PushMode;
import com.vaadin.shared.ui.ui.Transport;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Component;
import com.vaadin.ui.UI;

import es.dasaur.mvp.View;
import es.dasaur.talaialde.main.MainPresenter;
import es.dasaur.talaialde.users.LoginPresenter;
import es.dasaur.talaialde.users.security.SecurityUtils;

@SpringUI
@PreserveOnRefresh
@Theme("mytheme")
@Push(value = PushMode.MANUAL, transport=Transport.WEBSOCKET_XHR)
@PropertySource("classpath:application.properties")
@Widgetset("com.vaadin.v7.Vaadin7WidgetSet")
public class VaadinUI extends UI {
    
    private static final long serialVersionUID = 9119967750005352962L;
    
    @Autowired
    private MainPresenter mainPresenter;
    
    @Autowired
    private LoginPresenter loginPresenter;
    
    @Autowired
    private Locale locale;

    @Override
    protected void init(VaadinRequest request) {
        this.getSession().setLocale(locale);
        Page.getCurrent().setTitle(mainPresenter.getMessage(APP_TITLE));
        
        if(SecurityUtils.isLoggedIn()){
            loadMainView();
        } else {
            setView(loginPresenter.view());
        }
    }

    private void setView(View<?> view) {
        setContent((Component)view);
    }

    public void loadMainView() {
        mainPresenter.init();
        setView(mainPresenter.view());
    }

}
