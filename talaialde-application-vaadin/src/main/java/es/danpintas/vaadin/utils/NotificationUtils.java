package es.danpintas.vaadin.utils;

import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.Notification;

public class NotificationUtils {

  private NotificationUtils() {
    // utils class
  }

  private static final int NOTIFICATION_DELAY = 3000;

  public static final void showWarning(String caption, String description) {
    showNotification(caption, description, Notification.Type.WARNING_MESSAGE);
  }

  public static final void showWarning(String description) {
    showWarning(null, description);
  }

  public static void showMessage(String caption, String description) {
    showNotification(caption, description, Notification.Type.HUMANIZED_MESSAGE);
  }

  public static void showMessage(String description) {
    showMessage(null, description);
  }

  public static void showError(String caption, String description) {
    showNotification(caption, description, Notification.Type.ERROR_MESSAGE);
  }

  public static void showError(String description) {
    showError(null, description);
  }

  private static void showNotification(String caption, String description,
      Notification.Type type) {
    Notification notif = new Notification(caption, description, type);
    notif.setHtmlContentAllowed(true);
    notif.setDelayMsec(NOTIFICATION_DELAY);
    notif.setPosition(Position.BOTTOM_CENTER);
    notif.setHtmlContentAllowed(true);
    notif.show(Page.getCurrent());
  }

}
