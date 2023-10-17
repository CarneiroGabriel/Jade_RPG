import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class Mago extends Jogabilidade {

    protected void setup() {
        // Inicialize os atributos do guerreiro
        vida = 50;
        energia = 100;
        defesa = 20;
        System.out.println("Olá Agente Mago " + getAID().getName() + " Qual sua jogada ?");

        // Adicione um comportamento cíclico para lidar com mensagens recebidas
        addBehaviour(new CyclicBehaviour(this) {
            public void action() {
                // Verifique e trate as mensagens recebidas
                ACLMessage msg = receive();
                if (msg != null) {
                    String conteudo = msg.getContent();
                    if (conteudo.equals("Magia")) {
                        Magia(msg.getSender());
                    } else if (conteudo.equals("MagiaEmArea")) {
                        MagiaEmArea(msg.getSender().getResolversArray());
                    } else if (conteudo.equals("BuffOrDebuff")) {
                        BuffOrDebuff(msg.getSender().getResolversArray());
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
