package es.danpintas.mvp;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import es.danpintas.talaialde.main.MainPresenter;

public abstract class AbstractPresenter 
        <M, V extends View<? extends Presenter<M,V>>>
        implements Presenter<M, V> {
    
    private M model;
    
    private V view;
    
    private String title;
    
    private MainPresenter mainPresenter;
    
    @PostConstruct
    public final void postConstruct() {
        view.setPresenter(this);
        init();
    }
    
    @Override
    public void init(){
        view.init();
    }
    
    @Override
    public final String getMessage(String id, Object... args){
        return view.getMessage(id, args);
    }

    @Override
    public final M model() {
        return model;
    }

    /**
     * Sets the model associated to the presenter.
     * @param model New model to associate.
     */
    @Autowired
    public final void setModel(M model) {
        this.model = model;
    }
    
    @Override
    public final V view() {
        return view;
    }

    /**
     * Sets the view associated to the presenter.
     * @param view New view to associate.
     */
    @Autowired
    public final void setView(V view) {
        this.view = view;
    }

    @Override
    public final String getTitle() {
        return title;
    }

    @Override
    public final void setTitle(String title) {
        this.title = title;
    }

    @Override
    public MainPresenter getMainPresenter() {
        return mainPresenter;
    }

    @Override
    public void setMainPresenter(MainPresenter mainPresenter) {
        this.mainPresenter = mainPresenter;
    }
    
}
