import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class GoblinGuerreiro extends Agent {

    protected  int vida;
    protected  int energia;
    protected  int defesa;

    public boolean debuff;
    public boolean defesaUp;
    Jogabilidade Jogabilidade;
    protected void setup() {
        // Inicialize os atributos do guerreiro
        vida = 50;
        energia = 100;
        defesa = 25;
        int defesabkp = defesa;
        debuff = false;
        defesaUp=false;
        int energiabkp = energia;
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

                        if (defesaUp){
                            defesa = defesabkp;
                        }

                        verificaVida();
                    } else if (conteudo.equals("AtaqueEmArea")) {
                        String energiaValue = msg.getUserDefinedParameter("Energia");
                        int energiaInimigo = Integer.parseInt(energiaValue);
                        String tipoAtaque = msg.getUserDefinedParameter("TipoAtaque");
                        vida = Jogabilidade.recebeAtaque(vida,energiaInimigo,defesa,tipoAtaque, getLocalName(),true);

                        if (defesaUp){
                            defesa = defesabkp;
                        }

                        verificaVida();
                    } else if (conteudo.equals("Debuff")) {
                        String energiaValue = msg.getUserDefinedParameter("Energia");
                        int energiaInimigo = Integer.parseInt(energiaValue);
                        String tipoAtaque = msg.getUserDefinedParameter("TipoAtaque");
                        energia = energia - energiaInimigo;
                        debuff= true;

                        System.out.println("Debuff de " + energiaInimigo + " de energia tomado");

                    } else if (msg.getContent().equals("AtaqueInimigo")) {

                        String NumeroInimigo = msg.getUserDefinedParameter("NumeroInimigo");
                        enviaMsg("Aliado" + NumeroInimigo,"AtaqueInimigo","Energia", "" + energia,"TipoAtaque", "Espadada");
                        if (debuff){
                            energia = energiabkp;
                        }

                    }else if (msg.getContent().equals("AtaqueInimigoEmArea")) {

                        ataqueEmArea();
                        if (debuff){
                            energia = energiabkp;
                        }

                        // Responder ao ataque (por exemplo, calcular dano)

                    } else if (msg.getContent().equals("EspecialInimigo")) {
                        defesa = defesa + Jogabilidade.D20();
                        defesaUp = true;

                        System.out.println("Defesa aplicada em " + getLocalName() + " Nova defesa " + defesa);


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
        sendMsg.addReceiver (new AID( "Aliado1",AID.ISLOCALNAME));
        sendMsg.addReceiver (new AID( "Aliado2",AID.ISLOCALNAME));
        sendMsg.addReceiver (new AID( "Aliado3",AID.ISLOCALNAME));
        sendMsg.setContent ("AtaqueInimigo");
        sendMsg.addUserDefinedParameter("Energia", "" + energia);
        sendMsg.addUserDefinedParameter("TipoAtaque", "EspadadaEmArea");
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
    }
}
