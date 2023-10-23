import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class Mago extends Agent {
    protected  int vida;
    protected  int energia;
    protected  int defesa;

    protected void setup() {
        // Inicialize os atributos do guerreiro
        vida = 50;
        energia = 100;
        defesa = 20;

        // Adicione um comportamento cíclico para lidar com mensagens recebidas
        addBehaviour(new CyclicBehaviour(this) {
            public void action () {
                ACLMessage msg = myAgent.receive();
                if (msg != null ) {
                    String content = msg.getContent();
                    if (content.equalsIgnoreCase("Ataque")){

                                String NumeroInimigo = msg.getUserDefinedParameter("NumeroInimigo");

                                enviaMsg("Inimigo" + NumeroInimigo,"Ataque","Energia", "" + energia,"TipoAtaque", "Magia");


                    }else if (content.equalsIgnoreCase("Especial")) {
                        DeBuff();
                    }else if (content.equalsIgnoreCase("AtaqueEmArea")) {
                        ataqueEmArea();
                    }else if (content.equalsIgnoreCase("AtaqueInimigo")) {

                        String energiaValue = msg.getUserDefinedParameter("Energia");
                        int energiaInimigo = Integer.parseInt(energiaValue);
                        String tipoAtaque = msg.getUserDefinedParameter("TipoAtaque");
                        vida = Jogabilidade.recebeAtaque(vida,energiaInimigo,defesa,tipoAtaque,getLocalName());

                        verificaVida();

                    }else if (content.equalsIgnoreCase("AtaqueInimigoEmArea")) {

                        String energiaValue = msg.getUserDefinedParameter("Energia");
                        int energiaInimigo = Integer.parseInt(energiaValue);
                        String tipoAtaque = msg.getUserDefinedParameter("TipoAtaque");
                        vida = Jogabilidade.recebeAtaque(vida,energiaInimigo,defesa,tipoAtaque, getLocalName(),true);

                        verificaVida();
                    } else if (content.equalsIgnoreCase("EspecialInimigo")) {


                        // Responder ao ataque (por exemplo, calcular dano)
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

    public void ataqueEmArea(){
        ACLMessage sendMsg = new ACLMessage (ACLMessage.INFORM);
        sendMsg.addReceiver (new AID( "Inimigo1",AID.ISLOCALNAME));
        sendMsg.addReceiver (new AID( "Inimigo2",AID.ISLOCALNAME));
        sendMsg.addReceiver (new AID( "Inimigo3",AID.ISLOCALNAME));
        sendMsg.setContent ("AtaqueEmArea");
        sendMsg.addUserDefinedParameter("Energia", "" + energia);
        sendMsg.addUserDefinedParameter("TipoAtaque", "Magia em Area");
        send (sendMsg);
    }

    public void verificaVida(){
        if (vida < 0){
            ACLMessage sendMsg = new ACLMessage (ACLMessage.INFORM);
            sendMsg.addReceiver (new AID( "Mestre",AID.ISLOCALNAME));
            sendMsg.setContent ("AliadoMorreu");
            sendMsg.addUserDefinedParameter("NomeAgente", getLocalName());
            sendMsg.addUserDefinedParameter("vida", "" + vida);
            send(sendMsg);
            //getAgent().doDelete();
        }
    }

    protected void DeBuff() {

        ACLMessage sendMsg = new ACLMessage (ACLMessage.INFORM);
        sendMsg.addReceiver (new AID( "Inimigo1",AID.ISLOCALNAME));
        sendMsg.addReceiver (new AID( "Inimigo2",AID.ISLOCALNAME));
        sendMsg.addReceiver (new AID( "Inimigo3",AID.ISLOCALNAME));
        sendMsg.setContent ("Debuff");
        sendMsg.addUserDefinedParameter("Energia", "" + Jogabilidade.D20());
        sendMsg.addUserDefinedParameter("TipoAtaque", "Debuff em Area");
        send (sendMsg);
    }
}