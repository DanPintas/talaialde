package es.dasaur.talaialde.billing.freeline;

import es.dasaur.mvp.View;

public interface FreeBillLineView 
        extends View<FreeBillLinePresenter> {

    void setLine(JpaFreeLine line);

}
