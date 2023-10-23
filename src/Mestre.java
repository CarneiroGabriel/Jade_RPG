import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

import java.util.*;

public class Mestre extends  Agent{
    protected void setup() {
        // Configurar o ambiente JADE
        //System.out.println("Olá Mestre " + getAID().getName() + " Qual sua jogada ?");


        boolean aliadoAtaca = true;
        int i = 0;
        int[] inimigos = {1,1,1};
        int[] aliados = {1,1,1};
        while(true){

            int[] posicaoAliados = encontrarPosicoesDosUns(aliados);
            int[] posicaoInimigos = encontrarPosicoesDosUns(inimigos);

                ACLMessage msgReceive = receive();
                if (msgReceive != null) {
                    String conteudo = msgReceive.getContent();
                    if (conteudo.equals("InimigoMorreu")) {

                        int vida = Integer.parseInt(msgReceive.getUserDefinedParameter("vida"));
                        String NomeAgente = msgReceive.getUserDefinedParameter("NomeAgente");
                        if(vida<=0) {
                            int numero = inimigoMorto(NomeAgente);
                            System.out.println("O inimigo " + numero + " Morreu");
                            inimigos[numero - 1] = 0;

                            posicaoInimigos = encontrarPosicoesDosUns(inimigos);
                            if (posicaoInimigos.length == 0) {

                                System.out.println("A batalha foi ganha pelos aliados");
                                break;
                            }
                        }

                    }else if (conteudo.equals("AliadoMorreu")){
                        int vida = Integer.parseInt(msgReceive.getUserDefinedParameter("vida"));
                        if(vida<=0) {

                            String NomeAgente = msgReceive.getUserDefinedParameter("NomeAgente");
                            int numero = aliadoMorto(NomeAgente);
                            System.out.println("O Aliado " + numero + " Morreu");
                            aliados[numero - 1] = 0;

                            posicaoAliados = encontrarPosicoesDosUns(aliados);

                            if (posicaoAliados.length == 0) {

                                System.out.println("A batalha foi ganha pelos Inimigos");
                                break;
                            }
                        }
                    }
                }

            if (aliadoAtaca){
                if(i < contarUns(aliados)) {
                    int vez = posicaoAliados[i] + 1;


                    ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                    msg.addReceiver(new AID("Aliado" + vez, AID.ISLOCALNAME));

                    msg.addUserDefinedParameter("NumeroInimigo", "" + EscolherInimigo(inimigos, "Inimigo"));
                    dialogoBatalha();
                    msg.setContent(qualAtaqueAliado(scanner()));
                    send(msg);
                    i++;
                }else{
                    i = 0;
                    aliadoAtaca= false;
                }
            }else{
                if(i < contarUns(inimigos)) {
                    int vez = posicaoInimigos[i] + 1;
                    ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                    msg.addReceiver(new AID("Inimigo" + vez , AID.ISLOCALNAME));
                    msg.addUserDefinedParameter("NumeroInimigo", "" + EscolherInimigo(aliados, "Aliado"));
                    dialogoBatalha();
                    msg.setContent(qualAtaqueInimigo(scanner()));
                    send(msg);
                    i++;

                }else{
                    i = 0;
                    aliadoAtaca= true;
                }
            }
            pedeVidaBatalha(inimigos);
            pedeVidaBatalha(aliados);

            }

        }


    protected String qualAtaqueAliado(int scan){
        while(true) {
            switch (scan){
                case 1:
                    return "Ataque";
                case 2:
                    return "AtaqueEmArea";
                case 3:
                    return "Especial";
            }
        }
    }



    public void pedeVidaBatalha(int[] aliadosOuInimigos) {
        List<Integer> posicoesDosUns = new ArrayList<>();

        for (int i = 0; i < aliadosOuInimigos.length; i++) {
            if (aliadosOuInimigos[i] == 1) {
                posicoesDosUns.add(i);
            }
        }

        for (int aliadosVivos : posicoesDosUns) {
                int nAliado = aliadosVivos + 1;
                ACLMessage msgVida = new ACLMessage(ACLMessage.INFORM);
                msgVida.addReceiver(new AID("Aliado" + nAliado, AID.ISLOCALNAME));
                msgVida.setContent("enviaVidaMestre");
                send(msgVida);
        }
    }
    public int inimigoMorto(String nomeInimigo){
         if(Objects.equals(nomeInimigo, "Inimigo1")){
             return 1;
         }else if(Objects.equals(nomeInimigo, "Inimigo2")){
             return 2;
         }else if(Objects.equals(nomeInimigo, "Inimigo3")){
             return 3;
         }
        return 0;
    }

    public int aliadoMorto(String nomeAliado){
        if(Objects.equals(nomeAliado, "Aliado1")){
            return 1;
        }else if(Objects.equals(nomeAliado, "Aliado2")){
            return 2;
        }else if(Objects.equals(nomeAliado, "Aliado3")){
            return 3;
        }
        return 0;
    }

    public void dialogoBatalha(){
        System.out.println("1 - Ataque");
        System.out.println("2 - Ataque em Area");
        System.out.println("3 - Especial");
        System.out.println("Qual Seu Ataque ?");
    }

    public static int contarUns(int[] array) {
        int count = 0;

        for (int elemento : array) {
            if (elemento == 1) {
                count++;
            }
        }

        return count;
    }

    protected String qualAtaqueInimigo(int scan){
        while(true) {
            switch (scan){
                case 1:
                    return "AtaqueInimigo";
                case 2:
                    return "AtaqueInimigoEmArea";
                case 3:
                    return "EspecialInimigo";
            }
        }
    }

    public int EscolherInimigo(int[] inimigos, String nomeInimigo){
         // 0 representa indisponível, 1 representa disponível


        while (true) {

            System.out.println("Escolha seu "+ nomeInimigo +" :");
            for (int i = 0; i < inimigos.length; i++) {
                if (inimigos[i] == 1) {
                    int f = 1 + i;
                    System.out.println(nomeInimigo + " " + f + " vivo");

                }
            }

            int scanner = scanner();
            if (scanner <= inimigos.length) {
                if (inimigos[scanner - 1] == 1) {
                    System.out.println("Você irá atacar o Inimigo " + scanner);
                    return scanner;
                } else {

                    System.out.println("Inimigo " + scanner + " esta morto já");

                }
                }
            }
        }

    public int[] encontrarPosicoesDosUns(int[] array) {
        int count = 0;
        for (int elemento : array) {
            if (elemento == 1) {
                count++;
            }
        }

        int[] posicoesDosUns = new int[count];
        int index = 0;

        for (int i = 0; i < array.length; i++) {
            if (array[i] == 1) {
                posicoesDosUns[index] = i;
                index++;
            }
        }

        return posicoesDosUns;
    }


    public int scanner(){
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    public void enviaMsg(String destino,String conteudo, String key1, String value1, String key2, String value2){
        ACLMessage sendMsg = new ACLMessage (ACLMessage.INFORM);
        sendMsg.addReceiver (new AID( destino,AID.ISLOCALNAME));
        sendMsg.setContent (conteudo);
        sendMsg.addUserDefinedParameter(key1, value1);
        sendMsg.addUserDefinedParameter(key2, value2);
        send (sendMsg);
    }

}







