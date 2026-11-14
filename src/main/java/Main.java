import config.ApplicationConfig;
import console.OperationConsoleListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        OperationConsoleListener operationConsoleListener = context.getBean(OperationConsoleListener.class);
        operationConsoleListener.listenUpdates();

        context.close();
    }
}
