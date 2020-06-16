package es.dasaur.talaialde.billing.line;

import es.dasaur.mvp.View;

public interface BillLineView 
        extends View<BillLinePresenter> {

    void setLine(Line line);

}
