import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class Mago extends Agent {
    protected  int vida;
    protected  int energia;
    protected  int defesa;



    Jogabilidade Jogabilidae;
    protected void setup() {
        // Inicialize os atributos do guerreiro
        vida = 50;
        energia = 100;
        defesa = 20;
        String toString = null;
        System.out.println("Olá Agente Mago " + getAID().getName() + " Qual sua jogada ?");

        // Adicione um comportamento cíclico para lidar com mensagens recebidas
        addBehaviour(new CyclicBehaviour(this) {
            public void action () {
                ACLMessage msg = myAgent.receive();
                if (msg != null ) {
                    String content = msg.getContent();
                    if (content.equalsIgnoreCase("Ataque")){

                                ACLMessage sendMsg = new ACLMessage (ACLMessage.INFORM);
                                sendMsg.addReceiver (new AID( "Inimigo",AID.ISLOCALNAME));
                                sendMsg.setContent ("Ataque");
                                sendMsg.addUserDefinedParameter("Energia", "" + energia);
                                sendMsg.addUserDefinedParameter("TipoAtaque", "Magia");
                                myAgent.send (sendMsg);

                    }else if (content.equalsIgnoreCase("Especial")) {

                    }else if (content.equalsIgnoreCase("AtaqueEmArea")) {

                    }else if (content.equalsIgnoreCase("AtaqueInimigo")) {

                        String energiaValue = msg.getUserDefinedParameter("Energia");
                        int energiaInimigo = Integer.parseInt(energiaValue);
                        String tipoAtaque = msg.getUserDefinedParameter("TipoAtaque");
                        vida = Jogabilidade.recebeAtaque(vida,energiaInimigo,defesa,tipoAtaque,getLocalName());

                    }else if (content.equalsIgnoreCase("AtaqueInimigoEmArea")) {


                        // Responder ao ataque (por exemplo, calcular dano)
                        System.out.println("Goblin recebeu um ataque!");
                    } else if (content.equalsIgnoreCase("EspecialInimigo")) {


                        // Responder ao ataque (por exemplo, calcular dano)
                        System.out.println("Goblin recebeu um ataque!");
                    }
                    if (vida < 0){
                        ACLMessage sendMsg = new ACLMessage (ACLMessage.INFORM);
                        sendMsg.addReceiver (new AID( "Mestre",AID.ISLOCALNAME));
                        sendMsg.setContent ("AliadoMorreu");
                        sendMsg.addUserDefinedParameter("NumeroAliado", "1" /* + nInimigo */);
                        myAgent.send (sendMsg);

                        getAgent().doDelete();
                    }
                }
            }
        });
    }

    protected void Magia(AID alvo) {
        // Lógica para calcular o sucesso do ataque e o dano
        // Envie uma mensagem de resposta para o agente alvo
    }

    protected void MagiaEmArea(AID[] alvos) {
        // Lógica para calcular o sucesso do ataque em área e o dano para cada alvo
        // Envie mensagens de resposta para os agentes alvos
    }

    protected void BuffOrDebuff(AID[] alvos) {
        energia = energia * 2;
        defesa = defesa * 2;
        vida =+ 10;
    }
}