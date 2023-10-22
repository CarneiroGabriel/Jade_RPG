import jade.core.AID;
import jade.core.Agent;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.lang.acl.ACLMessage;
import jade.wrapper.AgentContainer;

import java.util.Scanner;

public class Mestre extends  Agent{
    protected void setup() {
        // Configurar o ambiente JADE
        Jogabilidade Jogabilidade = new Jogabilidade();
        System.out.println("Olá Mestre " + getAID().getName() + " Qual sua jogada ?");


        boolean aliadoAtaca = true;
        int i = 0;
        int inimigos = 1;
        int aliados = 1;
        while(true){

                ACLMessage msgReceive = receive();
                if (msgReceive != null) {
                    String conteudo = msgReceive.getContent();
                    if (conteudo.equals("InimigoMorreu")) {

                        System.out.println("A batalha foi ganha pelos aliados" );
                        break;
                    }else if (conteudo.equals("AliadoMorreu")){
                        System.out.println("A batalha foi ganha pelos Inimigos" );
                        break;
                    }
                }

            if (aliadoAtaca){
                if(i < inimigos) {
                    i++;
                    ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                    msg.addReceiver(new AID("Jorge", AID.ISLOCALNAME));
                    msg.setContent(qualAtaqueAliado(scanner()));
                    send(msg);

                }else{
                    i = 0;
                    aliadoAtaca= false;
                    continue;
                }
            }else{
                if(i < aliados) {
                    i++;
                    ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                    msg.addReceiver(new AID("Inimigo", AID.ISLOCALNAME));
                    msg.setContent(qualAtaqueInimigo(scanner()));
                    send(msg);

                }else{
                    i = 0;
                    aliadoAtaca= true;
                    continue;
                }
            }

        }

    }


    protected String qualAtaqueAliado(int scan){
        while(true) {
            switch (scan){
                case 1:
                    return "Ataque";
                case 2:
                    return "AtaqueEspecial";
                case 3:
                    return "Especial";
            }
        }
    }

    protected String qualAtaqueInimigo(int scan){
        while(true) {
            switch (scan){
                case 1:
                    return "AtaqueInimigo";
                case 2:
                    return "AtaqueInimigoEmArea";
                case 3:
                    return "EspecialInimigo";
            }
        }
    }

    public int scanner(){
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }
}





