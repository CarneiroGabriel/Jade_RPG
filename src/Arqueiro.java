import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class Arqueiro extends Jogabilidade {

    protected void setup() {
        // Inicialize os atributos do guerreiro
        vida = 70;
        energia = 70;
        defesa = 30;
        System.out.println("Olá Agente Arqueiro " + getAID().getName() + " Qual sua jogada ?");

        // Adicione um comportamento cíclico para lidar com mensagens recebidas
        addBehaviour(new CyclicBehaviour(this) {
            public void action() {
                // Verifique e trate as mensagens recebidas
                ACLMessage msg = receive();
                if (msg != null) {
                    String conteudo = msg.getContent();
                    if (conteudo.equals("Flechada")) {
                        Flechada(msg.getSender());
                    } else if (conteudo.equals("Chuva-de-Flecha")) {
                        ChuvaDeFlecha(msg.getSender().getResolversArray());
                    } else if (conteudo.equals("Mira")) {
                        Mira();
                    }
                }
            }
        });
    }

    protected void Flechada(AID alvo) {
        // Lógica para calcular o sucesso do ataque e o dano
        // Envie uma mensagem de resposta para o agente alvo
    }

    protected void ChuvaDeFlecha(AID[] alvos) {
        // Lógica para calcular o sucesso do ataque em área e o dano para cada alvo
        // Envie mensagens de resposta para os agentes alvos
    }

    protected void Mira() {
        energia = energia * 2;
    }
}
