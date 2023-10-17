import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.core.AID;
import java.util.Random;

public class Jogabilidade extends Agent {
    public String player;
    public int vida;
    public int dano;
    protected int energia;
    protected int defesa;


    public void setPlayer(String player) {
        this.player = player;
    }

    public int D20() {
        Random random = new Random();
        return random.nextInt(20);
    }

    public int D6() {
        Random random = new Random();
        return random.nextInt(6);
    }

    public int calculaDano(int energia) {
        float multiplicador = energia / 100 + 1;
        float calculoDano = multiplicador * D20();
        int danoCausado = Math.round(calculoDano);
        return danoCausado;

    }

    public int calculaDefesa(int defesa) {
        float multiplicador = defesa / 100 + 1;
        float calculoDef = multiplicador * D6();
        int def = Math.round(calculoDef);
        return def;
    }

    public void recebeAtaque() {
        int def = calculaDefesa(this.defesa);
        int dano = calculaDano(this.energia);

        if (def > dano) {
            vida = vida + 1;
        } else {
            int danoCausado = dano - def;
            vida = vida - danoCausado;
        }
    }
}

