import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;

import static jdk.jfr.internal.EventWriterKey.block;

public class Guerreiro extends Jogabilidade {

    private AID[] Inimigos;

    protected void setup() {
        // Inicialize os atributos do guerreiro
        vida = 95;
        energia = 25;
        defesa = 50;
        System.out.println("Olá Agente Guerreiro " + getAID().getName() + " Qual sua jogada ?");

        // Adicione um comportamento cíclico para lidar com mensagens recebidas
        addBehaviour(new CyclicBehaviour(this) {
            protected  void onTick(){
                DFAgentDescription template = new DFAgentDescription();
                ServiceDescription sd = new ServiceDescription();
                try{
                    DFAgentDescription[] result = DFService.search(myAgent, template);
                    System.out.println("Os Inimigos são ");
                    for (int i = 0;i<result.length;++i){
                        Inimigos[i] = result[i].getName();
                        System.out.println(Inimigos[i].getName());
                    }
                } catch (FIPAException fe) {
                    fe.printStackTrace();
                }
            }
            public void action() {
                // Verifique e trate as mensagens recebidas
                ACLMessage msg = receive();
                if (msg != null) {
                    String conteudo = msg.getContent();
                    if (conteudo.equals("Ataque")) {
                        realizarAtaque(msg.getSender());
                    } else if (conteudo.equals("AtaqueEmArea")) {
                        realizarAtaqueEmArea(msg.getSender().getResolversArray());
                    } else if (conteudo.equals("Defesa")) {
                        realizarDefesa();
                    }
                }
            }
        });
    }

    protected void realizarAtaque(AID alvo) {
        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
        msg.addReceiver(alvo);
        msg.setContent("RecebeAtaque");
        send(msg);
        block();
    }

    protected void realizarAtaqueEmArea(AID[] alvos) {
        // Lógica para calcular o sucesso do ataque em área e o dano para cada alvo
        // Envie mensagens de resposta para os agentes alvos
    }

    protected void realizarDefesa() {
        defesa = defesa * 2;
    }
}
