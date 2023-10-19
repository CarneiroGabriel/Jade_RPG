import jade.core.Agent;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

public class Main {
    public static void main(String[] args) {
        // Configurar o ambiente JADE
        Profile profile = new ProfileImpl();
        profile.setParameter(Profile.MAIN_HOST, "localhost"); // Endereço IP do host JADE
        profile.setParameter(Profile.MAIN_PORT, "" + 1099); // Porta padrão do JADE

        ContainerController container = jade.core.Runtime.instance().createMainContainer(profile);

        GoblinGuerreiro GoblinGuerreiro = new GoblinGuerreiro();
        AgentController agentController = null;
        try {
            agentController = container.createNewAgent("Inimigo", "GoblinGuerreiro.java", GoblinGuerreiro.getArguments());
            agentController.start();
        } catch (StaleProxyException e) {
            throw new RuntimeException(e);
        }
    }
}



