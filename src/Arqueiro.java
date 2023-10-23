import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class Arqueiro extends Agent {

    protected  int vida;
    protected  int energia;
    protected  int defesa;
    public boolean miraUp;


    protected void setup() {
        // Inicialize os atributos do guerreiro
        vida = 70;
        energia = 70;
        defesa = 30;
        int energiabkp = energia;
        System.out.println("Olá Agente Arqueiro " + getAID().getName() + " Qual sua jogada ?");

        // Adicione um comportamento cíclico para lidar com mensagens recebidas
        addBehaviour(new CyclicBehaviour(this) {
            public void action() {
                // Verifique e trate as mensagens recebidas
                ACLMessage msg = receive();
                if (msg != null ) {
                    String content = msg.getContent();
                    if (content.equalsIgnoreCase("Ataque")){

                        String NumeroInimigo = msg.getUserDefinedParameter("NumeroInimigo");

                        enviaMsg("Inimigo" + NumeroInimigo,"Ataque","Energia", "" + energia,"TipoAtaque", "Flechada");
                        if(miraUp){
                            energia = energiabkp;
                        }

                    }else if (content.equalsIgnoreCase("Especial")) {

                    }else if (content.equalsIgnoreCase("AtaqueEmArea")) {
                        ataqueEmArea();

                        if(miraUp){
                            energia = energiabkp;
                        }
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
        send(sendMsg);
    }

    public void ataqueEmArea(){


        ACLMessage sendMsg = new ACLMessage (ACLMessage.INFORM);
        sendMsg.addReceiver (new AID( "Inimigo1",AID.ISLOCALNAME));
        sendMsg.addReceiver (new AID( "Inimigo2",AID.ISLOCALNAME));
        sendMsg.addReceiver (new AID( "Inimigo3",AID.ISLOCALNAME));
        sendMsg.setContent ("AtaqueEmArea");
        sendMsg.addUserDefinedParameter("Energia", "" + energia);
        sendMsg.addUserDefinedParameter("TipoAtaque", "Chuva de Flechas");
        send(sendMsg);
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


    protected void Mira() {
        energia = energia + Jogabilidade.D20();

        miraUp = true;
    }
}

