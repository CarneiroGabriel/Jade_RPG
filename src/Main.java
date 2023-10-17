import jade.core.Agent;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;

public class Main {
    public static void main(String[] args) {
        jade.core.Runtime runtime = jade.core.Runtime.instance();
        Profile profile = new ProfileImpl();
        AgentContainer container = runtime.createMainContainer(profile);

        try {
            AgentController agente = container.createNewAgent("Jorge", "Jogabilidade.java", null);
            agente.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



