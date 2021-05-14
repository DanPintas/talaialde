package es.dasaur.talaialde.main;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import es.dasaur.mvp.AbstractPresenter;
import es.dasaur.mvp.Presenter;
import es.dasaur.mvp.View;
import es.dasaur.talaialde.users.security.SecurityUtils;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class MainPresenterImpl 
        extends AbstractPresenter<MainService, MainView>
        implements MainPresenter, ApplicationContextAware {
    
    private Map<String, Presenter<?,?>> presentersAbiertos;
    
    private ApplicationContext context;
    
    @Autowired
    public MainPresenterImpl(MainView view){
        presentersAbiertos = new ConcurrentHashMap<>();
    }

    @Override
    public final Presenter<?, ?> openPresenter(
            Class<? extends Presenter<?, ?>> tipoAccion, String titulo) {
        Presenter<?, ?> presenter = null;
        View<?> vistaAbierta = getView(titulo);
        if (vistaAbierta != null) {
            view().show(vistaAbierta);
            presenter = vistaAbierta.presenter();
            //vistaAbierta.presenter().init();
        } else {
            presenter = context.getBean(tipoAccion);
            presenter.setMainPresenter(this);
            presenter.setTitle(titulo);
            presenter.init();

            presentersAbiertos.put(titulo, presenter);
            view().addSubview(presenter.view(), titulo);
        }
        return presenter;
    }
    
    @Override
    public View<?> getView(String titulo) {
        Presenter<?, ?> presenter = presentersAbiertos.get(titulo);
        return presenter != null ? presenter.view() : null;
    }

    @Override
    public void closePresenter(String titulo) {
        presentersAbiertos.remove(titulo);
    }

    @Override
    public void logout() {
        SecurityUtils.logout();
    }

    @Override
    public void setApplicationContext(ApplicationContext context)
            throws BeansException {
        this.context = context;
    }
    
}
