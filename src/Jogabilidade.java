import java.util.Random;

public class Jogabilidade {

    public static int D20() {
        Random random = new Random();
        return random.nextInt(20);
    }

    public static int D6() {
        Random random = new Random();
        return random.nextInt(6);
    }

    public static int calculaDano(int energia) {
        float multiplicador = energia / 100 + 1;
        float calculoDano = multiplicador * D20();
        int danoCausado = Math.round(calculoDano);
        return danoCausado;

    }

    public static int calculaDefesa(int defesa) {
        float multiplicador = defesa / 100 + 1;
        float calculoDef = multiplicador * D6();
        int def = Math.round(calculoDef);
        return def;
    }

    public static void recebeAtaque(int vidaAtual, int energia, int defesa, String ataque) {
        int def = calculaDefesa(defesa);
        int dano = calculaDano(energia);

        if (def >= dano) {
            vidaAtual = vidaAtual + 1;
        } else {
            int danoCausado = dano - def;
            vidaAtual = vidaAtual - danoCausado;

            System.out.println("Dano Tomado de " + ataque + " : " + danoCausado);
            System.out.println("Vida Atual: " + vidaAtual);
        }
    }
}