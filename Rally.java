import java.util.*;

public class Rally{
    private static Map<String, User> users = new HashMap<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Bem-vindo ao Rally! Escolha uma opção:");
            System.out.println("1. Registrar");
            System.out.println("2. Login");
            System.out.println("0. Sair");

            int opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    registrarUsuario(scanner);
                    break;
                case 2:
                    realizarLogin(scanner);
                    break;
                case 0:
                    System.out.println("Obrigado por usar o Rally. Até mais!");
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }
        }
    }

    private static void registrarUsuario(Scanner scanner) {
        System.out.println("----- Registro -----");

        System.out.print("Email: ");
        String email = scanner.next();

        if (users.containsKey(email)) {
            System.out.println("Usuário já registrado com esse email. Tente fazer login.");
            return;
        }

        System.out.print("Senha: ");
        String senha = scanner.next();

        System.out.print("Idade: ");
        int idade = scanner.nextInt();

        System.out.print("Altura: ");
        double altura = scanner.nextDouble();

        System.out.print("Peso: ");
        double peso = scanner.nextDouble();

        User user = new User(email, senha, idade, altura, peso);
        users.put(email, user);

        System.out.println("Registro realizado com sucesso!");
    }

    private static void realizarLogin(Scanner scanner) {
        System.out.println("----- Login -----");

        System.out.print("Email: ");
        String email = scanner.next();

        System.out.print("Senha: ");
        String senha = scanner.next();

        User user = users.get(email);

        if (user == null || !user.getSenha().equals(senha)) {
            System.out.println("Email ou senha incorretos. Tente novamente.");
            return;
        }

        System.out.println("Login realizado com sucesso!");

        System.out.println("Bem-vindo, " + user.getEmail() + "!");
        System.out.println("Idade: " + user.getIdade());
        System.out.println("Altura: " + user.getAltura());
        System.out.println("Peso: " + user.getPeso());

        System.out.print("Digite a meta de caminhada em minutos: ");
        int metaCaminhada = scanner.nextInt();

        user.setMetaCaminhada(metaCaminhada);

        System.out.println("Meta de caminhada atualizada para " + metaCaminhada + " minutos.");

        while (true) {
            System.out.println("\nEscolha uma opção:");
            System.out.println("1. Iniciar corrida");
            System.out.println("2. Finalizar corrida");
            System.out.println("3. Mostrar calorias gastas");
            System.out.println("4. Verificar meta de caminhada");
            System.out.println("5. Mostrar ranking de outros usuários");
            System.out.println("6. Atualizar localização GPS");
            System.out.println("0. Voltar");

            int opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    iniciarCorrida(user, scanner);
                    break;
                case 2:
                    finalizarCorrida(user, scanner);
                    break;
                case 3:
                    mostrarCaloriasGastas(user);
                    break;
                case 4:
                    verificarMetaCaminhada(user);
                    break;
                case 5:
                    mostrarRanking();
                    break;
                case 6:
                    atualizarLocalizacaoGPS(user, scanner);
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }
        }
    }

    private static void iniciarCorrida(User user, Scanner scanner) {
        if (user.isCorrendo()) {
            System.out.println("Você já está em uma corrida.");
            return;
        }

        System.out.println("Iniciando corrida...");

        System.out.print("Digite a distância em quilômetros: ");
        double distancia = scanner.nextDouble();

        user.iniciarCorrida(distancia);

        System.out.println("Corrida iniciada! Boa sorte!");

        // Simulação de tempo decorrido durante a corrida
        try {
            Thread.sleep(5000);  // 5 segundos (apenas para ilustração)
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        user.finalizarCorrida();
    }

    private static void finalizarCorrida(User user, Scanner scanner) {
        if (!user.isCorrendo()) {
            System.out.println("Você não está em uma corrida.");
            return;
        }

        System.out.println("Finalizando corrida...");

        System.out.print("Digite o tempo decorrido em minutos: ");
        int tempoDecorrido = scanner.nextInt();

        user.finalizarCorrida(tempoDecorrido);

        System.out.println("Corrida finalizada! Parabéns!");

        mostrarCaloriasGastas(user);
    }

    private static void mostrarCaloriasGastas(User user) {
        double calorias = user.getCaloriasGastas();

        System.out.println("Calorias gastas: " + calorias + " cal");
    }

    private static void verificarMetaCaminhada(User user) {
        int metaCaminhada = user.getMetaCaminhada();
        int passos = user.getPassosRealizados();

        if (passos >= metaCaminhada) {
            System.out.println("Parabéns! Você atingiu a meta de caminhada!");
        } else {
            System.out.println("Faltam " + (metaCaminhada - passos) + " passos para atingir a meta de caminhada.");
        }
    }

    private static void mostrarRanking() {
        List<User> sortedUsers = new ArrayList<>(users.values());
        Collections.sort(sortedUsers, Comparator.comparingDouble(User::getDistanciaPercorrida).reversed());

        System.out.println("\nRanking de usuários:");

        for (int i = 0; i < sortedUsers.size(); i++) {
            User user = sortedUsers.get(i);
            System.out.println((i + 1) + ". " + user.getEmail() + " - " + user.getDistanciaPercorrida() + " km");
        }
    }

    private static void atualizarLocalizacaoGPS(User user, Scanner scanner) {
        if (!user.isCorrendo()) {
            System.out.println("Você não está em uma corrida. Inicie uma corrida para atualizar a localização GPS.");
            return;
        }

        System.out.print("Digite a latitude: ");
        double latitude = scanner.nextDouble();

        System.out.print("Digite a longitude: ");
        double longitude = scanner.nextDouble();

        user.atualizarLocalizacaoGPS(latitude, longitude);

        System.out.println("Localização GPS atualizada!");
    }
}

