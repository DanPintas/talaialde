package es.dasaur.vaadin.mvp;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;

import es.dasaur.mvp.Presenter;
import es.dasaur.mvp.View;
import es.dasaur.talaialde.constants.Messages;
import es.dasaur.vaadin.utils.NotificationUtils;

public abstract class VaadinAbstractView
        <P extends Presenter<?, ? extends View<P>>>
        extends CustomComponent
        implements View<P>, Component {

    private static final long serialVersionUID = -1459845644525324349L;
    
    private transient P presenter;
    
    @Autowired
    private Locale locale;
    
    @Override
    public final P presenter(){
        return presenter;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public final void setPresenter(Presenter<?, ?> presenter) {
        this.presenter = (P) presenter;
    }
    
    @Override
    public final String getMessage(String id, Object... args){
        return Messages.getMessage(id, args, locale);
    }
    
    @Override
    public final void showWarning(String caption, String description){
        NotificationUtils.showWarning(caption, description);
    }
    
}
