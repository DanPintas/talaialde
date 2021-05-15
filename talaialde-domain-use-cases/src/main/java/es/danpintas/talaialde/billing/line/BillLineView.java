package es.danpintas.talaialde.billing.line;

import es.danpintas.mvp.View;

public interface BillLineView 
        extends View<BillLinePresenter> {

    void setLine(JpaLine line);

}
