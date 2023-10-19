import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class Mago extends Agent {
    protected  int vida;
    protected  int energia;
    protected  int defesa;

    public int inimigoEscolhido;

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
                    if (content.equalsIgnoreCase("Aguardando acao")){
                        switch (Jogabilidae.scanner()) {
                            case 1:
                                ACLMessage sendMsg = new ACLMessage (ACLMessage.INFORM);
                                sendMsg.addReceiver (new AID( "Inimigo",AID.ISLOCALNAME));
                                sendMsg.setContent ("Ataque");
                                sendMsg.addUserDefinedParameter("Energia", "" + energia);
                                sendMsg.addUserDefinedParameter("TipoAtaque", "Magia");
                                myAgent.send (sendMsg);
                                break;
                            case 2:

                                break;
                            case 3:
                                break;
                        };
                    }else if (content.equalsIgnoreCase("Ataque")) {

                    }else if (content.equalsIgnoreCase("AtaqueEmArea")) {

                    }else {
                        block();
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