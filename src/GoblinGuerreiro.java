import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class GoblinGuerreiro extends Guerreiro {

    protected  int vida;
    protected  int energia;
    protected  int defesa;
    Jogabilidade Jogabilidade;
    protected void setup() {
        // Inicialize os atributos do guerreiro
        vida = 50;
        energia = 15;
        defesa = 25;
        System.out.println("Olá Agente Goblin Guerreiro " + getAID().getName() + " Qual sua jogada ?");

        // Adicione um comportamento cíclico para lidar com mensagens recebidas
        addBehaviour(new CyclicBehaviour(this) {
            public void action() {
                // Verifique e trate as mensagens recebidas
                ACLMessage msg = receive();
                if (msg != null) {
                    String conteudo = msg.getContent();
                    if (conteudo.equals("Ataque")) {
                        String energiaValue = msg.getUserDefinedParameter("Energia");
                        int energiaInimigo = Integer.parseInt(energiaValue);
                        String tipoAtaque = msg.getUserDefinedParameter("TipoAtaque");
                        vida = Jogabilidade.recebeAtaque(vida,energiaInimigo,defesa,tipoAtaque);
                    } else if (conteudo.equals("AtaqueEmArea")) {
                        realizarAtaqueEmArea(msg.getSender().getResolversArray());
                    } else if (conteudo.equals("Especial")) {
                        realizarDefesa();
                    } else if (msg.getContent().equals("AtaqueGoblin")) {
                        // Responder ao ataque (por exemplo, calcular dano)
                        System.out.println("Goblin recebeu um ataque!");
                    }else if (msg.getContent().equals("AtaqueGoblinArea")) {
                        // Responder ao ataque (por exemplo, calcular dano)
                        System.out.println("Goblin recebeu um ataque!");
                    } else if (msg.getContent().equals("EspecialGoblin")) {
                        // Responder ao ataque (por exemplo, calcular dano)
                        System.out.println("Goblin recebeu um ataque!");
                    }
                }
            }
        });
    }
}
