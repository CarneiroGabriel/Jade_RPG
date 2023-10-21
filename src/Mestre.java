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
        while(true){
            int scanner = scanner();
            if (aliadoAtaca){
                ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                msg.addReceiver(new AID("Jorge", AID.ISLOCALNAME));
                msg.setContent(qualAtaqueAliado(scanner));
                send(msg);

                aliadoAtaca= false;
            }else{


                aliadoAtaca= true;
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

    public static int scanner(){
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }
}