class User {
    private String email;
    private String senha;
    private int idade;
    private double altura;
    private double peso;
    private int metaCaminhada;
    private boolean correndo;
    private double distanciaPercorrida;
    private int passosRealizados;
    private double caloriasGastas;
    private double latitude;
    private double longitude;

    public User(String email, String senha, int idade, double altura, double peso) {
        this.email = email;
        this.senha = senha;
        this.idade = idade;
        this.altura = altura;
        this.peso = peso;
        this.correndo = false;
        this.distanciaPercorrida = 0.0;
        this.passosRealizados = 0;
        this.caloriasGastas = 0.0;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public int getIdade() {
        return idade;
    }

    public double getAltura() {
        return altura;
    }

    public double getPeso() {
        return peso;
    }

    public int getMetaCaminhada() {
        return metaCaminhada;
    }

    public void setMetaCaminhada(int metaCaminhada) {
        this.metaCaminhada = metaCaminhada;
    }

    public boolean isCorrendo() {
        return correndo;
    }

    public double getDistanciaPercorrida() {
        return distanciaPercorrida;
    }

    public int getPassosRealizados() {
        return passosRealizados;
    }

    public double getCaloriasGastas() {
        return caloriasGastas;
    }

    public void iniciarCorrida(double distancia) {
        correndo = true;
        distanciaPercorrida = 0.0;
        passosRealizados = 0;
        caloriasGastas = 0.0;

        System.out.println("Corrida iniciada! Boa sorte!");
    }

    public void finalizarCorrida() {
        correndo = false;

        System.out.println("Corrida finalizada! Parabéns!");

        mostrarCaloriasGastas();
    }

    public void finalizarCorrida(int tempoDecorrido) {
        correndo = false;

        calcularCaloriasGastas(tempoDecorrido);

        System.out.println("Corrida finalizada! Parabéns!");

        mostrarCaloriasGastas();
    }

    private void calcularCaloriasGastas(int tempoDecorrido) {
        // Fórmula de cálculo de calorias gastas durante uma corrida (exemplo simplificado)
        double caloriasPorMinuto = 5.0;  // Valor hipotético
        caloriasGastas = caloriasPorMinuto * tempoDecorrido;
    }

    public void atualizarLocalizacaoGPS(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    private void mostrarCaloriasGastas() {
        System.out.println("Calorias gastas: " + caloriasGastas + " cal");
    }
}