package es.dasaur.vaadin.components;

import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 * Modal Dialog for action confirming
 * 
 * @author dsalas
 *
 */
public class ConfirmWindow extends Window {

    private static final long serialVersionUID = 189784687L;
    
    private Runnable cancelAction;

    public ConfirmWindow(String title, String description,
            String acceptCaption, String cancelCaption, 
            Runnable acceptAction, Runnable cancelAction) {
        this(title, description, acceptCaption, cancelCaption, acceptAction);
        this.cancelAction = cancelAction;
    }
    
    public ConfirmWindow(String title, String description,
            String acceptCaption, String cancelCaption, 
            Runnable caption) {
        super(title);
        setModal(true);
        setResizable(false);
        setDraggable(false);
        center();
        
        Label lbDesc = new Label(description);
        lbDesc.setContentMode(ContentMode.HTML);
        VerticalLayout vlDesc = new VerticalLayout(lbDesc);
        vlDesc.setMargin(true);
        
        Button btAccept = new Button(acceptCaption, FontAwesome.CHECK);
        btAccept.setSizeFull();
        Button btCancel = new Button(cancelCaption, FontAwesome.TIMES);
        btCancel.setSizeFull();
        HorizontalLayout lowerActions = new HorizontalLayout(
                btAccept, btCancel);
        lowerActions.setSizeFull();
        lowerActions.setMargin(true);
        lowerActions.setSpacing(true);
        
        VerticalLayout layout = new VerticalLayout(
                vlDesc, lowerActions);
        layout.setMargin(true);
        setContent(layout);
        
        btAccept.addClickListener(event -> execute(caption));
        btCancel.addClickListener(event -> close());
        addCloseListener(event -> closeAction());
    }

    private void closeAction() {
        if (cancelAction != null) {
            UI.getCurrent().getSession().access(cancelAction);
        }
    }

    private void execute(Runnable accion) {
        cancelAction = null;
        close();
        UI.getCurrent().getSession().access(accion);
    }
    
    public static void show(String title, String description,
            String acceptCaption, String cancelCaption, 
            Runnable acceptAction, Runnable cancelAction) {
        UI.getCurrent().addWindow(new ConfirmWindow(title, description,
                acceptCaption, cancelCaption, cancelAction));
    }

    public static void show(String title, String description,
            String acceptCaption, String cancelCaption, Runnable action) {
        UI.getCurrent().addWindow(new ConfirmWindow(title, description,
                acceptCaption, cancelCaption, action));
    }

}
