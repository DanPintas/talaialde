package es.danpintas.talaialde.main;

import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.VerticalLayout;
import es.danpintas.mvp.Presenter;
import es.danpintas.mvp.View;
import es.danpintas.talaialde.constants.ConstantesEstilo;
import es.danpintas.talaialde.constants.Messages;
import es.danpintas.talaialde.main.components.MainBar;
import es.danpintas.talaialde.main.components.PanelPrincipal;
import es.danpintas.talaialde.users.security.SecurityUtils;
import es.danpintas.vaadin.mvp.VaadinAbstractView;
import es.danpintas.vaadin.security.VaadinSecurityUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;

@SpringComponent
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class MainViewImpl
    extends VaadinAbstractView<MainPresenter>
    implements MainView {

  private static final long serialVersionUID = 1893863211443388996L;

  private TabSheet tabSheet;

  private PanelPrincipal panelPrincipal;

  @Override
  public final void init() {
    VerticalLayout vlMain = new VerticalLayout();
    vlMain.setMargin(false);
    vlMain.setSpacing(false);
    vlMain.setHeight("100%");

    MainPresenter mainPresenter = presenter();
    MenuBar menu = new MainBar(mainPresenter);
    HorizontalLayout hlMenuBar = new HorizontalLayout(
        menu, getBotonCerrarSesion());
    hlMenuBar.setSpacing(false);
    hlMenuBar.setWidth("100%");
    hlMenuBar.setExpandRatio(menu, 1.0f);
    vlMain.addComponent(hlMenuBar);

    tabSheet = new TabSheet();
    tabSheet.setCloseHandler(this::cerrarDetalle);
    vlMain.addComponent(tabSheet);
    vlMain.setExpandRatio(tabSheet, 1.0f);
    tabSheet.setVisible(false);

    panelPrincipal = new PanelPrincipal();
    vlMain.addComponent(panelPrincipal);
    vlMain.setExpandRatio(panelPrincipal, 1.0f);

    VerticalLayout vlMainWrapper = new VerticalLayout(vlMain);
    vlMainWrapper.setMargin(false);
    vlMainWrapper.setComponentAlignment(vlMain, Alignment.TOP_CENTER);
    vlMainWrapper.setSizeFull();

    setCompositionRoot(vlMainWrapper);
    setSizeFull();
  }

  private Component getBotonCerrarSesion() {
    MenuBar menuBar = new MenuBar();
    MenuItem miUser = menuBar.addItem(SecurityUtils.getUserName(),
        FontAwesome.USER, null);
    MenuItem miLogout = miUser.addItem(getMessage(Messages.MENU_LOGOUT),
        FontAwesome.SIGN_OUT, event -> logout());
    menuBar.addStyleName("barra-menus-mopa");
    miLogout.setStyleName("link");
    return menuBar;
  }

  private void logout() {
    SecurityUtils.logout();
    VaadinSecurityUtils.logout();
  }

  private void cerrarDetalle(TabSheet tabSheet, Component component) {
    VaadinAbstractView<?> view = (VaadinAbstractView<?>) component;
    Presenter<?, ?> presenter = view.presenter();
    MainPresenter mainPresenter = (MainPresenter) presenter();
    mainPresenter.closePresenter(presenter.getTitle());

    Tab tab = tabSheet.getTab(component);
    int pos = tabSheet.getTabPosition(tab);
    tabSheet.removeComponent(component);
    tabSheet.setSelectedTab(pos - 1);
    tabSheet.setSelectedTab(pos);

    if (tabSheet.getComponentCount() == 0) {
      tabSheet.setVisible(false);
      panelPrincipal.setVisible(true);
    }
  }

  @Override
  public final void addSubview(View<?> view, String titulo) {
    Component comp = (Component) view;
    comp.addStyleName(ConstantesEstilo.VIEW);
    Tab tab = tabSheet.addTab(comp, titulo);
    tab.setClosable(true);
    tabSheet.setSelectedTab(comp);
    tabSheet.setSizeFull();
    tabSheet.setVisible(true);
    panelPrincipal.setVisible(false);
  }

  @Override
  public final void show(View<?> vistaAbierta) {
    Component comp = (Component) vistaAbierta;
    tabSheet.setSelectedTab(comp);
  }

  @Override
  public void closeView(String title) {
    cerrarDetalle(tabSheet, (Component) presenter().getView(title));
  }

}
