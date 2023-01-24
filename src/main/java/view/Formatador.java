package view;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

// Classe que lida com a formatação da exibição de certos tipos de dado como Strings
public class Formatador {
    private final DateTimeFormatter formatData;
    private final DecimalFormat formatDecimal;
    public Formatador(DateTimeFormatter formatData, DecimalFormat formatDecimal) {
        this.formatData = formatData;
        this.formatDecimal = formatDecimal;
    }
    public Formatador() {
        this.formatDecimal = new DecimalFormat("#,##0.00");;
        this.formatData = DateTimeFormatter.ofPattern("dd/MM/yyyy");;
    }

    // Exibe os dados com um espaçamento padrão entre um dado e outro
    // Usada para permitir a exibição alinhada dos dados em várias linhas
    static String criarStringEspacada(int espaco, Object... campos) {
        StringBuilder resultado = new StringBuilder();
        for (Object campo : campos) {
            // Insere o dados na string, com os espaços à direita do dado
            resultado.append(String.format("%-" + espaco + "s", campo.toString()));

            // Acrescenta um espaço a mais para caso o dado consuma todo o espaço disponível
            resultado.append(" ");
        }
        return resultado.toString();
    }
    public static String criarStringEspacada(Object ... campos) {
        return criarStringEspacada(20, campos);
    }

    public String formatarData (LocalDate data){
        return data.format(formatData);
    }
    public String formatarDecimal(BigDecimal salario) {
        return formatDecimal.format(salario);
    }
}
