import jade.core.AID;
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
        energia = 100;
        defesa = 25;
        //System.out.println("Olá Agente Goblin Guerreiro " + getAID().getName() + " Qual sua jogada ?");

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
                        vida = Jogabilidade.recebeAtaque(vida,energiaInimigo,defesa,tipoAtaque, getLocalName());

                        verificaVida();
                    } else if (conteudo.equals("AtaqueEmArea")) {


                        realizarAtaqueEmArea(msg.getSender().getResolversArray());
                    } else if (conteudo.equals("Especial")) {

                        realizarDefesa();
                    } else if (msg.getContent().equals("AtaqueInimigo")) {
                        String NumeroInimigo = msg.getUserDefinedParameter("NumeroInimigo");
                        enviaMsg("Aliado" + NumeroInimigo,"AtaqueInimigo","Energia", "" + energia,"TipoAtaque", "Espadada");
                        /*
                        ACLMessage sendMsg = new ACLMessage (ACLMessage.INFORM);
                        sendMsg.addReceiver (new AID( "Aliado" + NumeroInimigo,AID.ISLOCALNAME));
                        sendMsg.setContent ("AtaqueInimigo");
                        sendMsg.addUserDefinedParameter("Energia", "" + energia);
                        sendMsg.addUserDefinedParameter("TipoAtaque", "Espadada");
                        myAgent.send (sendMsg);*/
                    }else if (msg.getContent().equals("AtaqueInimigoEmArea")) {


                        // Responder ao ataque (por exemplo, calcular dano)
                        System.out.println("Goblin recebeu um ataque!");
                    } else if (msg.getContent().equals("EspecialInimigo")) {


                        // Responder ao ataque (por exemplo, calcular dano)
                        System.out.println("Goblin recebeu um ataque!");
                    }else if(msg.getContent().equals("enviaVidaMestre")){
                        enviaVida();
                    }
                }
            }
        });
    }

    public void enviaMsg(String destino,String conteudo, String key1, String value1, String key2, String value2){
        ACLMessage sendMsg = new ACLMessage (ACLMessage.INFORM);
        sendMsg.addReceiver (new AID( destino,AID.ISLOCALNAME));
        sendMsg.setContent (conteudo);
        sendMsg.addUserDefinedParameter(key1, value1);
        sendMsg.addUserDefinedParameter(key2, value2);
        send (sendMsg);
    }
    public void verificaVida(){
        if(vida<0){
            ACLMessage sendMsg = new ACLMessage(ACLMessage.INFORM);
            sendMsg.addReceiver(new AID("Mestre", AID.ISLOCALNAME));
            sendMsg.setContent("InimigoMorreu");
            sendMsg.addUserDefinedParameter("NomeAgente", getLocalName());
            sendMsg.addUserDefinedParameter("vida", "" + vida);
            send(sendMsg);
            //getAgent().doDelete();
        }
    }

    public void enviaVida(){
        System.out.println("Vida do " + getLocalName() + ": " + vida);

        /*ACLMessage sendMsg = new ACLMessage (ACLMessage.INFORM);
        sendMsg.addReceiver (new AID( "Mestre",AID.ISLOCALNAME));
        sendMsg.setContent ("VidaAliado");
        sendMsg.addUserDefinedParameter("vida", "" + vida);
        sendMsg.addUserDefinedParameter("Aliado", getLocalName());
        send (sendMsg);*/
    }
}
