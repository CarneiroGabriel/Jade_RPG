import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;

import java.util.Scanner;

public class Mestre extends  Agent{
    protected void setup() {
        // Configurar o ambiente JADE
        Jogabilidade Jogabilidade = new Jogabilidade();
        System.out.println("Olá Mestre " + getAID().getName() + " Qual sua jogada ?");


        Jogabilidade.scanner();
        boolean aliadoAtaca = true;
        int i = 1;
        while(true){
            int scanner = scanner();
            if (aliadoAtaca){
                if(i <= 3) {
                    ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                    msg.addReceiver(new AID("Jorge", AID.ISLOCALNAME));
                    msg.setContent(qualAtaqueAliado(scanner));
                    send(msg);
                    i++;
                }else{
                    i = 0;
                    aliadoAtaca= false;
                }
            }else{
                if(i <= 3) {
                    ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                    msg.addReceiver(new AID("Inimigo", AID.ISLOCALNAME));
                    msg.setContent(qualAtaqueInimigo(scanner));
                    send(msg);
                    i++;
                }else{
                    i = 0;
                    aliadoAtaca= true;
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

    public static int scanner(){
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }
}





