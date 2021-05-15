package es.danpintas.talaialde.billing.freeline;

import es.danpintas.mvp.View;

public interface FreeBillLineView 
        extends View<FreeBillLinePresenter> {

    void setLine(JpaFreeLine line);

}
