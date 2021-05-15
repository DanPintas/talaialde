package es.dasaur.talaialde.users;

import static es.dasaur.talaialde.constants.Messages.*;

import com.vaadin.v7.ui.PasswordField;
import com.vaadin.v7.ui.TextField;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.util.StringUtils;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import es.dasaur.talaialde.VaadinUI;
import es.dasaur.talaialde.constants.ConstantesEstilo;
import es.dasaur.talaialde.main.components.AppLogo;
import es.dasaur.vaadin.mvp.VaadinAbstractView;
import es.dasaur.vaadin.utils.NotificationUtils;

@SpringComponent
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class LoginViewImpl extends VaadinAbstractView<LoginPresenter>
        implements LoginView {

    private static final long serialVersionUID = -4965017203966072203L;

    @Override
    public void init() {
        Component login = buildLoginForm();

        VerticalLayout content = new VerticalLayout();
        content.setMargin(false);
        content.setSizeFull();

        content.addComponent(login);
        content.setComponentAlignment(login, Alignment.MIDDLE_CENTER);

        VerticalLayout vlLoginWrapper = new VerticalLayout(content);
        vlLoginWrapper.setMargin(false);
        vlLoginWrapper.setComponentAlignment(content, Alignment.MIDDLE_CENTER);
        vlLoginWrapper.setSizeFull();

        setCompositionRoot(vlLoginWrapper);
        setSizeFull();
    }

    private Component buildLoginForm() {
        final VerticalLayout loginPanel = new VerticalLayout();
        loginPanel.setMargin(false);

        loginPanel.setSizeUndefined();
        loginPanel.setMargin(true);
        loginPanel.setSpacing(true);

        AppLogo appLogo = new AppLogo();
        loginPanel.addComponent(appLogo);
        loginPanel.setComponentAlignment(appLogo, Alignment.TOP_CENTER);

        Component fields = buildFields();
        loginPanel.addComponent(fields);
        loginPanel.setComponentAlignment(fields, Alignment.TOP_CENTER);

        return loginPanel;
    }

    private Component buildFields() {

        final TextField username = new TextField();
        username.setIcon(FontAwesome.USER);
        username.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        username.setValidationVisible(false);
        username.focus();

        final PasswordField password = new PasswordField();
        password.setIcon(FontAwesome.LOCK);
        password.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        password.setValidationVisible(false);
        password.setImmediate(true);
        password.setNullRepresentation("");

        final Button signin = new Button(getMessage(LOGIN_DO_LOGIN),
                FontAwesome.SIGN_IN);
        signin.setClickShortcut(KeyCode.ENTER);
        
        username.addStyleName(ConstantesEstilo.FULL_WIDTH);
        password.addStyleName(ConstantesEstilo.FULL_WIDTH);
        signin.addStyleName(ConstantesEstilo.FULL_WIDTH);

        final VerticalLayout fields = new VerticalLayout(username, password,
                signin);
        fields.setMargin(false);
        fields.setSpacing(true);

        fields.setComponentAlignment(username, Alignment.BOTTOM_CENTER);
        fields.setComponentAlignment(password, Alignment.BOTTOM_CENTER);
        fields.setComponentAlignment(signin, Alignment.BOTTOM_CENTER);

        fields.setSizeFull();

        username.setTabIndex(1);
        password.setTabIndex(2);
        signin.setTabIndex(3);

        signin.addClickListener(
                event -> doLogin(username.getValue(), password.getValue()));

        return fields;
    }

    private void doLogin(String username, String password) {
        if (StringUtils.isEmpty(username)) {
            NotificationUtils.showWarning(getMessage(LOGIN_ERROR_TITLE),
                    getMessage(LOGIN_ERROR_NO_USER));
        } else if (presenter().login(username, password)) {
            VaadinUI ui = (VaadinUI) UI.getCurrent();
            ui.loadMainView();
        }
    }

}
